package com.example.data.ai

import com.example.data.local.BlueprintJsonAdapter
import com.example.model.ComplementaryBusinessSuggestion
import com.example.model.FlywheelLoop
import com.example.model.FrameworkBlueprint
import com.example.model.PathwayStep
import com.example.model.ShadowLayerArchitecture
import com.example.model.StrategyVocabulary
import com.example.model.SuggestedTermRefinement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class FrameworkSuggestionResult(
    val recommendedFormula: String,
    val recommendedTerms: List<String>,
    val strategicRationale: String,
    val decisionChecklistAnswers: List<String>,
    val suggestedComplementaryBusinesses: List<ComplementaryBusinessSuggestion>,
    val termRefinements: List<SuggestedTermRefinement>
)

object StrategicSynthesizer {

    fun suggestFramework(
        businesses: List<String>,
        capital: String,
        context: String,
        demography: String,
        gender: String,
        currentSelectedTerms: List<String>
    ): FrameworkSuggestionResult {
        val bizText = businesses.joinToString(" ").lowercase()
        val capText = capital.lowercase()
        val ctxText = context.lowercase()

        val is25LakhRange = capText.contains("25") && (capText.contains("lakh") || capText.contains("lac") || capText.contains("l"))

        val isHighCapital = capText.contains("50") || capText.contains("crore") || capText.contains("cr") ||
                capText.contains("100,000") || capText.contains("million") || capText.contains("1 cr")

        val formula: String
        val recommendedTerms = mutableListOf<String>()
        val rationale: String
        val complementary = mutableListOf<ComplementaryBusinessSuggestion>()
        val termRefinements = mutableListOf<SuggestedTermRefinement>()
        val checklistAnswers = mutableListOf<String>()

        if (is25LakhRange) {
            // Practical Default for ৳25 Lakh: Original + Customer-first + Hybrid + Shadow + Flywheel
            formula = StrategyVocabulary.DEFAULT_25_LAKH_FORMULA
            recommendedTerms.addAll(listOf("Original", "Customer-first", "Hybrid", "Shadow", "Flywheel", "Vertical"))
            rationale = "For ৳25 Lakh in $context, the practical default is 'Original + Customer-first + Hybrid + Shadow + Flywheel'. Rather than locking scarce capital into heavy fixed assets immediately, you sell a proven product first to generate cash flow, concurrently build a shadow supplier/logistics/data backbone to lower unit cost, and use the expanding margin to enter adjacent businesses without external debt."

            checklistAnswers.add("1. What is scarce first: Capital preservation is paramount; do not lock ৳25 Lakh in illiquid property.")
            checklistAnswers.add("2. Which asset will still be valuable in 5 years: Proprietary supplier contracts, logistics fleet, and transaction data.")
            checklistAnswers.add("3. Which activity can be reused: The Shadow procurement hub can supply multiple sister retail brands.")
            checklistAnswers.add("4. What does each cycle improve: Bulk order velocity drops procurement cost by 15-22%.")
            checklistAnswers.add("5. Value capture: Retail product margins (28-35%) and shadow wholesale distribution spread.")

            complementary.add(
                ComplementaryBusinessSuggestion(
                    businessName = "Shadow Direct-Farmer Aggregation Node",
                    businessType = "Backstage Procurement & Quality Sorting",
                    strategicSynergy = "Bypasses local commission agents (Aratdars) to secure raw materials at wholesale source cost.",
                    whySuggested = "Protects gross margin by cutting out 12-18% middleman fees.",
                    recommendedArchetype = "Shadow + Vertical"
                )
            )
            complementary.add(
                ComplementaryBusinessSuggestion(
                    businessName = "Value-Added Branded Packaging Line",
                    businessType = "Downstream Retail Channel",
                    strategicSynergy = "Packages sorted commodities under a consumer brand targeting $demography.",
                    whySuggested = "Bulk trading alone has razor-thin margins; branded packaging captures 30%+ net margins.",
                    recommendedArchetype = "Horizontal + Adjacent"
                )
            )
            complementary.add(
                ComplementaryBusinessSuggestion(
                    businessName = "Shared Micro-Logistics Backhaul Fleet",
                    businessType = "Distribution Infrastructure",
                    strategicSynergy = "Delivers customer goods in the morning and carries farm produce on return trips.",
                    whySuggested = "Eliminates empty return miles and turns transport overhead into a profit-sharing asset.",
                    recommendedArchetype = "Modular + Leverage"
                )
            )

            termRefinements.add(
                SuggestedTermRefinement(
                    termName = "Shadow",
                    category = "Archetype",
                    rationale = "Keeps your backstage sourcing, processing, and logistics hidden from copycat competitors.",
                    formulaImpact = "Preserves your low-cost pricing moat."
                )
            )
            termRefinements.add(
                SuggestedTermRefinement(
                    termName = "Customer-first",
                    category = "Starting Point",
                    rationale = "Guarantees that cash flow from retail sales is active on Day 1 before major capital commitments.",
                    formulaImpact = "Eliminates working-capital starvation."
                )
            )
        } else if (isHighCapital) {
            // High Capital: Opposite + Asset-first + Vertical + Integrated + Flywheel
            formula = "Opposite + Asset-first + Vertical + Integrated + Flywheel"
            recommendedTerms.addAll(listOf("Opposite", "Asset-first", "Vertical", "Integrated", "Flywheel", "Shadow"))
            rationale = "With substantial capital ($capital) in $context, the 'Opposite Model' flips the conventional sequence. You acquire high-yield processing/storage infrastructure upfront (Asset-first), secure vertical margin control from source to shelf, and activate a self-reinforcing scale flywheel."

            checklistAnswers.add("1. What is scarce first: Infrastructure access and high-capacity processing bottlenecks.")
            checklistAnswers.add("2. 5-Year Asset: High-capacity commercial storage and proprietary sorting technology.")
            checklistAnswers.add("3. Reusable Activity: Centralized grading, packaging, and cold-transit fleet.")
            checklistAnswers.add("4. Cycle Improvement: Larger throughput drives down per-unit processing cost below any competitor.")
            checklistAnswers.add("5. Value Capture: Asset appreciation, terminal processing fees, and full vertical retail margin.")

            complementary.add(
                ComplementaryBusinessSuggestion(
                    businessName = "Contract Farming & Pre-Harvest Financing Syndicate",
                    businessType = "Upstream Integration",
                    strategicSynergy = "Guarantees exclusive supply to your processing asset 60 days before peak harvest.",
                    whySuggested = "Locks in raw inventory and stabilizes factory capacity utilization above 85%.",
                    recommendedArchetype = "Vertical + Asset-first"
                )
            )
            complementary.add(
                ComplementaryBusinessSuggestion(
                    businessName = "Omnichannel FMCG Distribution Network",
                    businessType = "Downstream Channel Access",
                    strategicSynergy = "Places processed goods across 500+ verified retail and supermarket partners.",
                    whySuggested = "High processing volume requires guaranteed off-take channels.",
                    recommendedArchetype = "Distribution-first + Horizontal"
                )
            )
        } else {
            // Balanced General Strategy
            formula = "Hybrid + Customer-first + Adjacent + Modular + Flywheel"
            recommendedTerms.addAll(listOf("Hybrid", "Customer-first", "Adjacent", "Modular", "Flywheel", "Shadow"))
            rationale = "For your business portfolio with starting capital of $capital in $context, a 'Hybrid + Customer-first' approach secures early customer liquidity while modularizing operational units so they can expand into high-margin adjacent spaces without breaking group cash flow."

            checklistAnswers.add("1. What is scarce first: Customer trust and immediate cash-flow velocity.")
            checklistAnswers.add("2. 5-Year Asset: Customer distribution relationship and proprietary order data.")
            checklistAnswers.add("3. Reusable Activity: Marketing, sales team, customer support, and delivery infrastructure.")
            checklistAnswers.add("4. Cycle Improvement: Increased order volume attracts higher-tier suppliers at better terms.")
            checklistAnswers.add("5. Value Capture: High customer lifetime value (LTV) and multi-product cross-selling.")

            complementary.add(
                ComplementaryBusinessSuggestion(
                    businessName = "Shared Operations & Treasury Backbone",
                    businessType = "Central Coordination",
                    strategicSynergy = "Consolidates accounting, order dispatch, and credit control across all ventures.",
                    whySuggested = "Prevents administrative overhead from multiplying as you add business lines.",
                    recommendedArchetype = "Hub-and-spoke + Modular"
                )
            )
        }

        return FrameworkSuggestionResult(
            recommendedFormula = formula,
            recommendedTerms = recommendedTerms,
            strategicRationale = rationale,
            decisionChecklistAnswers = checklistAnswers,
            suggestedComplementaryBusinesses = complementary,
            termRefinements = termRefinements
        )
    }

