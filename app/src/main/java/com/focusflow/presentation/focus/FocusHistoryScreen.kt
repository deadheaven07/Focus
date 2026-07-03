package com.focusflow.presentation.focus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.focusflow.data.local.entity.FocusSessionEntity
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusHistoryScreen(
    viewModel: FocusHistoryViewModel = hiltViewModel()
) {
    val sessions by viewModel.sessions.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Focus History") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sessions) { session ->
                FocusSessionItem(session = session)
            }
        }
    }
}

@Composable
fun FocusSessionItem(session: FocusSessionEntity) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = session.type.displayName, style = MaterialTheme.typography.titleMedium)
                if (session.isCompleted) {
                    Text("✅ Completed", color = MaterialTheme.colorScheme.primary)
                } else {
                    Text("❌ Interrupted", color = MaterialTheme.colorScheme.error)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            val date = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(Date(session.startTime))
            Text(text = date, style = MaterialTheme.typography.bodySmall)
            Text(text = "Duration: ${session.durationMillis / 1000 / 60} min", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
