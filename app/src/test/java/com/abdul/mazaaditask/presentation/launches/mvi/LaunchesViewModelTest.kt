package com.abdul.mazaaditask.presentation.launches.mvi

import app.cash.turbine.test
import com.abdul.mazaaditask.domain.model.Launch
import com.abdul.mazaaditask.domain.usecase.GetLaunchesUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LaunchesViewModelTest {
    
    private lateinit var getLaunchesUseCase: GetLaunchesUseCase
    private lateinit var viewModel: LaunchesViewModel
    
    @Before
    fun setup() {
        getLaunchesUseCase = mock()
        viewModel = LaunchesViewModel(getLaunchesUseCase)
    }
    
    @Test
    fun `initial state is correct`() = runTest {
        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(false, initialState.isLoading)
            assertEquals(emptyList<Launch>(), initialState.launches)
            assertEquals(null, initialState.error)
        }
    }
    
    @Test
    fun `load launches success`() = runTest {
        val launches = listOf(
            Launch("1", "Mission 1", "Rocket 1", "Type 1", "rocket1", "Site 1"),
            Launch("2", "Mission 2", "Rocket 2", "Type 2", "rocket2", "Site 2")
        )
        
        whenever(getLaunchesUseCase(20, 0)).thenReturn(Result.success(launches))
        
        viewModel.processIntent(LaunchesIntent.LoadLaunches)
        
        viewModel.state.test {
            skipItems(1) // Skip initial state
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)
            
            val successState = awaitItem()
            assertEquals(false, successState.isLoading)
            assertEquals(launches, successState.launches)
            assertEquals(null, successState.error)
        }
    }
    
    @Test
    fun `load launches error`() = runTest {
        val error = Exception("Network error")
        whenever(getLaunchesUseCase(20, 0)).thenReturn(Result.failure(error))
        
        viewModel.processIntent(LaunchesIntent.LoadLaunches)
        
        viewModel.state.test {
            skipItems(1) // Skip initial state
            skipItems(1) // Skip loading state
            val errorState = awaitItem()
            assertEquals(false, errorState.isLoading)
            assertEquals("Network error", errorState.error)
        }
    }
}

