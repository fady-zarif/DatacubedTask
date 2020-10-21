package com.fady.datacubedtask.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addVideo(videoModel: VideoModel)

    @Query("SELECT * FROM video_table")
    fun getVideos(): LiveData<List<VideoModel>>


}