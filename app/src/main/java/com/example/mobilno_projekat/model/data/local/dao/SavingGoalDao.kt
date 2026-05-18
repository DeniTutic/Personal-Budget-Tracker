package com.example.mobilno_projekat.model.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mobilno_projekat.model.data.local.entity.SavingGoalEntity

@Dao
interface SavingGoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavingGoal(goal: SavingGoalEntity): Long

    @Update
    suspend fun updateSavingGoal(goal: SavingGoalEntity)

    @Delete
    suspend fun deleteSavingGoal(goal: SavingGoalEntity)

    @Query("SELECT * FROM saving_goals")
    suspend fun getAllSavingGoals(): List<SavingGoalEntity>

    @Query("SELECT * FROM saving_goals WHERE id = :id")
    suspend fun getSavingGoalById(id: Int): SavingGoalEntity?

    @Query("DELETE FROM saving_goals")
    suspend fun deleteAllSavingGoals()
}