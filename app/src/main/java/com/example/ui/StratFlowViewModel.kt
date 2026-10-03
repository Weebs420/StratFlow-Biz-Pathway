package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.FrameworkSuggestionResult
import com.example.data.ai.RiskSynthesizer
import com.example.data.ai.StrategicSynthesizer
import com.example.data.local.AppDatabase
import com.example.data.local.BlueprintEntity
import com.example.data.repository.PathwayRepository
import com.example.model.FrameworkBlueprint
import com.example.model.FrameworkLayer
import com.example.model.RiskMitigationRoadmap
import com.example.model.StrategyVocabulary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppNavTab(val label: String) {
    BUILDER("Builder"),
    RISK_ROADMAP("Risk Roadmap"),
    BLUEPRINT("Pathway & Framework"),
    SAVED("Saved"),
    GLOSSARY("Vocabulary")
}

data class PresetIdea(
    val name: String,
    val businesses: String,
    val capital: String,
    val context: String,
    val demography: String,
    val gender: String,
    val defaultTerms: List<String>
)

class StratFlowViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PathwayRepository

    init {
        val db = AppDatabase.getInstance(application)
        repository = PathwayRepository(db.blueprintDao())
    }

    val savedBlueprints: StateFlow<List<BlueprintEntity>> = repository.allBlueprints
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _navTab = MutableStateFlow(AppNavTab.BUILDER)
    val navTab: StateFlow<AppNavTab> = _navTab.asStateFlow()

    private val _businessesText = MutableStateFlow("Primary Crop Aggregation Hub\nSolar Cold-Storage Facility\nPackaged Branded Kitchen Staples")
    val businessesText: StateFlow<String> = _businessesText.asStateFlow()

    private val _startingCapital = MutableStateFlow("৳25 Lakh")
    val startingCapital: StateFlow<String> = _startingCapital.asStateFlow()

    private val _marketContext = MutableStateFlow("Bangladesh Peri-Urban Wholesale to City Consumers")
    val marketContext: StateFlow<String> = _marketContext.asStateFlow()

    private val _targetDemography = MutableStateFlow("Urban Middle Class & Small Restaurant Owners")
    val targetDemography: StateFlow<String> = _targetDemography.asStateFlow()

    private val _targetGender = MutableStateFlow("All / Family Decision Makers")
    val targetGender: StateFlow<String> = _targetGender.asStateFlow()

    private val _selectedTerms = MutableStateFlow<Set<String>>(
        setOf("Original", "Customer-first", "Hybrid", "Shadow", "Flywheel")
    )
    val selectedTerms: StateFlow<Set<String>> = _selectedTerms.asStateFlow()

    private val _selectedArchetypeLens = MutableStateFlow("Original")
    val selectedArchetypeLens: StateFlow<String> = _selectedArchetypeLens.asStateFlow()

    val archetypeLenses = listOf("Original", "Opposite", "Hybrid", "Shadow")

    private val _currentBlueprint = MutableStateFlow<FrameworkBlueprint?>(null)
    val currentBlueprint: StateFlow<FrameworkBlueprint?> = _currentBlueprint.asStateFlow()

    private val _suggestionResult = MutableStateFlow<FrameworkSuggestionResult?>(null)
    val suggestionResult: StateFlow<FrameworkSuggestionResult?> = _suggestionResult.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _isSuggesting = MutableStateFlow(false)
    val isSuggesting: StateFlow<Boolean> = _isSuggesting.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    private val _selectedGlossaryLayer = MutableStateFlow<FrameworkLayer?>(null)
    val selectedGlossaryLayer: StateFlow<FrameworkLayer?> = _selectedGlossaryLayer.asStateFlow()

    val presets = listOf(
        PresetIdea(
            name = "৳25 Lakh Practical Default Group",
            businesses = "Primary Crop Aggregation Hub\nSolar Cold-Storage Facility\nPackaged Branded Kitchen Staples",
            capital = "৳25 Lakh",
            context = "Bangladesh Peri-Urban Wholesale to City Consumers",
            demography = "Urban Middle Class & Small Restaurant Owners",
            gender = "All / Family Decision Makers",
            defaultTerms = listOf("Original", "Customer-first", "Hybrid", "Shadow", "Flywheel")
        ),
        PresetIdea(
            name = "Opposite Model Asset-Backed Setup",
            businesses = "Central Grain Silo & Cold Warehouse\nWholesale Contract Logistics Fleet\nDownstream Supermarket Supply",
            capital = "৳60 Lakh",
            context = "Regional Agro Logistics & Processing",
            demography = "Enterprise Food Processors & Hotel Chains",
            gender = "All",
            defaultTerms = listOf("Opposite", "Asset-first", "Vertical", "Integrated", "Flywheel")
        ),
        PresetIdea(
            name = "Fashion Manufacturing & D2C Brand",
            businesses = "In-house Micro Stitching & Dyeing Unit\nB2B White-Label Garment Supply\nPremium Sustainable D2C Streetwear Label",
            capital = "$35,000",
            context = "South Asian Apparel Export & Domestic Urban E-commerce",
            demography = "Gen Z & Young Working Professionals (18-32)",
            gender = "Unisex / Female focus",
            defaultTerms = listOf("Hybrid", "Shadow", "Vertical", "Horizontal", "Flywheel")
        ),
        PresetIdea(
            name = "B2B SaaS & Tech Agency Ecosystem",
            businesses = "Enterprise Custom Automation Agency\nProprietary CRM & Billing Micro-SaaS\nDeveloper Training & Talent Placement Hub",
            capital = "$20,000",
            context = "Global Remote & Emerging Market SMEs",
            demography = "SMB Founders & Tech Executives",
            gender = "All",
            defaultTerms = listOf("Original", "Customer-first", "Ecosystem", "Flywheel", "Modular")
        )
    )

    fun setNavTab(tab: AppNavTab) {
        _navTab.value = tab
    }

    fun onBusinessesChanged(newText: String) {
        _businessesText.value = newText
    }

    fun onCapitalChanged(newText: String) {
        _startingCapital.value = newText
    }

    fun onContextChanged(newText: String) {
        _marketContext.value = newText
    }

    fun onDemographyChanged(newText: String) {
        _targetDemography.value = newText
    }

    fun onGenderChanged(newText: String) {
        _targetGender.value = newText
    }

    fun toggleTerm(termName: String) {
        val current = _selectedTerms.value.toMutableSet()
        if (current.contains(termName)) {
            current.remove(termName)
        } else {
            current.add(termName)
        }
        _selectedTerms.value = current
    }

    fun setTerms(terms: List<String>) {
        _selectedTerms.value = terms.toSet()
    }

    fun clearTerms() {
        _selectedTerms.value = emptySet()
    }

    fun setGlossaryLayer(layer: FrameworkLayer?) {
        _selectedGlossaryLayer.value = layer
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }

    fun loadPreset(preset: PresetIdea) {
        _businessesText.value = preset.businesses
        _startingCapital.value = preset.capital
        _marketContext.value = preset.context
        _targetDemography.value = preset.demography
        _targetGender.value = preset.gender
        _selectedTerms.value = preset.defaultTerms.toSet()
        _userMessage.value = "Loaded preset: ${preset.name}"
    }

    fun smartSuggestFramework() {
        viewModelScope.launch {
            _isSuggesting.value = true
            try {
                val bizList = parseBusinesses(_businessesText.value)
                val suggestion = StrategicSynthesizer.suggestFramework(
                    businesses = bizList,
                    capital = _startingCapital.value,
                    context = _marketContext.value,
                    demography = _targetDemography.value,
                    gender = _targetGender.value,
                    currentSelectedTerms = _selectedTerms.value.toList()
                )
                _suggestionResult.value = suggestion
                _userMessage.value = "Optimal framework suggested!"
            } catch (e: Exception) {
                _userMessage.value = "Failed to suggest framework: ${e.message}"
            } finally {
                _isSuggesting.value = false
            }
        }
    }

    fun applySuggestedTerms() {
        val suggestion = _suggestionResult.value ?: return
        val updated = _selectedTerms.value.toMutableSet()
        updated.addAll(suggestion.recommendedTerms)
        _selectedTerms.value = updated
        _userMessage.value = "Applied recommended archetypes & formula!"
    }

    fun addComplementaryBusiness(bizName: String) {
        val current = _businessesText.value.trim()
        val updated = if (current.isEmpty()) bizName else "$current\n$bizName"
        _businessesText.value = updated
        _userMessage.value = "Added '$bizName' to your businesses list!"
    }

    fun setArchetypeLens(newLens: String, triggerRegeneration: Boolean = true) {
        _selectedArchetypeLens.value = newLens
        val currentTerms = _selectedTerms.value.toMutableSet()
        // Remove existing top archetypes
        currentTerms.removeAll(setOf("Original", "Opposite", "Hybrid", "Shadow"))
        currentTerms.add(newLens)

        if (newLens == "Opposite") {
            currentTerms.remove("Customer-first")
            currentTerms.add("Asset-first")
            if (!currentTerms.contains("Vertical")) currentTerms.add("Vertical")
            if (!currentTerms.contains("Flywheel")) currentTerms.add("Flywheel")
        } else if (newLens == "Original") {
            currentTerms.remove("Asset-first")
            currentTerms.remove("Capital-first")
            currentTerms.add("Customer-first")
        } else if (newLens == "Hybrid") {
            currentTerms.add("Customer-first")
            currentTerms.add("Modular")
        } else if (newLens == "Shadow") {
            currentTerms.add("Integrated")
            currentTerms.add("Vertical")
        }
        _selectedTerms.value = currentTerms

        if (triggerRegeneration && _currentBlueprint.value != null) {
            generatePathwayAndFramework(overrideTerms = currentTerms.toList())
            _userMessage.value = "Framework transformed to $newLens Perspective!"
        }
    }

    fun invertToOppositeOrOriginal() {
        val next = if (_selectedArchetypeLens.value == "Opposite") "Original" else "Opposite"
        setArchetypeLens(next, triggerRegeneration = true)
    }

    fun generatePathwayAndFramework(overrideTerms: List<String>? = null) {
        viewModelScope.launch {
            val bizList = parseBusinesses(_businessesText.value)
            if (bizList.isEmpty()) {
                _userMessage.value = "Please enter at least one business entity!"
                return@launch
            }

            _isGenerating.value = true
            try {
                val termsToUse = overrideTerms ?: _selectedTerms.value.toList()
                val blueprint = StrategicSynthesizer.generateBlueprint(
                    businesses = bizList,
                    capital = _startingCapital.value,
                    context = _marketContext.value,
                    demography = _targetDemography.value,
                    gender = _targetGender.value,
                    selectedTerms = termsToUse
                )
                _currentBlueprint.value = blueprint
                _navTab.value = AppNavTab.BLUEPRINT
                _userMessage.value = "Strategic pathway successfully architected!"
            } catch (e: Exception) {
                _userMessage.value = "Error generating framework: ${e.message}"
            } finally {
                _isGenerating.value = false
            }
        }
    }

    fun saveCurrentBlueprint() {
        val bp = _currentBlueprint.value ?: return
        viewModelScope.launch {
            try {
                val savedId = repository.saveBlueprint(bp)
                _currentBlueprint.value = bp.copy(id = savedId)
                _userMessage.value = "Framework saved to your blueprints vault!"
            } catch (e: Exception) {
                _userMessage.value = "Failed to save framework: ${e.message}"
            }
        }
    }

    fun loadBlueprint(id: Long) {
        viewModelScope.launch {
            try {
                val bp = repository.getBlueprintById(id)
                if (bp != null) {
                    _currentBlueprint.value = bp
                    _businessesText.value = bp.businessesInput.joinToString("\n")
                    _startingCapital.value = bp.startingCapital
                    _marketContext.value = bp.marketContext
                    _targetDemography.value = bp.targetDemography
                    _targetGender.value = bp.targetGender
                    _selectedTerms.value = bp.selectedTerms.toSet()
                    _navTab.value = AppNavTab.BLUEPRINT
                    _userMessage.value = "Loaded blueprint: ${bp.title}"
                }
            } catch (e: Exception) {
                _userMessage.value = "Failed to load blueprint: ${e.message}"
            }
        }
    }

    fun deleteBlueprint(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteBlueprint(id)
                _userMessage.value = "Blueprint removed."
            } catch (e: Exception) {
                _userMessage.value = "Error deleting blueprint: ${e.message}"
            }
        }
    }

    // ==========================================
    // RISK MITIGATION ROADMAP (GEMINI API)
    // ==========================================

    private val _riskProjectIdea = MutableStateFlow("Direct-to-Consumer Organic Cold-Pressed Oil & Kitchen Staples")
    val riskProjectIdea: StateFlow<String> = _riskProjectIdea.asStateFlow()

    private val _riskCapital = MutableStateFlow("৳25 Lakh")
    val riskCapital: StateFlow<String> = _riskCapital.asStateFlow()

    private val _riskArchetype = MutableStateFlow("Original (Customer-First)")
    val riskArchetype: StateFlow<String> = _riskArchetype.asStateFlow()

    private val _currentRiskRoadmap = MutableStateFlow<RiskMitigationRoadmap?>(null)
    val currentRiskRoadmap: StateFlow<RiskMitigationRoadmap?> = _currentRiskRoadmap.asStateFlow()

    private val _isGeneratingRiskRoadmap = MutableStateFlow(false)
    val isGeneratingRiskRoadmap: StateFlow<Boolean> = _isGeneratingRiskRoadmap.asStateFlow()

    fun onRiskProjectIdeaChanged(text: String) { _riskProjectIdea.value = text }
    fun onRiskCapitalChanged(text: String) { _riskCapital.value = text }
    fun onRiskArchetypeChanged(archetype: String) { _riskArchetype.value = archetype }

    fun loadCurrentBuilderIntoRiskRoadmap() {
        val bizList = parseBusinesses(_businessesText.value)
        if (bizList.isNotEmpty()) {
            _riskProjectIdea.value = bizList.joinToString(" + ")
        }
        _riskCapital.value = _startingCapital.value
        _riskArchetype.value = when (_selectedArchetypeLens.value) {
            "Opposite" -> "Opposite (Asset-First Inversion)"
            "Hybrid" -> "Hybrid (Dual-Track)"
            "Shadow" -> "Shadow (Backstage Moat)"
            else -> "Original (Customer-First)"
        }
        _navTab.value = AppNavTab.RISK_ROADMAP
        generateRiskRoadmap()
    }

    fun generateRiskRoadmap() {
        viewModelScope.launch {
            if (_riskProjectIdea.value.isBlank()) {
                _userMessage.value = "Please enter a project idea!"
                return@launch
            }

            _isGeneratingRiskRoadmap.value = true
            try {
                val roadmap = RiskSynthesizer.generateRiskRoadmap(
                    projectIdea = _riskProjectIdea.value.trim(),
                    capital = _riskCapital.value.trim(),
                    archetype = _riskArchetype.value
                )
                _currentRiskRoadmap.value = roadmap
                val source = if (roadmap.generatedWithAi) "Gemini API" else "Executive Risk Engine"
                _userMessage.value = "Risk Mitigation Roadmap generated ($source)!"
            } catch (e: Exception) {
                _userMessage.value = "Failed to generate roadmap: ${e.message}"
            } finally {
                _isGeneratingRiskRoadmap.value = false
            }
        }
    }

    private fun parseBusinesses(text: String): List<String> {
        val lines = text.split("\n", ",").map { it.trim() }.filter { it.isNotBlank() }
        return lines.distinct()
    }
}
