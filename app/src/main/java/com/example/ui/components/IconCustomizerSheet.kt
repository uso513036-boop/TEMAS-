package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.data.model.AppIconDef
import com.example.data.model.AppIconType
import com.example.data.model.IconShape
import com.example.data.model.IconStyle
import com.example.util.ShortcutHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconCustomizerSheet(
    initialIconDef: AppIconDef? = null,
    onDismiss: () -> Unit,
    onSaveCustomIcon: ((AppIconDef) -> Unit)? = null
) {
    val context = LocalContext.current
    var selectedApp by remember { mutableStateOf(initialIconDef?.appType ?: AppIconType.WHATSAPP) }
    var selectedShape by remember { mutableStateOf(initialIconDef?.shape ?: IconShape.SQUIRCLE) }
    var selectedStyle by remember { mutableStateOf(IconStyle.NEO_GLOW) }
    var hasBadge by remember { mutableStateOf(initialIconDef?.hasNotificationBadge ?: true) }

    // Color gradient options
    val palettes = listOf(
        Pair(Color(0xFF8B5CF6), Color(0xFF06B6D4)), // Cyber Violet / Cyan
        Pair(Color(0xFFEC4899), Color(0xFFF43F5E)), // Neon Magenta / Rose
        Pair(Color(0xFF10B981), Color(0xFF047857)), // Emerald Mint
        Pair(Color(0xFFF59E0B), Color(0xFFEA580C)), // Sunset Amber
        Pair(Color(0xFF38BDF8), Color(0xFF1D4ED8)), // Electric Blue
        Pair(Color(0xFF1F2937), Color(0xFF111827)), // Obsidian Black
        Pair(Color(0xFFF472B6), Color(0xFFA78BFA))  // Pastel Dream
    )
    var selectedPaletteIndex by remember { mutableStateOf(0) }
    val (startColor, endColor) = palettes[selectedPaletteIndex]

    val currentIconDef = AppIconDef(
        appType = selectedApp,
        customLabel = selectedApp.defaultLabel,
        foregroundColor = Color.White,
        backgroundStartColor = startColor,
        backgroundEndColor = endColor,
        shape = selectedShape,
        hasNotificationBadge = hasBadge
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF131024),
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color.White.copy(alpha = 0.3f)) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
        ) {
            Text(
                text = "Taller de Iconos Personalizados",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Diseña y crea iconos únicos para tus aplicaciones favoritas",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Live Interactive Preview Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.radialGradient(
                            listOf(Color(0xFF261D4E), Color(0xFF0D0A1C))
                        )
                    )
                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val appVector = when (selectedApp) {
                        AppIconType.WHATSAPP -> Icons.Filled.Chat
                        AppIconType.INSTAGRAM -> Icons.Filled.CameraAlt
                        AppIconType.SPOTIFY -> Icons.Filled.MusicNote
                        AppIconType.CAMERA -> Icons.Filled.PhotoCamera
                        AppIconType.CHROME -> Icons.Filled.Language
                        AppIconType.YOUTUBE -> Icons.Filled.PlayArrow
                        AppIconType.PHONE -> Icons.Filled.Call
                        AppIconType.MESSAGES -> Icons.Filled.Message
                        AppIconType.SETTINGS -> Icons.Filled.Settings
                        AppIconType.PHOTOS -> Icons.Filled.Photo
                        AppIconType.MUSIC -> Icons.Filled.Headphones
                        AppIconType.CALENDAR -> Icons.Filled.CalendarMonth
                        AppIconType.WEATHER -> Icons.Filled.WbSunny
                        AppIconType.CLOCK -> Icons.Filled.AccessTime
                    }

                    AppIconItem(
                        name = currentIconDef.customLabel,
                        icon = appVector,
                        color = startColor,
                        shape = selectedShape,
                        style = selectedStyle,
                        size = 64.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 1. App Selector
            Text("1. Elige la Aplicación", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(AppIconType.values()) { app ->
                    val isSelected = selectedApp == app
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedApp = app },
                        label = { Text(app.defaultLabel, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF6366F1),
                            selectedLabelColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.08f),
                            labelColor = Color.White.copy(alpha = 0.8f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Shape Selector
            Text("2. Silueta / Forma del Icono", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val shapes = listOf(
                    IconShape.SQUIRCLE to "Squircle",
                    IconShape.CIRCLE to "Círculo",
                    IconShape.ROUNDED_SQUARE to "Cuadrado",
                    IconShape.HEXAGON to "Hexágono",
                    IconShape.SHIELD to "Escudo"
                )
                shapes.forEach { (shape, name) ->
                    val isSelected = selectedShape == shape
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) Color(0xFF8B5CF6) else Color.White.copy(alpha = 0.08f),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedShape = shape }
                    ) {
                        Text(
                            text = name,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Style Selector
            Text("3. Acabado Visual", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val styles = listOf(
                    IconStyle.NEO_GLOW to "Neón Glow",
                    IconStyle.MINIMAL_FLAT to "Flat Matte",
                    IconStyle.GLASS_ORB to "Cristal 3D",
                    IconStyle.CYBER_OUTLINE to "Contorno Cyber",
                    IconStyle.PASTEL_GLYPH to "Pastel Suave",
                    IconStyle.CLAY_3D to "Claymorphism"
                )
                items(styles) { (style, name) ->
                    val isSelected = selectedStyle == style
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) Color(0xFFEC4899) else Color.White.copy(alpha = 0.08f),
                        modifier = Modifier.clickable { selectedStyle = style }
                    ) {
                        Text(
                            text = name,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Color Palette Picker
            Text("4. Paleta de Color y Gradiente", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                palettes.forEachIndexed { index, (start, end) ->
                    val isSelected = selectedPaletteIndex == index
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(start, end)))
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.2f),
                                shape = CircleShape
                            )
                            .clickable { selectedPaletteIndex = index },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Button(
                onClick = {
                    val success = ShortcutHelper.pinShortcutToHomeScreen(context, currentIconDef)
                    if (success) {
                        Toast.makeText(context, "¡Icono enviado a la Pantalla de Inicio!", Toast.LENGTH_LONG).show()
                        onSaveCustomIcon?.invoke(currentIconDef)
                        onDismiss()
                    } else {
                        Toast.makeText(context, "Icono personalizado guardado en tu colección", Toast.LENGTH_SHORT).show()
                        onSaveCustomIcon?.invoke(currentIconDef)
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6366F1)
                )
            ) {
                Icon(Icons.Filled.AddHome, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Crear Acceso Directo en Móvil", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
