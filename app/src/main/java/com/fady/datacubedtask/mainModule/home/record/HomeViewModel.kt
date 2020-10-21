package com.fady.datacubedtask.mainModule.home.record

import android.widget.SeekBar
import androidx.databinding.adapters.SeekBarBindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fady.datacubedtask.Constants.MINIMUM_RECORDING_DURATION
import com.fady.datacubedtask.Constants.VIDEO_TITLE_MIN_LENGTH

class HomeViewModel : ViewModel(),
    SeekBarBindingAdapter.OnProgressChanged {
    val seekBarLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val videoTitleErrorLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val stateLiveData: MutableLiveData<State> by lazy { MutableLiveData<State>() }
    var path: String? = null


    // to handle seek bar starts from 15 seconds
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        seekBarLiveData.value = (progress + MINIMUM_RECORDING_DURATION).toString()
    }


    fun onRecordClick(videoTitle: String) {
        if (checkTitleValidation(videoTitle)) {
            // TODO: 10/20/20 Add check to handle if TITLE_EXISTS will show alertDialog to proceed
            stateLiveData.value = State.START_RECORDING
        }
    }

    private fun checkTitleValidation(videoTitle: String): Boolean {
        if (videoTitle.isEmpty() || videoTitle.trim().length < VIDEO_TITLE_MIN_LENGTH) {
            stateLiveData.value =
                State.TITLE_NOT_VALID
            videoTitleErrorLiveData.value = true
            return false
        }
        return true
    }
}

enum class State { START_RECORDING, TITLE_EXISTS, TITLE_NOT_VALID }

