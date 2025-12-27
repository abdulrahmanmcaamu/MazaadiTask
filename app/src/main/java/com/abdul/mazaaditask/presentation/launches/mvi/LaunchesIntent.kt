package com.abdul.mazaaditask.presentation.launches.mvi

sealed class LaunchesIntent {
    object LoadLaunches : LaunchesIntent()
    object LoadMore : LaunchesIntent()
    object ClearError : LaunchesIntent()
}

