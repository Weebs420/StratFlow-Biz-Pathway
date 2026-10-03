package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.FrameworkLayer
import com.example.model.StrategyVocabulary
import com.example.ui.StratFlowViewModel
import com.example.ui.components.ArchetypeChip
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.PurpleFlywheel
import com.example.ui.theme.RoseRisk
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BuilderScreen(
    viewModel: StratFlowViewModel,
    modifier: Modifier = Modifier
) {
    val businessesText by viewModel.businessesText.collectAsStateWithLifecycle()
    val startingCapital by viewModel.startingCapital.collectAsStateWithLifecycle()
    val marketContext by viewModel.marketContext.collectAsStateWithLifecycle()
    val targetDemography by viewModel.targetDemography.collectAsStateWithLifecycle()
    val targetGender by viewModel.targetGender.collectAsStateWithLifecycle()
    val selectedTerms by viewModel.selectedTerms.collectAsStateWithLifecycle()
    val suggestionResult by viewModel.suggestionResult.collectAsStateWithLifecycle()
    val isGenerating by viewModel.isGenerating.collectAsStateWithLifecycle()
    val isSuggesting by viewModel.isSuggesting.collectAsStateWithLifecycle()

    var selectedLayerTab by remember { mutableStateOf<FrameworkLayer?>(null) }
    var filterFilterMode by remember { mutableStateOf("ALL") } // ALL, EXECUTIVE_11, BIG_4

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .widthIn(max = 720.dp)
        ) {
            // Header Hero Banner
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, AmberGold.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AmberGold.copy(alpha = 0.2f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = AmberGold,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Business Pathway Architect",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "3-Layer Framework & Risk Mitigation Engine",
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate400
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Slate850)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "\"A business-pathway archetype is a repeatable logic for sequencing capital, customers, capabilities, assets, and supporting systems to build a compounding business group.\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate300,
                            lineHeight = 17.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Preset Quick-Loads
                    Text(
                        text = "Quick-Start Group Scenarios:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AmberGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        viewModel.presets.forEach { preset ->
                            Box(
                                modifier = Modifier
                                    .testTag("preset_${preset.name.take(10).replace(" ", "_")}")
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Slate850)
                                    .border(1.dp, Slate700, RoundedCornerShape(8.dp))
                                    .clickable { viewModel.loadPreset(preset) }
                                    .padding(horizontal = 12.dp, vertical = 7.dp)
                            ) {
                                Text(
                                    text = preset.name,
                                    fontSize = 12.sp,
                                    color = CyanAccent,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SECTION 1: BUSINESS(ES)
            SectionHeader(
                title = "1. Business(es)",
                subtitle = "One per line or comma-separated",
                icon = Icons.Default.Business,
                badgeText = "${businessesText.lines().filter { it.isNotBlank() }.size} Entities"
            )

            OutlinedTextField(
                value = businessesText,
                onValueChange = { viewModel.onBusinessesChanged(it) },
                placeholder = {
                    Text(
                        "e.g.\nPrimary Crop Aggregation Hub\nSolar Cold-Storage Facility\nPackaged Branded Kitchen Staples",
                        color = Slate400
                    )
                },
                minLines = 3,
                maxLines = 6,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_businesses"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AmberGold,
                    unfocusedBorderColor = Slate700,
                    focusedContainerColor = Slate900,
                    unfocusedContainerColor = Slate900
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // SECTION 2: CAPITAL, CONTEXT, DEMOGRAPHY & GENDER
            SectionHeader(
                title = "2. Capital & Market Context",
                subtitle = "Starting capital, geography, demography & gender",
                icon = Icons.Default.MonetizationOn
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Slate800, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Starting Budget / Capital:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = startingCapital,
                        onValueChange = { viewModel.onCapitalChanged(it) },
                        placeholder = { Text("e.g. ৳25 Lakh, $50,000", color = Slate400) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = AmberGold)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_capital"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AmberGold,
                            unfocusedBorderColor = Slate700,
                            focusedContainerColor = Slate850,
                            unfocusedContainerColor = Slate850
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    // Quick capital presets
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("৳5 Lakh", "৳15 Lakh", "৳25 Lakh", "৳50 Lakh", "৳1 Crore", "$25K", "$100K").forEach { amount ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (startingCapital == amount) AmberGold.copy(alpha = 0.25f) else Slate800)
                                    .clickable { viewModel.onCapitalChanged(amount) }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = amount,
                                    fontSize = 11.sp,
                                    color = if (startingCapital == amount) AmberGold else Slate400,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Market Context (Specific Market / Frictions):",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = marketContext,
                        onValueChange = { viewModel.onContextChanged(it) },
                        placeholder = { Text("e.g. Bangladesh / Dhaka Agro wholesale to retail", color = Slate400) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Place, contentDescription = null, tint = CyanAccent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_context"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyanAccent,
                            unfocusedBorderColor = Slate700,
                            focusedContainerColor = Slate850,
                            unfocusedContainerColor = Slate850
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Demography / Audience:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = targetDemography,
                                onValueChange = { viewModel.onDemographyChanged(it) },
                                placeholder = { Text("e.g. Urban households", color = Slate400) },
                                singleLine = true,
                                leadingIcon = {
                                    Icon(Icons.Default.People, contentDescription = null, tint = PurpleFlywheel, modifier = Modifier.size(18.dp))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("input_demography"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PurpleFlywheel,
                                    unfocusedBorderColor = Slate700,
                                    focusedContainerColor = Slate850,
                                    unfocusedContainerColor = Slate850
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Gender Focus:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = targetGender,
                                onValueChange = { viewModel.onGenderChanged(it) },
                                placeholder = { Text("e.g. All / Women", color = Slate400) },
                                singleLine = true,
                                leadingIcon = {
                                    Icon(Icons.Default.Wc, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(18.dp))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("input_gender"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = EmeraldSuccess,
                                    unfocusedBorderColor = Slate700,
                                    focusedContainerColor = Slate850,
                                    unfocusedContainerColor = Slate850
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // SECTION 3: 3-LAYER STRATEGY TERMS & ARCHETYPES
            SectionHeader(
                title = "3. Select Strategy Terms & Archetypes",
                subtitle = "Layer 1: Archetype → Layer 2: Expansion → Layer 3: Compounding",
                icon = Icons.Default.Lightbulb,
                badgeText = "${selectedTerms.size} Active"
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Slate800, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Filter Mode Toggle: All vs Executive 11 vs Big 4
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(
                            selected = filterFilterMode == "ALL",
                            onClick = { filterFilterMode = "ALL" },
                            label = { Text("All Terms", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Slate700,
                                selectedLabelColor = Color.White
                            )
                        )
                        FilterChip(
                            selected = filterFilterMode == "BIG_4",
                            onClick = { filterFilterMode = "BIG_4" },
                            label = { Text("The Big 4 Archetypes", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AmberGold.copy(alpha = 0.25f),
                                selectedLabelColor = AmberGold
                            )
                        )
                        FilterChip(
                            selected = filterFilterMode == "EXECUTIVE_11",
                            onClick = { filterFilterMode = "EXECUTIVE_11" },
                            label = { Text("Executive 11 Terms", fontSize = 11.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(13.dp))
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CyanAccent.copy(alpha = 0.25f),
                                selectedLabelColor = CyanAccent
                            )
                        )
                        if (selectedTerms.isNotEmpty()) {
                            Text(
                                text = "Clear",
                                color = RoseRisk,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clickable { viewModel.clearTerms() }
                                    .padding(vertical = 8.dp, horizontal = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Layer tabs
                    val layers = FrameworkLayer.values().toList()
                    ScrollableTabRow(
                        selectedTabIndex = if (selectedLayerTab == null) 0 else layers.indexOf(selectedLayerTab) + 1,
                        containerColor = Slate850,
                        contentColor = AmberGold,
                        edgePadding = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Tab(
                            selected = selectedLayerTab == null,
                            onClick = { selectedLayerTab = null },
                            text = { Text("All Layers", fontSize = 12.sp) }
                        )
                        layers.forEach { l ->
                            Tab(
                                selected = selectedLayerTab == l,
                                onClick = { selectedLayerTab = l },
                                text = { Text(l.displayName, fontSize = 12.sp) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val filteredTerms = StrategyVocabulary.ALL_TERMS.filter { term ->
                        val matchesMode = when (filterFilterMode) {
                            "BIG_4" -> term.isTopArchetype
                            "EXECUTIVE_11" -> term.isExecutiveCore
                            else -> true
                        }
                        val matchesLayer = selectedLayerTab == null || term.layer == selectedLayerTab
                        matchesMode && matchesLayer
                    }

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        filteredTerms.forEach { term ->
                            val isSelected = selectedTerms.contains(term.name)
                            ArchetypeChip(
                                termName = term.name,
                                layer = term.layer,
                                isSelected = isSelected,
                                isExecutiveCore = term.isExecutiveCore,
                                isTopArchetype = term.isTopArchetype,
                                onToggle = { viewModel.toggleTerm(term.name) }
                            )
                        }
                    }

                    // Structured Syntax Formula Box
                    Spacer(modifier = Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Slate850)
                            .border(1.dp, AmberGold.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = AmberGold, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Syntax: [Archetype] + [Starting] + [Expansion] + [Operating] + [Compounding]",
                                    fontSize = 11.sp,
                                    color = AmberGold,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (selectedTerms.isNotEmpty()) selectedTerms.joinToString(" + ") else "Select archetypes & modifiers above to construct your formula",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedTerms.isNotEmpty()) Color.White else Slate400
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // SMART SUGGEST SECTION
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CyanAccent.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = CyanAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Smart Suggest Suitable Framework & Terms",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Evaluates your starting capital ($startingCapital) and market parameters to suggest the most appropriate archetypes, alternative businesses, and term refinements.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = { viewModel.smartSuggestFramework() },
                        enabled = !isSuggesting,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("button_smart_suggest"),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = CyanAccent),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (isSuggesting) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                color = CyanAccent,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Analyzing Pathway Dynamics...")
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Suggest Optimal Archetypes & Synergies", fontWeight = FontWeight.SemiBold)
                        }
                    }

                    AnimatedVisibility(visible = suggestionResult != null) {
                        suggestionResult?.let { res ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Slate850)
                                    .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                    .padding(14.dp)
                            ) {
                                Text(
                                    text = "Recommended Strategy Formula:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = CyanAccent
                                )
                                Text(
                                    text = res.recommendedFormula,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AmberGold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = res.strategicRationale,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 18.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = { viewModel.applySuggestedTerms() },
                                    colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Slate950),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("button_apply_suggested_terms")
                                ) {
                                    Text("Apply Recommended Formula (${res.recommendedTerms.joinToString(", ")})", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }

                                if (res.suggestedComplementaryBusinesses.isNotEmpty()) {
                                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Slate700)
                                    Text(
                                        text = "Complementary Businesses to Strengthen the Group:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldSuccess
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))

                                    res.suggestedComplementaryBusinesses.forEach { comp ->
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Slate900)
                                                .padding(10.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = comp.businessName,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(4.dp))
                                                        .background(AmberGold.copy(alpha = 0.2f))
                                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                                ) {
                                                    Text(comp.recommendedArchetype, fontSize = 10.sp, color = AmberGold)
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(text = comp.strategicSynergy, fontSize = 11.sp, color = Slate400)
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .clickable { viewModel.addComplementaryBusiness(comp.businessName) }
                                                    .padding(vertical = 2.dp)
                                            ) {
                                                Icon(Icons.Default.Add, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(14.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("+ Add to your businesses list", fontSize = 11.sp, color = EmeraldSuccess, fontWeight = FontWeight.SemiBold)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // PRIMARY GENERATE BUTTON
            Button(
                onClick = { viewModel.generatePathwayAndFramework() },
                enabled = !isGenerating,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("button_generate_framework"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmberGold,
                    contentColor = Slate950
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(
                        strokeWidth = 2.5.dp,
                        color = Slate950,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Architecting Full Pathway & Framework...",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Architect Pathway & Framework",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
