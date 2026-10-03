package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.model.PathwayStep
import com.example.ui.AppNavTab
import com.example.ui.StratFlowViewModel
import com.example.ui.components.ParameterPill
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldBg
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.PurpleFlywheel
import com.example.ui.theme.RoseBg
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
fun BlueprintDetailScreen(
    viewModel: StratFlowViewModel,
    modifier: Modifier = Modifier
) {
    val currentBlueprint by viewModel.currentBlueprint.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (currentBlueprint == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No Framework Blueprint Generated Yet",
                    style = MaterialTheme.typography.titleMedium,
                    color = Slate400
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.setNavTab(AppNavTab.BUILDER) },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Slate950)
                ) {
                    Text("Go to Framework Builder", fontWeight = FontWeight.Bold)
                }
            }
        }
        return
    }

    val bp = currentBlueprint!!
    val scrollState = rememberScrollState()
    var selectedStepIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 18.dp)
                .widthIn(max = 760.dp)
        ) {
            // Navigation & Top Actions
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { viewModel.setNavTab(AppNavTab.BUILDER) },
                        modifier = Modifier
                            .testTag("button_back_to_builder")
                            .clip(CircleShape)
                            .background(Slate850)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Builder",
                            tint = AmberGold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pathway Blueprint",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Blueprint Export", generateShareableText(bp))
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Full Framework copied to clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .testTag("button_copy_blueprint")
                            .clip(CircleShape)
                            .background(Slate850)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Report",
                            tint = CyanAccent
                        )
                    }

                    Button(
                        onClick = { viewModel.saveCurrentBlueprint() },
                        colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Slate950),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("button_save_blueprint")
                    ) {
                        Icon(Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save Blueprint", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // STRATEGY FORMULA BANNER
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, AmberGold, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(AmberGold.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "STRATEGY ARCHETYPE FORMULA",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = AmberGold
                            )
                        }

                        Text(
                            text = "${bp.steps.size} Stages",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyanAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = bp.strategyFormula,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = bp.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AmberGoldLight()
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = bp.executiveSummary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Slate300,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Slate800)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Parameter Pills Row
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (bp.startingCapital.isNotBlank()) {
                            ParameterPill(label = "Capital", value = bp.startingCapital, icon = Icons.Default.MonetizationOn, accentColor = AmberGold)
                        }
                        if (bp.marketContext.isNotBlank()) {
                            ParameterPill(label = "Market", value = bp.marketContext, icon = Icons.Default.Place, accentColor = CyanAccent)
                        }
                        if (bp.targetDemography.isNotBlank()) {
                            ParameterPill(label = "Audience", value = bp.targetDemography, icon = Icons.Default.People, accentColor = PurpleFlywheel)
                        }
                        if (bp.targetGender.isNotBlank()) {
                            ParameterPill(label = "Gender", value = bp.targetGender, icon = Icons.Default.Wc, accentColor = EmeraldSuccess)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // VISUAL PIPELINE STAGES SELECTOR
            SectionHeader(
                title = "Execution Pathway Stages",
                subtitle = "Step-by-step rollout sequence",
                icon = Icons.Default.Layers
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                bp.steps.forEachIndexed { idx, step ->
                    val isSelected = selectedStepIndex == idx
                    Box(
                        modifier = Modifier
                            .testTag("step_tab_$idx")
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) AmberGold.copy(alpha = 0.2f) else Slate900)
                            .border(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) AmberGold else Slate700,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { selectedStepIndex = idx }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) AmberGold else Slate800)
                            ) {
                                Text(
                                    text = "${step.stepIndex}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Slate950 else Slate300
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = step.businessName,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) AmberGold else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = step.primaryTerm,
                                    fontSize = 10.sp,
                                    color = CyanAccent
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SELECTED STEP DEEP DIVE CARD
            val activeStep = bp.steps.getOrNull(selectedStepIndex) ?: bp.steps.first()
            StepDetailCard(step = activeStep)

            Spacer(modifier = Modifier.height(20.dp))

            // ALL STEPS OVERVIEW (if multiple steps)
            if (bp.steps.size > 1) {
                SectionHeader(
                    title = "Full Sequential Roadmap Overview",
                    subtitle = "Comprehensive breakdown of all portfolio units",
                    icon = Icons.Default.Speed
                )

                bp.steps.forEachIndexed { idx, st ->
                    if (idx != selectedStepIndex) {
                        StepSummaryAccordion(step = st, onSelect = { selectedStepIndex = idx })
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SHADOW LAYER ARCHITECTURE
            SectionHeader(
                title = "Shadow System Architecture",
                subtitle = "The hidden parallel operating layer supporting the group",
                icon = Icons.Default.Shield,
                badgeText = "Parallel Moat"
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CyanAccent.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ShadowItemRow(title = "Procurement Engine", desc = bp.shadowArchitecture.procurementEngine, icon = Icons.Default.MonetizationOn, accent = AmberGold)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Slate800)
                    ShadowItemRow(title = "Data Compounding Layer", desc = bp.shadowArchitecture.dataCompounding, icon = Icons.Default.Speed, accent = CyanAccent)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Slate800)
                    ShadowItemRow(title = "Logistics Backbone", desc = bp.shadowArchitecture.logisticsBackbone, icon = Icons.Default.Place, accent = EmeraldSuccess)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Slate800)
                    ShadowItemRow(title = "Supplier Syndicate Network", desc = bp.shadowArchitecture.supplierNetwork, icon = Icons.Default.People, accent = PurpleFlywheel)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Slate800)
                    ShadowItemRow(title = "Core Tech Stack", desc = bp.shadowArchitecture.techStack, icon = Icons.Default.Layers, accent = Color(0xFFF472B6))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // FLYWHEEL REINFORCING LOOP
            SectionHeader(
                title = "Compounding Flywheel Loop",
                subtitle = "Self-reinforcing feedback mechanism",
                icon = Icons.Default.AllInclusive,
                badgeText = "Compounding Cycle"
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PurpleFlywheel.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    bp.flywheel.stages.forEachIndexed { index, stageText ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(PurpleFlywheel.copy(alpha = 0.2f))
                                    .border(1.dp, PurpleFlywheel, CircleShape)
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PurpleFlywheel
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = stageText,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Slate850)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = bp.flywheel.compoundingNarrative,
                            fontSize = 12.sp,
                            color = Slate300,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SUGGESTED COMPLEMENTARY BUSINESSES
            if (bp.complementaryBusinesses.isNotEmpty()) {
                SectionHeader(
                    title = "Complementary Business Opportunities",
                    subtitle = "Adjacent extensions to reinforce your core group",
                    icon = Icons.Default.Edit
                )

                bp.complementaryBusinesses.forEach { comp ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Slate900),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .border(1.dp, Slate800, RoundedCornerShape(12.dp))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = comp.businessName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AmberGold
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(CyanAccent.copy(alpha = 0.2f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(comp.recommendedArchetype, fontSize = 11.sp, color = CyanAccent)
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "Synergy: ${comp.strategicSynergy}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Rationale: ${comp.whySuggested}", fontSize = 11.sp, color = Slate400)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // FOOTER ACTIONS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.setNavTab(AppNavTab.BUILDER) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AmberGold),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Tweak in Builder")
                }

                Button(
                    onClick = { viewModel.saveCurrentBlueprint() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Slate950),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Save to Vault", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun StepDetailCard(step: PathwayStep) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Slate700, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Step Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(AmberGold)
                    ) {
                        Text(
                            text = "${step.stepIndex}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate950
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = step.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${step.category} • ${step.strategicRole}",
                            fontSize = 12.sp,
                            color = Slate400
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyanAccent.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = step.primaryTerm,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )
                }
            }

            // Sequence Rationale
            if (step.sequenceRationale.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Slate850)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Sequence Logic: ${step.sequenceRationale}",
                        fontSize = 12.sp,
                        color = AmberGoldLight(),
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 1. WHY IT WILL WORK (PROS)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(EmeraldBg.copy(alpha = 0.4f))
                    .border(1.dp, EmeraldSuccess.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = EmeraldSuccess,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Why It Will Work (Value Drivers & Catalysts):",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldSuccess
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                step.whyItWillWork.forEach { pro ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text("• ", color = EmeraldSuccess, fontWeight = FontWeight.Bold)
                        Text(
                            text = pro,
                            fontSize = 12.sp,
                            color = Slate100(),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. WHY IT MIGHT NOT WORK (CONS)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(RoseBg.copy(alpha = 0.4f))
                    .border(1.dp, RoseRisk.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = RoseRisk,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Why It Might Not Work (Failure Modes & Risks):",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = RoseRisk
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                step.whyItMightNotWork.forEach { con ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text("• ", color = RoseRisk, fontWeight = FontWeight.Bold)
                        Text(
                            text = con,
                            fontSize = 12.sp,
                            color = Slate100(),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. ROOT CAUSE & WHY/HOW TO REMOVE THE PROBLEM
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Slate850)
                    .border(1.dp, AmberGold.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = null,
                        tint = AmberGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Why / How To Remove The Problem (Root Causes):",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AmberGold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = step.rootCauseAndWhyToRemoveProblem,
                    fontSize = 12.sp,
                    color = Slate300,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. ACTIONABLE RISK MITIGATION STEPS
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Slate850)
                    .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = CyanAccent,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Actionable Steps to Mitigate Risks & Overcome Barriers:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                step.actionableRiskMitigationSteps.forEachIndexed { i, action ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "${i + 1}. ",
                            color = CyanAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = action,
                            fontSize = 12.sp,
                            color = Slate100(),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Capital Allocation & KPI
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Slate800)
                        .padding(10.dp)
                ) {
                    Column {
                        Text("Capital Allocation", fontSize = 11.sp, color = Slate400)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(step.estimatedCapitalAllocation, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = AmberGold)
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Slate800)
                        .padding(10.dp)
                ) {
                    Column {
                        Text("Success Milestone KPI", fontSize = 11.sp, color = Slate400)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(step.keyPerformanceIndicator, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = EmeraldSuccess)
                    }
                }
            }
        }
    }
}

@Composable
fun StepSummaryAccordion(step: PathwayStep, onSelect: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .border(1.dp, Slate800, RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Slate800)
                ) {
                    Text(
                        text = "${step.stepIndex}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AmberGold
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = step.businessName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${step.primaryTerm} • ${step.strategicRole}",
                        fontSize = 11.sp,
                        color = Slate400
                    )
                }
            }

            Text(
                text = "View Details →",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = CyanAccent
            )
        }
    }
}

