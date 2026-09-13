package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.R

enum class IconShape {
    SQUIRCLE,
    CIRCLE,
    ROUNDED_SQUARE,
    HEXAGON,
    SHIELD
}

enum class IconStyle {
    NEO_GLOW,
    MINIMAL_FLAT,
    PASTEL_GLYPH,
    GLASS_ORB,
    CLAY_3D,
    CYBER_OUTLINE
}

enum class WidgetType {
    WEATHER_CLOCK,
    MUSIC_PLAYER,
    BATTERY_PERFORMANCE,
    CALENDAR_AGENDA,
    DAILY_QUOTE,
    FITNESS_TRACKER
}

enum class WidgetThemeStyle(val displayName: String) {
    GLASSMORPHISM("Cristal Esmerilado"),
    AMOLED_BLACK("Negro AMOLED"),
    NEUMORPHIC("Neumórfico Suave"),
    CYBER_NEON("Neón Cyberpunk"),
    FROST_LIGHT("Luz Nórdica")
}

enum class AppIconType(val defaultLabel: String) {
    WHATSAPP("WhatsApp"),
    INSTAGRAM("Instagram"),
    SPOTIFY("Spotify"),
    CAMERA("Cámara"),
    CHROME("Navegador"),
    YOUTUBE("YouTube"),
    PHONE("Teléfono"),
    MESSAGES("Mensajes"),
    SETTINGS("Ajustes"),
    PHOTOS("Galería"),
    MUSIC("Música"),
    CALENDAR("Calendario"),
    WEATHER("Clima"),
    CLOCK("Reloj")
}

data class AppIconDef(
    val appType: AppIconType,
    val customLabel: String = appType.defaultLabel,
    val foregroundColor: Color = Color.White,
    val backgroundStartColor: Color = Color(0xFF1E1B4B),
    val backgroundEndColor: Color = Color(0xFF4338CA),
    val shape: IconShape = IconShape.SQUIRCLE,
    val hasNotificationBadge: Boolean = false
)

data class IconPack(
    val id: String,
    val name: String,
    val style: IconStyle,
    val shape: IconShape,
    val description: String,
    val accentStart: Color,
    val accentEnd: Color,
    val icons: List<AppIconDef>
)

data class WallpaperItem(
    val id: String,
    val title: String,
    val category: String,
    val resId: Int,
    val resolution: String = "8K Ultra HD",
    val aspectRatio: String = "9:16",
    val downloads: String = "128K",
    val rating: Float = 4.9f,
    val palette: List<Color> = listOf(Color(0xFF6366F1), Color(0xFFA855F7), Color(0xFFEC4899)),
    val isFavorite: Boolean = false
)

data class ThemePack(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val wallpaperRes: Int,
    val wallpaperTitle: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val accentColor: Color,
    val surfaceColor: Color,
    val iconPackId: String,
    val iconStyle: IconStyle,
    val iconShape: IconShape,
    val defaultWidget: WidgetType,
    val downloads: String,
    val rating: Float,
    val isApplied: Boolean = false
)

data class WidgetCustomConfig(
    val widgetType: WidgetType,
    val style: WidgetThemeStyle = WidgetThemeStyle.GLASSMORPHISM,
    val cornerRadius: Int = 24,
    val opacity: Float = 0.88f,
    val accentColor: Color = Color(0xFF38BDF8),
    val showBorder: Boolean = true
)
