package com.example.data.repository

import com.example.data.local.BlueprintDao
import com.example.data.local.BlueprintEntity
import com.example.data.local.BlueprintJsonAdapter
import com.example.model.FrameworkBlueprint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PathwayRepository(private val dao: BlueprintDao) {

    val allBlueprints: Flow<List<BlueprintEntity>> = dao.getAllBlueprints()

    suspend fun saveBlueprint(blueprint: FrameworkBlueprint): Long {
        val json = BlueprintJsonAdapter.toJson(blueprint)
        val entity = BlueprintEntity(
            id = if (blueprint.id > 0) blueprint.id else 0,
            title = blueprint.title,
            formula = blueprint.strategyFormula,
            businessesSummary = blueprint.businessesInput.joinToString(", "),
            startingCapital = blueprint.startingCapital,
            marketContext = blueprint.marketContext,
            targetDemography = blueprint.targetDemography,
            targetGender = blueprint.targetGender,
            selectedTermsJson = blueprint.selectedTerms.joinToString(", "),
            blueprintJson = json,
            createdAt = System.currentTimeMillis()
        )
        return dao.insertBlueprint(entity)
    }

    suspend fun getBlueprintById(id: Long): FrameworkBlueprint? {
        val entity = dao.getBlueprintById(id) ?: return null
        return try {
            val bp = BlueprintJsonAdapter.fromJson(entity.blueprintJson)
            bp.copy(id = entity.id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteBlueprint(id: Long) {
        dao.deleteBlueprintById(id)
    }
}
