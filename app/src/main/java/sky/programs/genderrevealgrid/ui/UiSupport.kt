package sky.programs.genderrevealgrid.ui

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke

internal fun DrawScope.drawCloud(colors: List<Color>) {
    val color = colors.first().copy(alpha = 0.88f)
    drawCircle(color = color, radius = size.minDimension * 0.35f, center = Offset(size.width * 0.35f, size.height * 0.5f))
    drawCircle(color = color, radius = size.minDimension * 0.45f, center = Offset(size.width * 0.55f, size.height * 0.45f))
    drawCircle(color = color, radius = size.minDimension * 0.35f, center = Offset(size.width * 0.75f, size.height * 0.55f))
}

internal fun DrawScope.drawBalloon(colors: List<Color>) {
    val color = colors[1].copy(alpha = 0.9f)
    drawOval(
        color = color,
        topLeft = Offset(size.width * 0.2f, size.height * 0.1f),
        size = Size(size.width * 0.6f, size.height * 0.7f)
    )
    val path = Path().apply {
        moveTo(size.width * 0.5f, size.height * 0.8f)
        lineTo(size.width * 0.45f, size.height * 0.9f)
        lineTo(size.width * 0.55f, size.height * 0.9f)
        close()
    }
    drawPath(path = path, color = color)
    drawLine(color = Color.Gray.copy(alpha = 0.4f), start = Offset(size.width * 0.5f, size.height * 0.9f), end = Offset(size.width * 0.5f, size.height), strokeWidth = 2f)
}

internal fun DrawScope.drawSparkle(colors: List<Color>) {
    val color = colors.last().copy(alpha = 0.95f)
    val path = Path().apply {
        moveTo(size.width * 0.5f, 0f)
        quadraticTo(size.width * 0.5f, size.height * 0.5f, size.width, size.height * 0.5f)
        quadraticTo(size.width * 0.5f, size.height * 0.5f, size.width * 0.5f, size.height)
        quadraticTo(size.width * 0.5f, size.height * 0.5f, 0f, size.height * 0.5f)
        quadraticTo(size.width * 0.5f, size.height * 0.5f, size.width * 0.5f, 0f)
        close()
    }
    drawPath(path = path, color = color)
}

internal fun DrawScope.drawHeart(colors: List<Color>) {
    val color = colors[1].copy(alpha = 0.85f)
    val path = Path().apply {
        moveTo(size.width * 0.5f, size.height * 0.35f)
        cubicTo(size.width * 0.2f, size.height * 0.1f, 0f, size.height * 0.45f, size.width * 0.5f, size.height * 0.9f)
        cubicTo(size.width, size.height * 0.45f, size.width * 0.8f, size.height * 0.1f, size.width * 0.5f, size.height * 0.35f)
        close()
    }
    drawPath(path = path, color = color)
}

internal fun DrawScope.drawSuperheroBadge(colors: List<Color>) {
    val shield = Path().apply {
        moveTo(size.width * 0.2f, size.height * 0.2f)
        lineTo(size.width * 0.8f, size.height * 0.2f)
        lineTo(size.width * 0.9f, size.height * 0.5f)
        lineTo(size.width * 0.5f, size.height * 0.9f)
        lineTo(size.width * 0.1f, size.height * 0.5f)
        close()
    }
    drawPath(
        path = shield,
        brush = Brush.verticalGradient(
            listOf(colors.first(), colors[1], Color(0xFFFFD447))
        )
    )
    drawPath(path = shield, color = Color.White.copy(alpha = 0.72f), style = Stroke(width = size.minDimension * 0.05f))

    val bolt = Path().apply {
        moveTo(size.width * 0.56f, size.height * 0.24f)
        lineTo(size.width * 0.42f, size.height * 0.52f)
        lineTo(size.width * 0.54f, size.height * 0.52f)
        lineTo(size.width * 0.44f, size.height * 0.8f)
        lineTo(size.width * 0.68f, size.height * 0.44f)
        lineTo(size.width * 0.56f, size.height * 0.44f)
        close()
    }
    drawPath(path = bolt, color = Color(0xFFFFF3A8))
}

