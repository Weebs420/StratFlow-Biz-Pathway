package com.example.data.local

import com.example.model.ComplementaryBusinessSuggestion
import com.example.model.FlywheelLoop
import com.example.model.FrameworkBlueprint
import com.example.model.PathwayStep
import com.example.model.ShadowLayerArchitecture
import com.example.model.SuggestedTermRefinement
import org.json.JSONArray
import org.json.JSONObject

object BlueprintJsonAdapter {

    fun toJson(blueprint: FrameworkBlueprint): String {
        val root = JSONObject()
        root.put("id", blueprint.id)
        root.put("title", blueprint.title)
        root.put("strategyFormula", blueprint.strategyFormula)
        root.put("startingCapital", blueprint.startingCapital)
        root.put("marketContext", blueprint.marketContext)
        root.put("targetDemography", blueprint.targetDemography)
        root.put("targetGender", blueprint.targetGender)
        root.put("executiveSummary", blueprint.executiveSummary)
        root.put("createdAt", blueprint.createdAt)

        val bizArr = JSONArray()
        blueprint.businessesInput.forEach { bizArr.put(it) }
        root.put("businessesInput", bizArr)

        val termsArr = JSONArray()
        blueprint.selectedTerms.forEach { termsArr.put(it) }
        root.put("selectedTerms", termsArr)

        // Steps
        val stepsArr = JSONArray()
        blueprint.steps.forEach { step ->
            val sObj = JSONObject()
            sObj.put("stepIndex", step.stepIndex)
            sObj.put("title", step.title)
            sObj.put("businessName", step.businessName)
            sObj.put("category", step.category)
            sObj.put("strategicRole", step.strategicRole)
            sObj.put("primaryTerm", step.primaryTerm)
            sObj.put("sequenceRationale", step.sequenceRationale)

            val prosArr = JSONArray()
            step.whyItWillWork.forEach { prosArr.put(it) }
            sObj.put("whyItWillWork", prosArr)

            val consArr = JSONArray()
            step.whyItMightNotWork.forEach { consArr.put(it) }
            sObj.put("whyItMightNotWork", consArr)

            sObj.put("rootCauseAndWhyToRemoveProblem", step.rootCauseAndWhyToRemoveProblem)

            val mitigationArr = JSONArray()
            step.actionableRiskMitigationSteps.forEach { mitigationArr.put(it) }
            sObj.put("actionableRiskMitigationSteps", mitigationArr)

            sObj.put("estimatedCapitalAllocation", step.estimatedCapitalAllocation)
            sObj.put("keyPerformanceIndicator", step.keyPerformanceIndicator)
            stepsArr.put(sObj)
        }
        root.put("steps", stepsArr)

        // Shadow architecture
        val shadowObj = JSONObject()
        shadowObj.put("procurementEngine", blueprint.shadowArchitecture.procurementEngine)
        shadowObj.put("dataCompounding", blueprint.shadowArchitecture.dataCompounding)
        shadowObj.put("logisticsBackbone", blueprint.shadowArchitecture.logisticsBackbone)
        shadowObj.put("supplierNetwork", blueprint.shadowArchitecture.supplierNetwork)
        shadowObj.put("techStack", blueprint.shadowArchitecture.techStack)
        root.put("shadowArchitecture", shadowObj)

        // Flywheel
        val flywheelObj = JSONObject()
        val fStages = JSONArray()
        blueprint.flywheel.stages.forEach { fStages.put(it) }
        flywheelObj.put("stages", fStages)
        flywheelObj.put("compoundingNarrative", blueprint.flywheel.compoundingNarrative)
        root.put("flywheel", flywheelObj)

        // Complementary businesses
        val compArr = JSONArray()
        blueprint.complementaryBusinesses.forEach { comp ->
            val cObj = JSONObject()
            cObj.put("businessName", comp.businessName)
            cObj.put("businessType", comp.businessType)
            cObj.put("strategicSynergy", comp.strategicSynergy)
            cObj.put("whySuggested", comp.whySuggested)
            cObj.put("recommendedArchetype", comp.recommendedArchetype)
            compArr.put(cObj)
        }
        root.put("complementaryBusinesses", compArr)

        // Suggested terms
        val termSugArr = JSONArray()
        blueprint.suggestedTerms.forEach { sug ->
            val tObj = JSONObject()
            tObj.put("termName", sug.termName)
            tObj.put("category", sug.category)
            tObj.put("rationale", sug.rationale)
            tObj.put("formulaImpact", sug.formulaImpact)
            termSugArr.put(tObj)
        }
        root.put("suggestedTerms", termSugArr)

        return root.toString()
    }

