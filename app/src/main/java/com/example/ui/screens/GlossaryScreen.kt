package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.model.StrategyTerm
import com.example.model.StrategyVocabulary
import com.example.ui.StratFlowViewModel
import com.example.ui.components.LayerColor
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.PurpleFlywheel
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900

@Composable
fun GlossaryScreen(
    viewModel: StratFlowViewModel,
    modifier: Modifier = Modifier
) {
    val selectedTerms by viewModel.selectedTerms.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    var selectedLayerTab by remember { mutableStateOf<FrameworkLayer?>(null) }
    var filterExecutiveCoreOnly by remember { mutableStateOf(false) }
    var expandedTermId by remember { mutableStateOf<String?>(null) }

    val filteredTerms = StrategyVocabulary.ALL_TERMS.filter { term ->
        val matchesSearch = searchQuery.isBlank() ||
                term.name.contains(searchQuery, ignoreCase = true) ||
                term.logic.contains(searchQuery, ignoreCase = true) ||
                term.coreQuestion.contains(searchQuery, ignoreCase = true)
        val matchesLayer = selectedLayerTab == null || term.layer == selectedLayerTab
        val matchesCore = !filterExecutiveCoreOnly || term.isExecutiveCore
        matchesSearch && matchesLayer && matchesCore
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .widthIn(max = 720.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Strategy Archetypes & Terms",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "The 3-Layer Business Architecture Glossary",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(AmberGold.copy(alpha = 0.2f))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "${StrategyVocabulary.ALL_TERMS.size} Terms",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AmberGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Search & Filter
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search term, question, logic...", color = Slate400) },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = AmberGold)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_search_glossary"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AmberGold,
                    unfocusedBorderColor = Slate700,
                    focusedContainerColor = Slate900,
                    unfocusedContainerColor = Slate900
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Quick Filter: Executive 11 terms
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilterChip(
                    selected = filterExecutiveCoreOnly,
                    onClick = { filterExecutiveCoreOnly = !filterExecutiveCoreOnly },
                    label = { Text("The 11 Executive Terms", fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(14.dp))
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AmberGold.copy(alpha = 0.25f),
                        selectedLabelColor = AmberGold
                    ),
                    modifier = Modifier.testTag("filter_glossary_executive")
                )

                Text(
                    text = "${filteredTerms.size} results",
                    fontSize = 12.sp,
                    color = Slate400
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Layer Tabs
            val layers = FrameworkLayer.values().toList()
            ScrollableTabRow(
                selectedTabIndex = if (selectedLayerTab == null) 0 else layers.indexOf(selectedLayerTab) + 1,
                containerColor = Slate850,
                contentColor = AmberGold,
                edgePadding = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Tab(
                    selected = selectedLayerTab == null,
                    onClick = { selectedLayerTab = null },
                    text = { Text("All", fontSize = 12.sp) }
                )
                layers.forEach { l ->
                    Tab(
                        selected = selectedLayerTab == l,
                        onClick = { selectedLayerTab = l },
                        text = { Text(l.displayName.replace("Layer ", "L"), fontSize = 12.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // List of Terms
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredTerms, key = { it.id }) { term ->
                    val isExpanded = expandedTermId == term.id
                    val isSelectedInBuilder = selectedTerms.contains(term.name)

                    GlossaryTermCard(
                        term = term,
                        isExpanded = isExpanded,
                        isSelectedInBuilder = isSelectedInBuilder,
                        onToggleExpand = {
                            expandedTermId = if (isExpanded) null else term.id
                        },
                        onToggleBuilderSelection = {
                            viewModel.toggleTerm(term.name)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GlossaryTermCard(
    term: StrategyTerm,
    isExpanded: Boolean,
    isSelectedInBuilder: Boolean,
    onToggleExpand: () -> Unit,
    onToggleBuilderSelection: () -> Unit
) {
    val layerColor = LayerColor(term.layer)

    Card(
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("glossary_item_${term.id}")
            .clickable(onClick = onToggleExpand)
            .border(
                width = if (isExpanded) 1.5.dp else 1.dp,
                color = if (isExpanded) layerColor else Slate800,
                shape = RoundedCornerShape(14.dp)
            )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(layerColor)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = term.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (term.isTopArchetype) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(AmberGold.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("Top Archetype", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AmberGold)
                        }
                    } else if (term.isExecutiveCore) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CyanAccent.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("Executive 11", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CyanAccent)
                        }
                    }
                }

                Button(
                    onClick = onToggleBuilderSelection,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelectedInBuilder) layerColor.copy(alpha = 0.25f) else Slate800,
                        contentColor = if (isSelectedInBuilder) layerColor else Slate300
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    if (isSelectedInBuilder) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Active", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    } else {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add", fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Question: ${term.coreQuestion}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = layerColor
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = term.logic,
                fontSize = 12.sp,
                color = Slate300,
                lineHeight = 18.sp
            )

            // Expanded Details
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Slate850)
                        .padding(12.dp)
                ) {
                    if (term.exampleSyntax.isNotBlank()) {
                        Text(
                            text = "Syntax Placement Example:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CyanAccent
                        )
                        Text(
                            text = term.exampleSyntax,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AmberGold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (term.realWorldExample.isNotBlank()) {
                        Text(
                            text = "Real-World Execution Example:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = EmeraldSuccess
                        )
                        Text(
                            text = term.realWorldExample,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Layer: ${term.layer.displayName}",
                        fontSize = 11.sp,
                        color = Slate400
                    )
                }
            }
        }
    }
}
