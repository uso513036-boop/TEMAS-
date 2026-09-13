package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.AppIconItem
import com.example.ui.components.IconCustomizerSheet

@Composable
fun IconsScreen(
    iconPacks: List<IconPack>,
    activePackId: String,
    onSelectPack: (IconPack) -> Unit
) {
    val context = LocalContext.current
    var selectedShape by remember { mutableStateOf(IconShape.SQUIRCLE) }
    var selectedPack by remember { mutableStateOf(iconPacks.firstOrNull { it.id == activePackId } ?: iconPacks.first()) }
    var customizingAppDef by remember { mutableStateOf<AppIconDef?>(null) }
    var showCustomizerSheet by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Studio Banner: Custom Icon Creator
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.5.dp, Brush.horizontalGradient(listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))), RoundedCornerShape(22.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF2E1065), Color(0xFF1E1B4B))
                        ),
                        RoundedCornerShape(22.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.AutoFixHigh, contentDescription = null, tint = Color(0xFFF472B6), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Taller Personalizado", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF472B6))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Crea tus propios iconos", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Diseña formas, degradados y añádelos directo al escritorio", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                    }

                    Button(
                        onClick = {
                            customizingAppDef = null
                            showCustomizerSheet = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC4899)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Diseñar", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // Packs Horizontal Selector
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Text("Paquetes de Iconos Disponibles", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(iconPacks) { pack ->
                        val isSelected = selectedPack.id == pack.id
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFF261E47) else Color(0xFF161226)
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) pack.accentStart else Color.White.copy(alpha = 0.1f)
                            ),
                            modifier = Modifier
                                .width(160.dp)
                                .clickable {
                                    selectedPack = pack
                                    selectedShape = pack.shape
                                    onSelectPack(pack)
                                }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(pack.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White, maxLines = 1)
                                    if (isSelected) {
                                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = pack.accentStart, modifier = Modifier.size(16.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(pack.style.name.replace("_", " "), fontSize = 10.sp, color = pack.accentStart, fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(8.dp))
                                // 3 mini icons
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    val miniApps = listOf(Icons.Filled.Chat, Icons.Filled.CameraAlt, Icons.Filled.MusicNote)
                                    miniApps.forEach { ico ->
                                        AppIconItem(name = "", icon = ico, color = pack.accentStart, shape = pack.shape, style = pack.style, size = 26.dp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Shape Filter Tabs
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text("Cambiar Silueta de Iconos", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val shapeList = listOf(
                        IconShape.SQUIRCLE to "Squircle",
                        IconShape.CIRCLE to "Círculo",
                        IconShape.ROUNDED_SQUARE to "Cuadrado",
                        IconShape.HEXAGON to "Hexágono",
                        IconShape.SHIELD to "Escudo"
                    )
                    shapeList.forEach { (shape, name) ->
                        val isSelected = selectedShape == shape
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) Color(0xFF6366F1) else Color.White.copy(alpha = 0.08f),
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
            }
        }

        // Grid of Apps with Current Style & Shape
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Iconos de Aplicaciones", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Toca para personalizar", fontSize = 11.sp, color = Color(0xFF38BDF8))
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // 4-Column Apps Grid inside column
        item {
            val apps = selectedPack.icons
            val rows = apps.chunked(4)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rows.forEach { rowApps ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        rowApps.forEach { appDef ->
                            val iconVector = when (appDef.appType) {
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

                            Box(
                                modifier = Modifier.clickable {
                                    customizingAppDef = appDef.copy(shape = selectedShape)
                                    showCustomizerSheet = true
                                }
                            ) {
                                AppIconItem(
                                    name = appDef.customLabel,
                                    icon = iconVector,
                                    color = appDef.backgroundStartColor,
                                    shape = selectedShape,
                                    style = selectedPack.style,
                                    size = 54.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCustomizerSheet) {
        IconCustomizerSheet(
            initialIconDef = customizingAppDef,
            onDismiss = { showCustomizerSheet = false }
        )
    }
}
