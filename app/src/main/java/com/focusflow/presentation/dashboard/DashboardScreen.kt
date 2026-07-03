package com.focusflow.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onSeeAllHabits: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("FocusFlow") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StreakCard(totalStreak = state.totalStreak)
            }
            item {
                ProgressCard(progress = state.progress)
            }
            if (state.insights.isNotEmpty()) {
                item {
                    Text("Smart Insights", style = MaterialTheme.typography.titleLarge)
                }
                items(state.insights.size) { index ->
                    InsightCard(insight = state.insights[index].message)
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Today's Habits", style = MaterialTheme.typography.titleLarge)
                    TextButton(onClick = onSeeAllHabits) {
                        Text("See All")
                    }
                }
            }
            item {
                HabitSummaryRow(label = "Completed", count = state.completedHabits.size, color = Color.Green)
            }
            item {
                HabitSummaryRow(label = "Pending", count = state.pendingHabits.size, color = Color.Gray)
            }
        }
    }
}

@Composable
fun StreakCard(totalStreak: Int) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Total Streak", style = MaterialTheme.typography.labelLarge)
            Text("$totalStreak 🔥", style = MaterialTheme.typography.displayMedium)
        }
    }
}

@Composable
fun ProgressCard(progress: Float) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Daily Progress", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().height(12.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("${(progress * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun InsightCard(insight: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("💡", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.width(16.dp))
            Text(insight, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun HabitSummaryRow(label: String, count: Int, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text("$count", color = color, style = MaterialTheme.typography.titleMedium)
    }
}
