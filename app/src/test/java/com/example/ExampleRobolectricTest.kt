package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.ThemeRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Theme Studio", appName)
  }

  @Test
  fun `repository loads preset themes and wallpapers`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repository = ThemeRepository(context)
    val themes = repository.getPresetThemes()
    val wallpapers = repository.getPresetWallpapers()
    val iconPacks = repository.getIconPacks()

    assertTrue("Themes should not be empty", themes.isNotEmpty())
    assertTrue("Wallpapers should not be empty", wallpapers.isNotEmpty())
    assertTrue("Icon packs should not be empty", iconPacks.isNotEmpty())
  }
}