internal fun DrawScope.drawMagicWand(colors: List<Color>) {
    val wandStart = Offset(size.width * 0.24f, size.height * 0.76f)
    val wandEnd = Offset(size.width * 0.72f, size.height * 0.28f)
    drawLine(
        brush = Brush.linearGradient(listOf(Color(0xFF7C5A2C), Color(0xFFC59B58))),
        start = wandStart,
        end = wandEnd,
        strokeWidth = size.minDimension * 0.08f,
        cap = androidx.compose.ui.graphics.StrokeCap.Round
    )
    drawCircle(color = Color(0xFFF4D97A), radius = size.minDimension * 0.08f, center = wandEnd)
    drawCircle(color = Color.White.copy(alpha = 0.8f), radius = size.minDimension * 0.04f, center = wandEnd)
}

internal fun DrawScope.drawLightningBolt(colors: List<Color>) {
    val path = Path().apply {
        moveTo(size.width * 0.6f, size.height * 0.04f)
        lineTo(size.width * 0.28f, size.height * 0.46f)
        lineTo(size.width * 0.46f, size.height * 0.46f)
        lineTo(size.width * 0.24f, size.height * 0.96f)
        lineTo(size.width * 0.74f, size.height * 0.38f)
        lineTo(size.width * 0.54f, size.height * 0.38f)
        close()
    }
    drawPath(
        path = path,
        brush = Brush.verticalGradient(listOf(Color(0xFFFFF1A0), Color(0xFFFFD447), colors[1]))
    )
}

internal fun DrawScope.drawComicBurst(colors: List<Color>) {
    val center = Offset(size.width * 0.5f, size.height * 0.5f)
    val radiusOuter = size.minDimension * 0.48f
    val radiusInner = size.minDimension * 0.22f
    val path = Path()
    repeat(12) { index ->
        val angle = Math.toRadians((index * 30.0) - 90.0)
        val radius = if (index % 2 == 0) radiusOuter else radiusInner
        val x = center.x + (kotlin.math.cos(angle) * radius).toFloat()
        val y = center.y + (kotlin.math.sin(angle) * radius).toFloat()
        if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path = path, color = Color(0xFFFFF5BE).copy(alpha = 0.82f))
    drawPath(path = path, color = colors.last().copy(alpha = 0.54f), style = Stroke(width = size.minDimension * 0.05f))
}

internal fun DrawScope.drawCitySkyline(colors: List<Color>) {
    val baseColor = Color(0xFF0A122A).copy(alpha = 0.95f)
    val buildings = listOf(
        Triple(0.0f, 0.12f, 0.38f),
        Triple(0.12f, 0.10f, 0.56f),
        Triple(0.22f, 0.14f, 0.48f),
        Triple(0.36f, 0.12f, 0.72f),
        Triple(0.48f, 0.10f, 0.50f),
        Triple(0.58f, 0.14f, 0.62f),
        Triple(0.72f, 0.10f, 0.44f),
        Triple(0.82f, 0.11f, 0.54f),
        Triple(0.93f, 0.07f, 0.36f)
    )
    buildings.forEach { (x, widthFraction, heightFraction) ->
        drawRect(
            color = baseColor,
            topLeft = Offset(size.width * x, size.height * (1f - heightFraction)),
            size = Size(size.width * widthFraction, size.height * heightFraction)
        )
        // Windows
        val winW = size.width * 0.018f
        val winH = size.height * 0.028f
        val cols = if (widthFraction > 0.12f) 3 else 2
        val rows = (heightFraction * 5).toInt().coerceIn(2, 4)
        repeat(rows) { r ->
            repeat(cols) { c ->
                val lit = (r + c) % 3 != 0
                drawRect(
                    color = if (lit) Color(0xFFFFD54F).copy(alpha = 0.45f)
                    else Color(0xFF4FC3F7).copy(alpha = 0.15f),
                    topLeft = Offset(
                        size.width * (x + 0.02f + c * 0.035f),
                        size.height * (1f - heightFraction + 0.04f + r * 0.09f)
                    ),
                    size = Size(winW, winH)
                )
            }
        }
    }
    // Antenna on tallest building
    val antennaX = size.width * 0.42f
    val antennaTop = size.height * 0.22f
    drawLine(
        color = Color(0xFF37474F),
        start = Offset(antennaX, size.height * 0.28f),
        end = Offset(antennaX, antennaTop),
        strokeWidth = 2.5f
    )
    drawCircle(
        color = Color(0xFFE53935).copy(alpha = 0.7f),
        radius = size.minDimension * 0.012f,
        center = Offset(antennaX, antennaTop)
    )
}

