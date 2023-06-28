package com.taru.ui.pages.plants.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.taru.databinding.SearchFragmentBinding
import com.taru.ui.base.FragmentBase
import com.taru.ui.pages.plants.search.autocomplete.SearchAutoCompleteAdapter
import com.taru.ui.pages.plants.common.PlantsPagingAdapter
import com.taru.ui.pages.plants.search.recent.RecentPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Niraj on 14-01-2023.
 */
@AndroidEntryPoint
class SearchFragment : FragmentBase(true) {

    private val args: SearchFragmentArgs by navArgs()

    private var mBackCallback: OnBackPressedCallback? = null
    private val mViewModel: SearchViewModel by viewModels()
    private lateinit var vBinding: SearchFragmentBinding

    private lateinit var mListAdapterAutoComplete: SearchAutoCompleteAdapter

    //    private lateinit var mListAdapter: SearchPlantsAdapter
    private lateinit var mPagingAdapter: PlantsPagingAdapter
    private lateinit var mPagingAdapterRecent: RecentPagingAdapter
    private var plantsJob: Job? = null
    private var plantsLoadJob: Job? = null
    private var recentJob: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            SearchFragmentBinding.inflate(inflater, container, false).apply {
                bViewModel = mViewModel
            }
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vBinding.lifecycleOwner = this.viewLifecycleOwner

        mListAdapterAutoComplete = SearchAutoCompleteAdapter() {

        }
        /*  mListAdapter = SearchPlantsAdapter() {
                  modelPlant ->
          //navigateToDetail(modelPlant.id)
          }*/

        lifecycleScope.launch {
            withCreated {
//            vBinding.weatherImage.load(R.drawable.pic_weather)

                /*   vBinding.searchViewRecyclerview.adapter = mListAdapterAutoComplete



                   var list = mutableListOf(
                       ModelRecent(0), ModelRecent(1), ModelRecent(2),
                       ModelRecent(3), ModelRecent(4), ModelRecent(5),
                       ModelRecent(6), ModelRecent(7), ModelRecent(8), ModelRecent(9)
                   )


                   mListAdapterAutoComplete.submitList(list)*/
                mViewModel.initArgs(args)
                initPagingAdapterRecent()
                getRecentSearchEntries(null)

                initPagingAdapter()
                getPlantsSearchEntries()
//            getRecentSearchEntries(q)

                /*   vBinding.recyclerview.adapter = mListAdapter
                   var list2 = mutableListOf(
                       ModelPlant(0), ModelPlant(1), ModelPlant(2),
                       ModelPlant(3), ModelPlant(4), ModelPlant(5),
                       ModelPlant(6), ModelPlant(7), ModelPlant(8), ModelPlant(9)
                   )


                   mListAdapter.submitList(list2)*/
            }
        }

        vBinding.searchBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        vBinding.searchBar.setText(args.q)// =

        vBinding.searchView
            .getEditText()

            .setOnEditorActionListener { v, actionId, event ->
                vBinding.searchBar.setText(vBinding.searchView.getText())
                vBinding.searchView.hide()
                mViewModel.setQ(vBinding.searchView.text.toString())
                getPlantsSearchEntries()
//                mViewModel.loadSearch()
                false
            }
        vBinding.searchView
            .getEditText()
            .addTextChangedListener { text ->
                getRecentSearchEntries(text?.toString())
            }

        mBackCallback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (vBinding.searchView.isShowing && vBinding.searchBar.collapse(
                    vBinding.searchView,
                    vBinding.appBarLayout
                )
            ) {
                // Clear selection.
                this.remove()
                return@addCallback;
            } else {
                this.remove()
                requireActivity().onBackPressedDispatcher.onBackPressed()
//                this.
//                mBackCallback?.handleOnBackPressed()
            }
            Log.d("SearchFragment: ", "callback: ")
            /*else{
                Log.d("SearchFragment: ", "callback: ")
                this.handleOnBackPressed()
            }*/
            // Handle the back button event
        }

    }

    private fun initPagingAdapter() {
        mPagingAdapter = PlantsPagingAdapter {
            navigateToDetail(it.plantId)
        }
        vBinding.recyclerview.adapter = mPagingAdapter

        mPagingAdapter.addLoadStateListener { loadState ->
            Log.d("SearchFragment", "loadState:  ${loadState}")
            /* binding.articleList.isVisible = loadState.refresh is LoadState.NotLoading
             binding.progress.isVisible = loadState.refresh is LoadState.Loading
             manageErrors(loadState)*/
        }
    }

    private fun navigateToDetail(plantId: Int) {
        findNavController().navigate(SearchFragmentDirections.navigateToPlantDetail(plantId))
    }

    private fun initPagingAdapterRecent() {


        mPagingAdapterRecent = RecentPagingAdapter {
            mViewModel.setQ(q = it.q)
//            vBinding.searchView.setText(it.q)
            vBinding.searchBar.setText(it.q)// = it.q
            vBinding.searchView.hide()
            getPlantsSearchEntries()

        }
        vBinding.searchViewRecyclerview.adapter = mPagingAdapterRecent


        mPagingAdapterRecent.addLoadStateListener { loadState ->
            Log.d("SearchFragment", "loadState:  ${loadState}")
            /* binding.articleList.isVisible = loadState.refresh is LoadState.NotLoading
             binding.progress.isVisible = loadState.refresh is LoadState.Loading
             manageErrors(loadState)*/
        }
    }

    private fun getPlantsSearchEntries() {
        plantsJob?.cancel()

        plantsLoadJob?.cancel()
        plantsJob = lifecycleScope.launch {
            mViewModel.loadSearch().collectLatest {
                mPagingAdapter.submitData(it)
            }
            Log.d("SearchFragment", "getPlantsSearchEntries: job")

        }
        plantsLoadJob = lifecycleScope.launch {
            mPagingAdapter.loadStateFlow.collectLatest {
                if (it.refresh is LoadState.Loading) {
                    mViewModel.setProgress(true)
                } else {
                    mViewModel.setProgress(false)

                }
            }
            Log.d("SearchFragment", "getPlantsSearchEntries: job")

        }
    }

    private fun getRecentSearchEntries(q: String?) {
        recentJob?.cancel()
        recentJob = lifecycleScope.launch {

            mViewModel.loadRecentSearch(q).collectLatest {


                mPagingAdapterRecent.submitData(it)
            }
            Log.d("SearchFragment", "getPlantsSearchEntries: job")

        }

    }

}