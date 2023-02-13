package com.taru.ui.pages.slider

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSnapHelper
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideCustomImageLoader
import com.taru.databinding.SliderFragmentBinding
import com.taru.domain.glide.CustomImageSizeModel
import com.taru.ui.base.FragmentBase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Created by Niraj on 13-02-2023.
 */
@AndroidEntryPoint
class SliderFragment : FragmentBase(true) {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private val args: SliderFragmentArgs by navArgs()
    private val mViewModel: SliderViewModel by viewModels()
    private lateinit var vBinding: SliderFragmentBinding

    private lateinit var mAdapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            SliderFragmentBinding.inflate(inflater, container, false).apply {
                bViewModel = mViewModel
            }
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vBinding.lifecycleOwner = this.viewLifecycleOwner
        BigImageViewer.initialize(
            GlideCustomImageLoader.with(
                appContext,
                CustomImageSizeModel::class.java
            )
        )
        lifecycleScope.launchWhenCreated {


            mAdapter = SliderAdapter()
            vBinding.recycler.adapter = mAdapter
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(vBinding.recycler)

            mViewModel.initArgs(args)


//            vBinding.plantDetailImage.load(R.drawable.pic_scan_1)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        BigImageViewer.imageLoader().cancelAll();
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()

        mViewModel.bImages.observe(viewLifecycleOwner){
            mAdapter.submitList(it)
        }
     /*   mViewModel.bKeywords.observe(viewLifecycleOwner) {
            addChips(vBinding.plantDetailSectionRegionChipGroup, it)
        }

        mViewModel.mEventOnActionGallery.observe(viewLifecycleOwner, LiveDataObserver{
            // open gallery

        })*/
    }
}