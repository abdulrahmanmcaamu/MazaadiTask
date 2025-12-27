package com.abdul.mazaaditask.presentation.launchdetail.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.mazaaditask.domain.usecase.GetLaunchDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Launch Detail screen following MVI pattern
 * 
 * Manages state for displaying detailed information about a single launch
 */
@HiltViewModel
class LaunchDetailViewModel @Inject constructor(
    private val getLaunchDetailUseCase: GetLaunchDetailUseCase
) : ViewModel() {
    
    // Mutable state flow - only ViewModel can modify
    private val _state = MutableStateFlow(LaunchDetailState())
    
    // Public immutable state flow - UI observes this
    val state: StateFlow<LaunchDetailState> = _state.asStateFlow()
    
    /**
     * Processes user intents and updates state accordingly
     */
    fun processIntent(intent: LaunchDetailIntent) {
        when (intent) {
            is LaunchDetailIntent.LoadLaunchDetail -> fetchLaunchDetails(intent.id)
            is LaunchDetailIntent.ClearError -> dismissError()
        }
    }
    
    /**
     * Fetches detailed information for a specific launch
     * @param id Launch ID to fetch details for
     */
    private fun fetchLaunchDetails(id: String) {
        viewModelScope.launch {
            // Show loading indicator
            _state.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    error = null
                )
            }
            
            // Fetch launch details from repository
            val result = getLaunchDetailUseCase(id)
            
            result.fold(
                onSuccess = { launchDetail ->
                    // Success: Update state with launch details
                    _state.update { currentState ->
                        currentState.copy(
                            launchDetail = launchDetail,
                            isLoading = false,
                            error = null
                        )
                    }
                },
                onFailure = { exception ->
                    // Error: Show error message
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = exception.message ?: "Unable to load launch details"
                        )
                    }
                }
            )
        }
    }
    
    /**
     * Clears error state when user dismisses error
     */
    private fun dismissError() {
        _state.update { it.copy(error = null) }
    }
}

