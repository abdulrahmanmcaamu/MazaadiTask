package com.abdul.mazaaditask.data.remote

import com.abdul.mazaaditask.GetLaunchDetailQuery
import com.abdul.mazaaditask.GetLaunchesQuery
import com.apollographql.apollo3.ApolloClient
import javax.inject.Inject

/**
 * Remote data source for fetching launch data from GraphQL API
 *
 * This layer is responsible for:
 * - Making GraphQL queries using Apollo Client
 * - Handling network errors
 * - Returning Result type for error handling
 */
class LaunchRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {

    /**
     * Fetches paginated list of launches from GraphQL API
     * @param limit Number of items to fetch
     * @param offset Starting position for pagination
     * @return Result containing GraphQL response data or error
     */
    suspend fun getLaunches(limit: Int, offset: Int): Result<GetLaunchesQuery.Data?> {
        return try {
            // Execute GraphQL query with pagination parameters
            val response = apolloClient.query(
                GetLaunchesQuery()
            ).execute()

            // Check if data is present in response
            if (response.data != null) {
                Result.success(response.data)
            } else {
                // GraphQL query succeeded but no data returned
                Result.failure(Exception("No launches data received from server"))
            }
        } catch (e: Exception) {
            // Network or parsing error
            Result.failure(e)
        }
    }

    /**
     * Fetches detailed information about a specific launch
     * @param id Launch ID to fetch
     * @return Result containing launch detail data or error
     */
    suspend fun getLaunchDetail(id: String): Result<GetLaunchDetailQuery.Data?>
    {
        return try {
            // Execute GraphQL query with launch ID
            val response = apolloClient.query(
                GetLaunchDetailQuery(id = id)

            ).execute()

            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("No launch detail data received from server"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