internal fun DrawScope.drawHeroStar(colors: List<Color>) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val path = Path()
    repeat(10) { index ->
        val angle = Math.toRadians((index * 36.0) - 90.0)
        val radius = if (index % 2 == 0) size.minDimension * 0.44f else size.minDimension * 0.18f
        val x = center.x + (kotlin.math.cos(angle) * radius).toFloat()
        val y = center.y + (kotlin.math.sin(angle) * radius).toFloat()
        if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path = path, brush = Brush.radialGradient(listOf(Color(0xFFFFF6C7), colors.first(), colors.last())))
}

internal fun DrawScope.drawHeroSilhouette(colors: List<Color>) {
    val bodyColor = Color.White.copy(alpha = 0.30f)
    val capeColor = Color(0xFFE53935).copy(alpha = 0.22f)

    // Cape streaming behind the hero
    val capePath = Path().apply {
        moveTo(size.width * 0.38f, size.height * 0.40f)
        cubicTo(
            size.width * 0.22f, size.height * 0.44f,
            size.width * 0.10f, size.height * 0.56f,
            size.width * 0.06f, size.height * 0.78f
        )
        lineTo(size.width * 0.14f, size.height * 0.72f)
        cubicTo(
            size.width * 0.18f, size.height * 0.58f,
            size.width * 0.26f, size.height * 0.50f,
            size.width * 0.40f, size.height * 0.48f
        )
        close()
    }
    drawPath(path = capePath, color = capeColor, style = Fill)

    // Torso (angled forward, flying)
    val torso = Path().apply {
        moveTo(size.width * 0.32f, size.height * 0.54f)
        lineTo(size.width * 0.62f, size.height * 0.38f)
        lineTo(size.width * 0.64f, size.height * 0.46f)
        lineTo(size.width * 0.34f, size.height * 0.60f)
        close()
    }
    drawPath(path = torso, color = bodyColor, style = Fill)

    // Head
    drawOval(
        color = bodyColor,
        topLeft = Offset(size.width * 0.62f, size.height * 0.28f),
        size = Size(size.width * 0.13f, size.height * 0.15f)
    )

    // Forward arm (stretched ahead, fist)
    val armForward = Path().apply {
        moveTo(size.width * 0.62f, size.height * 0.38f)
        lineTo(size.width * 0.90f, size.height * 0.26f)
        lineTo(size.width * 0.88f, size.height * 0.32f)
        lineTo(size.width * 0.63f, size.height * 0.43f)
        close()
    }
    drawPath(path = armForward, color = bodyColor, style = Fill)

    // Trailing legs
    val legs = Path().apply {
        moveTo(size.width * 0.32f, size.height * 0.54f)
        lineTo(size.width * 0.12f, size.height * 0.64f)
        lineTo(size.width * 0.14f, size.height * 0.70f)
        lineTo(size.width * 0.34f, size.height * 0.62f)
        close()
    }
    drawPath(path = legs, color = bodyColor, style = Fill)

    // Speed lines
    val lineColor = Color.White.copy(alpha = 0.14f)
    drawLine(lineColor, Offset(size.width * 0.02f, size.height * 0.34f), Offset(size.width * 0.22f, size.height * 0.34f), strokeWidth = 2f)
    drawLine(lineColor, Offset(0f, size.height * 0.44f), Offset(size.width * 0.16f, size.height * 0.44f), strokeWidth = 1.5f)
    drawLine(lineColor, Offset(size.width * 0.06f, size.height * 0.54f), Offset(size.width * 0.20f, size.height * 0.54f), strokeWidth = 1.5f)
}

