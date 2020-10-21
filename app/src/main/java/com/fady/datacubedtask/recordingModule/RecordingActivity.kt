package com.fady.datacubedtask.recordingModule

import android.os.Bundle
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.fady.datacubedtask.Constants.VIDEO_EXTENSION
import com.fady.datacubedtask.Constants.VIDEO_MODEL_EXTRA_KEY
import com.fady.datacubedtask.IntentUtils
import com.fady.datacubedtask.R
import com.fady.datacubedtask.databinding.ActivityRecordingBinding
import com.fady.datacubedtask.mainModule.home.MainActivity
import com.fady.datacubedtask.data.VideoModel
import org.koin.android.viewmodel.ext.android.viewModel

class RecordingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordingBinding
    private lateinit var holder: SurfaceHolder
    private val recordingViewModel: RecordingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recording)
        binding.lifecycleOwner = this
        binding.recordingViewModel = recordingViewModel
        recordingViewModel.stopRecordLiveData.observe(this, Observer {
            if (it) {
                IntentUtils.startActivity<Void>(this@RecordingActivity, MainActivity::class.java)
                finish()
            }
        })
        surfaceHolder(intent.getParcelableExtra(VIDEO_MODEL_EXTRA_KEY))
    }

    private fun surfaceHolder(videoModel: VideoModel) {
        holder = binding.surfaceView.holder
        holder.addCallback(recordingViewModel)
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        recordingViewModel.initRecorder(
            videoModel,
            this.applicationContext.getFileStreamPath(videoModel.title + VIDEO_EXTENSION).path
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }
}