package com.example.ui.screens

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WidgetCustomConfig
import com.example.data.model.WidgetType
import com.example.ui.components.InteractiveWidgetCard
import com.example.ui.components.WidgetCustomizerSheet
import com.example.widget.ThemeStudioWidgetProvider

@Composable
fun WidgetsScreen() {
    val context = LocalContext.current
    var editingConfig by remember { mutableStateOf<WidgetCustomConfig?>(null) }

    // Map to keep track of user customized configs per widget type
    var widgetConfigs by remember {
        mutableStateOf(
            mapOf(
                WidgetType.WEATHER_CLOCK to WidgetCustomConfig(WidgetType.WEATHER_CLOCK, accentColor = Color(0xFF38BDF8)),
                WidgetType.MUSIC_PLAYER to WidgetCustomConfig(WidgetType.MUSIC_PLAYER, accentColor = Color(0xFFA855F7)),
                WidgetType.BATTERY_PERFORMANCE to WidgetCustomConfig(WidgetType.BATTERY_PERFORMANCE, accentColor = Color(0xFF10B981)),
                WidgetType.CALENDAR_AGENDA to WidgetCustomConfig(WidgetType.CALENDAR_AGENDA, accentColor = Color(0xFFF59E0B)),
                WidgetType.DAILY_QUOTE to WidgetCustomConfig(WidgetType.DAILY_QUOTE, accentColor = Color(0xFFEC4899)),
                WidgetType.FITNESS_TRACKER to WidgetCustomConfig(WidgetType.FITNESS_TRACKER, accentColor = Color(0xFF06B6D4))
            )
        )
    }

    val widgetList = listOf(
        Pair(WidgetType.WEATHER_CLOCK, "Reloj & Clima Dinámico"),
        Pair(WidgetType.MUSIC_PLAYER, "Reproductor de Música & Medios"),
        Pair(WidgetType.BATTERY_PERFORMANCE, "Batería & Rendimiento del Móvil"),
        Pair(WidgetType.CALENDAR_AGENDA, "Agenda de Tareas & Calendario"),
        Pair(WidgetType.DAILY_QUOTE, "Inspiración & Cita Diaria"),
        Pair(WidgetType.FITNESS_TRACKER, "Contador de Pasos & Hidratación")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Explanatory Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF0C243B), Color(0xFF0F172A))
                        ),
                        RoundedCornerShape(20.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFF38BDF8).copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Widgets, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(24.dp))
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Widgets Interactivos en Vivo", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(
                            "Prueba los controles directamente aquí y personaliza el diseño antes de colocarlos en tu teléfono.",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        items(widgetList) { (type, title) ->
            val config = widgetConfigs[type] ?: WidgetCustomConfig(type)
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    TextButton(
                        onClick = { editingConfig = config },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Icon(Icons.Filled.Tune, contentDescription = null, tint = config.accentColor, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ajustes", fontSize = 12.sp, color = config.accentColor, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                InteractiveWidgetCard(
                    widgetType = type,
                    config = config,
                    onCustomizeClick = { editingConfig = config }
                )
            }
        }
    }

    editingConfig?.let { cfg ->
        WidgetCustomizerSheet(
            initialConfig = cfg,
            onDismiss = { editingConfig = null },
            onSaveConfig = { updated ->
                widgetConfigs = widgetConfigs.toMutableMap().also { it[updated.widgetType] = updated }
                Toast.makeText(context, "Estilo de widget actualizado", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
