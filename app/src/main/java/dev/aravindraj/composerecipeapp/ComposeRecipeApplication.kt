package dev.aravindraj.composerecipeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComposeRecipeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        init {
            System.loadLibrary("composerecipeapp")
        }
    }
}