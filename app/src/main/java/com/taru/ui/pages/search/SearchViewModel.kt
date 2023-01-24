package com.taru.ui.pages.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.domain.base.result.DomainResult
import com.taru.domain.plant.usecase.GetPlantsByQueryUseCase
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
    val plantsByQueryUseCase: GetPlantsByQueryUseCase
): ViewModelBase() {
    fun loadSearch(q:String): Flow<PagingData<PlantSearchEntryEntity>> {
        var result = plantsByQueryUseCase.invoke(q)
        return result.cachedIn(viewModelScope)
       /* if(result is DomainResult.Success){
           var pagingData =
            return pagingData

        }else {

        }
        return  null*/


    }

}