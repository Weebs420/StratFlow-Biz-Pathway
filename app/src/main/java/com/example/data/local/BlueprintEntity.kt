package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_blueprints")
data class BlueprintEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val formula: String,
    val businessesSummary: String,
    val startingCapital: String,
    val marketContext: String,
    val targetDemography: String,
    val targetGender: String,
    val selectedTermsJson: String,
    val blueprintJson: String,
    val createdAt: Long = System.currentTimeMillis()
)
