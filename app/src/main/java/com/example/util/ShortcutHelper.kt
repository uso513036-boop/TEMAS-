package com.example.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color as AndroidColor
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.MainActivity
import com.example.data.model.AppIconDef
import com.example.data.model.IconShape

object ShortcutHelper {

    fun createIconBitmap(context: Context, iconDef: AppIconDef, sizePx: Int = 192): Bitmap {
        val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val size = sizePx.toFloat()
        val padding = size * 0.08f
        val rect = RectF(padding, padding, size - padding, size - padding)

        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = LinearGradient(
                rect.left, rect.top, rect.right, rect.bottom,
                iconDef.backgroundStartColor.toArgb(),
                iconDef.backgroundEndColor.toArgb(),
                Shader.TileMode.CLAMP
            )
        }

        // Draw background shape
        when (iconDef.shape) {
            IconShape.CIRCLE -> {
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2f, bgPaint)
            }
            IconShape.SQUIRCLE, IconShape.ROUNDED_SQUARE -> {
                val radius = size * 0.22f
                canvas.drawRoundRect(rect, radius, radius, bgPaint)
            }
            IconShape.HEXAGON -> {
                val path = Path().apply {
                    val cx = rect.centerX()
                    val cy = rect.centerY()
                    val r = rect.width() / 2f
                    for (i in 0 until 6) {
                        val angle = Math.toRadians((60 * i - 30).toDouble())
                        val x = (cx + r * Math.cos(angle)).toFloat()
                        val y = (cy + r * Math.sin(angle)).toFloat()
                        if (i == 0) moveTo(x, y) else lineTo(x, y)
                    }
                    close()
                }
                canvas.drawPath(path, bgPaint)
            }
            IconShape.SHIELD -> {
                val path = Path().apply {
                    val w = rect.width()
                    val h = rect.height()
                    moveTo(rect.left, rect.top)
                    lineTo(rect.right, rect.top)
                    lineTo(rect.right, rect.top + h * 0.6f)
                    quadTo(rect.centerX(), rect.bottom, rect.left, rect.top + h * 0.6f)
                    close()
                }
                canvas.drawPath(path, bgPaint)
            }
        }

        // Draw inner glow/border
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = size * 0.02f
            color = AndroidColor.argb(70, 255, 255, 255)
        }
        if (iconDef.shape == IconShape.CIRCLE) {
            canvas.drawCircle(rect.centerX(), rect.centerY(), (rect.width() / 2f) - borderPaint.strokeWidth, borderPaint)
        } else {
            canvas.drawRoundRect(rect, size * 0.22f, size * 0.22f, borderPaint)
        }

        // Draw Icon Letter / Symbol Initial
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = iconDef.foregroundColor.toArgb()
            textSize = size * 0.38f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        val symbolChar = iconDef.customLabel.take(1).uppercase()
        val textY = rect.centerY() - ((textPaint.descent() + textPaint.ascent()) / 2f)
        canvas.drawText(symbolChar, rect.centerX(), textY, textPaint)

        return bitmap
    }

    fun pinShortcutToHomeScreen(
        context: Context,
        iconDef: AppIconDef
    ): Boolean {
        if (!ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            return false
        }

        val bitmap = createIconBitmap(context, iconDef)
        val iconCompat = IconCompat.createWithBitmap(bitmap)

        // Launch MainActivity or generic intent
        val launchIntent = Intent(context, MainActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            putExtra("EXTRA_LAUNCHED_FROM_THEME_ICON", iconDef.customLabel)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val shortcutInfo = ShortcutInfoCompat.Builder(context, "custom_icon_${iconDef.appType.name}_${System.currentTimeMillis()}")
            .setShortLabel(iconDef.customLabel)
            .setLongLabel("${iconDef.customLabel} (Theme Studio)")
            .setIcon(iconCompat)
            .setIntent(launchIntent)
            .build()

        return ShortcutManagerCompat.requestPinShortcut(context, shortcutInfo, null)
    }
}
