package com.taru.data.remote.identify

import android.net.Uri
import com.taru.data.base.remote.handleApi
import com.taru.data.remote.plants.ApiPlants
import com.taru.tools.file.UriToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

/**
 * Created by Niraj on 26-01-2023.
 */
class RemoteIdentifySource @Inject constructor(
    var apiIdentify: ApiIdentify,
    val uriToFile: UriToFile
){
    suspend fun  identify(organ: String, file: File) = withContext(Dispatchers.IO) {
//        val file = uriToFile.getImageBody(uri)

        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())


        //RequestBody.create(MediaType.parse("text/plain"), organ))
        val map: MutableMap<String, RequestBody> = mutableMapOf()
        val organ = createPartFromString(organ)
        map.put("organs", organ)
        return@withContext handleApi { apiIdentify.identify(images = MultipartBody.Part.createFormData("images", file.name, requestFile),
            partMap = map) }


    }
    fun createPartFromString(stringData: String): RequestBody {
        return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}