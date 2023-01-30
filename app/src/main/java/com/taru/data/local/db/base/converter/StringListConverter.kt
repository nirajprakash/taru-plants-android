package com.taru.data.local.db.base.converter

import androidx.room.TypeConverter

/**
 * Created by Niraj on 30-01-2023.
 */
class StringListConverter {
    @TypeConverter
    fun fromString(stringListString: String?): List<String> {
        return stringListString?.split(",")?.map { it } ?: listOf()
    }

    @TypeConverter
    fun toString(stringList: List<String>): String? {
        if(stringList.isEmpty()){
            return  null
        }
        return stringList.joinToString(separator = ",")
    }
}