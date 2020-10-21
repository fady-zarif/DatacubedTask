package com.fady.datacubedtask.data

import androidx.lifecycle.LiveData

class VideoRepository(private val videoDao: VideoDao) {

    fun saveFile(videoModel: VideoModel, callback: () -> Unit) {
        videoDao.addVideo(videoModel)
        callback.invoke()
    }

    fun getVideos(): LiveData<List<VideoModel>> {
        return videoDao.getVideos()
    }

}