@Composable
fun ShadowItemRow(title: String, desc: String, icon: androidx.compose.ui.graphics.vector.ImageVector, accent: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = 0.2f))
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = accent, modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = accent)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 18.sp)
        }
    }
}

private fun AmberGoldLight() = Color(0xFFFDE68A)
private fun Slate100() = Color(0xFFF1F5F9)

private fun generateShareableText(bp: com.example.model.FrameworkBlueprint): String {
    val sb = StringBuilder()
    sb.appendLine("=== ${bp.title} ===")
    sb.appendLine("Strategy Formula: ${bp.strategyFormula}")
    sb.appendLine("Starting Capital: ${bp.startingCapital}")
    sb.appendLine("Market Context: ${bp.marketContext}")
    sb.appendLine("Demography: ${bp.targetDemography} | Gender: ${bp.targetGender}")
    sb.appendLine()
    sb.appendLine("EXECUTIVE SUMMARY:")
    sb.appendLine(bp.executiveSummary)
    sb.appendLine()
    sb.appendLine("--- EXECUTION PATHWAY STAGES ---")
    bp.steps.forEach { st ->
        sb.appendLine("[Phase ${st.stepIndex}: ${st.businessName}]")
        sb.appendLine("Role: ${st.strategicRole} | Primary Archetype: ${st.primaryTerm}")
        sb.appendLine("Why it will work:")
        st.whyItWillWork.forEach { sb.appendLine("  + $it") }
        sb.appendLine("Why it might not work (Risks):")
        st.whyItMightNotWork.forEach { sb.appendLine("  - $it") }
        sb.appendLine("Root Cause Remedy: ${st.rootCauseAndWhyToRemoveProblem}")
        sb.appendLine("Actionable Risk Mitigation Steps:")
        st.actionableRiskMitigationSteps.forEach { sb.appendLine("  * $it") }
        sb.appendLine("Capital: ${st.estimatedCapitalAllocation} | KPI: ${st.keyPerformanceIndicator}")
        sb.appendLine()
    }
    sb.appendLine("--- SHADOW SUPPORTING SYSTEM ---")
    sb.appendLine("Procurement: ${bp.shadowArchitecture.procurementEngine}")
    sb.appendLine("Data Compounding: ${bp.shadowArchitecture.dataCompounding}")
    sb.appendLine("Logistics Backbone: ${bp.shadowArchitecture.logisticsBackbone}")
    sb.appendLine("Supplier Network: ${bp.shadowArchitecture.supplierNetwork}")
    sb.appendLine("Tech Stack: ${bp.shadowArchitecture.techStack}")
    sb.appendLine()
    sb.appendLine("--- COMPOUNDING FLYWHEEL LOOP ---")
    bp.flywheel.stages.forEachIndexed { i, s -> sb.appendLine("  (${i + 1}) $s") }
    sb.appendLine("Narrative: ${bp.flywheel.compoundingNarrative}")
    return sb.toString()
}
