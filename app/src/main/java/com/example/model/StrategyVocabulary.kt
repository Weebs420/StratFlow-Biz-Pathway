package com.example.model

enum class FrameworkLayer(val displayName: String, val questionAnswered: String) {
    ARCHETYPE(
        "1. Strategic Archetype",
        "What is the overall strategic logic of the pathway?"
    ),
    STARTING_POINT(
        "2. Starting Point",
        "What comes first: customers, capital, assets, capabilities, or distribution?"
    ),
    EXPANSION_DIRECTION(
        "3. Expansion Direction",
        "Where and in which dimension does the business group grow?"
    ),
    OPERATING_DESIGN(
        "4. Operating Design",
        "How are activities, teams, and assets organized internally?"
    ),
    COMPOUNDING_ENGINE(
        "5. Compounding Engine",
        "Why do returns and competitive advantages grow stronger over time?"
    )
}

data class StrategyTerm(
    val id: String,
    val name: String,
    val layer: FrameworkLayer,
    val coreQuestion: String,
    val logic: String,
    val isTopArchetype: Boolean = false,
    val isExecutiveCore: Boolean = false,
    val exampleSyntax: String = "",
    val realWorldExample: String = ""
)

object StrategyVocabulary {

    const val CORE_FORMULA = "Strategy = Archetype + Starting Point + Expansion Direction + Operating Design + Compounding Engine"

    const val NAMING_SYNTAX = "[Archetype] + [Starting point] + [Expansion direction] + [Operating design] + [Compounding engine]"

    val DECISION_CHECKLIST = listOf(
        "What is scarce first: customers, capital, assets, capability, or distribution?",
        "Which asset will still be valuable in five years?",
        "Which activity can be reused across more than one business?",
        "What does each cycle improve for the next cycle?",
        "Where will the group capture value: margin, asset appreciation, recurring revenue, data, financing, or market power?"
    )

    const val DEFAULT_25_LAKH_FORMULA = "Original + Customer-first + Hybrid + Shadow + Flywheel"
    const val DEFAULT_25_LAKH_EXPLANATION = "Sell a proven product → generate cash → build supplier/logistics/data capability → lower cost → expand into adjacent businesses."

