package com.abdul.mazaaditask.domain.usecase

import com.abdul.mazaaditask.domain.model.Launch
import com.abdul.mazaaditask.domain.repository.LaunchRepository

/**
 * Use case for fetching paginated list of launches
 * 
 * Clean Architecture: Use cases contain business logic
 * This use case delegates to repository, keeping domain layer clean
 */
class GetLaunchesUseCase(
    private val repository: LaunchRepository
) {
    /**
     * Fetches launches with pagination support
     * @param limit Number of items to fetch
     * @param offset Starting position for pagination
     * @return Result containing list of launches or error
     */
    suspend operator fun invoke(limit: Int, offset: Int): Result<List<Launch>> {
        return repository.getLaunches(limit, offset)
    }
}

