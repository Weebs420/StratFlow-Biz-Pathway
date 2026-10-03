package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BlueprintDao {
    @Query("SELECT * FROM saved_blueprints ORDER BY createdAt DESC")
    fun getAllBlueprints(): Flow<List<BlueprintEntity>>

    @Query("SELECT * FROM saved_blueprints WHERE id = :id LIMIT 1")
    suspend fun getBlueprintById(id: Long): BlueprintEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlueprint(entity: BlueprintEntity): Long

    @Query("DELETE FROM saved_blueprints WHERE id = :id")
    suspend fun deleteBlueprintById(id: Long)

    @Query("DELETE FROM saved_blueprints")
    suspend fun clearAll()
}