    val ALL_TERMS: List<StrategyTerm> = listOf(
        // ==================== 1. ARCHETYPES (THE FOUR) ====================
        StrategyTerm(
            id = "original",
            name = "Original",
            layer = FrameworkLayer.ARCHETYPE,
            coreQuestion = "What is the conventional route?",
            logic = "Customer → demand → capability → asset. Sell first, prove demand, build assets later.",
            isTopArchetype = true,
            isExecutiveCore = true,
            exampleSyntax = "Original + Customer-first + Horizontal + Integrated + Flywheel",
            realWorldExample = "Sell small-batch packaged goods to home cooks; invest in processing machinery only when order volume requires automation."
        ),
        StrategyTerm(
            id = "opposite",
            name = "Opposite",
            layer = FrameworkLayer.ARCHETYPE,
            coreQuestion = "What if the normal order is reversed?",
            logic = "Capital → asset → capability → distribution → customer. Build the foundation/processing first, then scale demand with cost leadership.",
            isTopArchetype = true,
            isExecutiveCore = true,
            exampleSyntax = "Opposite + Asset-first + Vertical + Integrated + Flywheel",
            realWorldExample = "Acquire commercial cold storage silos and grain sorting machinery first; use 20% lower processing costs to win wholesale supply contracts."
        ),
        StrategyTerm(
            id = "hybrid",
            name = "Hybrid",
            layer = FrameworkLayer.ARCHETYPE,
            coreQuestion = "What if two routes run together?",
            logic = "Run cash-flow generation and asset/capability building at the same time. Use one track to fund the other.",
            isTopArchetype = true,
            isExecutiveCore = true,
            exampleSyntax = "Hybrid + Customer-first + Shadow + Modular + Flywheel",
            realWorldExample = "Operate high-turnover retail trading to generate daily operating liquidity while quietly building proprietary agro-processing lines."
        ),
        StrategyTerm(
            id = "shadow",
            name = "Shadow",
            layer = FrameworkLayer.ARCHETYPE,
            coreQuestion = "What supporting system should be built behind the visible business?",
            logic = "The visible business earns revenue; a hidden procurement, logistics, data, technology, financing, or talent system becomes the real advantage.",
            isTopArchetype = true,
            isExecutiveCore = true,
            exampleSyntax = "Hybrid + Shadow + Vertical + Integrated + Flywheel",
            realWorldExample = "A visible restaurant group whose true competitive moat is an unpublicized bulk farm-gate procurement and chilled distribution fleet."
        ),

        // ==================== 2. STARTING POINT ====================
        StrategyTerm(
            id = "customer_first",
            name = "Customer-first",
            layer = FrameworkLayer.STARTING_POINT,
            coreQuestion = "How do we validate initial demand?",
            logic = "Secure committed paying customers before committing capital to inventory, premises, or equipment.",
            exampleSyntax = "Original + Customer-first",
            realWorldExample = "Pre-booking corporate catering contracts before leasing a central commercial kitchen."
        ),
        StrategyTerm(
            id = "capital_first",
            name = "Capital-first",
            layer = FrameworkLayer.STARTING_POINT,
            coreQuestion = "Can deep capital secure market leadership immediately?",
            logic = "Deploy financial reserves upfront to subsidize infrastructure, endure extended payback periods, or consolidate competitors.",
            exampleSyntax = "Opposite + Capital-first",
            realWorldExample = "Deploying ৳50L+ to buy out distressed regional warehouses at a 40% discount during market downturns."
        ),
        StrategyTerm(
            id = "asset_first",
            name = "Asset-first",
            layer = FrameworkLayer.STARTING_POINT,
            coreQuestion = "Which physical or digital infrastructure anchors our moat?",
            logic = "Secure critical physical property, specialized machinery, or technical IP before launching consumer sales.",
            exampleSyntax = "Opposite + Asset-first",
            realWorldExample = "Securing licensed cold-storage facilities and sorting grading lines before signing farm contracts."
        ),
        StrategyTerm(
            id = "capability_first",
            name = "Capability-first",
            layer = FrameworkLayer.STARTING_POINT,
            coreQuestion = "What operational craft must be mastered first?",
            logic = "Perfect quality control, unit economics, and operational efficiency before attempting multi-location expansion.",
            exampleSyntax = "Original + Capability-first",
            realWorldExample = "Operating a single pilot cloud kitchen for 90 days until food waste is reliably under 1.5%."
        ),
        StrategyTerm(
            id = "distribution_first",
            name = "Distribution-first",
            layer = FrameworkLayer.STARTING_POINT,
            coreQuestion = "How is channel access locked down before products exist?",
            logic = "Build direct distribution or access to retail shelves first, making product launch risk nearly zero.",
            exampleSyntax = "Original + Distribution-first",
            realWorldExample = "Building an active direct-delivery route servicing 400 neighborhood grocers before launching private-label staples."
        ),

        // ==================== 3. EXPANSION DIRECTION ====================
        StrategyTerm(
            id = "vertical",
            name = "Vertical",
            layer = FrameworkLayer.EXPANSION_DIRECTION,
            coreQuestion = "Where in the value chain do we expand?",
            logic = "Move up/down the value chain (upstream towards raw sourcing, downstream towards branded retail).",
            isExecutiveCore = true,
            exampleSyntax = "Vertical + Integrated",
            realWorldExample = "An apparel brand owning its yarn sourcing, knitting unit, and direct-to-consumer flagship storefronts."
        ),
        StrategyTerm(
            id = "horizontal",
            name = "Horizontal",
            layer = FrameworkLayer.EXPANSION_DIRECTION,
            coreQuestion = "What related products serve similar customers?",
            logic = "Add same-level businesses for similar customers/channels to maximize share of wallet.",
            isExecutiveCore = true,
            exampleSyntax = "Horizontal + Stack",
            realWorldExample = "A grocery retail line expanding into household cleaning, kitchenware, and personal care."
        ),
        StrategyTerm(
            id = "adjacent",
            name = "Adjacent",
            layer = FrameworkLayer.EXPANSION_DIRECTION,
            coreQuestion = "What nearby opportunity reuses our strategic assets?",
            logic = "Enter nearby opportunities that reuse a strategic asset, distribution channel, or operational capability.",
            isExecutiveCore = true,
            exampleSyntax = "Adjacent + Leverage",
            realWorldExample = "A refrigerated milk delivery fleet offering cold-transport services to pharmaceutical blood banks during midday downtime."
        ),
        StrategyTerm(
            id = "portfolio",
            name = "Portfolio",
            layer = FrameworkLayer.EXPANSION_DIRECTION,
            coreQuestion = "How do we allocate capital across independent units?",
            logic = "Own independent businesses under one group; focus on capital allocation and cash hedging across varied economic cycles.",
            exampleSyntax = "Portfolio + Modular",
            realWorldExample = "A business group owning an agro export agency, a commercial printing unit, and an equipment leasing enterprise."
        ),
        StrategyTerm(
            id = "umbrella",
            name = "Umbrella",
            layer = FrameworkLayer.EXPANSION_DIRECTION,
            coreQuestion = "How do brands share common governance?",
            logic = "Multiple businesses share a common parent identity, executive governance, legal compliance, or corporate brand equity.",
            exampleSyntax = "Umbrella + Ecosystem",
            realWorldExample = "A parent holding group providing legal, centralized banking credit lines, and executive oversight for three consumer subsidiaries."
        ),

        // ==================== 4. OPERATING DESIGN ====================
        StrategyTerm(
            id = "integrated",
            name = "Integrated",
            layer = FrameworkLayer.OPERATING_DESIGN,
            coreQuestion = "How tightly coupled are group activities?",
            logic = "One controlled system: Operations communicate and execute with unified governance and zero internal friction.",
            isExecutiveCore = true,
            exampleSyntax = "Integrated + Pipeline",
            realWorldExample = "Direct digital inventory link between field farm aggregators, city transit hubs, and retail grocery shelves."
        ),
        StrategyTerm(
            id = "modular",
            name = "Modular",
            layer = FrameworkLayer.OPERATING_DESIGN,
            coreQuestion = "Can components be connected or swapped independently?",
            logic = "Independent but connectable components that can service outside customers or pivot without breaking the parent company.",
            exampleSyntax = "Modular + Hub-and-spoke",
            realWorldExample = "An in-house packaging and sorting unit that packages group products while selling 40% excess capacity to external clients."
        ),
        StrategyTerm(
            id = "layered",
            name = "Layered",
            layer = FrameworkLayer.OPERATING_DESIGN,
            coreQuestion = "How do we build one tier upon another?",
            logic = "Build one capability strictly on top of another once the lower tier proves positive cash flow.",
            exampleSyntax = "Layered + Sequential",
            realWorldExample = "Layer 1: Raw wholesale; Layer 2: Cleaning and grading; Layer 3: Retail consumer packaging; Layer 4: Proprietary retail shops."
        ),
        StrategyTerm(
            id = "stack",
            name = "Stack",
            layer = FrameworkLayer.OPERATING_DESIGN,
            coreQuestion = "How do capabilities reinforce each other?",
            logic = "Accumulate complementary capabilities (supply + logistics + software + brand) that create an insurmountable barrier to entry.",
            exampleSyntax = "Stack + Data",
            realWorldExample = "Pairing proprietary logistics routing software, farmer micro-credit underwriting, and cold warehouses into an indivisible stack."
        ),
        StrategyTerm(
            id = "hub_and_spoke",
            name = "Hub-and-spoke",
            layer = FrameworkLayer.OPERATING_DESIGN,
            coreQuestion = "How does a central capability coordinate extensions?",
            logic = "A central capability coordinates dependent regional or product extensions.",
            exampleSyntax = "Hub-and-spoke + Modular",
            realWorldExample = "One central commercial preparation hub supplying ten neighborhood express pickup kiosks."
        ),
        StrategyTerm(
            id = "pipeline",
            name = "Pipeline",
            layer = FrameworkLayer.OPERATING_DESIGN,
            coreQuestion = "How do orders and goods move through stages?",
            logic = "Move customers, orders, or materials through defined, standardized throughput stages.",
            exampleSyntax = "Pipeline + Vertical",
            realWorldExample = "Direct line flow: Farmer intake → Automated optical grading → Vacuum packing → Scheduled supermarket delivery."
        ),

        // ==================== 5. COMPOUNDING ENGINE ====================
        StrategyTerm(
            id = "flywheel",
            name = "Flywheel",
            layer = FrameworkLayer.COMPOUNDING_ENGINE,
            coreQuestion = "How does each turn of the cycle improve the next turn?",
            logic = "A self-reinforcing loop where each cycle improves the economics or inputs of the next cycle (Volume → Lower Cost → Better Offer → More Volume).",
            isExecutiveCore = true,
            exampleSyntax = "Flywheel + Integrated",
            realWorldExample = "Higher order volumes unlock cheaper bulk farm prices, lowering retail prices, attracting more shoppers, driving higher volume."
        ),
        StrategyTerm(
            id = "ecosystem",
            name = "Ecosystem",
            layer = FrameworkLayer.COMPOUNDING_ENGINE,
            coreQuestion = "How do multiple businesses reinforce each other?",
            logic = "Multiple businesses/partners mutually reinforce one another, creating strong network stickiness and high switching costs.",
            isExecutiveCore = true,
            exampleSyntax = "Ecosystem + Platform",
            realWorldExample = "A group providing dairy processing, farmer veterinary care, cold transport, and retail dairy boutiques."
        ),
        StrategyTerm(
            id = "platform",
            name = "Platform",
            layer = FrameworkLayer.COMPOUNDING_ENGINE,
            coreQuestion = "How do we connect independent market sides?",
            logic = "Infrastructure connects two or more independent participant groups and reduces search, matching, and transaction friction.",
            isExecutiveCore = true,
            exampleSyntax = "Platform + Data",
            realWorldExample = "A B2B digital marketplace connecting rural potato growers directly with urban food processors without taking balance sheet risk."
        ),
        StrategyTerm(
            id = "data",
            name = "Data",
            layer = FrameworkLayer.COMPOUNDING_ENGINE,
            coreQuestion = "How do transactions compound intelligence?",
            logic = "Transactions create data → better forecasting → better offers & procurement → more transactions.",
            exampleSyntax = "Data + Flywheel",
            realWorldExample = "Tracking daily vegetable price movements across 50 wholesale markets to dynamically optimize forward buying contracts."
        ),
        StrategyTerm(
            id = "leverage",
            name = "Leverage",
            layer = FrameworkLayer.COMPOUNDING_ENGINE,
            coreQuestion = "How does one asset create multiple outputs?",
            logic = "One asset produces multiple outputs, multiplying revenue without proportional increases in capital expenditure.",
            exampleSyntax = "Leverage + Modular",
            realWorldExample = "A master spice grinding facility supplying private retail brands, bulk restaurant packaging, and export clients simultaneously."
        ),
        StrategyTerm(
            id = "network_effects",
            name = "Network effects",
            layer = FrameworkLayer.COMPOUNDING_ENGINE,
            coreQuestion = "Does value increase as more participants join?",
            logic = "Each new buyer, supplier, or delivery partner directly increases the value of the platform for all existing members.",
            exampleSyntax = "Platform + Network effects",
            realWorldExample = "A localized courier network where adding more neighborhood shops reduces delivery route density and lowers per-drop costs for all."
        )
    )

    val THE_BIG_4_ARCHETYPES: List<StrategyTerm> = ALL_TERMS.filter { it.isTopArchetype }
    val EXECUTIVE_11_TERMS: List<StrategyTerm> = ALL_TERMS.filter { it.isExecutiveCore }

    fun findById(id: String): StrategyTerm? = ALL_TERMS.find { it.id.equals(id, ignoreCase = true) }
    fun findByName(name: String): StrategyTerm? = ALL_TERMS.find { it.name.equals(name, ignoreCase = true) }
}