internal fun DrawScope.drawOwl(colors: List<Color>) {
    val bodyColor = Color(0xFFD8C4A2).copy(alpha = 0.92f)
    val wingColor = Color(0xFF8C6D4B).copy(alpha = 0.82f)
    val darkAccent = Color(0xFF5C4324).copy(alpha = 0.7f)

    // Ear tufts
    val leftEar = Path().apply {
        moveTo(size.width * 0.36f, size.height * 0.32f)
        lineTo(size.width * 0.32f, size.height * 0.16f)
        lineTo(size.width * 0.42f, size.height * 0.28f)
        close()
    }
    drawPath(path = leftEar, color = darkAccent)
    val rightEar = Path().apply {
        moveTo(size.width * 0.64f, size.height * 0.32f)
        lineTo(size.width * 0.68f, size.height * 0.16f)
        lineTo(size.width * 0.58f, size.height * 0.28f)
        close()
    }
    drawPath(path = rightEar, color = darkAccent)

    // Body
    drawOval(
        color = bodyColor,
        topLeft = Offset(size.width * 0.28f, size.height * 0.28f),
        size = Size(size.width * 0.44f, size.height * 0.48f)
    )

    // Chest (lighter belly)
    drawOval(
        color = Color(0xFFF0E4CC).copy(alpha = 0.6f),
        topLeft = Offset(size.width * 0.36f, size.height * 0.46f),
        size = Size(size.width * 0.28f, size.height * 0.26f)
    )

    // Wings
    drawOval(
        color = wingColor,
        topLeft = Offset(size.width * 0.06f, size.height * 0.36f),
        size = Size(size.width * 0.30f, size.height * 0.24f)
    )
    drawOval(
        color = wingColor,
        topLeft = Offset(size.width * 0.64f, size.height * 0.36f),
        size = Size(size.width * 0.30f, size.height * 0.24f)
    )

    // Eyes (large, owl-like)
    drawCircle(color = Color.White, radius = size.minDimension * 0.10f, center = Offset(size.width * 0.42f, size.height * 0.42f))
    drawCircle(color = Color.White, radius = size.minDimension * 0.10f, center = Offset(size.width * 0.58f, size.height * 0.42f))
    drawCircle(color = Color(0xFFF5A623), radius = size.minDimension * 0.07f, center = Offset(size.width * 0.42f, size.height * 0.42f))
    drawCircle(color = Color(0xFFF5A623), radius = size.minDimension * 0.07f, center = Offset(size.width * 0.58f, size.height * 0.42f))
    drawCircle(color = Color(0xFF25283D), radius = size.minDimension * 0.04f, center = Offset(size.width * 0.42f, size.height * 0.42f))
    drawCircle(color = Color(0xFF25283D), radius = size.minDimension * 0.04f, center = Offset(size.width * 0.58f, size.height * 0.42f))
    // Eye glint
    drawCircle(color = Color.White.copy(alpha = 0.8f), radius = size.minDimension * 0.015f, center = Offset(size.width * 0.44f, size.height * 0.40f))
    drawCircle(color = Color.White.copy(alpha = 0.8f), radius = size.minDimension * 0.015f, center = Offset(size.width * 0.60f, size.height * 0.40f))

    // Beak
    drawPath(
        path = Path().apply {
            moveTo(size.width * 0.50f, size.height * 0.48f)
            lineTo(size.width * 0.44f, size.height * 0.56f)
            lineTo(size.width * 0.56f, size.height * 0.56f)
            close()
        },
        color = Color(0xFFF4D97A)
    )
}

internal fun DrawScope.drawFloatingCandle(colors: List<Color>) {
    drawRoundRect(
        color = Color(0xFFF7F0DE),
        topLeft = Offset(size.width * 0.42f, size.height * 0.3f),
        size = Size(size.width * 0.16f, size.height * 0.5f),
        cornerRadius = CornerRadius(size.minDimension * 0.04f)
    )
    drawCircle(
        brush = Brush.radialGradient(listOf(Color(0xFFFFD37A), Color.Transparent)),
        radius = size.minDimension * 0.25f,
        center = Offset(size.width * 0.5f, size.height * 0.2f)
    )
    drawCircle(color = Color.White, radius = size.minDimension * 0.04f, center = Offset(size.width * 0.5f, size.height * 0.2f))
}

