package com.abdul.mazaaditask.data.repository

import com.abdul.mazaaditask.data.remote.LaunchRemoteDataSource
import com.abdul.mazaaditask.domain.model.Launch
import com.abdul.mazaaditask.domain.model.LaunchDetail
import com.abdul.mazaaditask.domain.repository.LaunchRepository
import javax.inject.Inject

class LaunchRepositoryImpl @Inject constructor(
    private val remoteDataSource: LaunchRemoteDataSource
) : LaunchRepository {

    override suspend fun getLaunches(limit: Int, offset: Int): Result<List<Launch>> {

        return remoteDataSource.getLaunches(limit, offset).mapCatching { data ->

            // 1) handle null root safely
            val launchesList = data
                ?.launches
                ?.launches
                .orEmpty()

            // 2) always RETURN a list -> important part
            launchesList.mapNotNull { launch ->

                launch?.let {
                    Launch(
                        id = it.id ?: "",
                        missionName = it.mission?.name ?: "",
                        rocketName = it.rocket?.name ?: "",
                        rocketType = it.rocket?.type ?: "",
                        rocketId = it.rocket?.id ?: "",
                        site = it.site ?: ""
                    )
                }
            }
        }
    }

    override suspend fun getLaunchDetail(id: String): Result<LaunchDetail> {

        return remoteDataSource.getLaunchDetail(id).mapCatching { data ->

            val launch = data?.launch
                ?: throw Exception("Launch not found")

            LaunchDetail(
                id = launch.id ?: "",
                missionName = launch.mission?.name ?: "",
                missionPatch = launch.mission?.missionPatch,
                rocketName = launch.rocket?.name ?: "",
                rocketType = launch.rocket?.type ?: "",
                rocketId = launch.rocket?.id ?: "",
                site = launch.site ?: ""
            )
        }
    }
}