    suspend fun generateBlueprint(
        businesses: List<String>,
        capital: String,
        context: String,
        demography: String,
        gender: String,
        selectedTerms: List<String>
    ): FrameworkBlueprint = withContext(Dispatchers.Default) {
        val cleanBusinesses = businesses.map { it.trim() }.filter { it.isNotBlank() }
        val effectiveBizList = if (cleanBusinesses.isEmpty()) {
            listOf("Core Business Operations", "Supply & Sourcing Hub", "Branded Distribution Channel")
        } else {
            cleanBusinesses
        }

        val effectiveTerms = if (selectedTerms.isEmpty()) {
            listOf("Original", "Customer-first", "Hybrid", "Shadow", "Flywheel")
        } else {
            selectedTerms
        }

        // Try Gemini REST API if key is available
        if (GeminiClient.isConfigured()) {
            val aiResult = tryGenerateWithGemini(
                businesses = effectiveBizList,
                capital = capital,
                context = context,
                demography = demography,
                gender = gender,
                selectedTerms = effectiveTerms
            )
            if (aiResult != null) {
                return@withContext aiResult
            }
        }

        // Built-in strategic reasoner
        generateHeuristicBlueprint(
            businesses = effectiveBizList,
            capital = capital,
            context = context,
            demography = demography,
            gender = gender,
            selectedTerms = effectiveTerms
        )
    }

