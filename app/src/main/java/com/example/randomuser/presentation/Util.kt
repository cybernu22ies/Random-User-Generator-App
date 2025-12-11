package com.example.randomuser.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle


fun Color.linearGradient(
    start: Offset = Offset(x = -2000f, y = 0.0f),
    end: Offset = Offset(x = 2800f, y = 0f)
) =
    Brush.linearGradient(
        listOf(
            Color.White,
            this,
            Color.Black,
        ),
        start = start,
        end = end
    )

fun TextStyle.setColor(color: Color) =
    TextStyle(
        color = color,
        fontSize = this.fontSize,
        fontWeight = this.fontWeight,
        fontStyle = this.fontStyle,
        fontSynthesis = this.fontSynthesis,
        fontFamily = this.fontFamily,
        fontFeatureSettings = this.fontFeatureSettings,
        letterSpacing = this.letterSpacing,
        baselineShift = this.baselineShift,
        textGeometricTransform = this.textGeometricTransform,
        localeList = this.localeList,
        background = this.background,
        textDecoration = this.textDecoration,
        shadow = this.shadow,
        drawStyle = this.drawStyle,
        textAlign = this.textAlign,
        textDirection = this.textDirection,
        lineHeight = this.lineHeight,
        textIndent = this.textIndent,
        platformStyle = this.platformStyle,
        lineHeightStyle = this.lineHeightStyle,
        lineBreak = this.lineBreak,
        hyphens = this.hyphens,
        textMotion = this.textMotion
    )

