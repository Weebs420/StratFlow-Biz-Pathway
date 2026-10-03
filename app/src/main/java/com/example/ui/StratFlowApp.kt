package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.BlueprintDetailScreen
import com.example.ui.screens.BuilderScreen
import com.example.ui.screens.GlossaryScreen
import com.example.ui.screens.SavedBlueprintsScreen
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900

data class NavItem(
    val tab: AppNavTab,
    val icon: ImageVector,
    val label: String,
    val tag: String
)

@Composable
fun StratFlowApp(
    viewModel: StratFlowViewModel,
    modifier: Modifier = Modifier
) {
    val navTab by viewModel.navTab.collectAsStateWithLifecycle()
    val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(userMessage) {
        userMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearUserMessage()
        }
    }

    // Handle back button on secondary tabs
    if (navTab != AppNavTab.BUILDER) {
        BackHandler {
            viewModel.setNavTab(AppNavTab.BUILDER)
        }
    }

    val navItems = listOf(
        NavItem(AppNavTab.BUILDER, Icons.Default.AutoAwesome, "Builder", "nav_builder"),
        NavItem(AppNavTab.BLUEPRINT, Icons.Default.Layers, "Pathway", "nav_blueprint"),
        NavItem(AppNavTab.SAVED, Icons.Default.Bookmark, "Vault", "nav_saved"),
        NavItem(AppNavTab.GLOSSARY, Icons.AutoMirrored.Filled.MenuBook, "Glossary", "nav_glossary")
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = Slate900,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                navItems.forEach { item ->
                    val isSelected = navTab == item.tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.setNavTab(item.tab) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AmberGold,
                            selectedTextColor = AmberGold,
                            indicatorColor = Slate850,
                            unselectedIconColor = Slate400,
                            unselectedTextColor = Slate400
                        ),
                        modifier = Modifier.testTag(item.tag)
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (navTab) {
                AppNavTab.BUILDER -> BuilderScreen(viewModel = viewModel)
                AppNavTab.BLUEPRINT -> BlueprintDetailScreen(viewModel = viewModel)
                AppNavTab.SAVED -> SavedBlueprintsScreen(viewModel = viewModel)
                AppNavTab.GLOSSARY -> GlossaryScreen(viewModel = viewModel)
            }
        }
    }
}
