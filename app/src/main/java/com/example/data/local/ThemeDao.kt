package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ThemeDao {

    @Query("SELECT * FROM favorite_wallpapers ORDER BY addedAt DESC")
    fun getAllFavoriteWallpapers(): Flow<List<FavoriteWallpaperEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteWallpaper(wallpaper: FavoriteWallpaperEntity)

    @Query("DELETE FROM favorite_wallpapers WHERE wallpaperId = :wallpaperId")
    suspend fun deleteFavoriteWallpaper(wallpaperId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_wallpapers WHERE wallpaperId = :wallpaperId)")
    suspend fun isWallpaperFavorite(wallpaperId: String): Boolean

    @Query("SELECT * FROM active_theme_config WHERE id = 1")
    fun getActiveTheme(): Flow<ActiveThemeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveTheme(theme: ActiveThemeEntity)

    @Query("SELECT * FROM custom_icon_shortcuts ORDER BY createdAt DESC")
    fun getAllCustomShortcuts(): Flow<List<CustomIconShortcutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomShortcut(shortcut: CustomIconShortcutEntity)
}