internal fun DrawScope.drawMagicWandTrail(colors: List<Color>) {
    // Wand stick
    val wandStart = Offset(size.width * 0.14f, size.height * 0.74f)
    val wandTip = Offset(size.width * 0.52f, size.height * 0.34f)
    drawLine(
        brush = Brush.linearGradient(
            listOf(Color(0xFF5C3D1C), Color(0xFF8B6332), Color(0xFFC9A462)),
            start = wandStart,
            end = wandTip
        ),
        start = wandStart,
        end = wandTip,
        strokeWidth = size.minDimension * 0.06f,
        cap = androidx.compose.ui.graphics.StrokeCap.Round
    )

    // Sparkle trail from the wand tip
    val trailPath = Path().apply {
        moveTo(wandTip.x, wandTip.y)
        quadraticTo(size.width * 0.62f, size.height * 0.18f, size.width * 0.82f, size.height * 0.30f)
        quadraticTo(size.width * 0.92f, size.height * 0.45f, size.width * 0.78f, size.height * 0.60f)
    }
    drawPath(
        path = trailPath,
        brush = Brush.linearGradient(
            listOf(Color(0xFFE8C1FF), Color(0xFF88C8FF), Color(0xFFF4D97A))
        ),
        style = Stroke(
            width = size.minDimension * 0.06f,
            pathEffect = PathEffect.cornerPathEffect(24f)
        )
    )

    // Wand tip glow
    drawCircle(
        brush = Brush.radialGradient(listOf(Color(0xFFF4D97A), Color.Transparent)),
        radius = size.minDimension * 0.14f,
        center = wandTip
    )
    drawCircle(color = Color.White, radius = size.minDimension * 0.03f, center = wandTip)

    // Sparkle dots along the trail
    val sparkles = listOf(
        Offset(size.width * 0.60f, size.height * 0.20f) to Color(0xFFF4D97A),
        Offset(size.width * 0.72f, size.height * 0.24f) to Color(0xFFE8C1FF),
        Offset(size.width * 0.84f, size.height * 0.35f) to Color(0xFFB3E5FC),
        Offset(size.width * 0.86f, size.height * 0.48f) to Color(0xFFF4D97A),
        Offset(size.width * 0.80f, size.height * 0.56f) to Color(0xFFE8C1FF),
    )
    sparkles.forEach { (pos, color) ->
        drawCircle(color = color.copy(alpha = 0.7f), radius = size.minDimension * 0.022f, center = pos)
    }
}

internal fun DrawScope.drawCrescentMoon(colors: List<Color>) {
    val moonColor = Color(0xFFF4D97A)
    val center = Offset(size.width * 0.5f, size.height * 0.5f)
    // Outer glow
    drawCircle(
        brush = Brush.radialGradient(listOf(moonColor.copy(alpha = 0.18f), Color.Transparent)),
        radius = size.minDimension * 0.50f,
        center = center
    )
    // Moon body
    drawCircle(color = moonColor.copy(alpha = 0.92f), radius = size.minDimension * 0.35f, center = center)
    // Shadow to create crescent shape
    drawCircle(color = Color(0xFF0B0618), radius = size.minDimension * 0.30f, center = Offset(size.width * 0.64f, size.height * 0.40f))
}

internal fun DrawScope.drawMagicDust(colors: List<Color>) {
    val palette = listOf(Color(0xFFF4D97A), Color(0xFFE8C1FF), Color(0xFFB7F3E8), Color.White)
    repeat(8) { i ->
        val x = (i * 0.15f + 0.1f) % 1f
        val y = (i * 0.12f + 0.2f) % 1f
        drawCircle(
            color = palette[i % palette.size].copy(alpha = 0.6f),
            radius = size.minDimension * (0.02f + (i % 3) * 0.02f),
            center = Offset(size.width * x, size.height * y)
        )
    }
}

internal fun DrawScope.drawScroll(colors: List<Color>) {
    drawRoundRect(
        color = Color(0xFFF4E6C8).copy(alpha = 0.9f),
        topLeft = Offset(size.width * 0.2f, size.height * 0.34f),
        size = Size(size.width * 0.6f, size.height * 0.32f),
        cornerRadius = CornerRadius(size.minDimension * 0.06f)
    )
}

