package com.taru.ui.labs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.taru.databinding.LabsMainFragmentBinding
import com.taru.tools.livedata.LiveDataObserver
import com.taru.ui.base.FragmentBase
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Niraj on 20-11-2022.
 */
@AndroidEntryPoint
class LabsFragmentMain : FragmentBase(true) {


    private val mViewModel: LabsMainViewModel by viewModels()
    private lateinit var vBinding: LabsMainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            LabsMainFragmentBinding.inflate(inflater, container, false).apply {
                viewModel = mViewModel
                lifecycleOwner = this@LabsFragmentMain.viewLifecycleOwner
            }
        return vBinding.root

//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setupViewModelObservers() {
//        super.setupViewModelObservers()

        mViewModel.mEventNavigate.observe(viewLifecycleOwner, LiveDataObserver {
            navigateTo(it)
        })
    }

    private fun navigateTo(it: Int) {
/*        //val action = LabsFragmentMain.actionNavigationHomeToShowAllFragment(movieListType)
        fi//ndNavController().navigate(action)*/

        Log.i("LabsFragmentMain", "navigateTo $it")

        when (it) {
            LabsConstants.Navigation.SAMPLE_1 -> {
                //val action = LabsFragmentMainDirections.navigateToLabsSample1()
                //findNavController().navigate(action)
            }
            LabsConstants.Navigation.THREE_D -> {
                //val action = LabsFragmentMainDirections.navigateToLabs3d()
                //findNavController().navigate(action)
            }
            LabsConstants.Navigation.THREE_D_SENSE -> {
                //val action = LabsFragmentMainDirections.navigateToLabs3dSense()
                //findNavController().navigate(action)
            }
            LabsConstants.Navigation.THREE_D_SENSE2 -> {
                //val action = LabsFragmentMainDirections.navigateToLabs3dSense2()
                //findNavController().navigate(action)
            }
            LabsConstants.Navigation.THREE_D_MODEL -> {
                //val action = LabsFragmentMainDirections.navigateToLabs3dModel()
                //findNavController().navigate(action)
            }
            LabsConstants.Navigation.DEFAULT -> {
                val action = LabsFragmentMainDirections.navigateGlobalToNavDefault()
                findNavController().navigate(action)
            }
            LabsConstants.Navigation.JSON -> {
                //val action = LabsFragmentMainDirections.navigateToLabsJson()
                //findNavController().navigate(action)
            }
            LabsConstants.Navigation.TEMPLATE -> {
                //val action = LabsFragmentMainDirections.navigateToLabsTemplate()
                //findNavController().navigate(action)
            }
            LabsConstants.Navigation.LAUNCHER -> {
                //val action = LabsFragmentMainDirections.navigateToLauncher()
                //findNavController().navigate(action)
            }
            LabsConstants.Navigation.AD_LIST -> {
                //val action = LabsFragmentMainDirections.navigateToAdListFragment()
                //findNavController().navigate(action)
            }
            LabsConstants.Navigation.INFO -> {
                //val action = LabsFragmentMainDirections.navigateToInfoFragment()
                //findNavController().navigate(action)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


//    onCreateV


}