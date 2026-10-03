package com.example.data.ai

import android.util.Log
import com.example.model.KillSwitchTrigger
import com.example.model.RiskItem
import com.example.model.RiskMitigationRoadmap
import com.example.model.RoadmapPhase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

object RiskSynthesizer {
    private const val TAG = "RiskSynthesizer"

    suspend fun generateRiskRoadmap(
        projectIdea: String,
        capital: String,
        archetype: String
    ): RiskMitigationRoadmap = withContext(Dispatchers.Default) {
        val geminiResult = tryGenerateWithGemini(projectIdea, capital, archetype)
        if (geminiResult != null) {
            return@withContext geminiResult
        }
        // Fallback to heuristic risk architecture
        generateHeuristicRiskRoadmap(projectIdea, capital, archetype)
    }

    private suspend fun tryGenerateWithGemini(
        projectIdea: String,
        capital: String,
        archetype: String
    ): RiskMitigationRoadmap? = withContext(Dispatchers.IO) {
        try {
            val systemPrompt = """
You are a world-class venture risk architect, forensic auditor, and turnaround specialist.
Deconstruct the user's project idea, capital budget, and strategic framework archetype to formulate a bulletproof Risk Mitigation Roadmap.

Return a STRICT JSON object with this exact structure:
{
  "executiveSummary": "Concise executive diagnosis of the top vulnerabilities and overarching risk management posture",
  "topRisks": [
    {
      "riskName": "e.g. Working Capital Asphyxiation / Capacity Idle Drag",
      "severity": "CRITICAL or HIGH or MODERATE",
      "probability": "HIGH or MEDIUM or LOW",
      "description": "Specific failure mechanism in this market",
      "rootCause": "The underlying structural flaw",
      "preventiveAction": "Preemptive structural fix"
    }
  ],
  "roadmapPhases": [
    {
      "phaseNumber": 1,
      "title": "Phase 1: Pre-Launch Capital Defense & Guardrails",
      "timeframe": "Days 1–30",
      "primaryObjective": "Core survival milestone",
      "keyActionSteps": ["step 1", "step 2", "step 3"],
      "guardrails": ["guardrail 1", "guardrail 2"],
      "exitCriteria": "Measurable validation required to advance"
    },
    {
      "phaseNumber": 2,
      "title": "Phase 2: Operational Stabilization & Stress-Testing",
      "timeframe": "Days 31–90",
      "primaryObjective": "Achieve unit economic breakeven under pressure",
      "keyActionSteps": ["step 1", "step 2", "step 3"],
      "guardrails": ["guardrail 1", "guardrail 2"],
      "exitCriteria": "Measurable validation required to advance"
    },
    {
      "phaseNumber": 3,
      "title": "Phase 3: Scale Insulation & Compounding Moat",
      "timeframe": "Days 91–180+",
      "primaryObjective": "Lock in defensible market dominance",
      "keyActionSteps": ["step 1", "step 2", "step 3"],
      "guardrails": ["guardrail 1", "guardrail 2"],
      "exitCriteria": "Measurable validation required to advance"
    }
  ],
  "killSwitches": [
    {
      "metric": "Key warning indicator (e.g. 60-Day Accounts Receivable, Capacity Utilization)",
      "threshold": "Exact red-line trigger level",
      "requiredResponse": "Non-negotiable emergency action (e.g. freeze procurement, pivot to third-party lease)"
    }
  ],
  "compoundingSafeguards": "How backstage systems, reserves, and contracts create permanent defense over 3-5 years"
}
""".trimIndent()

            val userPrompt = """
Project Idea: $projectIdea
Estimated Capital: ${if (capital.isBlank()) "Standard Venture Reserve (e.g. ৳25 Lakh / $50K)" else capital}
Strategic Archetype Lens: $archetype

Generate an exhaustive, realistic, non-generic Risk Mitigation Roadmap formatted as valid JSON.
""".trimIndent()

            val rawJson = GeminiClient.generateContent(systemPrompt, userPrompt) ?: return@withContext null
            val clean = cleanJson(rawJson)
            parseRiskRoadmapJson(clean, projectIdea, capital, archetype)
        } catch (e: Exception) {
            Log.w(TAG, "Gemini risk roadmap generation fallback", e)
            null
        }
    }