    fun fromJson(jsonStr: String): FrameworkBlueprint {
        val root = JSONObject(jsonStr)
        val id = root.optLong("id", 0)
        val title = root.optString("title", "Framework Pathway")
        val strategyFormula = root.optString("strategyFormula", "Original + Integrated + Flywheel")
        val startingCapital = root.optString("startingCapital", "")
        val marketContext = root.optString("marketContext", "")
        val targetDemography = root.optString("targetDemography", "")
        val targetGender = root.optString("targetGender", "")
        val executiveSummary = root.optString("executiveSummary", "")
        val createdAt = root.optLong("createdAt", System.currentTimeMillis())

        val bizList = mutableListOf<String>()
        root.optJSONArray("businessesInput")?.let { arr ->
            for (i in 0 until arr.length()) bizList.add(arr.getString(i))
        }

        val termsList = mutableListOf<String>()
        root.optJSONArray("selectedTerms")?.let { arr ->
            for (i in 0 until arr.length()) termsList.add(arr.getString(i))
        }

        val steps = mutableListOf<PathwayStep>()
        root.optJSONArray("steps")?.let { arr ->
            for (i in 0 until arr.length()) {
                val sObj = arr.getJSONObject(i)
                val pros = mutableListOf<String>()
                sObj.optJSONArray("whyItWillWork")?.let { pArr ->
                    for (j in 0 until pArr.length()) pros.add(pArr.getString(j))
                }
                val cons = mutableListOf<String>()
                sObj.optJSONArray("whyItMightNotWork")?.let { cArr ->
                    for (j in 0 until cArr.length()) cons.add(cArr.getString(j))
                }
                val mitigations = mutableListOf<String>()
                sObj.optJSONArray("actionableRiskMitigationSteps")?.let { mArr ->
                    for (j in 0 until mArr.length()) mitigations.add(mArr.getString(j))
                }

                steps.add(
                    PathwayStep(
                        stepIndex = sObj.optInt("stepIndex", i + 1),
                        title = sObj.optString("title", "Step ${i + 1}"),
                        businessName = sObj.optString("businessName", "Business Entity"),
                        category = sObj.optString("category", "Operational Tier"),
                        strategicRole = sObj.optString("strategicRole", "Core Function"),
                        primaryTerm = sObj.optString("primaryTerm", "Original"),
                        sequenceRationale = sObj.optString("sequenceRationale", ""),
                        whyItWillWork = pros,
                        whyItMightNotWork = cons,
                        rootCauseAndWhyToRemoveProblem = sObj.optString("rootCauseAndWhyToRemoveProblem", ""),
                        actionableRiskMitigationSteps = mitigations,
                        estimatedCapitalAllocation = sObj.optString("estimatedCapitalAllocation", "Balanced"),
                        keyPerformanceIndicator = sObj.optString("keyPerformanceIndicator", "ROI & Cash Flow")
                    )
                )
            }
        }

        val sObj = root.optJSONObject("shadowArchitecture")
        val shadow = ShadowLayerArchitecture(
            procurementEngine = sObj?.optString("procurementEngine") ?: "Direct bulk aggregation",
            dataCompounding = sObj?.optString("dataCompounding") ?: "Daily unit margins & buyer preferences",
            logisticsBackbone = sObj?.optString("logisticsBackbone") ?: "Dedicated regional transport",
            supplierNetwork = sObj?.optString("supplierNetwork") ?: "Tier-1 direct contracts",
            techStack = sObj?.optString("techStack") ?: "Lightweight ERP and mobile ordering"
        )

        val fObj = root.optJSONObject("flywheel")
        val stages = mutableListOf<String>()
        fObj?.optJSONArray("stages")?.let { arr ->
            for (i in 0 until arr.length()) stages.add(arr.getString(i))
        }
        val flywheel = FlywheelLoop(
            stages = if (stages.isNotEmpty()) stages else listOf(
                "Customer Order", "Cash Flow Generation", "Procurement Leverage", "Unit Cost Reduction", "Better Offer", "More Customers"
            ),
            compoundingNarrative = fObj?.optString("compoundingNarrative") ?: "Compounding volume-price loop"
        )

        val complementary = mutableListOf<ComplementaryBusinessSuggestion>()
        root.optJSONArray("complementaryBusinesses")?.let { arr ->
            for (i in 0 until arr.length()) {
                val cObj = arr.getJSONObject(i)
                complementary.add(
                    ComplementaryBusinessSuggestion(
                        businessName = cObj.optString("businessName"),
                        businessType = cObj.optString("businessType"),
                        strategicSynergy = cObj.optString("strategicSynergy"),
                        whySuggested = cObj.optString("whySuggested"),
                        recommendedArchetype = cObj.optString("recommendedArchetype")
                    )
                )
            }
        }

        val suggestedTerms = mutableListOf<SuggestedTermRefinement>()
        root.optJSONArray("suggestedTerms")?.let { arr ->
            for (i in 0 until arr.length()) {
                val tObj = arr.getJSONObject(i)
                suggestedTerms.add(
                    SuggestedTermRefinement(
                        termName = tObj.optString("termName"),
                        category = tObj.optString("category"),
                        rationale = tObj.optString("rationale"),
                        formulaImpact = tObj.optString("formulaImpact")
                    )
                )
            }
        }

        return FrameworkBlueprint(
            id = id,
            title = title,
            strategyFormula = strategyFormula,
            businessesInput = bizList,
            startingCapital = startingCapital,
            marketContext = marketContext,
            targetDemography = targetDemography,
            targetGender = targetGender,
            selectedTerms = termsList,
            steps = steps,
            shadowArchitecture = shadow,
            flywheel = flywheel,
            complementaryBusinesses = complementary,
            suggestedTerms = suggestedTerms,
            executiveSummary = executiveSummary,
            createdAt = createdAt
        )
    }
}
