package com.example.model

data class RiskItem(
    val riskName: String,
    val severity: String, // "CRITICAL", "HIGH", "MODERATE"
    val probability: String, // "HIGH", "MEDIUM", "LOW"
    val description: String,
    val rootCause: String,
    val preventiveAction: String
)

data class RoadmapPhase(
    val phaseNumber: Int,
    val title: String,
    val timeframe: String, // e.g. "Days 1–30", "Days 31–90", "Days 91–180"
    val primaryObjective: String,
    val keyActionSteps: List<String>,
    val guardrails: List<String>,
    val exitCriteria: String
)

data class KillSwitchTrigger(
    val metric: String,
    val threshold: String,
    val requiredResponse: String
)

data class RiskMitigationRoadmap(
    val id: Long = 0,
    val projectIdea: String,
    val estimatedCapital: String,
    val strategicArchetype: String, // e.g. "Original (Customer-First)" or "Opposite (Asset-First)"
    val executiveSummary: String,
    val topRisks: List<RiskItem>,
    val roadmapPhases: List<RoadmapPhase>,
    val killSwitches: List<KillSwitchTrigger>,
    val compoundingSafeguards: String,
    val generatedWithAi: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
