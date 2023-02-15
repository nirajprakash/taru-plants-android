package com.taru.ui.pages.scan

import android.Manifest
import android.Manifest.permission.CAMERA
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.impl.LiveDataObservable
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import com.permissionx.guolindev.PermissionX
import com.taru.R
import com.taru.data.remote.identify.IdentifyConstants
import com.taru.databinding.NavHomeFragmentBinding
import com.taru.databinding.ScanFragmentBinding
import com.taru.tools.livedata.LiveDataObserver
import com.taru.ui.base.FragmentBase
import com.taru.ui.pages.nav.home.NavHomeViewModel
import com.taru.ui.pages.nav.home.category.HomeCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Niraj on 11-01-2023.
 */
@AndroidEntryPoint
class ScanFragment : FragmentBase(true) {

    private var mImageCapture: ImageCapture? = null
    private val mViewModel: ScanViewModel by viewModels()
    private lateinit var vBinding: ScanFragmentBinding


    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            ScanFragmentBinding.inflate(inflater, container, false).apply {
                bViewModel = mViewModel
            }
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vBinding.lifecycleOwner = this.viewLifecycleOwner
        vBinding.topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            findNavController().popBackStack()
        }
        lifecycleScope.launchWhenCreated {
            getPermission()
        }

       /* vBinding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_scan_action_image -> {
                    Log.d("ScanFragment", "onClickPickImage: ")
                    true
                }
                else -> false
            }
        }*/

    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()



        mViewModel.mEventButtonAllowed.observe(viewLifecycleOwner, LiveDataObserver {
            if (!it) {
                Snackbar.make(
                    requireContext(),
                    vBinding.coordinator,
                    "Quota limit Exceeded !! Try Next Day",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                /*
                Snackbar.make(requireContext(),
                    vBinding.coordinator,
                    "Try Next Day",
                    Snackbar.LENGTH_LONG).show()
                    */

            }

        })

        mViewModel.bIsButtonEnabled.observe(viewLifecycleOwner) {
//            vBinding.bottomAppBar.menu.findItem(R.id.menu_scan_action_image).isEnabled = it
        }

        mViewModel.mEventOnActionScan.observe(viewLifecycleOwner) {
            takePhoto()
        }

        mViewModel.bScanMessage.observe(viewLifecycleOwner) {

            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreviewAndCapture(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreviewAndCapture(cameraProvider: ProcessCameraProvider) {
        var preview: Preview = Preview.Builder()
            .build()

        var cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        preview.setSurfaceProvider(vBinding.previewView.surfaceProvider)
        mImageCapture = ImageCapture.Builder().build()
        try {
            // Unbind use cases before rebinding
            cameraProvider.unbindAll()

            var camera = cameraProvider.bindToLifecycle(
                this as LifecycleOwner,
                cameraSelector,
                preview,
                mImageCapture
            )


        } catch (exc: Exception) {
            Log.e("ScanFragment", "Use case binding failed", exc)
        }


//        camera.


    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = mImageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("ScanFragment", "Photo capture failed: ${exc.message}", exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
//                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    Log.d("ScanFragment", msg)
                    output.savedUri?.let {
                        mViewModel.setUri(it)
                        dialogPlantType()
                    }
                }
            }
        )
    }

    private fun dialogPlantType() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select Plant Part")
            .setMessage("Choose any one option")
            .setNeutralButton("Cancel") { dialog, which ->
                // Respond to neutral button press
                dialog.cancel()

            }
            .setNegativeButton("Leaf") { dialog, which ->
                mViewModel.identifyFor(IdentifyConstants.ORGAN_LEAF)
                dialog.cancel()

            }
            .setPositiveButton("Flower") { dialog, which ->
                mViewModel.identifyFor(IdentifyConstants.ORGAN_FLOWER)
                dialog.cancel()
            }
            .show()
    }

    /* **********************************************************************************
     *                                Get Permission
     */

    private fun getPermission() {
        PermissionX.init(requireActivity())
            .permissions(CAMERA)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "Core fundamental are based on these permissions",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                   /* Toast.makeText(
                        requireContext(),
                        "All permissions are granted",
                        Toast.LENGTH_LONG
                    ).show()*/
                    setupCamera()
                    mViewModel.enableButton()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}