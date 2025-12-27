package com.abdul.mazaaditask.presentation.launches.mvi

import com.abdul.mazaaditask.domain.model.Launch

data class LaunchesState(
    val launches: List<Launch> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentOffset: Int = 0
) {
    val isEmpty: Boolean
        get() = launches.isEmpty() && !isLoading && error == null
}

