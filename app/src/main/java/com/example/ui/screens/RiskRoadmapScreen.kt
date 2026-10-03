package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.RiskItem
import com.example.model.RiskMitigationRoadmap
import com.example.ui.StratFlowViewModel
import com.example.ui.components.SectionHeader
import com.example.ui.theme.GoldCream
import com.example.ui.theme.GoldWarm
import com.example.ui.theme.RoseBg
import com.example.ui.theme.RoseRisk
import com.example.ui.theme.TealBorder
import com.example.ui.theme.TealBorderSubtle
import com.example.ui.theme.TealCardSurface
import com.example.ui.theme.TealDarkBg
import com.example.ui.theme.TealPrimary
import com.example.ui.theme.TealSurfaceHigher
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RiskRoadmapScreen(
    viewModel: StratFlowViewModel,
    modifier: Modifier = Modifier
) {
    val projectIdea by viewModel.riskProjectIdea.collectAsStateWithLifecycle()
    val capital by viewModel.riskCapital.collectAsStateWithLifecycle()
    val archetype by viewModel.riskArchetype.collectAsStateWithLifecycle()
    val roadmap by viewModel.currentRiskRoadmap.collectAsStateWithLifecycle()
    val isGenerating by viewModel.isGeneratingRiskRoadmap.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var archetypeDropdownExpanded by remember { mutableStateOf(false) }

    val presetIdeas = listOf(
        "Solar Cold-Storage Facility & Crop Hub",
        "Direct-to-Consumer Organic Staples Brand",
        "B2B Institutional Agri-Procurement Syndicate",
        "Last-Mile Refrigerated Electric Van Fleet"
    )

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TealDarkBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .widthIn(max = 720.dp)
        ) {
            // HEADER BANNER
            Card(
                colors = CardDefaults.cardColors(containerColor = TealCardSurface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, GoldWarm.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(GoldWarm.copy(alpha = 0.2f))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = GoldWarm,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "AI Risk Mitigation Engine",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Automated Vulnerability Roadmap via Gemini API",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMuted
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(TealPrimary.copy(alpha = 0.25f))
                                .border(1.dp, TealPrimary.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = GoldWarm,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Gemini 3.5 Flash", fontSize = 10.sp, color = GoldCream, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // INPUT CARD
            Card(
                colors = CardDefaults.cardColors(containerColor = TealCardSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, TealBorder, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Project Idea / Venture Concept:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = GoldWarm
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = projectIdea,
                        onValueChange = { viewModel.onRiskProjectIdeaChanged(it) },
                        placeholder = { Text("e.g. Solar Cold-Storage Hub for High-Value Vegetables", color = TextMuted) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .testTag("input_risk_project_idea"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TealPrimary,
                            unfocusedBorderColor = TealBorder,
                            focusedContainerColor = TealSurfaceHigher,
                            unfocusedContainerColor = TealSurfaceHigher
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Preset chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        presetIdeas.forEach { idea ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (projectIdea == idea) TealPrimary.copy(alpha = 0.25f) else TealSurfaceHigher)
                                    .border(1.dp, if (projectIdea == idea) TealPrimary else TealBorderSubtle, RoundedCornerShape(6.dp))
                                    .clickable { viewModel.onRiskProjectIdeaChanged(idea) }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = idea,
                                    fontSize = 11.sp,
                                    color = if (projectIdea == idea) GoldCream else TextMuted,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Capital and Archetype in a Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Estimated Capital:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = capital,
                                onValueChange = { viewModel.onRiskCapitalChanged(it) },
                                placeholder = { Text("৳25 Lakh", color = TextMuted) },
                                singleLine = true,
                                leadingIcon = {
                                    Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = GoldWarm, modifier = Modifier.size(16.dp))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("input_risk_capital"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = GoldWarm,
                                    unfocusedBorderColor = TealBorder,
                                    focusedContainerColor = TealSurfaceHigher,
                                    unfocusedContainerColor = TealSurfaceHigher
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1.3f)) {
                            Text(
                                text = "Strategic Lens:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            ExposedDropdownMenuBox(
                                expanded = archetypeDropdownExpanded,
                                onExpandedChange = { archetypeDropdownExpanded = it },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = archetype,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = archetypeDropdownExpanded) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = TealPrimary,
                                        unfocusedBorderColor = TealBorder,
                                        focusedContainerColor = TealSurfaceHigher,
                                        unfocusedContainerColor = TealSurfaceHigher
                                    ),
                                    modifier = Modifier
                                        .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                                        .fillMaxWidth()
                                        .testTag("dropdown_risk_archetype"),
                                    shape = RoundedCornerShape(10.dp)
                                )

                                ExposedDropdownMenu(
                                    expanded = archetypeDropdownExpanded,
                                    onDismissRequest = { archetypeDropdownExpanded = false },
                                    modifier = Modifier.background(TealCardSurface)
                                ) {
                                    listOf(
                                        "Original (Customer-First)",
                                        "Opposite (Asset-First Inversion)",
                                        "Hybrid (Dual-Track)",
                                        "Shadow (Backstage Moat)"
                                    ).forEach { item ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = item,
                                                    fontSize = 12.sp,
                                                    color = if (archetype == item) GoldWarm else TextPrimary,
                                                    fontWeight = if (archetype == item) FontWeight.Bold else FontWeight.Normal
                                                )
                                            },
                                            onClick = {
                                                viewModel.onRiskArchetypeChanged(item)
                                                archetypeDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // GENERATE BUTTON
                    Button(
                        onClick = { viewModel.generateRiskRoadmap() },
                        enabled = !isGenerating,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("button_generate_risk_roadmap"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldWarm,
                            contentColor = TealDarkBg
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = TealDarkBg,
                                strokeWidth = 2.5.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Synthesizing Risk Roadmap with Gemini...", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate Risk Mitigation Roadmap", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // DISPLAY ROADMAP RESULT
            if (roadmap != null) {
                val rm = roadmap!!

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SectionHeader(
                        title = "Risk Mitigation Roadmap",
                        subtitle = "${rm.roadmapPhases.size} Strategic Phases • ${rm.topRisks.size} Critical Vulnerabilities",
                        icon = Icons.Default.Shield,
                        badgeText = if (rm.generatedWithAi) "AI Verified" else "Engine Mode"
                    )

                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Risk Roadmap", formatRiskRoadmapMarkdown(rm))
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Roadmap copied to clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TealSurfaceHigher, contentColor = TealPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(34.dp)
                            .testTag("button_copy_risk_roadmap")
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Copy", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Executive Diagnosis Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = TealCardSurface),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, GoldWarm.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = GoldWarm, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Executive Risk Diagnosis",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldWarm
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = rm.executiveSummary,
                            fontSize = 12.5.sp,
                            color = TextSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // TOP STRUCTURAL RISKS
                Text(
                    text = "Top Structural Vulnerabilities & Root Causes",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                rm.topRisks.forEach { risk ->
                    RiskItemCard(risk)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // PHASE-BY-PHASE TIMELINE
                Text(
                    text = "Phase-by-Phase Execution Roadmap",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                rm.roadmapPhases.forEach { phase ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = TealCardSurface),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, TealBorder, RoundedCornerShape(14.dp))
                            .padding(bottom = 10.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = phase.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldWarm
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(TealPrimary.copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(phase.timeframe, fontSize = 11.sp, color = TealPrimary, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Objective: ${phase.primaryObjective}",
                                fontSize = 12.sp,
                                color = TextPrimary,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Actionable Steps:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldCream
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            phase.keyActionSteps.forEach { step ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = TealPrimary,
                                        modifier = Modifier.size(14.dp).padding(top = 2.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(step, fontSize = 11.5.sp, color = TextSecondary, lineHeight = 16.sp)
                                }
                            }

                            if (phase.guardrails.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Mandatory Guardrails:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RoseRisk
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                phase.guardrails.forEach { g ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 2.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("⚠️ ", fontSize = 10.sp)
                                        Text(g, fontSize = 11.sp, color = TextSecondary, lineHeight = 16.sp)
                                    }
                                }
                            }

                            if (phase.exitCriteria.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(TealSurfaceHigher)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = "Exit Criteria: ${phase.exitCriteria}",
                                        fontSize = 11.sp,
                                        color = GoldCream,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // KILL SWITCH TRIGGERS
                if (rm.killSwitches.isNotEmpty()) {
                    Text(
                        text = "Red-Flag 'Kill Switch' Indicators",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = RoseRisk
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    rm.killSwitches.forEach { ks ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = RoseBg.copy(alpha = 0.35f)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, RoseRisk.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                .padding(bottom = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(ks.metric, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextPrimary)
                                    Text("Trigger: ${ks.threshold}", fontSize = 11.sp, color = RoseRisk, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Mandatory Response: ${ks.requiredResponse}",
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // COMPOUNDING SAFEGUARDS
                Card(
                    colors = CardDefaults.cardColors(containerColor = TealCardSurface),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, TealBorder, RoundedCornerShape(12.dp))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Long-Term Compounding Resilience",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TealPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = rm.compoundingSafeguards,
                            fontSize = 12.sp,
                            color = TextSecondary,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RiskItemCard(risk: RiskItem) {
    val severityColor = when (risk.severity.uppercase()) {
        "CRITICAL" -> RoseRisk
        "HIGH" -> GoldWarm
        else -> TealPrimary
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = TealCardSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, TealBorder, RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = risk.riskName,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(severityColor.copy(alpha = 0.2f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${risk.severity} • ${risk.probability} PROB",
                        fontSize = 10.sp,
                        color = severityColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = risk.description,
                fontSize = 11.5.sp,
                color = TextSecondary,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(TealSurfaceHigher)
                    .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = "Root Cause: ${risk.rootCause}",
                        fontSize = 10.5.sp,
                        color = TextMuted,
                        lineHeight = 15.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Preemptive Fix: ${risk.preventiveAction}",
                        fontSize = 11.sp,
                        color = GoldCream,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

fun formatRiskRoadmapMarkdown(rm: RiskMitigationRoadmap): String {
    val sb = StringBuilder()
    sb.appendLine("# Risk Mitigation Roadmap: ${rm.projectIdea}")
    sb.appendLine("**Capital:** ${rm.estimatedCapital} | **Archetype:** ${rm.strategicArchetype}")
    sb.appendLine()
    sb.appendLine("## Executive Risk Diagnosis")
    sb.appendLine(rm.executiveSummary)
    sb.appendLine()
    sb.appendLine("## Top Structural Risks")
    rm.topRisks.forEach { r ->
        sb.appendLine("### ${r.riskName} [${r.severity}]")
        sb.appendLine("- **Description:** ${r.description}")
        sb.appendLine("- **Root Cause:** ${r.rootCause}")
        sb.appendLine("- **Preventive Action:** ${r.preventiveAction}")
        sb.appendLine()
    }
    sb.appendLine("## Phase-by-Phase Roadmap")
    rm.roadmapPhases.forEach { p ->
        sb.appendLine("### ${p.title} (${p.timeframe})")
        sb.appendLine("**Objective:** ${p.primaryObjective}")
        sb.appendLine("**Actions:**")
        p.keyActionSteps.forEach { a -> sb.appendLine("- $a") }
        if (p.guardrails.isNotEmpty()) {
            sb.appendLine("**Guardrails:**")
            p.guardrails.forEach { g -> sb.appendLine("- ⚠️ $g") }
        }
        sb.appendLine("**Exit Criteria:** ${p.exitCriteria}")
        sb.appendLine()
    }
    sb.appendLine("## Red-Flag Kill Switches")
    rm.killSwitches.forEach { ks ->
        sb.appendLine("- **${ks.metric}**: Trigger at `${ks.threshold}` → Action: ${ks.requiredResponse}")
    }
    sb.appendLine()
    sb.appendLine("## Compounding Resilience")
    sb.appendLine(rm.compoundingSafeguards)
    return sb.toString()
}
