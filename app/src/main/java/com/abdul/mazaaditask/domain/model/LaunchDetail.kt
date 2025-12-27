package com.abdul.mazaaditask.domain.model

data class LaunchDetail(
    val id: String,
    val missionName: String,
    val missionPatch: String?,
    val rocketName: String,
    val rocketType: String,
    val rocketId: String,
    val site: String
)

