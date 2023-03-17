package com.taru.domain.identify.impl

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.taru.data.base.local.LocalResult
import com.taru.data.base.remote.ApiResult
import com.taru.data.local.db.log.IdentifyLogRoomEntity
import com.taru.data.local.db.plant.PlantSearchEntryEntity
import com.taru.data.local.source.LocalLogSource
import com.taru.data.local.source.LocalPlantSource
import com.taru.data.remote.identify.RemoteIdentifySource
import com.taru.data.remote.identify.toDomainModel
import com.taru.data.remote.plants.RemotePlantsSource
import com.taru.domain.base.result.DomainResult
import com.taru.domain.identify.IdentifyConstants
import com.taru.domain.identify.model.ModelIdentified
import com.taru.domain.identify.repository.IdentifyRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import me.shouheng.compress.Compress
import me.shouheng.compress.concrete
import java.io.File
import java.util.*
import javax.inject.Inject

/**
 * Created by Niraj on 26-01-2023.
 */
class DefaultIdentifyRepository  @Inject constructor(
    @ApplicationContext private val context: Context,
    private val remoteIdentifySource: RemoteIdentifySource,
    private val localLogSource: LocalLogSource
): IdentifyRepository {

    //    fun identify()
    override suspend fun identify(organ: String, uri: Uri): DomainResult<ModelIdentified> {

        val file  = compressFile(uri)

/*        val compressedImageFile = Compressor.compress(context, uri) {
            default(width = 640, format = Bitmap.CompressFormat.JPEG)
        }*/
        val apiResult = remoteIdentifySource.identify(organ, file)
        if(apiResult is ApiResult.Success){
            var calender = Calendar.getInstance()
//            calender.add(Calendar.DAY_OF_YEAR, -1);

            localLogSource.insert(IdentifyLogRoomEntity(dt=(calender.time.time/1000L).toInt()))
            // TODO add into log
//            Log.d("DefaultIdentityRepository", "identify: ${localresult.data}")
           return DomainResult.Success(apiResult.data.toDomainModel(uri))
        }else if(apiResult is ApiResult.Exception){
            apiResult.throwable.printStackTrace()
        }else if(apiResult is ApiResult.Message){

            return  DomainResult.Failure(Throwable("${apiResult.message}"))
        }

        return DomainResult.Failure(Throwable("some"))
    }

    override suspend fun isAllowed(): DomainResult<Boolean> {
        var countResult = localLogSource.getCountForDay()

        Log.d("DefaultIdentityRepository", "isAllowed: $countResult")
        if(countResult<= IdentifyConstants.ALLOWED_COUNT){
            return DomainResult.Success(true)
        }
        return DomainResult.Success(false)


    }

     suspend fun compressFile(uri: Uri): File {
        val file  = Compress.with(context, uri).concrete {
            withMaxHeight(500f)
            withMaxWidth(500f)
        }.get(Dispatchers.IO)

        return file
    }

}