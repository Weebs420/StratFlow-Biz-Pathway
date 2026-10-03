package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FrameworkLayer
import com.example.ui.theme.GoldCream
import com.example.ui.theme.GoldWarm
import com.example.ui.theme.TealBorder
import com.example.ui.theme.TealCardSurface
import com.example.ui.theme.TealLightAccent
import com.example.ui.theme.TealPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@Composable
fun LayerColor(layer: FrameworkLayer): Color {
    return when (layer) {
        FrameworkLayer.ARCHETYPE -> GoldWarm               // #E6BB3F
        FrameworkLayer.STARTING_POINT -> TealPrimary       // #318EA7
        FrameworkLayer.EXPANSION_DIRECTION -> GoldCream    // #F2DB98
        FrameworkLayer.OPERATING_DESIGN -> TealLightAccent
        FrameworkLayer.COMPOUNDING_ENGINE -> GoldWarm      // #E6BB3F
    }
}

@Composable
fun ArchetypeChip(
    termName: String,
    layer: FrameworkLayer,
    isSelected: Boolean,
    isExecutiveCore: Boolean,
    isTopArchetype: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chipColor = LayerColor(layer)
    val bgColor = if (isSelected) chipColor.copy(alpha = 0.20f) else TealCardSurface
    val borderColor = if (isSelected) chipColor else TealBorder.copy(alpha = 0.6f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .testTag("archetype_chip_${termName.lowercase()}")
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onToggle)
            .padding(horizontal = 12.dp, vertical = 7.dp)
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = chipColor,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        } else if (isTopArchetype) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(GoldWarm)
            )
            Spacer(modifier = Modifier.width(6.dp))
        } else if (isExecutiveCore) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Executive Core",
                tint = GoldWarm.copy(alpha = 0.85f),
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
        }

        Text(
            text = termName,
            color = if (isSelected) chipColor else TextPrimary,
            fontSize = 12.5.sp,
            fontWeight = if (isSelected || isTopArchetype || isExecutiveCore) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
fun ParameterPill(
    label: String,
    value: String,
    icon: ImageVector,
    accentColor: Color = TealPrimary,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(TealCardSurface)
            .border(1.dp, TealBorder.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = accentColor,
            modifier = Modifier.size(13.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "$label: ",
            color = TextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = TextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    icon: ImageVector? = null,
    badgeText: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 6.dp)
    ) {
        if (icon != null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(GoldWarm.copy(alpha = 0.15f))
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = GoldWarm,
                    modifier = Modifier.size(15.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }

        Box(modifier = Modifier.weight(1f)) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }
            }
        }

        if (badgeText != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(GoldWarm.copy(alpha = 0.18f))
                    .border(1.dp, GoldWarm.copy(alpha = 0.35f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = badgeText,
                    color = GoldWarm,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
