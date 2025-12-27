package com.abdul.mazaaditask.ui.compose.launchdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.abdul.mazaaditask.presentation.launchdetail.mvi.LaunchDetailIntent
import com.abdul.mazaaditask.presentation.launchdetail.mvi.LaunchDetailViewModel

/**
 * Screen displaying detailed information about a specific launch
 * 
 * Shows:
 * - Mission patch image
 * - Rocket information (name, type, ID)
 * - Mission name
 * - Launch site
 */
@Composable
fun LaunchDetailScreen(
    launchId: String,
    onBackClick: () -> Unit,
    viewModel: LaunchDetailViewModel = hiltViewModel()
) {
    // Observe state changes from ViewModel
    val state by viewModel.state.collectAsState()
    
    // Load launch details when screen is displayed or launchId changes
    LaunchedEffect(launchId) {
        viewModel.processIntent(LaunchDetailIntent.LoadLaunchDetail(launchId))
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Launch Detail (id: $launchId)") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                state.error != null -> {
                    ErrorMessage(
                        message = state.error,
                        onDismiss = { viewModel.processIntent(LaunchDetailIntent.ClearError) },
                        onRetry = { viewModel.processIntent(LaunchDetailIntent.LoadLaunchDetail(launchId)) },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                state.launchDetail != null -> {
                    LaunchDetailContent(
                        launchDetail = state.launchDetail,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun LaunchDetailContent(
    launchDetail: com.abdul.mazaaditask.domain.model.LaunchDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Mission Patch
        if (!launchDetail.missionPatch.isNullOrEmpty()) {
            AsyncImage(
                model = launchDetail.missionPatch,
                contentDescription = "Mission Patch",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            // Placeholder if no mission patch
            Surface(
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Patch",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        
        // Rocket Information
        InfoSection(
            title = "Rocket",
            items = listOf(
                "NAME: ${launchDetail.rocketName}",
                "TYPE: ${launchDetail.rocketType}",
                "ID: ${launchDetail.rocketId}"
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Mission Information
        InfoSection(
            title = "Mission:",
            items = listOf(
                "NAME: ${launchDetail.missionName}"
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Site Information
        InfoSection(
            title = "Site:",
            items = listOf(
                launchDetail.site
            )
        )
    }
}

@Composable
fun InfoSection(
    title: String,
    items: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        items.forEach { item ->
            Text(
                text = item,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun ErrorMessage(
    message: String,
    onDismiss: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
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

