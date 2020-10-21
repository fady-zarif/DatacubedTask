package com.fady.datacubedtask.di

import android.app.Application
import androidx.room.Room
import com.fady.datacubedtask.data.VideoDao
import com.fady.datacubedtask.data.VideoDatabase
import com.fady.datacubedtask.data.VideoRepository
import com.fady.datacubedtask.mainModule.home.savedRecording.SavedRecordingViewModel
import com.fady.datacubedtask.recordingModule.RecordingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val viewModule = module {
        single { VideoRepository(get()) }
        viewModel { RecordingViewModel(get()) }
        viewModel { SavedRecordingViewModel(get()) }
    }
    val databaseModule = module {
        fun provideDatabase(app: Application): VideoDatabase {
            return Room.databaseBuilder(app, VideoDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun provideUserDao(database: VideoDatabase): VideoDao = database.videoDao()

        single { provideDatabase(androidApplication()) }
        single { provideUserDao(get()) }

    }
}