package com.abdul.mazaaditask.di

import com.abdul.mazaaditask.data.repository.LaunchRepositoryImpl
import com.abdul.mazaaditask.domain.repository.LaunchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for binding repository implementations
 * 
 * Uses @Binds for interface-to-implementation binding
 * This is more efficient than @Provides when you have an interface
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    /**
     * Binds LaunchRepositoryImpl to LaunchRepository interface
     * Singleton ensures single instance throughout app lifecycle
     */
    @Binds
    @Singleton
    abstract fun bindLaunchRepository(
        launchRepositoryImpl: LaunchRepositoryImpl
    ): LaunchRepository
}

