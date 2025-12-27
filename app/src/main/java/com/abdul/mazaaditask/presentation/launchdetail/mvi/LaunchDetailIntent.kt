package com.abdul.mazaaditask.presentation.launchdetail.mvi

sealed class LaunchDetailIntent {
    data class LoadLaunchDetail(val id: String) : LaunchDetailIntent()
    object ClearError : LaunchDetailIntent()
}

