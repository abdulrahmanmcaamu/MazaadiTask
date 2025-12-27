package com.abdul.mazaaditask.presentation.launchdetail.mvi

import app.cash.turbine.test
import com.abdul.mazaaditask.domain.model.LaunchDetail
import com.abdul.mazaaditask.domain.usecase.GetLaunchDetailUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LaunchDetailViewModelTest {
    
    private lateinit var getLaunchDetailUseCase: GetLaunchDetailUseCase
    private lateinit var viewModel: LaunchDetailViewModel
    
    @Before
    fun setup() {
        getLaunchDetailUseCase = mock()
        viewModel = LaunchDetailViewModel(getLaunchDetailUseCase)
    }
    
    @Test
    fun `initial state is correct`() = runTest {
        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(false, initialState.isLoading)
            assertEquals(null, initialState.launchDetail)
            assertEquals(null, initialState.error)
        }
    }
    
    @Test
    fun `load launch detail success`() = runTest {
        val launchDetail = LaunchDetail(
            id = "1",
            missionName = "Mission 1",
            missionPatch = "https://example.com/patch.png",
            rocketName = "Rocket 1",
            rocketType = "Type 1",
            rocketId = "rocket1",
            site = "Site 1"
        )
        
        whenever(getLaunchDetailUseCase("1")).thenReturn(Result.success(launchDetail))
        
        viewModel.processIntent(LaunchDetailIntent.LoadLaunchDetail("1"))
        
        viewModel.state.test {
            skipItems(1) // Skip initial state
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)
            
            val successState = awaitItem()
            assertEquals(false, successState.isLoading)
            assertEquals(launchDetail, successState.launchDetail)
            assertEquals(null, successState.error)
        }
    }
    
    @Test
    fun `load launch detail error`() = runTest {
        val error = Exception("Not found")
        whenever(getLaunchDetailUseCase("1")).thenReturn(Result.failure(error))
        
        viewModel.processIntent(LaunchDetailIntent.LoadLaunchDetail("1"))
        
        viewModel.state.test {
            skipItems(1) // Skip initial state
            skipItems(1) // Skip loading state
            val errorState = awaitItem()
            assertEquals(false, errorState.isLoading)
            assertEquals("Not found", errorState.error)
        }
    }
}

