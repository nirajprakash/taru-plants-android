package com.taru.ui.pages.nav.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.taru.R
import com.taru.data.local.assets.entities.ModelCategory
import com.taru.databinding.NavHomeFragmentBinding
import com.taru.tools.livedata.LiveDataObserver
import com.taru.ui.base.FragmentBase
import com.taru.ui.pages.nav.home.category.HomeCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Niraj on 08-01-2023.
 */
@AndroidEntryPoint
class NavHomeFragment : FragmentBase(false) {
    private val mViewModel: NavHomeViewModel by viewModels()
    private lateinit var vBinding: NavHomeFragmentBinding

    private lateinit var mListAdapter: HomeCategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vBinding =
            NavHomeFragmentBinding.inflate(inflater, container, false).apply {
                bViewModel = mViewModel
            }
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vBinding.lifecycleOwner = this.viewLifecycleOwner

        mListAdapter = HomeCategoryAdapter {
                navigateToFiltered(it)
        }
        /*{
            Log.d("AdListFragment", "AdListAdapter: $it")
            navigateToDetail(it.key)
        }*/
        Log.d("AdListFragment", "onCraeted:")


        lifecycleScope.launchWhenCreated {
//            val layoutManager = GridLayoutManager(requireContext(), 2)

//            vBinding.recyclerViewAdList.layoutManager = layoutManager
            vBinding.recyclerview.adapter = mListAdapter


            /* layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                 override fun getSpanSize(position: Int): Int {
                     return mListAdapter.getItemViewType(position)
                 }
             }*/
            /*var list = mutableListOf(
                DepreModelCategory(0), DepreModelCategory(1), DepreModelCategory(2),
                DepreModelCategory(3), DepreModelCategory(4), DepreModelCategory(5),
                DepreModelCategory(6), DepreModelCategory(7), DepreModelCategory(8), DepreModelCategory(9)
            )


            mListAdapter.submitList(list)*/

            vBinding.homeWeatherCard.homeWeatherCardImage.load(R.drawable.pic_weather)
            vBinding.homeScannerCard.homeScannerCardImage1.load(R.drawable.pic_scan_1)
            vBinding.homeScannerCard.homeScannerCardImage2.load(R.drawable.pic_scan_2)
            vBinding.homeScannerCard.homeScannerCardImage3.load(R.drawable.pic_scan_3)

            mViewModel.initData()
//            throw RuntimeException("Test Crash")

            /*mViewModel.mListStateParcel?.let {

                Log.d("AdListAdapter", "listStae: ${it.toString()}")
//                layoutManager.onRestoreInstanceState(it)
//                mViewModel.saveListState(null)
            }*/

        }

    }

    private fun navigateToFiltered(category: ModelCategory) {
//        TODO("Not yet implemented")
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        mViewModel.mEventCategoryList.observe(viewLifecycleOwner, LiveDataObserver{
            mListAdapter.submitList(it)

        })
    }
}