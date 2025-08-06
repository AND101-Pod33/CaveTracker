package com.cavemanproductivity.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.cavemanproductivity.ui.theme.DarkSlateGray
import com.cavemanproductivity.ui.theme.Tomato

// Cave painting style icons
object CaveIcons {
    
    @Composable
    fun Fire(
        modifier: Modifier = Modifier,
        color: Color = Tomato,
        size: Int = 24
    ) {
        Canvas(modifier = modifier.size(size.dp)) {
            drawFire(color)
        }
    }
    
    @Composable
    fun StickFigure(
        modifier: Modifier = Modifier,
        color: Color = DarkSlateGray,
        size: Int = 24
    ) {
        Canvas(modifier = modifier.size(size.dp)) {
            drawStickFigure(color)
        }
    }
    
    @Composable
    fun Rock(
        modifier: Modifier = Modifier,
        color: Color = DarkSlateGray,
        size: Int = 24
    ) {
        Canvas(modifier = modifier.size(size.dp)) {
            drawRock(color)
        }
    }
    
    @Composable
    fun Moon(
        modifier: Modifier = Modifier,
        color: Color = DarkSlateGray,
        size: Int = 24
    ) {
        Canvas(modifier = modifier.size(size.dp)) {
            drawMoon(color)
        }
    }
    
    @Composable
    fun Spear(
        modifier: Modifier = Modifier,
        color: Color = DarkSlateGray,
        size: Int = 24
    ) {
        Canvas(modifier = modifier.size(size.dp)) {
            drawSpear(color)
        }
    }
    
    @Composable
    fun Cave(
        modifier: Modifier = Modifier,
        color: Color = DarkSlateGray,
        size: Int = 24
    ) {
        Canvas(modifier = modifier.size(size.dp)) {
            drawCave(color)
        }
    }
}

private fun DrawScope.drawFire(color: Color) {
    val path = Path().apply {
        moveTo(size.width * 0.5f, size.height * 0.9f)
        quadraticBezierTo(
            size.width * 0.3f, size.height * 0.7f,
            size.width * 0.4f, size.height * 0.4f
        )
        quadraticBezierTo(
            size.width * 0.45f, size.height * 0.2f,
            size.width * 0.5f, size.height * 0.1f
        )
        quadraticBezierTo(
            size.width * 0.55f, size.height * 0.2f,
            size.width * 0.6f, size.height * 0.4f
        )
        quadraticBezierTo(
            size.width * 0.7f, size.height * 0.7f,
            size.width * 0.5f, size.height * 0.9f
        )
        close()
    }
    drawPath(path, color = color)
}

private fun DrawScope.drawStickFigure(color: Color) {
    val strokeWidth = 4f
    
    // Head
    drawCircle(
        color = color,
        radius = size.width * 0.1f,
        center = Offset(size.width * 0.5f, size.height * 0.2f),
        style = Stroke(width = strokeWidth)
    )
    
    // Body
    drawLine(
        color = color,
        start = Offset(size.width * 0.5f, size.height * 0.3f),
        end = Offset(size.width * 0.5f, size.height * 0.7f),
        strokeWidth = strokeWidth
    )
    
    // Arms
    drawLine(
        color = color,
        start = Offset(size.width * 0.3f, size.height * 0.45f),
        end = Offset(size.width * 0.7f, size.height * 0.45f),
        strokeWidth = strokeWidth
    )
    
    // Legs
    drawLine(
        color = color,
        start = Offset(size.width * 0.5f, size.height * 0.7f),
        end = Offset(size.width * 0.3f, size.height * 0.9f),
        strokeWidth = strokeWidth
    )
    drawLine(
        color = color,
        start = Offset(size.width * 0.5f, size.height * 0.7f),
        end = Offset(size.width * 0.7f, size.height * 0.9f),
        strokeWidth = strokeWidth
    )
}

private fun DrawScope.drawRock(color: Color) {
    val path = Path().apply {
        moveTo(size.width * 0.2f, size.height * 0.8f)
        lineTo(size.width * 0.1f, size.height * 0.5f)
        lineTo(size.width * 0.3f, size.height * 0.2f)
        lineTo(size.width * 0.7f, size.height * 0.3f)
        lineTo(size.width * 0.9f, size.height * 0.6f)
        lineTo(size.width * 0.8f, size.height * 0.8f)
        close()
    }
    drawPath(path, color = color)
}

private fun DrawScope.drawMoon(color: Color) {
    drawCircle(
        color = color,
        radius = size.width * 0.4f,
        center = center,
        style = Stroke(width = 4f)
    )
    
    // Moon craters
    drawCircle(
        color = color,
        radius = size.width * 0.08f,
        center = Offset(size.width * 0.4f, size.height * 0.4f)
    )
    drawCircle(
        color = color,
        radius = size.width * 0.05f,
        center = Offset(size.width * 0.6f, size.height * 0.3f)
    )
    drawCircle(
        color = color,
        radius = size.width * 0.06f,
        center = Offset(size.width * 0.55f, size.height * 0.6f)
    )
}

private fun DrawScope.drawSpear(color: Color) {
    val strokeWidth = 6f
    
    // Spear shaft
    drawLine(
        color = color,
        start = Offset(size.width * 0.2f, size.height * 0.9f),
        end = Offset(size.width * 0.8f, size.height * 0.1f),
        strokeWidth = strokeWidth
    )
    
    // Spear tip
    val tipPath = Path().apply {
        moveTo(size.width * 0.8f, size.height * 0.1f)
        lineTo(size.width * 0.75f, size.height * 0.25f)
        lineTo(size.width * 0.85f, size.height * 0.25f)
        close()
    }
    drawPath(tipPath, color = color)
}

private fun DrawScope.drawCave(color: Color) {
    val path = Path().apply {
        moveTo(size.width * 0.1f, size.height * 0.9f)
        lineTo(size.width * 0.1f, size.height * 0.4f)
        quadraticBezierTo(
            size.width * 0.1f, size.height * 0.1f,
            size.width * 0.5f, size.height * 0.1f
        )
        quadraticBezierTo(
            size.width * 0.9f, size.height * 0.1f,
            size.width * 0.9f, size.height * 0.4f
        )
        lineTo(size.width * 0.9f, size.height * 0.9f)
        close()
    }
    drawPath(path, color = color, style = Stroke(width = 4f))
}