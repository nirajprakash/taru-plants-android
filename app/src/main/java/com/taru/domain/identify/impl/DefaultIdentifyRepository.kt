package com.taru.domain.identify.impl

import android.net.Uri
import com.taru.data.base.local.LocalResult
import com.taru.data.base.remote.ApiResult
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.data.local.source.LocalLogSource
import com.taru.data.local.source.LocalPlantSource
import com.taru.data.remote.identify.RemoteIdentifySource
import com.taru.data.remote.plants.RemotePlantsSource
import com.taru.domain.base.result.DomainResult
import com.taru.domain.identify.model.ModelIdentified
import com.taru.domain.identify.repository.IdentifyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Niraj on 26-01-2023.
 */
class DefaultIdentifyRepository  @Inject constructor(

    private val remoteIdentifySource: RemoteIdentifySource,
    private val localLogSource: LocalLogSource
): IdentifyRepository {

    //    fun identify()
    override suspend fun identify(organ: String, uri: Uri): DomainResult<ModelIdentified> {
        var localresult = remoteIdentifySource.identify(organ, uri)
        if(localresult is ApiResult.Success){
            // TODO add into log
        }

        return DomainResult.Failure(Throwable("some"))
    }

    override suspend fun isAllowed(): DomainResult<Boolean> {
        var countResult = localLogSource.getCountForDay()

        // TODO add check here
        return DomainResult.Success(true)
    }

}