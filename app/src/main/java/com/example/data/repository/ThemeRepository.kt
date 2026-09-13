package com.example.data.repository

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.R
import com.example.data.local.ActiveThemeEntity
import com.example.data.local.AppDatabase
import com.example.data.local.FavoriteWallpaperEntity
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val themeDao = db.themeDao()

    val favoriteWallpaperEntities: Flow<List<FavoriteWallpaperEntity>> =
        themeDao.getAllFavoriteWallpapers()

    val activeThemeEntity: Flow<ActiveThemeEntity?> =
        themeDao.getActiveTheme()

    fun getPresetThemes(): List<ThemePack> {
        return listOf(
            ThemePack(
                id = "theme_cyberpunk",
                title = "Cyberpunk Neon",
                subtitle = "Metrópolis futurista con neones violeta y cian",
                category = "Cyberpunk",
                wallpaperRes = R.drawable.img_wallpaper_cyberpunk,
                wallpaperTitle = "Cyber City 2099",
                primaryColor = Color(0xFFA855F7),
                secondaryColor = Color(0xFF06B6D4),
                accentColor = Color(0xFFEC4899),
                surfaceColor = Color(0xFF140D2B),
                iconPackId = "pack_neo_glow",
                iconStyle = IconStyle.NEO_GLOW,
                iconShape = IconShape.SQUIRCLE,
                defaultWidget = WidgetType.WEATHER_CLOCK,
                downloads = "342K",
                rating = 4.95f
            ),
            ThemePack(
                id = "theme_nature",
                title = "Atardecer Alpino",
                subtitle = "Paz montañosa con reflejos dorados y boreales",
                category = "Naturaleza",
                wallpaperRes = R.drawable.img_wallpaper_nature,
                wallpaperTitle = "Serenidad en las Alturas",
                primaryColor = Color(0xFF10B981),
                secondaryColor = Color(0xFFF59E0B),
                accentColor = Color(0xFF34D399),
                surfaceColor = Color(0xFF0D231E),
                iconPackId = "pack_minimal_flat",
                iconStyle = IconStyle.MINIMAL_FLAT,
                iconShape = IconShape.ROUNDED_SQUARE,
                defaultWidget = WidgetType.DAILY_QUOTE,
                downloads = "215K",
                rating = 4.88f
            ),
            ThemePack(
                id = "theme_minimal_glass",
                title = "Glassmorphism Minimal",
                subtitle = "Elegancia pura en 3D con cristal esmerilado",
                category = "Minimalista",
                wallpaperRes = R.drawable.img_wallpaper_minimal_abstract,
                wallpaperTitle = "Esferas de Cristal Pastel",
                primaryColor = Color(0xFFFB923C),
                secondaryColor = Color(0xFFA78BFA),
                accentColor = Color(0xFFF472B6),
                surfaceColor = Color(0xFF1E1B2E),
                iconPackId = "pack_glass_orb",
                iconStyle = IconStyle.GLASS_ORB,
                iconShape = IconShape.CIRCLE,
                defaultWidget = WidgetType.MUSIC_PLAYER,
                downloads = "410K",
                rating = 4.92f
            ),
            ThemePack(
                id = "theme_cosmic",
                title = "Cosmos Infinito",
                subtitle = "Nebulosas estelares y polvo cósmico brillante",
                category = "Espacio",
                wallpaperRes = R.drawable.img_wallpaper_cosmic,
                wallpaperTitle = "Nebulosa Ultravioleta",
                primaryColor = Color(0xFF8B5CF6),
                secondaryColor = Color(0xFF38BDF8),
                accentColor = Color(0xFFFBBF24),
                surfaceColor = Color(0xFF0B0A1F),
                iconPackId = "pack_cyber_outline",
                iconStyle = IconStyle.CYBER_OUTLINE,
                iconShape = IconShape.HEXAGON,
                defaultWidget = WidgetType.BATTERY_PERFORMANCE,
                downloads = "289K",
                rating = 4.97f
            ),
            ThemePack(
                id = "theme_pastel_dream",
                title = "Pastel Aura",
                subtitle = "Tonos suaves lilas, durazno y geometría orgánica",
                category = "Aesthetic",
                wallpaperRes = R.drawable.img_wallpaper_minimal_abstract,
                wallpaperTitle = "Aura Suave 8K",
                primaryColor = Color(0xFFF472B6),
                secondaryColor = Color(0xFFC084FC),
                accentColor = Color(0xFF38BDF8),
                surfaceColor = Color(0xFF26182C),
                iconPackId = "pack_pastel_glyphs",
                iconStyle = IconStyle.PASTEL_GLYPH,
                iconShape = IconShape.SQUIRCLE,
                defaultWidget = WidgetType.CALENDAR_AGENDA,
                downloads = "194K",
                rating = 4.86f
            ),
            ThemePack(
                id = "theme_fitness_pulse",
                title = "Pulse Vital",
                subtitle = "Energía viva, alto contraste y widgets deportivos",
                category = "Fitness",
                wallpaperRes = R.drawable.img_wallpaper_cyberpunk,
                wallpaperTitle = "Pulso Eléctrico",
                primaryColor = Color(0xFFF97316),
                secondaryColor = Color(0xFFE11D48),
                accentColor = Color(0xFFFACC15),
                surfaceColor = Color(0xFF1F1212),
                iconPackId = "pack_clay_3d",
                iconStyle = IconStyle.CLAY_3D,
                iconShape = IconShape.ROUNDED_SQUARE,
                defaultWidget = WidgetType.FITNESS_TRACKER,
                downloads = "165K",
                rating = 4.82f
            )
        )
    }

    fun getPresetWallpapers(): List<WallpaperItem> {
        return listOf(
            WallpaperItem(
                id = "wp_cyberpunk",
                title = "Metrópolis Cyberpunk 2099",
                category = "Cyberpunk",
                resId = R.drawable.img_wallpaper_cyberpunk,
                resolution = "8K Ultra HD",
                downloads = "184K",
                rating = 4.98f,
                palette = listOf(Color(0xFFA855F7), Color(0xFF06B6D4), Color(0xFFEC4899))
            ),
            WallpaperItem(
                id = "wp_nature",
                title = "Lago Alpino & Aurora",
                category = "Naturaleza",
                resId = R.drawable.img_wallpaper_nature,
                resolution = "8K Ultra HD",
                downloads = "152K",
                rating = 4.92f,
                palette = listOf(Color(0xFF10B981), Color(0xFFF59E0B), Color(0xFF065F46))
            ),
            WallpaperItem(
                id = "wp_minimal_abstract",
                title = "Esferas de Cristal y Velo 3D",
                category = "Minimalista",
                resId = R.drawable.img_wallpaper_minimal_abstract,
                resolution = "8K Ultra HD",
                downloads = "220K",
                rating = 4.95f,
                palette = listOf(Color(0xFFFB923C), Color(0xFFA78BFA), Color(0xFFFDF2F8))
            ),
            WallpaperItem(
                id = "wp_cosmic",
                title = "Nebulosa Estelar Cósmica",
                category = "Espacio",
                resId = R.drawable.img_wallpaper_cosmic,
                resolution = "8K Ultra HD",
                downloads = "196K",
                rating = 4.97f,
                palette = listOf(Color(0xFF8B5CF6), Color(0xFF38BDF8), Color(0xFFFDE047))
            )
        )
    }

    fun getIconPacks(): List<IconPack> {
        val baseIcons = listOf(
            AppIconDef(AppIconType.WHATSAPP, "WhatsApp", Color.White, Color(0xFF059669), Color(0xFF10B981), hasNotificationBadge = true),
            AppIconDef(AppIconType.INSTAGRAM, "Instagram", Color.White, Color(0xFF7C3AED), Color(0xFFEC4899), hasNotificationBadge = true),
            AppIconDef(AppIconType.SPOTIFY, "Spotify", Color.White, Color(0xFF15803D), Color(0xFF22C55E)),
            AppIconDef(AppIconType.CAMERA, "Cámara", Color.White, Color(0xFF4338CA), Color(0xFF6366F1)),
            AppIconDef(AppIconType.CHROME, "Chrome", Color.White, Color(0xFFB45309), Color(0xFFF59E0B)),
            AppIconDef(AppIconType.YOUTUBE, "YouTube", Color.White, Color(0xFFB91C1C), Color(0xFFEF4444)),
            AppIconDef(AppIconType.PHONE, "Teléfono", Color.White, Color(0xFF047857), Color(0xFF10B981)),
            AppIconDef(AppIconType.MESSAGES, "Mensajes", Color.White, Color(0xFF1D4ED8), Color(0xFF3B82F6), hasNotificationBadge = true),
            AppIconDef(AppIconType.SETTINGS, "Ajustes", Color.White, Color(0xFF374151), Color(0xFF6B7280)),
            AppIconDef(AppIconType.PHOTOS, "Fotos", Color.White, Color(0xFF9333EA), Color(0xFFC084FC)),
            AppIconDef(AppIconType.MUSIC, "Música", Color.White, Color(0xFFBE185D), Color(0xFFF43F5E)),
            AppIconDef(AppIconType.CALENDAR, "Calendario", Color.White, Color(0xFF0284C7), Color(0xFF38BDF8)),
            AppIconDef(AppIconType.WEATHER, "Clima", Color.White, Color(0xFF0284C7), Color(0xFF0EA5E9)),
            AppIconDef(AppIconType.CLOCK, "Reloj", Color.White, Color(0xFF4B5563), Color(0xFF9CA3AF))
        )

        return listOf(
            IconPack(
                id = "pack_neo_glow",
                name = "Neo Glow 3D",
                style = IconStyle.NEO_GLOW,
                shape = IconShape.SQUIRCLE,
                description = "Iconos con borde de neón brillante y degradados cyberpunk",
                accentStart = Color(0xFFA855F7),
                accentEnd = Color(0xFF06B6D4),
                icons = baseIcons.map { it.copy(shape = IconShape.SQUIRCLE) }
            ),
            IconPack(
                id = "pack_minimal_flat",
                name = "Minimal Flat Pro",
                style = IconStyle.MINIMAL_FLAT,
                shape = IconShape.ROUNDED_SQUARE,
                description = "Líneas limpias, acabados mate y paleta nórdica sofisticada",
                accentStart = Color(0xFF10B981),
                accentEnd = Color(0xFF065F46),
                icons = baseIcons.map { it.copy(shape = IconShape.ROUNDED_SQUARE) }
            ),
            IconPack(
                id = "pack_glass_orb",
                name = "Glass Spheres",
                style = IconStyle.GLASS_ORB,
                shape = IconShape.CIRCLE,
                description = "Esferas circulares con efecto de refracción de vidrio",
                accentStart = Color(0xFFFB923C),
                accentEnd = Color(0xFFA78BFA),
                icons = baseIcons.map { it.copy(shape = IconShape.CIRCLE) }
            ),
            IconPack(
                id = "pack_cyber_outline",
                name = "Cyber Hex Outline",
                style = IconStyle.CYBER_OUTLINE,
                shape = IconShape.HEXAGON,
                description = "Formato hexagonal futurista con contornos holográficos",
                accentStart = Color(0xFF8B5CF6),
                accentEnd = Color(0xFF38BDF8),
                icons = baseIcons.map { it.copy(shape = IconShape.HEXAGON) }
            ),
            IconPack(
                id = "pack_pastel_glyphs",
                name = "Pastel Glyphs",
                style = IconStyle.PASTEL_GLYPH,
                shape = IconShape.SQUIRCLE,
                description = "Tonos suaves pasteles con glifos minimalistas en blanco marfil",
                accentStart = Color(0xFFF472B6),
                accentEnd = Color(0xFFC084FC),
                icons = baseIcons.map { it.copy(shape = IconShape.SQUIRCLE) }
            ),
            IconPack(
                id = "pack_clay_3d",
                name = "Claymorphism 3D",
                style = IconStyle.CLAY_3D,
                shape = IconShape.ROUNDED_SQUARE,
                description = "Efecto de arcilla táctil tridimensional con sombras suaves",
                accentStart = Color(0xFFF97316),
                accentEnd = Color(0xFFE11D48),
                icons = baseIcons.map { it.copy(shape = IconShape.ROUNDED_SQUARE) }
            )
        )
    }

    suspend fun toggleFavoriteWallpaper(wallpaper: WallpaperItem): Boolean {
        val isFav = themeDao.isWallpaperFavorite(wallpaper.id)
        if (isFav) {
            themeDao.deleteFavoriteWallpaper(wallpaper.id)
            return false
        } else {
            themeDao.insertFavoriteWallpaper(
                FavoriteWallpaperEntity(
                    wallpaperId = wallpaper.id,
                    title = wallpaper.title,
                    category = wallpaper.category,
                    resId = wallpaper.resId,
                    resolution = wallpaper.resolution
                )
            )
            return true
        }
    }

    suspend fun applyTheme(theme: ThemePack) {
        themeDao.setActiveTheme(
            ActiveThemeEntity(
                themeId = theme.id,
                themeTitle = theme.title,
                wallpaperRes = theme.wallpaperRes,
                iconShapeName = theme.iconShape.name,
                iconStyleName = theme.iconStyle.name,
                widgetTypeName = theme.defaultWidget.name
            )
        )
    }
}
