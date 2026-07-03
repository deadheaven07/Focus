package com.focusflow.presentation.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusflow.core.util.AnalyticsHelper
import com.focusflow.core.util.DateUtils
import com.focusflow.domain.model.Habit
import com.focusflow.domain.usecase.AddHabitUseCase
import com.focusflow.domain.usecase.DeleteHabitUseCase
import com.focusflow.domain.usecase.GetHabitsUseCase
import com.focusflow.domain.usecase.ToggleHabitCompletionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val toggleHabitCompletionUseCase: ToggleHabitCompletionUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {

    // we keep the UI state here so Compose knows when to redraw
    private val _state = MutableStateFlow(HabitListState())
    val state: StateFlow<HabitListState> = _state.asStateFlow()

    init {
        // grab the habits as soon as we start
        loadHabits()
    }

    private fun loadHabits() {
        getHabitsUseCase()
            .onEach { habits ->
                _state.update { it.copy(habits = habits, isLoading = false) }
            }
            .onStart { _state.update { it.copy(isLoading = true) } }
            .catch { e -> _state.update { it.copy(error = e.message, isLoading = false) } }
            .launchIn(viewModelScope)
    }

    fun onToggleHabit(habit: Habit) {
        viewModelScope.launch {
            val result = toggleHabitCompletionUseCase(habit, DateUtils.getStartOfDay())
            if (result.isSuccess && !habit.isCompletedToday) {
                // only log if they just finished it
                analyticsHelper.logHabitCompleted(habit.name)
            } else if (result.isFailure) {
                _state.update { it.copy(error = "Couldn't update habit, try again?") }
            }
        }
    }

    fun addHabit(name: String, description: String, category: com.focusflow.domain.model.HabitCategory, frequency: com.focusflow.domain.model.HabitFrequency) {
        viewModelScope.launch {
            val newHabit = Habit(
                name = name,
                description = description,
                category = category,
                frequency = frequency
            )
            val result = addHabitUseCase(newHabit)
            if (result.isSuccess) {
                analyticsHelper.logHabitCreated(name, category.name)
            } else {
                _state.update { it.copy(error = "Oops, couldn't save your habit") }
            }
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            val result = deleteHabitUseCase(habit)
            if (result.isFailure) {
                _state.update { it.copy(error = "Couldn't delete that habit") }
            }
        }
    }
}
