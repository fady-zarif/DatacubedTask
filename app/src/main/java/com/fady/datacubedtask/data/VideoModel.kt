package com.fady.datacubedtask.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "video_table")
class VideoModel(
    @PrimaryKey
    val title: String,
    val duration: Int,
    val time: String
) : Parcelable