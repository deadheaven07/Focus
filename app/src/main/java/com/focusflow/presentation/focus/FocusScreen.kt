package com.focusflow.presentation.focus

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                style = MaterialTheme.typography.displayLarge
            )
            
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
