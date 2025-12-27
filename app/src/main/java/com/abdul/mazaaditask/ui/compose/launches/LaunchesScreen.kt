package com.abdul.mazaaditask.ui.compose.launches

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdul.mazaaditask.presentation.launches.mvi.LaunchesIntent
import com.abdul.mazaaditask.presentation.launches.mvi.LaunchesViewModel

/**
 * Main screen displaying list of rocket launches
 * 
 * Features:
 * - Displays launches in a scrollable list
 * - Automatic pagination when scrolling near end
 * - Error handling with retry option
 * - Loading states for initial load and pagination
 */
@Composable
fun LaunchesScreen(
    onLaunchClick: (String) -> Unit,
    viewModel: LaunchesViewModel = hiltViewModel()
) {
    // Observe state changes from ViewModel
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    
    // Handle pagination: Load more when user scrolls near the end
    LaunchedEffect(listState) {
        snapshotFlow { 
            // Get index of last visible item
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index 
        }.collect { lastVisibleIndex ->
            // Trigger load more when 3 items away from end
            if (lastVisibleIndex != null && 
                lastVisibleIndex >= state.launches.size - 3 && 
                state.hasMore && 
                !state.isLoadingMore) {
                viewModel.processIntent(LaunchesIntent.LoadMore)
            }
        }
    }
    
    // Load initial data when screen is first displayed
    LaunchedEffect(Unit) {
        if (state.launches.isEmpty() && !state.isLoading) {
            viewModel.processIntent(LaunchesIntent.LoadLaunches)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Launches",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        when {
            state.isLoading && state.launches.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            state.error != null -> {
                ErrorMessage(
                    message = state.error,
                    onDismiss = { viewModel.processIntent(LaunchesIntent.ClearError) },
                    onRetry = { viewModel.processIntent(LaunchesIntent.LoadLaunches) }
                )
            }
            
            state.isEmpty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No launches available")
                }
            }
            
            else -> {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.launches) { launch ->
                        LaunchItem(
                            launch = launch,
                            onClick = { onLaunchClick(launch.id) }
                        )
                    }
                    
                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Individual launch item in the list
 * Displays rocket name and mission name
 */
@Composable
fun LaunchItem(
    launch: com.abdul.mazaaditask.domain.model.Launch,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rocket icon placeholder
            // In a real app, you could load actual rocket images here
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
            
            // Launch information
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = launch.rocketName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = launch.missionName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ErrorMessage(
    message: String,
    onDismiss: () -> Unit,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Error",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                TextButton(onClick = onDismiss) {
                    Text("Dismiss")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onRetry) {
                    Text("Retry")
                }
            }
        }
    }
}

