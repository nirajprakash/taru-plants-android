package com.taru.ui.pages.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taru.data.local.db.plant.PlantRecentSearchEntity
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.domain.plant.usecase.GetPlantsByQueryUseCase
import com.taru.domain.plant.usecase.PlantRecentSearchUseCase
import com.taru.ui.base.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Niraj on 14-01-2023.
 */

// https://github.com/ersiver/Newsster/blob/master/app/src/main/java/com/ersiver/newsster/ui/list/ArticleListViewModel.kt
// https://proandroiddev.com/google-news-clone-in-kotlin-using-paging-3-and-hilt-2127d19fe09d
@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val plantsByQueryUseCase: GetPlantsByQueryUseCase,
    private val recentPlantByQueryUseCase: PlantRecentSearchUseCase
): ViewModelBase() {

    var mQuery : String = ""
    fun setQ(q: String){
        mQuery = q
    }

    fun loadSearch(): Flow<PagingData<PlantSearchEntryEntity>> {
        var result = plantsByQueryUseCase.invoke(mQuery)
        return result.cachedIn(viewModelScope)
       /* if(result is DomainResult.Success){
           var pagingData =
            return pagingData

        }else {

        }
        return  null*/


    }

    fun loadRecentSearch(q:String?): Flow<PagingData<PlantRecentSearchEntity>> {
        Log.d("SearchViewModel", "loadRecentSearch: ${q?.length} ${q}")
        var result = recentPlantByQueryUseCase.invoke(q)
        return result.cachedIn(viewModelScope)
    }

}