package com.fady.datacubedtask.recordingModule

import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.CountDownTimer
import android.util.Log
import android.view.SurfaceHolder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fady.datacubedtask.Constants.ONE_SECOND_MILLIS
import com.fady.datacubedtask.data.VideoModel
import com.fady.datacubedtask.data.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RecordingViewModel(private val repository: VideoRepository) : ViewModel(),
    SurfaceHolder.Callback {
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var path: String
    private lateinit var videoModel: VideoModel

    val timerLiveData: MutableLiveData<Long> by lazy { MutableLiveData<Long>() }
    val stopRecordLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    private fun startRecording(time: Int) {
        countDownTimer = object :
            CountDownTimer(time * ONE_SECOND_MILLIS + ONE_SECOND_MILLIS, ONE_SECOND_MILLIS) {
            override fun onFinish() {
                saveVideo()
            }

            override fun onTick(millisUntilFinished: Long) {
                timerLiveData.value = millisUntilFinished / ONE_SECOND_MILLIS
            }
        }
        countDownTimer.start()

    }

    /* was not sure if you want to encode the file to be stored into the database,
    ust saving the title and when display will get the file by the title */
    fun saveVideo() {
        mediaRecorder.stop()
        mediaRecorder.release()
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveFile(videoModel) {
                stopRecordLiveData.postValue(true)
            }
        }
    }

    fun initRecorder(videoModel: VideoModel, path: String) {
        this.path = path
        this.videoModel = videoModel
        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH))
        mediaRecorder.setOutputFile(path)
        mediaRecorder.setMaxDuration((videoModel.duration * ONE_SECOND_MILLIS).toInt())
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        mediaRecorder.stop()
        mediaRecorder.release()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mediaRecorder.setPreviewDisplay(holder?.surface)
        mediaRecorder.prepare()
        mediaRecorder.start()

        startRecording(videoModel.duration)
    }

    override fun onCleared() {
        super.onCleared()
        try {
            mediaRecorder.stop()
            mediaRecorder.release()
        } catch (exception: Exception) {
            Log.e("exception: ", exception.localizedMessage)
        }
        countDownTimer.cancel()
    }
}