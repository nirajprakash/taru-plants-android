package com.taru.ui.pages.plants.filtered

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.taru.databinding.PlantsFilteredFragmentBinding
import com.taru.ui.base.FragmentBase
import com.taru.ui.pages.plants.common.PlantsPagingAdapter
import com.taru.ui.pages.plants.search.autocomplete.SearchAutoCompleteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Niraj on 05-02-2023.
 */
@AndroidEntryPoint
class PlantsFilteredFragment : FragmentBase(true){

    private val args: PlantsFilteredFragmentArgs by navArgs()

    private val mViewModel: PlantsFilteredViewModel by viewModels()
    private lateinit var vBinding: PlantsFilteredFragmentBinding

    private lateinit var mListAdapterAutoComplete: SearchAutoCompleteAdapter
    //    private lateinit var mListAdapter: SearchPlantsAdapter
    private lateinit var mPagingAdapter: PlantsPagingAdapter
    private var plantsJob: Job? = null
    private var recentJob: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            PlantsFilteredFragmentBinding.inflate(inflater, container, false).apply {
                bViewModel = mViewModel
            }
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vBinding.lifecycleOwner = this.viewLifecycleOwner

        mListAdapterAutoComplete = SearchAutoCompleteAdapter(){

        }
        /*  mListAdapter = SearchPlantsAdapter() {
                  modelPlant ->
          //navigateToDetail(modelPlant.id)
          }*/

        lifecycleScope.launchWhenCreated {
//            vBinding.weatherImage.load(R.drawable.pic_weather)

            /*   vBinding.searchViewRecyclerview.adapter = mListAdapterAutoComplete



               var list = mutableListOf(
                   ModelRecent(0), ModelRecent(1), ModelRecent(2),
                   ModelRecent(3), ModelRecent(4), ModelRecent(5),
                   ModelRecent(6), ModelRecent(7), ModelRecent(8), ModelRecent(9)
               )


               mListAdapterAutoComplete.submitList(list)*/
         mViewModel.initArgs(args)

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

    private  fun getPlantsSearchEntries(){
        plantsJob?.cancel()
        plantsJob = lifecycleScope.launch {
            mViewModel.loadSearch().collectLatest { mPagingAdapter.submitData(it) }
            Log.d("SearchFragment", "getPlantsSearchEntries: job")

        }
    }

    private fun navigateToDetail(plantId: Int) {
        findNavController().navigate(PlantsFilteredFragmentDirections.navigateToPlantDetail(plantId))
    }
}