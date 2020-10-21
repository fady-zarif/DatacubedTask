package com.fady.datacubedtask.mainModule.home.savedRecording

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.fady.datacubedtask.Constants.GRID_SPAN_COUNT
import com.fady.datacubedtask.databinding.FragmentSavedRecordingBinding

import org.koin.android.viewmodel.ext.android.viewModel

class SavedRecordingFragment : Fragment() {

    private val savedRecordingViewModel: SavedRecordingViewModel by viewModel()
    private lateinit var binding: FragmentSavedRecordingBinding
    private lateinit var adapter: SavedRecordingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedRecordingBinding.inflate(layoutInflater, container, false)
        initRecycler()
        savedRecordingViewModel.getVideosList()
        savedRecordingViewModel.videosListLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                binding.tvDefaultMessage.visibility = View.GONE
                binding.rvVideos.visibility = View.VISIBLE
                adapter.submitList(it)
            }
        })
        return binding.root
    }

    private fun initRecycler() {
        binding.rvVideos.layoutManager = GridLayoutManager(activity, GRID_SPAN_COUNT)
        adapter = SavedRecordingAdapter { item, _ ->
            Toast.makeText(activity, item.title, Toast.LENGTH_SHORT).show()
        }
        binding.rvVideos.adapter = adapter
    }
}