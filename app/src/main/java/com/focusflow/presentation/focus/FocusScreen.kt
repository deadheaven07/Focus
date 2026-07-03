package com.focusflow.presentation.focus

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.focusflow.domain.model.FocusSessionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusScreen(
    viewModel: FocusViewModel = hiltViewModel(),
    onSeeHistory: () -> Unit
) {
    val timeLeft by viewModel.timeLeft.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val initialTime by viewModel.currentTotalDuration.collectAsState()

    val progress by animateFloatAsState(
        targetValue = if (initialTime > 0) timeLeft.toFloat() / initialTime else 0f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "Timer Progress"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "Pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isRunning) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Pulse Scale"
    )

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text("Focus Session") },
                actions = {
                    TextButton(onClick = onSeeHistory) {
                        Text("History")
                    }
                }
            ) 
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val minutes = (timeLeft / 1000) / 60
            val seconds = (timeLeft / 1000) % 60

            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(250.dp)) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 8.dp,
                    color = if (isRunning) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
                Text(
                    text = String.format("%02d:%02d", minutes, seconds),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.scale(scale)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))

            if (!isRunning && timeLeft == 0L) {
                // Select session type
                FocusSessionType.values().filter { it != FocusSessionType.CUSTOM }.forEach { type ->
                    Button(
                        onClick = { viewModel.startFocusSession(type) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Text("Start ${type.displayName} (${type.defaultDurationMinutes}m)")
                    }
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = { viewModel.pauseResume() }) {
                        Text(if (isRunning) "Pause" else "Resume")
                    }
                    Button(
                        onClick = { viewModel.stopSession() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Stop")
                    }
                }
            }
        }
    }
}
