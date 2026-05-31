package com.example.mobilno_projekat.model.repository.habit

import com.example.mobilno_projekat.model.datasource.network.dto.CreateHabitDto
import com.example.mobilno_projekat.model.datasource.network.dto.HabitDto
import com.example.mobilno_projekat.model.datasource.network.dto.UpdateHabitDto

interface HabitRepository {

    suspend fun getHabits(): List<HabitDto>

    suspend fun getHabitById(id: Int): HabitDto

    suspend fun createHabit(habit: CreateHabitDto): HabitDto

    suspend fun updateHabit(id: Int, habit: UpdateHabitDto): HabitDto

    suspend fun deleteHabit(id: Int)
}