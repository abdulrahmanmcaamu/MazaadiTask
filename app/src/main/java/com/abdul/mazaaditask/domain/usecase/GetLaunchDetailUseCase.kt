package com.abdul.mazaaditask.domain.usecase

import com.abdul.mazaaditask.domain.model.LaunchDetail
import com.abdul.mazaaditask.domain.repository.LaunchRepository

/**
 * Use case for fetching detailed information about a specific launch
 * 
 * Clean Architecture: Encapsulates business logic for getting launch details
 * This keeps ViewModels clean by delegating data operations to use cases
 */
class GetLaunchDetailUseCase(
    private val repository: LaunchRepository
) {
    /**
     * Fetches detailed information for a launch by ID
     * @param id Launch ID to fetch
     * @return Result containing launch details or error
     */
    suspend operator fun invoke(id: String): Result<LaunchDetail> {
        return repository.getLaunchDetail(id)
    }
}

