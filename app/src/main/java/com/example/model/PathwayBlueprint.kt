package com.example.model

data class PathwayStep(
    val stepIndex: Int,
    val title: String,
    val businessName: String,
    val category: String,
    val strategicRole: String,
    val primaryTerm: String,
    val sequenceRationale: String,
    val whyItWillWork: List<String>,
    val whyItMightNotWork: List<String>,
    val rootCauseAndWhyToRemoveProblem: String,
    val actionableRiskMitigationSteps: List<String>,
    val estimatedCapitalAllocation: String,
    val keyPerformanceIndicator: String
)

data class ShadowLayerArchitecture(
    val procurementEngine: String,
    val dataCompounding: String,
    val logisticsBackbone: String,
    val supplierNetwork: String,
    val techStack: String
)

data class FlywheelLoop(
    val stages: List<String>,
    val compoundingNarrative: String
)

data class ComplementaryBusinessSuggestion(
    val businessName: String,
    val businessType: String,
    val strategicSynergy: String,
    val whySuggested: String,
    val recommendedArchetype: String
)

data class SuggestedTermRefinement(
    val termName: String,
    val category: String,
    val rationale: String,
    val formulaImpact: String
)

data class FrameworkBlueprint(
    val id: Long = 0,
    val title: String,
    val strategyFormula: String,
    val businessesInput: List<String>,
    val startingCapital: String,
    val marketContext: String,
    val targetDemography: String,
    val targetGender: String,
    val selectedTerms: List<String>,
    val steps: List<PathwayStep>,
    val shadowArchitecture: ShadowLayerArchitecture,
    val flywheel: FlywheelLoop,
    val complementaryBusinesses: List<ComplementaryBusinessSuggestion>,
    val suggestedTerms: List<SuggestedTermRefinement>,
    val decisionChecklist: List<String> = emptyList(),
    val executiveSummary: String,
    val createdAt: Long = System.currentTimeMillis()
)
