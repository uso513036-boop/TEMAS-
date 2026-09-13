package com.example.ui.components

import android.content.Context
import android.os.BatteryManager
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WidgetCustomConfig
import com.example.data.model.WidgetThemeStyle
import com.example.data.model.WidgetType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun InteractiveWidgetCard(
    widgetType: WidgetType,
    config: WidgetCustomConfig = WidgetCustomConfig(widgetType = widgetType),
    modifier: Modifier = Modifier,
    onCustomizeClick: (() -> Unit)? = null
) {
    val bgBrush = when (config.style) {
        WidgetThemeStyle.GLASSMORPHISM -> Brush.linearGradient(
            listOf(
                Color(0xFF1E1B38).copy(alpha = config.opacity),
                Color(0xFF0F0D22).copy(alpha = config.opacity + 0.05f)
            )
        )
        WidgetThemeStyle.AMOLED_BLACK -> Brush.linearGradient(
            listOf(Color(0xFF000000), Color(0xFF0A0A0A))
        )
        WidgetThemeStyle.NEUMORPHIC -> Brush.linearGradient(
            listOf(Color(0xFF1E293B), Color(0xFF0F172A))
        )
        WidgetThemeStyle.CYBER_NEON -> Brush.linearGradient(
            listOf(Color(0xFF1F0C3B), Color(0xFF05172E))
        )
        WidgetThemeStyle.FROST_LIGHT -> Brush.linearGradient(
            listOf(Color(0xFF334155).copy(alpha = 0.9f), Color(0xFF1E293B).copy(alpha = 0.95f))
        )
    }

    val shape = RoundedCornerShape(config.cornerRadius.dp)
    val borderColor = if (config.showBorder) {
        when (config.style) {
            WidgetThemeStyle.CYBER_NEON -> config.accentColor.copy(alpha = 0.6f)
            else -> Color.White.copy(alpha = 0.18f)
        }
    } else Color.Transparent

    Card(
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(bgBrush)
            .border(1.5.dp, borderColor, shape)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            when (widgetType) {
                WidgetType.WEATHER_CLOCK -> WeatherClockWidgetContent(config)
                WidgetType.MUSIC_PLAYER -> MusicPlayerWidgetContent(config)
                WidgetType.BATTERY_PERFORMANCE -> BatteryPerformanceWidgetContent(config)
                WidgetType.CALENDAR_AGENDA -> CalendarAgendaWidgetContent(config)
                WidgetType.DAILY_QUOTE -> DailyQuoteWidgetContent(config)
                WidgetType.FITNESS_TRACKER -> FitnessTrackerWidgetContent(config)
            }

            if (onCustomizeClick != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onCustomizeClick,
                        colors = ButtonDefaults.textButtonColors(contentColor = config.accentColor),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Outlined.Tune, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Personalizar Widget", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

// 1. Weather & Clock Widget
@Composable
fun WeatherClockWidgetContent(config: WidgetCustomConfig) {
    val context = LocalContext.current
    var isCelsius by remember { mutableStateOf(true) }
    val cities = listOf("Madrid", "Tokio", "Nueva York", "Buenos Aires", "Londres")
    var cityIndex by remember { mutableStateOf(0) }
    var forecastTab by remember { mutableStateOf(0) }

    val currentCity = cities[cityIndex]
    val tempC = when (cityIndex) {
        0 -> 24
        1 -> 19
        2 -> 22
        3 -> 17
        else -> 16
    }
    val tempDisplay = if (isCelsius) "$tempC°C" else "${(tempC * 9 / 5) + 32}°F"

    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val dateFormat = remember { SimpleDateFormat("EEEE, d 'de' MMMM", Locale("es", "ES")) }
    val currentTime = remember { timeFormat.format(Date()) }
    val currentDate = remember { dateFormat.format(Date()).replaceFirstChar { it.uppercase() } }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = currentTime,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = (-1).sp
                )
                Text(
                    text = currentDate,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            // Interactive City Switcher & Temp
            Column(horizontalAlignment = Alignment.End) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = config.accentColor.copy(alpha = 0.2f),
                    modifier = Modifier.clickable {
                        cityIndex = (cityIndex + 1) % cities.size
                        Toast.makeText(context, "Ciudad: ${cities[cityIndex]}", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Filled.LocationOn, contentDescription = null, tint = config.accentColor, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(currentCity, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { isCelsius = !isCelsius }
                ) {
                    Icon(
                        imageVector = Icons.Filled.WbSunny,
                        contentDescription = null,
                        tint = Color(0xFFFBBF24),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = tempDisplay,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Interactive mini forecast row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val days = listOf("Hoy" to "24°", "Lun" to "23°", "Mar" to "26°", "Mié" to "21°", "Jue" to "25°")
            days.forEachIndexed { index, pair ->
                val isSelected = forecastTab == index
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) config.accentColor.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.05f),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp)
                        .clickable { forecastTab = index }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {
                        Text(pair.first, fontSize = 11.sp, color = if (isSelected) config.accentColor else Color.White.copy(alpha = 0.6f), fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(2.dp))
                        Icon(
                            imageVector = if (index == 3) Icons.Filled.WaterDrop else Icons.Filled.WbSunny,
                            contentDescription = null,
                            tint = if (index == 3) Color(0xFF38BDF8) else Color(0xFFFBBF24),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(pair.second, fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// 2. Music Player Widget
@Composable
fun MusicPlayerWidgetContent(config: WidgetCustomConfig) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(true) }
    var isFavorite by remember { mutableStateOf(false) }
    var currentTrackIndex by remember { mutableStateOf(0) }
    var trackProgress by remember { mutableStateOf(0.42f) }

    val tracks = listOf(
        Triple("Midnight Cyberpunk", "Neo Synthwave", "3:42"),
        Triple("Starlight Nebula", "Cosmic Dreams", "4:15"),
        Triple("Brisa de los Andes", "Atmospheric Chill", "3:08"),
        Triple("Pastel Sunset", "Lofi Beats", "2:55")
    )
    val track = tracks[currentTrackIndex]

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Album Art Box with animated pulsating glow
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(config.accentColor, Color(0xFF8B5CF6))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = "Álbum",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.first,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.second,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.65f),
                    maxLines = 1
                )
            }

            // Favorite Heart Toggle
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    Toast.makeText(
                        context,
                        if (isFavorite) "Añadida a favoritos ❤️" else "Eliminada de favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (isFavorite) Color(0xFFF43F5E) else Color.White.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Interactive Progress bar
        Slider(
            value = trackProgress,
            onValueChange = { trackProgress = it },
            colors = SliderDefaults.colors(
                thumbColor = config.accentColor,
                activeTrackColor = config.accentColor,
                inactiveTrackColor = Color.White.copy(alpha = 0.2f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("1:28", fontSize = 10.sp, color = Color.White.copy(alpha = 0.5f))
            Text(track.third, fontSize = 10.sp, color = Color.White.copy(alpha = 0.5f))
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    currentTrackIndex = if (currentTrackIndex > 0) currentTrackIndex - 1 else tracks.size - 1
                    trackProgress = 0f
                }
            ) {
                Icon(Icons.Filled.SkipPrevious, contentDescription = "Anterior", tint = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Surface(
                shape = CircleShape,
                color = config.accentColor,
                modifier = Modifier
                    .size(46.dp)
                    .clickable { isPlaying = !isPlaying }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = "Play/Pausa",
                        tint = Color.Black,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = {
                    currentTrackIndex = (currentTrackIndex + 1) % tracks.size
                    trackProgress = 0f
                }
            ) {
                Icon(Icons.Filled.SkipNext, contentDescription = "Siguiente", tint = Color.White)
            }
        }
    }
}

// 3. Battery & Device Health Widget
@Composable
fun BatteryPerformanceWidgetContent(config: WidgetCustomConfig) {
    val context = LocalContext.current
    val batteryManager = remember { context.getSystemService(Context.BATTERY_SERVICE) as? BatteryManager }
    val realBattery = remember { batteryManager?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) ?: 82 }

    var isCleaned by remember { mutableStateOf(false) }
    var performanceMode by remember { mutableStateOf(1) } // 0: Ahorro, 1: Equilibrado, 2: Gaming

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(config.accentColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.BatteryChargingFull,
                        contentDescription = null,
                        tint = config.accentColor,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Batería & Estado", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("$realBattery% • Carga Óptima", fontSize = 11.sp, color = Color.White.copy(alpha = 0.6f))
                }
            }

            // Quick Clean Button
            Button(
                onClick = {
                    isCleaned = true
                    Toast.makeText(context, "⚡ ¡1.4 GB liberados! Rendimiento al máximo", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = config.accentColor.copy(alpha = 0.25f)),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Filled.Bolt, contentDescription = null, tint = config.accentColor, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isCleaned) "Optimizado" else "Optimizar", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Progress indicators: Storage and RAM
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Memoria RAM", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                    Text(if (isCleaned) "48%" else "64%", fontSize = 10.sp, color = config.accentColor, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { if (isCleaned) 0.48f else 0.64f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape),
                    color = config.accentColor,
                    trackColor = Color.White.copy(alpha = 0.15f)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Almacenamiento", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                    Text("42%", fontSize = 10.sp, color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { 0.42f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape),
                    color = Color(0xFF10B981),
                    trackColor = Color.White.copy(alpha = 0.15f)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Interactive Mode Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val modes = listOf("Eco Ahorro", "Equilibrado", "Turbo Gaming")
            modes.forEachIndexed { idx, mode ->
                val selected = performanceMode == idx
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (selected) config.accentColor else Color.White.copy(alpha = 0.08f),
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            performanceMode = idx
                            Toast.makeText(context, "Modo activado: $mode", Toast.LENGTH_SHORT).show()
                        }
                ) {
                    Text(
                        text = mode,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) Color.Black else Color.White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}

// 4. Calendar & Agenda Tasks Widget
@Composable
fun CalendarAgendaWidgetContent(config: WidgetCustomConfig) {
    var tasks by remember {
        mutableStateOf(
            listOf(
                "Diseñar paleta de colores de iconos" to true,
                "Configurar fondo de pantalla 8K" to false,
                "Probar nuevo widget de música" to false,
                "Publicar tema en favoritos" to false
            )
        )
    }
    var selectedDay by remember { mutableStateOf(2) }
    val days = listOf("Lun 14", "Mar 15", "Mié 16", "Jue 17", "Vie 18")

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.CalendarMonth, contentDescription = null, tint = config.accentColor, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Agenda Diaria", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            Text("${tasks.count { it.second }}/${tasks.size} completadas", fontSize = 11.sp, color = config.accentColor)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Days picker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEachIndexed { index, day ->
                val isSelected = selectedDay == index
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isSelected) config.accentColor else Color.White.copy(alpha = 0.08f),
                    modifier = Modifier
                        .clickable { selectedDay = index }
                ) {
                    Text(
                        text = day,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.Black else Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tasks list with checkboxes
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            tasks.forEachIndexed { index, pair ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            tasks = tasks.toMutableList().also {
                                it[index] = pair.first to !pair.second
                            }
                        }
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (pair.second) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                        contentDescription = null,
                        tint = if (pair.second) config.accentColor else Color.White.copy(alpha = 0.4f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = pair.first,
                        fontSize = 12.sp,
                        color = if (pair.second) Color.White.copy(alpha = 0.5f) else Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// 5. Daily Quote Widget
@Composable
fun DailyQuoteWidgetContent(config: WidgetCustomConfig) {
    val context = LocalContext.current
    val quotes = listOf(
        "El diseño no es sólo cómo se ve o se siente. El diseño es cómo funciona." to "Steve Jobs",
        "La simplicidad es la máxima sofisticación." to "Leonardo da Vinci",
        "Haz que tu teléfono sea una extensión de tu propia creatividad." to "Theme Studio",
        "El orden y la estética crean claridad en la mente." to "Minimal Art",
        "Todo lo que puedas imaginar es real." to "Pablo Picasso"
    )
    var quoteIndex by remember { mutableStateOf(0) }
    val (text, author) = quotes[quoteIndex]

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.FormatQuote, contentDescription = null, tint = config.accentColor, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Inspiración Diaria", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            IconButton(
                onClick = { quoteIndex = (quoteIndex + 1) % quotes.size },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = "Siguiente Cita", tint = config.accentColor, modifier = Modifier.size(18.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "“$text”",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "— $author",
                fontSize = 12.sp,
                color = config.accentColor,
                fontWeight = FontWeight.SemiBold
            )

            TextButton(
                onClick = {
                    Toast.makeText(context, "Cita copiada al portapapeles", Toast.LENGTH_SHORT).show()
                },
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Outlined.ContentCopy, contentDescription = null, tint = Color.White.copy(alpha = 0.6f), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Copiar", fontSize = 11.sp, color = Color.White.copy(alpha = 0.6f))
            }
        }
    }
}

// 6. Fitness & Hydration Widget
@Composable
fun FitnessTrackerWidgetContent(config: WidgetCustomConfig) {
    var waterGlasses by remember { mutableStateOf(4) }
    val stepCount = 7420
    val stepGoal = 10000

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.DirectionsRun, contentDescription = null, tint = config.accentColor, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Salud & Movimiento", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            Text("$stepCount / $stepGoal pasos", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
        }

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(
            progress = { stepCount.toFloat() / stepGoal },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = config.accentColor,
            trackColor = Color.White.copy(alpha = 0.15f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Interactive Hydration tracker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.WaterDrop, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Agua: ${waterGlasses * 250} ml (${waterGlasses} vasos)", fontSize = 12.sp, color = Color.White)
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF0284C7).copy(alpha = 0.3f),
                modifier = Modifier.clickable {
                    if (waterGlasses < 12) waterGlasses++ else waterGlasses = 1
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("+250 ml", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
