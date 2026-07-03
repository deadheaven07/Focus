package com.focusflow.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusflow.domain.usecase.GetHabitsUseCase
import com.focusflow.domain.usecase.GetSmartHabitInsightsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val getSmartHabitInsightsUseCase: GetSmartHabitInsightsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    init {
        // watch the habit list and update the dashboard state whenever it changes
        getHabitsUseCase()
            .onEach { habits ->
                val totalStreak = habits.sumOf { it.streakCount }
                val insights = getSmartHabitInsightsUseCase(habits)

                _state.update { it.copy(
                    habits = habits, 
                    totalStreak = totalStreak, 
                    insights = insights,
                    isLoading = false
                ) }
            }
            .onStart { _state.update { it.copy(isLoading = true) } }
            .launchIn(viewModelScope)
    }
}
