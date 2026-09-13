package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*

@Composable
fun PhonePreviewSimulator(
    wallpaperRes: Int,
    iconShape: IconShape,
    iconStyle: IconStyle,
    activeWidgetType: WidgetType,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onApplyThemeClick: (() -> Unit)? = null
) {
    var selectedWidget by remember(activeWidgetType) { mutableStateOf(activeWidgetType) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        // Phone Outer Frame
        Surface(
            shape = RoundedCornerShape(36.dp),
            color = Color(0xFF0F0D1B),
            shadowElevation = 18.dp,
            modifier = Modifier
                .widthIn(max = 340.dp)
                .fillMaxWidth()
                .border(3.dp, Brush.verticalGradient(listOf(Color(0xFF4A3B72), Color(0xFF1E1635))), RoundedCornerShape(36.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(560.dp)
                    .clip(RoundedCornerShape(33.dp))
            ) {
                // 1. High-Res Wallpaper Background
                Image(
                    painter = painterResource(id = wallpaperRes),
                    contentDescription = "Fondo de Pantalla Activo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Subtle dark overlay to guarantee text contrast
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.45f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )

                // 2. Phone Screen Elements
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Status Bar + Dynamic Island
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "10:42",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            // Dynamic Pill / Camera Punch hole
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(18.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.Black.copy(alpha = 0.85f))
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Filled.SignalCellularAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                Icon(Icons.Filled.Wifi, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                Icon(Icons.Filled.Battery5Bar, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Interactive Widget
                        InteractiveWidgetCard(
                            widgetType = selectedWidget,
                            config = WidgetCustomConfig(
                                widgetType = selectedWidget,
                                accentColor = accentColor,
                                cornerRadius = 20,
                                opacity = 0.85f
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // 3. Grid of Custom App Icons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val firstRowApps = listOf(
                            Triple("WhatsApp", Icons.Filled.Chat, Color(0xFF10B981)),
                            Triple("Instagram", Icons.Filled.CameraAlt, Color(0xFFEC4899)),
                            Triple("Spotify", Icons.Filled.MusicNote, Color(0xFF22C55E)),
                            Triple("YouTube", Icons.Filled.PlayArrow, Color(0xFFEF4444))
                        )
                        val secondRowApps = listOf(
                            Triple("Galería", Icons.Filled.Photo, Color(0xFFA855F7)),
                            Triple("Ajustes", Icons.Filled.Settings, Color(0xFF64748B)),
                            Triple("Calendario", Icons.Filled.CalendarMonth, Color(0xFF38BDF8)),
                            Triple("Clima", Icons.Filled.WbSunny, Color(0xFFFBBF24))
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            firstRowApps.forEach { (name, icon, tint) ->
                                AppIconItem(
                                    name = name,
                                    icon = icon,
                                    color = tint,
                                    shape = iconShape,
                                    style = iconStyle
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            secondRowApps.forEach { (name, icon, tint) ->
                                AppIconItem(
                                    name = name,
                                    icon = icon,
                                    color = tint,
                                    shape = iconShape,
                                    style = iconStyle
                                )
                            }
                        }
                    }

                    // 4. Bottom Dock
                    Surface(
                        shape = RoundedCornerShape(26.dp),
                        color = Color.Black.copy(alpha = 0.45f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val dockApps = listOf(
                                Triple("Teléfono", Icons.Filled.Call, Color(0xFF10B981)),
                                Triple("Mensajes", Icons.Filled.Message, Color(0xFF38BDF8)),
                                Triple("Chrome", Icons.Filled.Language, Color(0xFFF59E0B)),
                                Triple("Cámara", Icons.Filled.PhotoCamera, Color(0xFF8B5CF6))
                            )
                            dockApps.forEach { (name, icon, tint) ->
                                AppIconItem(
                                    name = "",
                                    icon = icon,
                                    color = tint,
                                    shape = iconShape,
                                    style = iconStyle,
                                    size = 44.dp
                                )
                            }
                        }
                    }
                }

                // 5. Floating action to switch widget inside preview
                Surface(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.65f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 34.dp, end = 12.dp)
                        .clickable {
                            val allTypes = WidgetType.values()
                            val nextIndex = (selectedWidget.ordinal + 1) % allTypes.size
                            selectedWidget = allTypes[nextIndex]
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.SwapHoriz, contentDescription = "Cambiar widget", tint = accentColor, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Widget", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun AppIconItem(
    name: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    shape: IconShape,
    style: IconStyle,
    size: androidx.compose.ui.unit.Dp = 46.dp
) {
    val cornerRadius = when (shape) {
        IconShape.CIRCLE -> CircleShape
        IconShape.SQUIRCLE -> RoundedCornerShape(16.dp)
        IconShape.ROUNDED_SQUARE -> RoundedCornerShape(11.dp)
        IconShape.HEXAGON -> RoundedCornerShape(8.dp)
        IconShape.SHIELD -> RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
    }

    val bgBrush = when (style) {
        IconStyle.NEO_GLOW -> Brush.linearGradient(
            listOf(color.copy(alpha = 0.85f), Color(0xFF0F0B24))
        )
        IconStyle.MINIMAL_FLAT -> Brush.linearGradient(
            listOf(color, color.copy(alpha = 0.8f))
        )
        IconStyle.GLASS_ORB -> Brush.linearGradient(
            listOf(Color.White.copy(alpha = 0.35f), color.copy(alpha = 0.6f))
        )
        IconStyle.CYBER_OUTLINE -> Brush.linearGradient(
            listOf(Color(0xFF0A091A), Color(0xFF140D2B))
        )
        IconStyle.PASTEL_GLYPH -> Brush.linearGradient(
            listOf(color.copy(alpha = 0.7f), Color.White.copy(alpha = 0.2f))
        )
        IconStyle.CLAY_3D -> Brush.linearGradient(
            listOf(color, color.copy(alpha = 0.6f))
        )
    }

    val borderModifier = when (style) {
        IconStyle.CYBER_OUTLINE, IconStyle.NEO_GLOW -> Modifier.border(1.5.dp, color.copy(alpha = 0.8f), cornerRadius)
        IconStyle.GLASS_ORB -> Modifier.border(1.dp, Color.White.copy(alpha = 0.4f), cornerRadius)
        else -> Modifier.border(1.dp, Color.White.copy(alpha = 0.15f), cornerRadius)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(52.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(cornerRadius)
                .background(bgBrush)
                .then(borderModifier),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                tint = if (style == IconStyle.CYBER_OUTLINE) color else Color.White,
                modifier = Modifier.size(size * 0.52f)
            )
        }

        if (name.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}
