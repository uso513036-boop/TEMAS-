package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.IconPack
import com.example.data.model.ThemePack
import com.example.data.model.WallpaperItem
import com.example.data.repository.ThemeRepository
import com.example.ui.components.PhonePreviewSimulator
import com.example.util.WallpaperHelper
import kotlinx.coroutines.launch

enum class MainTab(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    THEMES("Temas", Icons.Filled.Palette),
    WALLPAPERS("Fondos 8K", Icons.Filled.Wallpaper),
    ICONS("Iconos", Icons.Filled.Apps),
    WIDGETS("Widgets", Icons.Filled.Widgets)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStudioScreen(
    repository: ThemeRepository
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var currentTab by remember { mutableStateOf(MainTab.THEMES) }
    val themes = remember { repository.getPresetThemes() }
    val wallpapers = remember { repository.getPresetWallpapers() }
    val iconPacks = remember { repository.getIconPacks() }

    val activeThemeEntity by repository.activeThemeEntity.collectAsState(initial = null)
    val favoriteEntities by repository.favoriteWallpaperEntities.collectAsState(initial = emptyList())
    val favoriteIds = remember(favoriteEntities) { favoriteEntities.map { it.wallpaperId }.toSet() }

    // Active theme state
    val activeThemeId = activeThemeEntity?.themeId ?: themes.first().id
    val currentTheme = remember(activeThemeId, themes) {
        themes.firstOrNull { it.id == activeThemeId } ?: themes.first()
    }

    // Active icon pack
    var activeIconPack by remember { mutableStateOf(iconPacks.first()) }

    // Phone preview dialog state
    var showPhonePreviewModal by remember { mutableStateOf(false) }
    var previewTheme by remember { mutableStateOf(currentTheme) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF0D0A1A),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                "Theme Studio",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                "Personalizador Móvil 8K",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }
                },
                actions = {
                    // Button to open 3D Phone Preview
                    Button(
                        onClick = {
                            previewTheme = currentTheme
                            showPhonePreviewModal = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6366F1)
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Filled.Smartphone,
                            contentDescription = "Simulador",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ver Móvil", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D0A1A),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF131024),
                contentColor = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                MainTab.values().forEach { tab ->
                    val isSelected = currentTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentTab = tab },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color(0xFF818CF8),
                            indicatorColor = Color(0xFF4F46E5),
                            unselectedIconColor = Color.White.copy(alpha = 0.5f),
                            unselectedTextColor = Color.White.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                MainTab.THEMES -> {
                    ThemesScreen(
                        themes = themes,
                        activeThemeId = activeThemeId,
                        onApplyTheme = { theme ->
                            coroutineScope.launch {
                                repository.applyTheme(theme)
                            }
                        },
                        onPreviewInPhone = { theme ->
                            previewTheme = theme
                            showPhonePreviewModal = true
                        }
                    )
                }
                MainTab.WALLPAPERS -> {
                    WallpapersScreen(
                        wallpapers = wallpapers,
                        favoriteIds = favoriteIds,
                        onToggleFavorite = { wp ->
                            coroutineScope.launch {
                                val isNowFav = repository.toggleFavoriteWallpaper(wp)
                                Toast.makeText(
                                    context,
                                    if (isNowFav) "Añadido a favoritos ❤️" else "Eliminado de favoritos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
                MainTab.ICONS -> {
                    IconsScreen(
                        iconPacks = iconPacks,
                        activePackId = activeIconPack.id,
                        onSelectPack = { activeIconPack = it }
                    )
                }
                MainTab.WIDGETS -> {
                    WidgetsScreen()
                }
            }
        }
    }

    // Interactive Phone Simulator Dialog
    if (showPhonePreviewModal) {
        Dialog(
            onDismissRequest = { showPhonePreviewModal = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.95f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header with Close & Title
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Simulador de Teléfono",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                "Tema: ${previewTheme.title}",
                                fontSize = 12.sp,
                                color = previewTheme.primaryColor
                            )
                        }

                        IconButton(
                            onClick = { showPhonePreviewModal = false }
                        ) {
                            Icon(Icons.Filled.Close, contentDescription = "Cerrar", tint = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Phone Simulator View
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        PhonePreviewSimulator(
                            wallpaperRes = previewTheme.wallpaperRes,
                            iconShape = previewTheme.iconShape,
                            iconStyle = previewTheme.iconStyle,
                            activeWidgetType = previewTheme.defaultWidget,
                            accentColor = previewTheme.primaryColor
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Apply Button from Simulator
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                WallpaperHelper.applyWallpaper(context, previewTheme.wallpaperRes, WallpaperHelper.WallpaperTarget.BOTH)
                                repository.applyTheme(previewTheme)
                                Toast.makeText(context, "¡Tema '${previewTheme.title}' aplicado al teléfono!", Toast.LENGTH_LONG).show()
                                showPhonePreviewModal = false
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = previewTheme.primaryColor)
                    ) {
                        Icon(Icons.Filled.FlashOn, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Aplicar Tema Completo al Teléfono",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
