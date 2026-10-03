package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// ==========================================
// USER SPECIFIED BRAND PALETTE
// #318EA7: Primary Ocean Teal
// #112A31: Deep Petroleum Dark Teal (Background)
// #F2DB98: Soft Gold Cream / Sand Accent
// #E6BB3F: Warm Rich Gold / Primary Action
// ==========================================

val TealPrimary = Color(0xFF318EA7)
val TealDarkBg = Color(0xFF112A31)
val GoldCream = Color(0xFFF2DB98)
val GoldWarm = Color(0xFFE6BB3F)

// Harmonious variations for minimal elevation & surfaces
val TealCardSurface = Color(0xFF16353E)
val TealSurfaceHigher = Color(0xFF1D424D)
val TealSurfaceElevated = Color(0xFF234F5C)
val TealBorder = Color(0xFF2B5865)
val TealBorderSubtle = Color(0xFF1F434E)
val TealLightAccent = Color(0xFF48A6BF)

val GoldCreamSubtle = Color(0x26F2DB98) // 15% opacity
val GoldWarmSubtle = Color(0x2EE6BB3F)  // 18% opacity
val TealPrimarySubtle = Color(0x26318EA7) // 15% opacity

// Text & Neutral Colors
val TextPrimary = Color(0xFFF8FAFC)
val TextSecondary = Color(0xFFCBD5E1)
val TextMuted = Color(0xFF90A8B1)
val TextGold = Color(0xFFF2DB98)

// Compatibility Tokens mapped to the new palette for cohesive app-wide styling
val Slate950 = Color(0xFF0C1E23)
val Slate900 = TealDarkBg         // #112A31
val Slate850 = TealCardSurface     // #16353E
val Slate800 = TealSurfaceHigher   // #1D424D
val Slate700 = TealBorder          // #2B5865
val Slate600 = Color(0xFF3A6977)
val Slate400 = TextMuted          // #90A8B1
val Slate300 = TextSecondary      // #CBD5E1
val Slate100 = TextPrimary        // #F8FAFC

// Semantic Accents
val AmberGold = GoldWarm           // #E6BB3F
val AmberGoldLight = GoldCream     // #F2DB98
val CyanAccent = TealPrimary       // #318EA7
val EmeraldSuccess = Color(0xFF2DD4BF)
val EmeraldBg = Color(0xFF0D332F)
val RoseRisk = Color(0xFFFB7185)
val RoseBg = Color(0xFF4C111E)
val PurpleFlywheel = Color(0xFFA78BFA)
val BlueInfo = Color(0xFF38BDF8)

// Theme tokens
val PrimaryGold = GoldWarm         // #E6BB3F
val OnPrimaryDark = TealDarkBg     // #112A31
val SecondaryCyan = TealPrimary    // #318EA7
val TertiaryPurple = GoldCream     // #F2DB98