    private suspend fun tryGenerateWithGemini(
        businesses: List<String>,
        capital: String,
        context: String,
        demography: String,
        gender: String,
        selectedTerms: List<String>
    ): FrameworkBlueprint? = withContext(Dispatchers.IO) {
        try {
            val systemPrompt = """
You are a world-class venture architect, strategy professor, and corporate finance advisor.
Analyze the user's business inputs, starting capital, market context, demography, gender focus, and 5-layer strategic framework:
Formula syntax: [Archetype] + [Starting point] + [Expansion direction] + [Operating design] + [Compounding engine]
Four archetypes: Original, Opposite, Hybrid, Shadow.
For each business step in the pathway, provide:
1. Why it will work (Pros & Catalysts)
2. Why it might not work (Cons & Risks)
3. Why / how to remove the problem (Root causes & Systemic fixes)
4. Actionable steps to mitigate potential risks and overcome barriers.
Return strict JSON matching the required schema.
""".trimIndent()

            val userPrompt = """
Businesses: ${businesses.joinToString(", ")}
Starting Capital: $capital
Market Context: $context
Demography: $demography
Gender: $gender
Selected Terms: ${selectedTerms.joinToString(" + ")}

Generate the complete strategic blueprint with deep, practical, non-generic advice in valid JSON format.
""".trimIndent()

            val jsonOutput = GeminiClient.generateContent(systemPrompt, userPrompt) ?: return@withContext null
            val blueprint = BlueprintJsonAdapter.fromJson(jsonOutput)
            blueprint.copy(
                businessesInput = businesses,
                startingCapital = capital,
                marketContext = context,
                targetDemography = demography,
                targetGender = gender,
                selectedTerms = selectedTerms
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun generateHeuristicBlueprint(
        businesses: List<String>,
        capital: String,
        context: String,
        demography: String,
        gender: String,
        selectedTerms: List<String>
    ): FrameworkBlueprint {
        val suggestion = suggestFramework(businesses, capital, context, demography, gender, selectedTerms)
        val formula = if (selectedTerms.isNotEmpty()) {
            selectedTerms.joinToString(" + ")
        } else {
            suggestion.recommendedFormula
        }

        val steps = mutableListOf<PathwayStep>()
        val capLabel = if (capital.isBlank()) "Budget Reserve" else capital
        val ctxLabel = if (context.isBlank()) "Target Market" else context

        businesses.forEachIndexed { index, bizName ->
            val stepNum = index + 1
            val assignedTerm = selectedTerms.getOrNull(index % selectedTerms.size)
                ?: when (stepNum) {
                    1 -> "Customer-first"
                    2 -> "Hybrid"
                    3 -> "Shadow"
                    4 -> "Vertical"
                    else -> "Flywheel"
                }

            val isFirstStep = stepNum == 1
            val isFinalStep = stepNum == businesses.size

            val role = when {
                isFirstStep -> "Frontline Revenue Engine & Demand Validation"
                isFinalStep -> "High-Margin Terminal Brand Equity & Customer Loyalty"
                else -> "Operational Backbone, Sorting & Value Multiplication"
            }

            val category = when (stepNum % 3) {
                1 -> "Upstream Sourcing / Aggregation Node"
                2 -> "Midstream Processing & Logistics Backbone"
                else -> "Downstream Customer & Retail Channel"
            }

            val capAllocation = when (stepNum) {
                1 -> "40% of $capLabel (Demand generation, initial inventory & liquid reserve)"
                2 -> "35% of $capLabel (Processing tools, storage & shadow logistics)"
                else -> "25% of $capLabel (Channel distribution, marketing & credit cushion)"
            }

            val kpi = when (stepNum) {
                1 -> "Cash Flow Positive within 60 Days; Repeat Buyer Rate > 35%"
                2 -> "Operating Waste < 1.8%; Turnaround Cycle < 48 Hours"
                else -> "Net Profit Margin > 22%; Customer Acquisition Payback < 25 Days"
            }

            val whyWork = listOf(
                "Directly addresses unmet demand in $ctxLabel by eliminating layers of unnecessary brokers and middlemen.",
                "Executes the $assignedTerm methodology to minimize fixed overhead, validating unit economics with real paying customers before expansion.",
                "Designed around the behavioral habits of $demography ${if (gender.isNotBlank()) "focusing on $gender segments" else ""}, creating organic word-of-mouth stickiness.",
                "Creates proprietary transaction and customer data that compounds every month, enabling automated re-ordering and predictive inventory stocking."
            )

            val whyNotWork = listOf(
                "Working Capital Freeze: Receivables trapped in credit terms with commercial distributors, delaying supplier payouts.",
                "Seasonal Supply Shock: Weather disruptions or localized commodity price spikes eroding gross margins.",
                "Incumbent Price Attacks: Traditional brokers temporarily undercutting prices to force the new venture out of the local market.",
                "Operational Execution Drift: Expanding into downstream packaging before mastering quality grading and inventory shrinkage."
            )

            val rootCause = "Root Cause Analysis: Ventures in $ctxLabel typically collapse not from lack of demand, but from 'Structural Working Capital Asphyxiation' and lack of backstage supply control. To permanently remove this problem: enforce strict 50-70% prepayment or collateralized escrow on commercial orders, build a dedicated Shadow supplier network that offers growers faster payments than traditional brokers, and establish locked forward contracts 45 days prior to harvest."

            val mitigationSteps = listOf(
                "Phase 1 (Days 1–30): Implement Zero-Credit Discipline—mandate upfront deposits on bulk orders; negotiate 15-day supplier credit terms backed by guaranteed purchase minimums.",
                "Phase 2 (Days 31–90): Build the Shadow Supplier Hub—onboard at least 5 primary production partners with transparent digital quality grading and 48-hour wire settlements.",
                "Phase 3 (Days 91–180): Lock in the Compounding Flywheel—reinvest 50% of monthly operating surplus into value-added processing and direct customer delivery to widen the cost advantage.",
                "Permanent Operational Guardrail: Maintain a mandatory 90-day cash operating buffer held strictly outside the daily working capital pool."
            )

            steps.add(
                PathwayStep(
                    stepIndex = stepNum,
                    title = "Phase $stepNum: Deploy $bizName",
                    businessName = bizName,
                    category = category,
                    strategicRole = role,
                    primaryTerm = assignedTerm,
                    sequenceRationale = if (isFirstStep) {
                        "Must launch first to generate immediate liquidity and prove customer willingness to pay before committing capital to backend assets."
                    } else {
                        "Deployed sequentially once Phase ${stepNum - 1} reaches steady-state cash breakeven, absorbing output without external debt."
                    },
                    whyItWillWork = whyWork,
                    whyItMightNotWork = whyNotWork,
                    rootCauseAndWhyToRemoveProblem = rootCause,
                    actionableRiskMitigationSteps = mitigationSteps,
                    estimatedCapitalAllocation = capAllocation,
                    keyPerformanceIndicator = kpi
                )
            )
        }

        val shadow = ShadowLayerArchitecture(
            procurementEngine = "Direct-source aggregation mechanism running in parallel to bypass traditional broker rings; forward supply agreements locked 45 days before seasonal price hikes.",
            dataCompounding = "Unified digital ledger logging daily transaction prices, seasonal yields, customer repeat intervals, and SKU-level contribution margins.",
            logisticsBackbone = "Hybrid dedicated micro-transit and coordinated backhaul routing to ensure trucks never travel empty, reducing delivery overhead by 24%.",
            supplierNetwork = "Incentivized farmer/grower syndicate with transparent digital weighing, consistent grading, and 48-hour settlement guarantees.",
            techStack = "Lightweight mobile order taking, automated WhatsApp customer replenishment notifications, and real-time cloud accounting dashboard."
        )

        val flywheel = FlywheelLoop(
            stages = listOf(
                "Acquire Target Customers in $ctxLabel ($demography)",
                "Generate Predictable Daily Cash Flow & Transaction Volume",
                "Compound Aggregate Order Volume to Secure Direct Bulk Farm/Factory Pricing",
                "Reduce Unit Procurement Costs by 15-25% Below Fragmented Competitors",
                "Reinvest Margin Surplus into Better Customer Pricing & Proprietary Processing Assets",
                "Expand Market Dominance Attracting More High-Value Customers"
            ),
            compoundingNarrative = "Each customer won increases aggregate procurement volume across the group. As procurement scale grows, unit costs fall, funding superior quality guarantees and lower prices, which in turn drives the next cycle of organic acquisition without escalating marketing expenses."
        )

        val summary = "An actionable, 5-layer business pathway architected for $capLabel in $ctxLabel. Governed by the $formula framework to systematically de-risk execution, secure backstage operational advantages, and build an enduring, compounding business group."

        return FrameworkBlueprint(
            title = "${businesses.firstOrNull() ?: "Enterprise"} Strategic Pathway & Framework",
            strategyFormula = formula,
            businessesInput = businesses,
            startingCapital = capital,
            marketContext = context,
            targetDemography = demography,
            targetGender = gender,
            selectedTerms = selectedTerms,
            steps = steps,
            shadowArchitecture = shadow,
            flywheel = flywheel,
            complementaryBusinesses = suggestion.suggestedComplementaryBusinesses,
            suggestedTerms = suggestion.termRefinements,
            executiveSummary = summary,
            createdAt = System.currentTimeMillis()
        )
    }
}
