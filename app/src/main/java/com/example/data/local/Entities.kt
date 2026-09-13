package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_wallpapers")
data class FavoriteWallpaperEntity(
    @PrimaryKey val wallpaperId: String,
    val title: String,
    val category: String,
    val resId: Int,
    val resolution: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "active_theme_config")
data class ActiveThemeEntity(
    @PrimaryKey val id: Int = 1,
    val themeId: String,
    val themeTitle: String,
    val wallpaperRes: Int,
    val iconShapeName: String,
    val iconStyleName: String,
    val widgetTypeName: String,
    val appliedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "custom_icon_shortcuts")
data class CustomIconShortcutEntity(
    @PrimaryKey val id: String,
    val appType: String,
    val customLabel: String,
    val shapeName: String,
    val bgStartColor: Long,
    val bgEndColor: Long,
    val iconColor: Long,
    val createdAt: Long = System.currentTimeMillis()
)
