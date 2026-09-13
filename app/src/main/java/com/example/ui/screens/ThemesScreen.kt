package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ThemePack
import com.example.ui.components.AppIconItem
import com.example.util.WallpaperHelper
import kotlinx.coroutines.launch

@Composable
fun ThemesScreen(
    themes: List<ThemePack>,
    activeThemeId: String,
    onApplyTheme: (ThemePack) -> Unit,
    onPreviewInPhone: (ThemePack) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedCategory by remember { mutableStateOf("Todos") }

    val categories = listOf("Todos", "Cyberpunk", "Minimalista", "Naturaleza", "Espacio", "Aesthetic", "Fitness")
    val filteredThemes = if (selectedCategory == "Todos") {
        themes
    } else {
        themes.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    val featuredTheme = themes.firstOrNull()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Featured Banner
        if (featuredTheme != null && selectedCategory == "Todos") {
            item {
                FeaturedThemeHero(
                    theme = featuredTheme,
                    isApplied = featuredTheme.id == activeThemeId,
                    onApply = {
                        coroutineScope.launch {
                            WallpaperHelper.applyWallpaper(context, featuredTheme.wallpaperRes, WallpaperHelper.WallpaperTarget.BOTH)
                            onApplyTheme(featuredTheme)
                            Toast.makeText(context, "¡Tema '${featuredTheme.title}' aplicado por completo!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onPreview = { onPreviewInPhone(featuredTheme) }
                )
            }
        }

        // Category Filter Chips
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Explorar Colecciones",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { category ->
                        val isSelected = selectedCategory == category
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = category },
                            label = { Text(category, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF6366F1),
                                selectedLabelColor = Color.White,
                                containerColor = Color.White.copy(alpha = 0.08f),
                                labelColor = Color.White.copy(alpha = 0.8f)
                            )
                        )
                    }
                }
            }
        }

        // Themes List
        items(filteredThemes) { theme ->
            val isCurrentActive = theme.id == activeThemeId
            ThemePackCard(
                theme = theme,
                isApplied = isCurrentActive,
                onApply = {
                    coroutineScope.launch {
                        WallpaperHelper.applyWallpaper(context, theme.wallpaperRes, WallpaperHelper.WallpaperTarget.BOTH)
                        onApplyTheme(theme)
                        Toast.makeText(context, "¡Tema '${theme.title}' aplicado con éxito!", Toast.LENGTH_SHORT).show()
                    }
                },
                onPreview = { onPreviewInPhone(theme) }
            )
        }
    }
}

@Composable
fun FeaturedThemeHero(
    theme: ThemePack,
    isApplied: Boolean,
    onApply: () -> Unit,
    onPreview: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.5.dp, Brush.horizontalGradient(listOf(theme.primaryColor, theme.secondaryColor)), RoundedCornerShape(24.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
        ) {
            Image(
                painter = painterResource(id = theme.wallpaperRes),
                contentDescription = theme.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient scrim
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xFF0F0B1E).copy(alpha = 0.95f))
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = theme.primaryColor.copy(alpha = 0.35f)
                ) {
                    Text(
                        text = "★ TEMA DESTACADO DE LA SEMANA",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = theme.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = theme.subtitle,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onApply,
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryColor),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Icon(if (isApplied) Icons.Filled.Check else Icons.Filled.Palette, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isApplied) "Tema Activo" else "Aplicar Tema", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onPreview,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.4f)),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Icon(Icons.Filled.Smartphone, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ver en Móvil", fontSize = 13.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ThemePackCard(
    theme: ThemePack,
    isApplied: Boolean,
    onApply: () -> Unit,
    onPreview: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161226)),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isApplied) theme.primaryColor else Color.White.copy(alpha = 0.1f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Wallpaper thumbnail
                    Box(
                        modifier = Modifier
                            .size(62.dp)
                            .clip(RoundedCornerShape(14.dp))
                    ) {
                        Image(
                            painter = painterResource(id = theme.wallpaperRes),
                            contentDescription = theme.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = theme.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = theme.subtitle,
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.65f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("${theme.rating}", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                            Text("•", fontSize = 11.sp, color = Color.White.copy(alpha = 0.4f))
                            Text("${theme.downloads} descargas", fontSize = 11.sp, color = Color.White.copy(alpha = 0.6f))
                        }
                    }
                }

                if (isApplied) {
                    Surface(
                        shape = CircleShape,
                        color = theme.primaryColor.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, theme.primaryColor)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Check, contentDescription = null, tint = theme.primaryColor, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("Activo", fontSize = 10.sp, color = theme.primaryColor, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Mini Icon Preview Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.04f))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pack de Iconos ${theme.iconStyle.name.replace("_", " ")}",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.75f),
                    fontWeight = FontWeight.Medium
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    val previewApps = listOf(
                        Icons.Filled.Chat to Color(0xFF10B981),
                        Icons.Filled.CameraAlt to Color(0xFFEC4899),
                        Icons.Filled.MusicNote to Color(0xFF22C55E),
                        Icons.Filled.PlayArrow to Color(0xFFEF4444)
                    )
                    previewApps.forEach { (icon, tint) ->
                        AppIconItem(
                            name = "",
                            icon = icon,
                            color = tint,
                            shape = theme.iconShape,
                            style = theme.iconStyle,
                            size = 28.dp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onPreview,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.25f)),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 6.dp)
                ) {
                    Icon(Icons.Filled.PhoneAndroid, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Ver en Móvil", fontSize = 12.sp, color = Color.White)
                }

                Button(
                    onClick = onApply,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isApplied) Color.White.copy(alpha = 0.15f) else theme.primaryColor
                    ),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 6.dp)
                ) {
                    Icon(if (isApplied) Icons.Filled.Check else Icons.Filled.FlashOn, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isApplied) "Aplicado" else "Aplicar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
