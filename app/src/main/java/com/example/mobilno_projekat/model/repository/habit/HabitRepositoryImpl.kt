package com.example.mobilno_projekat.model.repository.habit

import com.example.mobilno_projekat.model.datasource.network.dto.CreateHabitDto
import com.example.mobilno_projekat.model.datasource.network.dto.HabitDto
import com.example.mobilno_projekat.model.datasource.network.dto.UpdateHabitDto
import com.example.mobilno_projekat.model.datasource.network.service.HabitApiService
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val apiService: HabitApiService
) : HabitRepository {

    override suspend fun getHabits(): List<HabitDto> {
        return apiService.getHabits()
    }

    override suspend fun getHabitById(id: Int): HabitDto {
        return apiService.getHabitById(id)
    }

    override suspend fun createHabit(habit: CreateHabitDto): HabitDto {
        return apiService.createHabit(habit)
    }

    override suspend fun updateHabit(id: Int, habit: UpdateHabitDto): HabitDto {
        return apiService.updateHabit(id, habit)
    }

    override suspend fun deleteHabit(id: Int) {
        apiService.deleteHabit(id)
    }
}