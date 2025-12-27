package com.abdul.mazaaditask.presentation.launches.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.mazaaditask.domain.usecase.GetLaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel implementing MVI pattern for Launches screen
 * 
 * MVI Architecture:
 * - Intent: User actions (LoadLaunches, LoadMore, ClearError)
 * - State: UI state (launches list, loading, error)
 * - ViewModel: Processes intents and updates state
 */
@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : ViewModel() {
    
    // Private mutable state, exposed as immutable StateFlow
    private val _state = MutableStateFlow(LaunchesState())
    val state: StateFlow<LaunchesState> = _state.asStateFlow()
    
    private companion object {
        const val PAGE_SIZE = 20 // Number of items to load per page
    }
    
    /**
     * Main entry point for handling user intents
     * This is the core of MVI - all user actions come through here
     */
    fun processIntent(intent: LaunchesIntent) {
        when (intent) {
            is LaunchesIntent.LoadLaunches -> fetchInitialLaunches()
            is LaunchesIntent.LoadMore -> fetchMoreLaunches()
            is LaunchesIntent.ClearError -> dismissError()
        }
    }
    
    /**
     * Fetches the first page of launches
     * Resets offset to 0 and clears existing data
     */
    private fun fetchInitialLaunches() {
        viewModelScope.launch {
            // Set loading state and reset pagination
            _state.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    error = null,
                    currentOffset = 0
                )
            }
            
            // Call use case to get data from repository
            val result = getLaunchesUseCase(PAGE_SIZE, offset = 0)
            
            result.fold(
                onSuccess = { launches ->
                    // Success: Update state with new launches
                    _state.update { currentState ->
                        currentState.copy(
                            launches = launches,
                            isLoading = false,
                            error = null,
                            currentOffset = launches.size,
                            // If we got full page, there might be more data
                            hasMore = launches.size == PAGE_SIZE
                        )
                    }
                },
                onFailure = { exception ->
                    // Error: Show error message to user
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = exception.message ?: "Unable to load launches"
                        )
                    }
                }
            )
        }
    }
    
    /**
     * Loads next page of launches for pagination
     * Only loads if not already loading and more data is available
     */
    private fun fetchMoreLaunches() {
        val currentState = _state.value
        
        // Guard: Don't load if already loading or no more data
        if (currentState.isLoadingMore || !currentState.hasMore) {
            return
        }
        
        viewModelScope.launch {
            // Set loading more indicator
            _state.update { it.copy(isLoadingMore = true) }
            
            // Get current offset for pagination
            val offset = currentState.currentOffset
            val result = getLaunchesUseCase(PAGE_SIZE, offset)
            
            result.fold(
                onSuccess = { newLaunches ->
                    // Append new launches to existing list
                    _state.update { currentState ->
                        currentState.copy(
                            launches = currentState.launches + newLaunches,
                            isLoadingMore = false,
                            currentOffset = currentState.currentOffset + newLaunches.size,
                            hasMore = newLaunches.size == PAGE_SIZE
                        )
                    }
                },
                onFailure = { exception ->
                    // Show error but keep existing data
                    _state.update { currentState ->
                        currentState.copy(
                            isLoadingMore = false,
                            error = exception.message ?: "Failed to load more launches"
                        )
                    }
                }
            )
        }
    }
    
    /**
     * Clears the error state when user dismisses error
     */
    private fun dismissError() {
        _state.update { it.copy(error = null) }
    }
}

