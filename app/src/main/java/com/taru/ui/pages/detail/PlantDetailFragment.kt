package com.taru.ui.pages.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.taru.R
import com.taru.databinding.PlantDetailFragmentBinding
import com.taru.ui.base.FragmentBase
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Niraj on 13-01-2023.
 */
@AndroidEntryPoint
class PlantDetailFragment: FragmentBase(true) {
    private val mViewModel: PlantDetailViewModel by viewModels()
    private lateinit var vBinding: PlantDetailFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            PlantDetailFragmentBinding.inflate(inflater, container, false).apply {
                bViewModel = mViewModel
            }
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vBinding.lifecycleOwner = this.viewLifecycleOwner

        lifecycleScope.launchWhenCreated {
            vBinding.plantDetailImage.load(R.drawable.pic_scan_1)
        }
    }

}