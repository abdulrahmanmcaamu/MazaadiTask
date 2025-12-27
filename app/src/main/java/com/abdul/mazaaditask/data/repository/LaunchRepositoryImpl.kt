package com.abdul.mazaaditask.data.repository

import com.abdul.mazaaditask.data.remote.LaunchRemoteDataSource
import com.abdul.mazaaditask.domain.model.Launch
import com.abdul.mazaaditask.domain.model.LaunchDetail
import com.abdul.mazaaditask.domain.repository.LaunchRepository
import javax.inject.Inject

/**
 * Repository implementation following Clean Architecture
 * 
 * Responsibilities:
 * - Fetches data from remote data source (GraphQL API)
 * - Maps API models to domain models
 * - Handles data transformation and null safety
 */
class LaunchRepositoryImpl @Inject constructor(
    private val remoteDataSource: LaunchRemoteDataSource
) : LaunchRepository {
    
    /**
     * Gets paginated list of launches
     * Maps GraphQL response to domain model
     */
    override suspend fun getLaunches(limit: Int, offset: Int): Result<List<Launch>> {
        return remoteDataSource.getLaunches(limit, offset).mapCatching { graphQLData ->
            // Transform GraphQL response to domain models
            graphQLData.launches.mapNotNull { graphQLLaunch ->
                // Skip null launches and map to domain model
                graphQLLaunch?.let { launch ->
                    Launch(
                        id = launch.id ?: "",
                        missionName = launch.mission?.name ?: "",
                        rocketName = launch.rocket?.name ?: "",
                        rocketType = launch.rocket?.type ?: "",
                        rocketId = launch.rocket?.id ?: "",
                        site = launch.site ?: ""
                    )
                }
            }
        }
    }
    
    /**
     * Gets detailed information about a specific launch
     * Throws exception if launch not found
     */
    override suspend fun getLaunchDetail(id: String): Result<LaunchDetail> {
        return remoteDataSource.getLaunchDetail(id).mapCatching { graphQLData ->
            val graphQLLaunch = graphQLData.launch
                ?: throw Exception("Launch with id $id not found")
            
            // Map GraphQL model to domain model
            LaunchDetail(
                id = graphQLLaunch.id ?: "",
                missionName = graphQLLaunch.mission?.name ?: "",
                missionPatch = graphQLLaunch.mission?.missionPatch, // Can be null
                rocketName = graphQLLaunch.rocket?.name ?: "",
                rocketType = graphQLLaunch.rocket?.type ?: "",
                rocketId = graphQLLaunch.rocket?.id ?: "",
                site = graphQLLaunch.site ?: ""
            )
        }
    }
}

