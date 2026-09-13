package com.example.ui.components

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainActivity
import com.example.data.model.WidgetCustomConfig
import com.example.data.model.WidgetThemeStyle
import com.example.data.model.WidgetType
import com.example.widget.ThemeStudioWidgetProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetCustomizerSheet(
    initialConfig: WidgetCustomConfig,
    onDismiss: () -> Unit,
    onSaveConfig: (WidgetCustomConfig) -> Unit
) {
    val context = LocalContext.current
    var currentStyle by remember { mutableStateOf(initialConfig.style) }
    var cornerRadius by remember { mutableStateOf(initialConfig.cornerRadius.toFloat()) }
    var opacity by remember { mutableStateOf(initialConfig.opacity) }
    var selectedAccent by remember { mutableStateOf(initialConfig.accentColor) }

    val accentColors = listOf(
        Color(0xFF38BDF8), // Cyan Sky
        Color(0xFFA855F7), // Purple Neon
        Color(0xFFEC4899), // Hot Pink
        Color(0xFF10B981), // Emerald Mint
        Color(0xFFF59E0B), // Golden Amber
        Color(0xFFEF4444)  // Vibrant Red
    )

    val updatedConfig = WidgetCustomConfig(
        widgetType = initialConfig.widgetType,
        style = currentStyle,
        cornerRadius = cornerRadius.toInt(),
        opacity = opacity,
        accentColor = selectedAccent,
        showBorder = true
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
                text = "Editor de Widget Interactivo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Personaliza el aspecto, bordes, opacidad y colores",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Live Preview of the updated widget
            InteractiveWidgetCard(
                widgetType = initialConfig.widgetType,
                config = updatedConfig,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 1. Style Selection
            Text("1. Estilo Visual", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(WidgetThemeStyle.values()) { style ->
                    val isSelected = currentStyle == style
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) selectedAccent else Color.White.copy(alpha = 0.08f),
                        modifier = Modifier.clickable { currentStyle = style }
                    ) {
                        Text(
                            text = style.displayName,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.Black else Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Corner Radius Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("2. Curvatura de Esquinas", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("${cornerRadius.toInt()} dp", fontSize = 12.sp, color = selectedAccent, fontWeight = FontWeight.Bold)
            }
            Slider(
                value = cornerRadius,
                onValueChange = { cornerRadius = it },
                valueRange = 8f..36f,
                colors = SliderDefaults.colors(thumbColor = selectedAccent, activeTrackColor = selectedAccent)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Opacity Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("3. Opacidad del Fondo", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("${(opacity * 100).toInt()}%", fontSize = 12.sp, color = selectedAccent, fontWeight = FontWeight.Bold)
            }
            Slider(
                value = opacity,
                onValueChange = { opacity = it },
                valueRange = 0.4f..1.0f,
                colors = SliderDefaults.colors(thumbColor = selectedAccent, activeTrackColor = selectedAccent)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Accent Color
            Text("4. Color de Acento", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                accentColors.forEach { color ->
                    val isSelected = selectedAccent == color
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.2f),
                                shape = CircleShape
                            )
                            .clickable { selectedAccent = color },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Icon(Icons.Filled.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Pin to Home Screen Action
            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val appWidgetManager = AppWidgetManager.getInstance(context)
                        val provider = ComponentName(context, ThemeStudioWidgetProvider::class.java)
                        if (appWidgetManager.isRequestPinAppWidgetSupported) {
                            val successCallback = Intent(context, MainActivity::class.java)
                            val successPendingIntent = PendingIntent.getActivity(
                                context,
                                0,
                                successCallback,
                                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                            )
                            appWidgetManager.requestPinAppWidget(provider, null, successPendingIntent)
                            Toast.makeText(context, "Solicitud de Widget enviada a la Pantalla de Inicio", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Widget listo. Mantén presionada la pantalla de inicio para agregarlo", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(context, "Mantén pulsada tu pantalla de inicio para colocar el widget", Toast.LENGTH_LONG).show()
                    }
                    onSaveConfig(updatedConfig)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = selectedAccent)
            ) {
                Icon(Icons.Filled.Widgets, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Añadir Widget al Teléfono", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