    private fun cleanJson(raw: String): String {
        var text = raw.trim()
        if (text.startsWith("```json")) {
            text = text.substring(7)
        } else if (text.startsWith("```")) {
            text = text.substring(3)
        }
        if (text.endsWith("```")) {
            text = text.substring(0, text.length - 3)
        }
        return text.trim()
    }

    private fun parseRiskRoadmapJson(
        jsonStr: String,
        projectIdea: String,
        capital: String,
        archetype: String
    ): RiskMitigationRoadmap {
        val root = JSONObject(jsonStr)
        val executiveSummary = root.optString("executiveSummary", "Strategic risk roadmap architected for $projectIdea.")

        val topRisks = mutableListOf<RiskItem>()
        root.optJSONArray("topRisks")?.let { arr ->
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                topRisks.add(
                    RiskItem(
                        riskName = obj.optString("riskName", "Structural Risk"),
                        severity = obj.optString("severity", "HIGH"),
                        probability = obj.optString("probability", "MEDIUM"),
                        description = obj.optString("description", ""),
                        rootCause = obj.optString("rootCause", ""),
                        preventiveAction = obj.optString("preventiveAction", "")
                    )
                )
            }
        }

        val roadmapPhases = mutableListOf<RoadmapPhase>()
        root.optJSONArray("roadmapPhases")?.let { arr ->
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val stepsList = mutableListOf<String>()
                obj.optJSONArray("keyActionSteps")?.let { sArr ->
                    for (j in 0 until sArr.length()) stepsList.add(sArr.getString(j))
                }
                val guardrailsList = mutableListOf<String>()
                obj.optJSONArray("guardrails")?.let { gArr ->
                    for (j in 0 until gArr.length()) guardrailsList.add(gArr.getString(j))
                }

                roadmapPhases.add(
                    RoadmapPhase(
                        phaseNumber = obj.optInt("phaseNumber", i + 1),
                        title = obj.optString("title", "Phase ${i + 1}"),
                        timeframe = obj.optString("timeframe", "Days ${i * 45 + 1}–${(i + 1) * 60}"),
                        primaryObjective = obj.optString("primaryObjective", ""),
                        keyActionSteps = stepsList,
                        guardrails = guardrailsList,
                        exitCriteria = obj.optString("exitCriteria", "")
                    )
                )
            }
        }

        val killSwitches = mutableListOf<KillSwitchTrigger>()
        root.optJSONArray("killSwitches")?.let { arr ->
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                killSwitches.add(
                    KillSwitchTrigger(
                        metric = obj.optString("metric", "Metric"),
                        threshold = obj.optString("threshold", "Trigger"),
                        requiredResponse = obj.optString("requiredResponse", "Response")
                    )
                )
            }
        }

        val compounding = root.optString("compoundingSafeguards", "Reinvesting retained surpluses into direct physical controls and proprietary backhaul logistics.")

        return RiskMitigationRoadmap(
            projectIdea = projectIdea,
            estimatedCapital = capital,
            strategicArchetype = archetype,
            executiveSummary = executiveSummary,
            topRisks = topRisks,
            roadmapPhases = roadmapPhases,
            killSwitches = killSwitches,
            compoundingSafeguards = compounding,
            generatedWithAi = true,
            createdAt = System.currentTimeMillis()
        )
    }

    fun generateHeuristicRiskRoadmap(
        projectIdea: String,
        capital: String,
        archetype: String
    ): RiskMitigationRoadmap {
        val isOpposite = archetype.contains("Opposite", ignoreCase = true)
        val capLabel = if (capital.isBlank()) "Allocated Budget" else capital

        val summary = if (isOpposite) {
            "Opposite Model Risk Diagnosis for '$projectIdea': Because capital is deployed upfront into physical assets and processing infrastructure before demand is proven, the primary systemic hazards are Fixed Overhead Drag and Idle Capacity Burn. The roadmap enforces pre-launch capacity leasing to establish zero-loss operation while internal demand scales."
        } else {
            "Original Model Risk Diagnosis for '$projectIdea': Because customer demand and early sales are launched first, the primary systemic hazards are Working Capital Asphyxiation and Supplier Counterparty Fragility. The roadmap enforces strict Zero-Credit discipline and 50% prepayments to insulate liquid cash flow."
        }

        val risks = if (isOpposite) {
            listOf(
                RiskItem(
                    riskName = "Asset Capacity Idle Drag",
                    severity = "CRITICAL",
                    probability = "HIGH",
                    description = "Heavy capital committed to fixed facility equipment generates continuous depreciation and facility maintenance while daily throughput lingers under 50%.",
                    rootCause = "Launching physical operations without guaranteed off-take contracts or third-party baseline leasing.",
                    preventiveAction = "Pre-lease 40% of facility capacity to institutional commercial aggregators on 12-month fixed agreements prior to equipment commissioning."
                ),
                RiskItem(
                    riskName = "Working Capital Starvation Post-CapEx",
                    severity = "HIGH",
                    probability = "HIGH",
                    description = "Overspending on machinery and facility customization leaves inadequate cash reserve for bulk seasonal inventory procurement.",
                    rootCause = "Budget drift during asset acquisition phase draining liquid reserves.",
                    preventiveAction = "Quarantine 30% of total starting capital strictly for inventory cycles; never spend more than 55% of $capLabel on fixed assets."
                ),
                RiskItem(
                    riskName = "Incumbent Wholesale Margin Squeeze",
                    severity = "HIGH",
                    probability = "MEDIUM",
                    description = "Dominant regional brokers temporarily undercut terminal pricing to choke the new facility's cash inflows.",
                    rootCause = "Relying on open spot markets rather than direct institutional B2B supply agreements.",
                    preventiveAction = "Lock in forward supply agreements directly with institutional commercial buyers based on certified quality grading."
                ),
                RiskItem(
                    riskName = "Equipment Failure & Maintenance Delay",
                    severity = "MODERATE",
                    probability = "MEDIUM",
                    description = "Single-point-of-failure in critical cooling or processing line halts entire operations during peak season.",
                    rootCause = "Sourcing unvetted machinery without local spare parts availability and 24-hour service SLAs.",
                    preventiveAction = "Procure modular equipment with dual-redundancy and contract verified local technical response within 4 hours."
                )
            )
        } else {
            listOf(
                RiskItem(
                    riskName = "Receivables Asphyxiation (Credit Term Trap)",
                    severity = "CRITICAL",
                    probability = "HIGH",
                    description = "Supermarkets and wholesale distributors delay invoice payments past 60–90 days, freezing working capital while raw suppliers demand immediate cash.",
                    rootCause = "Offering competitive open credit to win initial accounts without collateralized backing.",
                    preventiveAction = "Enforce mandatory 50% deposit on wholesale orders and partner with digital factoring escrow to finance verified receivables."
                ),
                RiskItem(
                    riskName = "Supply Disintermediation by Aggregators",
                    severity = "HIGH",
                    probability = "HIGH",
                    description = "As sales volume grows, upstream suppliers or brokers bypass the venture and sell directly to the newly discovered customer base.",
                    rootCause = "Failing to build a proprietary 'Shadow' layer of quality grading and exclusive producer agreements.",
                    preventiveAction = "Tie producers to the venture through transparent digital weighing, 48-hour cash settlements, and guaranteed seasonal off-take quotas."
                ),
                RiskItem(
                    riskName = "Inventory Spoilage & Quality Variance",
                    severity = "HIGH",
                    probability = "MEDIUM",
                    description = "Unstandardized batches lead to customer returns, damaged brand credibility, and unexpected stock write-downs.",
                    rootCause = "Scaling demand before formalizing strict incoming batch grading protocols.",
                    preventiveAction = "Establish a 3-tier inspection protocol at intake and implement FIFO barcode tracking across transit hubs."
                ),
                RiskItem(
                    riskName = "Customer Acquisition Cost Creep",
                    severity = "MODERATE",
                    probability = "MEDIUM",
                    description = "Initial organic enthusiasm fades, forcing high ad spend or price discounts to sustain monthly revenue numbers.",
                    rootCause = "Lack of a compounding flywheel and automated customer repeat loops.",
                    preventiveAction = "Introduce recurring subscription bundles and automated WhatsApp replenishment discounts to keep retention over 45%."
                )
            )
        }

        val phases = listOf(
            RoadmapPhase(
                phaseNumber = 1,
                title = "Phase 1: Foundation Defense & Capital Guardrails",
                timeframe = "Days 1–30",
                primaryObjective = "Establish absolute cash liquidity safeguards and contractual fail-safes before market exposure.",
                keyActionSteps = listOf(
                    "Quarantine a dedicated 90-day cash operating reserve held strictly outside daily trading capital.",
                    "Enforce Zero-Unsecured-Credit policy: Require 50% upfront deposits on commercial purchase orders.",
                    "Vet all initial suppliers against a 5-point reliability checklist with mandatory 48-hour settlement guarantees.",
                    "Establish standardized digital intake ledger to track unit yield, moisture, and grading variance."
                ),
                guardrails = listOf(
                    "Never commit more than 20% of liquid capital to any single inventory batch or vendor.",
                    "Daily cash burn rate must remain strictly below 0.8% of total available reserve."
                ),
                exitCriteria = "100% of pilot transactions completed with zero uncollected invoices past 15 days."
            ),
            RoadmapPhase(
                phaseNumber = 2,
                title = "Phase 2: Operational Stabilization & Stress-Testing",
                timeframe = "Days 31–90",
                primaryObjective = "Scale throughput to cash-flow breakeven while stress-testing supplier and logistics redundancies.",
                keyActionSteps = listOf(
                    "Activate secondary backup sourcing partners for all critical supply lines to eliminate vendor lock-in.",
                    "Implement dynamic weekly pricing formulas tied to regional benchmark indices to protect gross margin floor.",
                    "Onboard anchor B2B commercial accounts on quarterly supply contracts with penalty clauses for late payment.",
                    "Conduct weekly inventory shrinkage audits; isolate root causes of any variance exceeding 1.2%."
                ),
                guardrails = listOf(
                    "Minimum gross margin floor set at 22%; automatic discount freezes if margin drops below threshold.",
                    "No credit extension granted to any buyer without at least 3 successful upfront transactions."
                ),
                exitCriteria = "Consecutive 30 days of operating cash-flow positive with repeat customer rate > 35%."
            ),
            RoadmapPhase(
                phaseNumber = 3,
                title = "Phase 3: Scale Defense & Compounding Moat",
                timeframe = "Days 91–180+",
                primaryObjective = "Widen competitive cost advantage and deploy retained earnings into proprietary physical assets.",
                keyActionSteps = listOf(
                    "Reinvest 45% of monthly operating surplus into value-add processing and dedicated cold transit.",
                    "Lock in long-term forward supply agreements 45 days prior to seasonal commodity price fluctuations.",
                    "Build proprietary customer order replenishment engine, driving repeat order cost to near zero.",
                    "Negotiate exclusive bulk transport rates through coordinated return-trip backhaul routing."
                ),
                guardrails = listOf(
                    "Never finance fixed asset expansion using short-term working capital lines.",
                    "Maintain continuous cash buffer equal to at least 4 months of fixed enterprise overhead."
                ),
                exitCriteria = "Unit operating costs verified at 18–25% below regional competitors; enterprise net margin > 20%."
            )
        )

        val killSwitches = listOf(
            KillSwitchTrigger(
                metric = "Overdue Accounts Receivable (>30 Days)",
                threshold = "Exceeds 15% of monthly revenue",
                requiredResponse = "Immediately halt shipments to delinquent accounts, shift all deliveries to COD only, and redirect volume to B2B cash partners."
            ),
            KillSwitchTrigger(
                metric = "Facility / Capital Utilization Rate",
                threshold = "Remains below 50% for 3 consecutive weeks",
                requiredResponse = "Activate emergency third-party commercial capacity leasing at wholesale rates to subsidize facility overhead."
            ),
            KillSwitchTrigger(
                metric = "Cash Runway Threshold",
                threshold = "Drops below 45 days of fixed overhead",
                requiredResponse = "Freeze all discretionary spending and new hiring; liquidate slow-moving stock at cost to replenish working cash."
            ),
            KillSwitchTrigger(
                metric = "Batch Quality Return Rate",
                threshold = "Exceeds 3.5% across two consecutive shipments",
                requiredResponse = "Pause intake from the responsible supplier, audit intake grading scales, and execute on-site supplier inspection."
            )
        )

        val compounding = "Compounding Resilience Engine: By enforcing strict cash liquidity buffers and turning operational scale into direct bulk pricing power, the enterprise turns time into an ally. As volume grows, per-unit risk declines, locking out competitors who lack backstage supply discipline."

        return RiskMitigationRoadmap(
            projectIdea = projectIdea,
            estimatedCapital = capital,
            strategicArchetype = archetype,
            executiveSummary = summary,
            topRisks = risks,
            roadmapPhases = phases,
            killSwitches = killSwitches,
            compoundingSafeguards = compounding,
            generatedWithAi = false,
            createdAt = System.currentTimeMillis()
        )
    }
}
