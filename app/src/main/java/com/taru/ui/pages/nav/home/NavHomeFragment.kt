package com.taru.ui.pages.nav.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.taru.R
import com.taru.databinding.NavHomeFragmentBinding
import com.taru.ui.base.FragmentBase
import com.taru.ui.pages.nav.home.category.HomeCategoryAdapter
import com.taru.ui.pages.nav.home.category.ModelCategory
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Niraj on 08-01-2023.
 */
@AndroidEntryPoint
class NavHomeFragment: FragmentBase(false) {
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

        mListAdapter = HomeCategoryAdapter()
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
            var list = mutableListOf(ModelCategory(0),ModelCategory(1),ModelCategory(2),
                ModelCategory(3),ModelCategory(4),ModelCategory(5),
                ModelCategory(6),ModelCategory(7),ModelCategory(8),ModelCategory(9))


                mListAdapter.submitList(list)
            vBinding.homeWeatherCard.homeWeatherCardImage.load(R.drawable.pic_weather)
//            throw RuntimeException("Test Crash")

            /*mViewModel.mListStateParcel?.let {

                Log.d("AdListAdapter", "listStae: ${it.toString()}")
//                layoutManager.onRestoreInstanceState(it)
//                mViewModel.saveListState(null)
            }*/

        }

    }
}