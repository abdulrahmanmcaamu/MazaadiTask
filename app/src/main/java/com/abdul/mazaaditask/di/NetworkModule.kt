package com.abdul.mazaaditask.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing network dependencies
 * 
 * This module provides:
 * - Apollo Client for GraphQL API calls
 * - Configured with the SpaceX launches API endpoint
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    /**
     * Provides Apollo Client instance for GraphQL queries
     * Singleton ensures single instance throughout app lifecycle
     */
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .build()
    }
}

