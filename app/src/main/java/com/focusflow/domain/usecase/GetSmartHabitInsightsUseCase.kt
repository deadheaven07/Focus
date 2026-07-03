package com.focusflow.domain.usecase

import com.focusflow.domain.model.Habit
import com.focusflow.domain.model.HabitInsight
import javax.inject.Inject

/**
 * this generates some useful tips for the user based on their habit history.
 * it's not actually AI, just some simple rules to keep them motivated.
 */
class GetSmartHabitInsightsUseCase @Inject constructor() {
    
    operator fun invoke(habits: List<Habit>): List<HabitInsight> {
        if (habits.isEmpty()) return emptyList()
        
        val insights = mutableListOf<HabitInsight>()
        
        // let's see which category they're doing best in
        val bestCategory = habits.groupBy { it.category }
            .mapValues { (_, habits) ->
                habits.map { it.streakCount }.average()
            }
            .maxByOrNull { it.value }

        bestCategory?.let {
            insights.add(HabitInsight("You're crushing it in ${it.key.name}! Keep that streak alive."))
        }
        
        // generic encouragement if they're doing well overall
        if (habits.any { it.streakCount > 5 }) {
            insights.add(HabitInsight("Consistency is key! You've got some solid streaks going."))
        } else {
            insights.add(HabitInsight("Every small step counts. Try to complete one habit today!"))
        }
        
        return insights
    }
}
