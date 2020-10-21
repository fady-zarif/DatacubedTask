package com.fady.datacubedtask.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoModel::class], version = 1, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}