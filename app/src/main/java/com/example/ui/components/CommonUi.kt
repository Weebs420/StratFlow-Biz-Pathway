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
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.PurpleFlywheel
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate850

@Composable
fun LayerColor(layer: FrameworkLayer): Color {
    return when (layer) {
        FrameworkLayer.ARCHETYPE -> AmberGold
        FrameworkLayer.STARTING_POINT -> EmeraldSuccess
        FrameworkLayer.EXPANSION_DIRECTION -> CyanAccent
        FrameworkLayer.OPERATING_DESIGN -> Color(0xFF38BDF8)
        FrameworkLayer.COMPOUNDING_ENGINE -> PurpleFlywheel
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
    val bgColor = if (isSelected) chipColor.copy(alpha = 0.22f) else Slate850
    val borderColor = if (isSelected) chipColor else Slate700

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .testTag("archetype_chip_${termName.lowercase()}")
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onToggle)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = chipColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        } else if (isTopArchetype) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(AmberGold)
            )
            Spacer(modifier = Modifier.width(6.dp))
        } else if (isExecutiveCore) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Executive Core",
                tint = AmberGold.copy(alpha = 0.85f),
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }

        Text(
            text = termName,
            color = if (isSelected) chipColor else MaterialTheme.colorScheme.onSurface,
            fontSize = 13.sp,
            fontWeight = if (isSelected || isTopArchetype || isExecutiveCore) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
fun ParameterPill(
    label: String,
    value: String,
    icon: ImageVector,
    accentColor: Color = CyanAccent,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Slate850)
            .border(1.dp, Slate700.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = accentColor,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "$label: ",
            color = Slate400,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
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
                    .background(AmberGold.copy(alpha = 0.15f))
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AmberGold,
                    modifier = Modifier.size(16.dp)
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
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400
                    )
                }
            }
        }

        if (badgeText != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(AmberGold.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = badgeText,
                    color = AmberGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