internal fun DrawScope.drawCastleSilhouette(colors: List<Color>) {
    val castleColor = Color(0xFF070414).copy(alpha = 0.9f)
    val path = Path().apply {
        moveTo(0f, size.height)
        lineTo(0f, size.height * 0.58f)
        // Left small tower
        lineTo(size.width * 0.06f, size.height * 0.58f)
        lineTo(size.width * 0.06f, size.height * 0.38f)
        lineTo(size.width * 0.08f, size.height * 0.22f) // pointed top
        lineTo(size.width * 0.10f, size.height * 0.38f)
        lineTo(size.width * 0.10f, size.height * 0.58f)
        // Wall
        lineTo(size.width * 0.22f, size.height * 0.58f)
        // Battlement crenels
        lineTo(size.width * 0.22f, size.height * 0.52f)
        lineTo(size.width * 0.26f, size.height * 0.52f)
        lineTo(size.width * 0.26f, size.height * 0.56f)
        lineTo(size.width * 0.30f, size.height * 0.56f)
        lineTo(size.width * 0.30f, size.height * 0.52f)
        lineTo(size.width * 0.34f, size.height * 0.52f)
        lineTo(size.width * 0.34f, size.height * 0.58f)
        // Left tall tower
        lineTo(size.width * 0.36f, size.height * 0.58f)
        lineTo(size.width * 0.36f, size.height * 0.28f)
        lineTo(size.width * 0.40f, size.height * 0.10f) // pointed top
        lineTo(size.width * 0.44f, size.height * 0.28f)
        lineTo(size.width * 0.44f, size.height * 0.58f)
        // Central great hall
        lineTo(size.width * 0.46f, size.height * 0.44f)
        lineTo(size.width * 0.50f, size.height * 0.32f)
        lineTo(size.width * 0.54f, size.height * 0.44f)
        // Right tall tower
        lineTo(size.width * 0.56f, size.height * 0.58f)
        lineTo(size.width * 0.56f, size.height * 0.26f)
        lineTo(size.width * 0.60f, size.height * 0.08f) // tallest pointed top
        lineTo(size.width * 0.64f, size.height * 0.26f)
        lineTo(size.width * 0.64f, size.height * 0.58f)
        // Right wall crenels
        lineTo(size.width * 0.68f, size.height * 0.52f)
        lineTo(size.width * 0.72f, size.height * 0.52f)
        lineTo(size.width * 0.72f, size.height * 0.56f)
        lineTo(size.width * 0.76f, size.height * 0.56f)
        lineTo(size.width * 0.76f, size.height * 0.52f)
        lineTo(size.width * 0.80f, size.height * 0.52f)
        lineTo(size.width * 0.80f, size.height * 0.58f)
        // Right small tower
        lineTo(size.width * 0.88f, size.height * 0.58f)
        lineTo(size.width * 0.88f, size.height * 0.36f)
        lineTo(size.width * 0.91f, size.height * 0.20f) // pointed top
        lineTo(size.width * 0.94f, size.height * 0.36f)
        lineTo(size.width * 0.94f, size.height * 0.58f)
        lineTo(size.width, size.height * 0.58f)
        lineTo(size.width, size.height)
        close()
    }
    drawPath(path = path, color = castleColor, style = Fill)

    // Illuminated windows
    val windowColor = Color(0xFFF4D97A).copy(alpha = 0.55f)
    val windows = listOf(
        Offset(size.width * 0.40f, size.height * 0.38f),
        Offset(size.width * 0.60f, size.height * 0.36f),
        Offset(size.width * 0.50f, size.height * 0.40f),
        Offset(size.width * 0.91f, size.height * 0.42f),
        Offset(size.width * 0.08f, size.height * 0.44f),
        Offset(size.width * 0.27f, size.height * 0.62f),
        Offset(size.width * 0.74f, size.height * 0.62f),
    )
    windows.forEach { pos ->
        drawCircle(
            brush = Brush.radialGradient(listOf(windowColor, Color.Transparent)),
            radius = size.minDimension * 0.022f,
            center = pos
        )
    }
}

internal fun DrawScope.drawMagicRune(colors: List<Color>) {
    val glowColor = Color(0xFFB7F3E8).copy(alpha = 0.75f)
    val strokeWidth = size.minDimension * 0.06f

    // Outer circle
    drawCircle(
        color = glowColor,
        radius = size.minDimension * 0.42f,
        center = Offset(size.width * 0.5f, size.height * 0.5f),
        style = Stroke(width = strokeWidth)
    )

    // Triangle inscribed
    val triangle = Path().apply {
        moveTo(size.width * 0.5f, size.height * 0.14f)
        lineTo(size.width * 0.15f, size.height * 0.78f)
        lineTo(size.width * 0.85f, size.height * 0.78f)
        close()
    }
    drawPath(path = triangle, color = glowColor.copy(alpha = 0.6f), style = Stroke(width = strokeWidth))

    // Vertical line through center
    drawLine(
        color = glowColor.copy(alpha = 0.45f),
        start = Offset(size.width * 0.5f, size.height * 0.14f),
        end = Offset(size.width * 0.5f, size.height * 0.78f),
        strokeWidth = strokeWidth * 0.6f
    )

    // Inner glow dot
    drawCircle(
        brush = Brush.radialGradient(listOf(Color(0xFFB7F3E8).copy(alpha = 0.5f), Color.Transparent)),
        radius = size.minDimension * 0.12f,
        center = Offset(size.width * 0.5f, size.height * 0.5f)
    )
}
