package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.IconShape
import com.example.data.model.IconStyle
import com.example.data.model.WallpaperItem
import com.example.util.WallpaperHelper
import kotlinx.coroutines.launch

@Composable
fun WallpaperViewerDialog(
    wallpaper: WallpaperItem,
    isFavorite: Boolean,
    onDismiss: () -> Unit,
    onToggleFavorite: (WallpaperItem) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var showIconsOverlay by remember { mutableStateOf(false) }
    var isApplying by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Fullscreen High-Res Image
            Image(
                painter = painterResource(id = wallpaper.resId),
                contentDescription = wallpaper.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Optional Icons Overlay preview
            if (showIconsOverlay) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.35f))
                        .padding(horizontal = 24.dp, vertical = 72.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            "10:42",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        val mockApps = listOf(
                            Triple("WhatsApp", Icons.Filled.Chat, Color(0xFF10B981)),
                            Triple("Instagram", Icons.Filled.CameraAlt, Color(0xFFEC4899)),
                            Triple("Spotify", Icons.Filled.MusicNote, Color(0xFF22C55E)),
                            Triple("YouTube", Icons.Filled.PlayArrow, Color(0xFFEF4444))
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            mockApps.forEach { (name, icon, tint) ->
                                AppIconItem(
                                    name = name,
                                    icon = icon,
                                    color = tint,
                                    shape = IconShape.SQUIRCLE,
                                    style = IconStyle.NEO_GLOW,
                                    size = 54.dp
                                )
                            }
                        }
                    }
                }
            }

            // Top Bar Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.size(42.dp)
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, contentDescription = "Cerrar", tint = Color.White)
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Toggle Mock Icons Preview
                    Surface(
                        shape = CircleShape,
                        color = if (showIconsOverlay) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.5f),
                        modifier = Modifier.size(42.dp)
                    ) {
                        IconButton(onClick = { showIconsOverlay = !showIconsOverlay }) {
                            Icon(
                                imageVector = if (showIconsOverlay) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = "Simular Iconos",
                                tint = Color.White
                            )
                        }
                    }

                    // Favorite Button
                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.5f),
                        modifier = Modifier.size(42.dp)
                    ) {
                        IconButton(onClick = { onToggleFavorite(wallpaper) }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (isFavorite) Color(0xFFF43F5E) else Color.White
                            )
                        }
                    }
                }
            }

            // Bottom Action Card
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFF161226).copy(alpha = 0.92f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.18f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = wallpaper.title,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color(0xFF38BDF8).copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            wallpaper.resolution,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF38BDF8),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        wallpaper.aspectRatio,
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.6f)
                                    )
                                }
                            }

                            // Download HD Button
                            OutlinedButton(
                                onClick = {
                                    coroutineScope.launch {
                                        val res = WallpaperHelper.saveWallpaperToGallery(
                                            context,
                                            wallpaper.resId,
                                            wallpaper.title
                                        )
                                        Toast.makeText(context, res.getOrDefault("Guardado"), Toast.LENGTH_SHORT).show()
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Filled.Download, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Guardar HD", fontSize = 12.sp, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Quick apply options row
                        Text(
                            "Aplicar como Fondo de Pantalla:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Home Screen
                            Button(
                                onClick = {
                                    isApplying = true
                                    coroutineScope.launch {
                                        val result = WallpaperHelper.applyWallpaper(
                                            context,
                                            wallpaper.resId,
                                            WallpaperHelper.WallpaperTarget.HOME
                                        )
                                        isApplying = false
                                        val msg = result.getOrElse { it.message ?: "Error al aplicar" }
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(vertical = 10.dp)
                            ) {
                                Icon(Icons.Filled.Home, contentDescription = null, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Inicio", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            // Lock Screen
                            Button(
                                onClick = {
                                    isApplying = true
                                    coroutineScope.launch {
                                        val result = WallpaperHelper.applyWallpaper(
                                            context,
                                            wallpaper.resId,
                                            WallpaperHelper.WallpaperTarget.LOCK
                                        )
                                        isApplying = false
                                        val msg = result.getOrElse { it.message ?: "Error al aplicar" }
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(vertical = 10.dp)
                            ) {
                                Icon(Icons.Filled.Lock, contentDescription = null, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Bloqueo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            // Both
                            Button(
                                onClick = {
                                    isApplying = true
                                    coroutineScope.launch {
                                        val result = WallpaperHelper.applyWallpaper(
                                            context,
                                            wallpaper.resId,
                                            WallpaperHelper.WallpaperTarget.BOTH
                                        )
                                        isApplying = false
                                        val msg = result.getOrElse { it.message ?: "Error al aplicar" }
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC4899)),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(vertical = 10.dp)
                            ) {
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Ambas", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
