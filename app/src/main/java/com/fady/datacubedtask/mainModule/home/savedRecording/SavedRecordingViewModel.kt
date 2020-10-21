package com.fady.datacubedtask.mainModule.home.savedRecording

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fady.datacubedtask.data.VideoModel
import com.fady.datacubedtask.data.VideoRepository

class SavedRecordingViewModel(private val repository: VideoRepository) : ViewModel() {
    lateinit var videosListLiveData: LiveData<List<VideoModel>>
    fun getVideosList() {
        videosListLiveData = repository.getVideos()
    }
}