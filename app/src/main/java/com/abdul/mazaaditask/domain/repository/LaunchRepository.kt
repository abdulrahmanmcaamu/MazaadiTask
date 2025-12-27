package com.abdul.mazaaditask.domain.repository

import com.abdul.mazaaditask.domain.model.Launch
import com.abdul.mazaaditask.domain.model.LaunchDetail
import kotlinx.coroutines.flow.Flow

interface LaunchRepository {
    suspend fun getLaunches(limit: Int, offset: Int): Result<List<Launch>>
    suspend fun getLaunchDetail(id: String): Result<LaunchDetail>
}

