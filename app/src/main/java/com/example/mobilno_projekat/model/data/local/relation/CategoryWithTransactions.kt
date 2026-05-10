package com.example.mobilno_projekat.model.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mobilno_projekat.model.data.local.entity.CategoryEntity
import com.example.mobilno_projekat.model.data.local.entity.TransactionEntity

data class CategoryWithTransactions(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val transactions: List<TransactionEntity>
)