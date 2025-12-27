package com.abdul.mazaaditask.di

import com.abdul.mazaaditask.domain.repository.LaunchRepository
import com.abdul.mazaaditask.domain.usecase.GetLaunchDetailUseCase
import com.abdul.mazaaditask.domain.usecase.GetLaunchesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing use cases
 * 
 * Use cases are created here and injected into ViewModels
 * Each use case depends on repository interface
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    
    /**
     * Provides GetLaunchesUseCase instance
     * Hilt automatically injects LaunchRepository dependency
     */
    @Provides
    @Singleton
    fun provideGetLaunchesUseCase(
        repository: LaunchRepository
    ): GetLaunchesUseCase {
        return GetLaunchesUseCase(repository)
    }
    
    /**
     * Provides GetLaunchDetailUseCase instance
     * Hilt automatically injects LaunchRepository dependency
     */
    @Provides
    @Singleton
    fun provideGetLaunchDetailUseCase(
        repository: LaunchRepository
    ): GetLaunchDetailUseCase {
        return GetLaunchDetailUseCase(repository)
    }
}

