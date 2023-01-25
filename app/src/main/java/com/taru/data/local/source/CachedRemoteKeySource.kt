package com.taru.data.local.source

import com.taru.data.local.db.cached.CachedRemoteKeyDao
import com.taru.data.local.db.cached.CachedRemoteKeyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Niraj on 24-01-2023.
 */
@Singleton
class CachedRemoteKeySource @Inject constructor(

    private var cachedRemoteKeyDao: CachedRemoteKeyDao,
){

    suspend fun insert(entry: CachedRemoteKeyEntity) = withContext(Dispatchers.IO) {
        return@withContext cachedRemoteKeyDao.insert(entry)
    }

    suspend fun getKeyFirst(refType:Int, q:String) = withContext(Dispatchers.IO){
        return@withContext cachedRemoteKeyDao.getKeyFirst(refType, q)
    }

    suspend fun getKey(refId:Int,refType:Int, q:String) = withContext(Dispatchers.IO){
        return@withContext cachedRemoteKeyDao.getKey(refId, refType, q)
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO){
        return@withContext cachedRemoteKeyDao.deleteAll()
    }
}