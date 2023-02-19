package com.taru.ui.pages.scan.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.ChipGroup
import com.taru.databinding.ScanResultChipBinding
import com.taru.databinding.ScanResultFragmentBinding
import com.taru.ui.base.FragmentBase
import com.taru.ui.pages.nav.home.NavHomeFragmentDirections
import com.taru.ui.pages.scan.ScanViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Niraj on 28-01-2023.
 */
@AndroidEntryPoint
class ScanResultFragment: FragmentBase(true) {


    private val args: ScanResultFragmentArgs by navArgs()
    private val mViewModel: ScanResultViewModel by viewModels()
    private lateinit var vBinding: ScanResultFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            ScanResultFragmentBinding.inflate(inflater, container, false).apply {
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
            mViewModel.initArgs(args)
        }
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()

        mViewModel.bKeywords.observe(viewLifecycleOwner) {
            addChips(vBinding.scanResultFragmentKeywords, it)
        }
        //  vBinding.weatherImage.load(R.drawable.pic_weather)
    }

    private fun addChips(chipGroup: ChipGroup, keywords: List<String>){

        // TODO find if tag exist in chipgroup
        // TODO add remaining tags in chipgroup
        // TODO remove tags not in the list
        /*if(chip.tag is String && it.name == chip.tag){
//                    found
        }*/
        chipGroup.removeAllViews()
        keywords.forEach {

            val chip = ScanResultChipBinding.inflate(
                LayoutInflater.from(requireContext()),
                chipGroup,
                false).root

            chip.text = it
            chip.tag = it
            chip.setOnClickListener { v -> navigateToSearch(v.tag as String) }
            chipGroup.addView(chip)
        }
    }

    private fun navigateToSearch(q: String){
        findNavController().navigate(ScanResultFragmentDirections.navigateToSearch(q))
    }


}