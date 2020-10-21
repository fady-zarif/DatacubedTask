package com.fady.datacubedtask.mainModule.home.record

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fady.datacubedtask.Constants.DEFAULT_SEEK_BAR_VALUE
import com.fady.datacubedtask.Constants.MINIMUM_RECORDING_DURATION
import com.fady.datacubedtask.Constants.PERMISSION_REQUEST_CODE
import com.fady.datacubedtask.Constants.VIDEO_MODEL_EXTRA_KEY
import com.fady.datacubedtask.IntentUtils
import com.fady.datacubedtask.R
import com.fady.datacubedtask.data.VideoModel
import com.fady.datacubedtask.databinding.FragmentHomeBinding
import com.fady.datacubedtask.recordingModule.RecordingActivity
import java.util.*


class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
//    private lateinit var dialog: ConfirmVideoTitleDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.homeVieModel = homeViewModel
        binding.lifecycleOwner = this
        binding.sbDuration.progress =
            savedInstanceState?.getInt(PROGRESS_SEEK_BAR_KEY) ?: DEFAULT_SEEK_BAR_VALUE
        homeViewModel.path = activity?.applicationContext?.filesDir?.path
        homeViewModel.seekBarLiveData.value =
            (binding.sbDuration.progress + MINIMUM_RECORDING_DURATION).toString()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            if (askPermission())
                handleState(it)
        })
    }

    private fun handleState(state: State) {
        when (state) {
            State.START_RECORDING -> {
                IntentUtils.startActivity(
                    this.requireActivity(),
                    RecordingActivity::class.java,
                    IntentUtils.IntentExtra(
                        VIDEO_MODEL_EXTRA_KEY,
                        VideoModel(
                            title = binding.etVideoTitle.text.toString(),
                            duration = binding.tvDuration.text.toString().toInt(),
                            time = Date().toString()
                        )
                    )
                )
            }
            State.TITLE_EXISTS -> {
//                dialog.show() /* will show dialog to ask user if he wants to replace */
            }
            State.TITLE_NOT_VALID -> {
                binding.etVideoTitle.error = getString(R.string.video_title_error)
            }
        }
    }

    private fun askPermission(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE
            )
            false
        } else
            true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PROGRESS_SEEK_BAR_KEY, binding.sbDuration.progress)
    }

    companion object {
        const val PROGRESS_SEEK_BAR_KEY = "progressKey"
    }
}