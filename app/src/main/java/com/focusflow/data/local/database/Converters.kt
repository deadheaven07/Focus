package com.focusflow.data.local.database

import androidx.room.TypeConverter
import com.focusflow.domain.model.HabitCategory
import com.focusflow.domain.model.HabitFrequency

class Converters {
    @TypeConverter
    fun fromHabitCategory(category: HabitCategory): String = category.name

    @TypeConverter
    fun toHabitCategory(value: String): HabitCategory = HabitCategory.valueOf(value)

    @TypeConverter
    fun fromHabitFrequency(frequency: HabitFrequency): String = frequency.name

    @TypeConverter
    fun toHabitFrequency(value: String): HabitFrequency = HabitFrequency.valueOf(value)
}
