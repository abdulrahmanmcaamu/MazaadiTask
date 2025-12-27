package com.abdul.mazaaditask.presentation.launchdetail.mvi

import com.abdul.mazaaditask.domain.model.LaunchDetail

data class LaunchDetailState(
    val launchDetail: LaunchDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

