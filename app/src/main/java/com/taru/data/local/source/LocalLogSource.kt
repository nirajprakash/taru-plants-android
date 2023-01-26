package com.taru.data.local.source

import com.taru.data.local.db.cached.CachedRemoteKeyEntity
import com.taru.data.local.db.location.LocationRoomDao
import com.taru.data.local.db.log.IdentifyLogRoomDao
import com.taru.data.local.db.log.IdentifyLogRoomEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Niraj on 26-01-2023.
 */
@Singleton
class LocalLogSource @Inject constructor(
    private var identifyLogRoomDao: IdentifyLogRoomDao
) {
    suspend fun insert(entity: IdentifyLogRoomEntity) = withContext(Dispatchers.IO) {
        return@withContext identifyLogRoomDao.insert(entity)
    }

    suspend fun getCountForDay() = withContext(Dispatchers.IO){

        var calender = Calendar.getInstance()
        calender.add(Calendar.DAY_OF_YEAR, -1);

        return@withContext identifyLogRoomDao.count((calender.timeInMillis/1000).toInt())
    }
}