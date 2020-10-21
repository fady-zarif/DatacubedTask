package com.fady.datacubedtask.mainModule.home.savedRecording

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fady.datacubedtask.data.VideoModel
import com.fady.datacubedtask.databinding.ItemLayoutBinding

class SavedRecordingAdapter(private val interaction: (item: VideoModel, pos: Int) -> Unit) :
    ListAdapter<VideoModel, SavedRecordingAdapter.SavedRecordingViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<VideoModel>() {
        override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
            return oldItem.title == newItem.title
        }
    }

    class SavedRecordingViewHolder(private val itemLayoutBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemLayoutBinding.root) {

        fun onBind(videoModel: VideoModel) {
            itemLayoutBinding.tvTitle.text = videoModel.title
            itemLayoutBinding.tvDuration.text = videoModel.duration.toString()
            itemLayoutBinding.tvTime.text = videoModel.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecordingViewHolder {
        val itemView = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedRecordingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SavedRecordingViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnClickListener {
            interaction.invoke(getItem(position), position)
        }
    }
}

