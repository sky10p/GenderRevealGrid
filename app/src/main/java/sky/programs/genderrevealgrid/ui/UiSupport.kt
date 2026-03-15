package sky.programs.genderrevealgrid.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sky.programs.genderrevealgrid.model.ThemeConfig
import sky.programs.genderrevealgrid.model.ThemeIcon
import sky.programs.genderrevealgrid.model.WinningGender
import sky.programs.genderrevealgrid.ui.theme.StageAccentFamily
import sky.programs.genderrevealgrid.ui.theme.StageRoundedFamily

@Composable
internal fun HeroHeader(
    eyebrow: String,
    title: String,
    subtitle: String,
    theme: ThemeConfig
) {
    Card(
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.78f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ThemeIconBadge(
                icon = theme.icon,
                colors = theme.backgroundColors,
                modifier = Modifier.size(68.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = eyebrow,
                    style = MaterialTheme.typography.labelLarge.copy(
                        letterSpacing = 1.4.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF7D7890)
                    )
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2B2D42)
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF5B6170))
                )
            }
        }
    }
}

@Composable
internal fun SectionCard(
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.82f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2B2D42)
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF697287))
                )
            }
            content()
        }
    }
}

@Composable
internal fun ThemeIconBadge(
    icon: ThemeIcon,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(1.dp, Color.White.copy(alpha = 0.72f), CircleShape)
            .background(Color.White.copy(alpha = 0.7f), CircleShape)
            .padding(7.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            when (icon) {
                ThemeIcon.BALLOON -> drawBalloon(colors)
                ThemeIcon.CLOUD -> drawCloud(colors)
                ThemeIcon.SPARKLES -> drawSparkle(colors)
            }
        }
    }
}

@Composable
internal fun PlayfulMomentHeader(
    eyebrow: String? = null,
    title: String,
    subtitle: String,
    theme: ThemeConfig,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ThemeIconBadge(
                icon = theme.icon,
                colors = theme.backgroundColors,
                modifier = Modifier.size(64.dp)
            )
            if (!eyebrow.isNullOrBlank()) {
                Text(
                    text = eyebrow,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = StageRoundedFamily,
                        letterSpacing = 1.1.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8C879B)
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = StageRoundedFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    lineHeight = 42.sp,
                    color = Color(0xFF25283D)
                ),
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = StageAccentFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 26.sp,
                    lineHeight = 30.sp,
                    color = Color(0xFFF39AC5)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
internal fun PremiumBadge() {
    Box(
        modifier = Modifier
            .size(28.dp)
            .background(Color.White.copy(alpha = 0.78f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(16.dp)) {
            val stroke = size.minDimension * 0.12f
            drawRoundRect(
                color = Color(0xFF30334A),
                topLeft = Offset(size.width * 0.22f, size.height * 0.48f),
                size = Size(size.width * 0.56f, size.height * 0.34f),
                cornerRadius = CornerRadius(size.minDimension * 0.1f)
            )
            drawArc(
                color = Color(0xFF30334A),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(size.width * 0.22f, size.height * 0.12f),
                size = Size(size.width * 0.56f, size.height * 0.5f),
                style = Stroke(width = stroke)
            )
        }
    }
}

internal fun ThemeConfig.backgroundBrush(): Brush = Brush.linearGradient(backgroundColors)

internal fun ThemeConfig.boardPanelBrush(): Brush = Brush.linearGradient(
    listOf(
        boardFrameColor.copy(alpha = 0.98f),
        Color.White.copy(alpha = 0.95f),
        boardFrameColor.copy(alpha = 0.9f)
    )
)

internal fun ThemeConfig.celebrationBrush(): Brush = Brush.linearGradient(
    listOf(
        Color.White.copy(alpha = 0.98f),
        celebrationColors.first().copy(alpha = 0.22f),
        celebrationColors.last().copy(alpha = 0.18f)
    )
)

internal fun ThemeConfig.buttonBrush(): Brush = Brush.horizontalGradient(celebrationColors)

internal fun WinningGender.accentColor(): Color =
    if (this == WinningGender.BOY) Color(0xFF73BEDC) else Color(0xFFF39AC5)

internal fun WinningGender.softColor(): Color =
    if (this == WinningGender.BOY) Color(0xFFEAF8FF) else Color(0xFFFFEFF7)

internal fun DrawScope.drawCloud(colors: List<Color>) {
    val base = Color.White.copy(alpha = 0.94f)
    val tint = colors.last().copy(alpha = 0.22f)
    drawCircle(
        color = tint,
        radius = size.minDimension * 0.25f,
        center = Offset(size.width * 0.34f, size.height * 0.61f)
    )
    drawCircle(
        color = tint,
        radius = size.minDimension * 0.3f,
        center = Offset(size.width * 0.52f, size.height * 0.47f)
    )
    drawCircle(
        color = tint,
        radius = size.minDimension * 0.24f,
        center = Offset(size.width * 0.68f, size.height * 0.61f)
    )
    drawRoundRect(
        color = tint,
        topLeft = Offset(size.width * 0.2f, size.height * 0.58f),
        size = Size(size.width * 0.6f, size.height * 0.22f),
        cornerRadius = CornerRadius(size.minDimension * 0.12f)
    )
    drawCircle(
        color = base,
        radius = size.minDimension * 0.23f,
        center = Offset(size.width * 0.34f, size.height * 0.56f)
    )
    drawCircle(
        color = base,
        radius = size.minDimension * 0.28f,
        center = Offset(size.width * 0.52f, size.height * 0.42f)
    )
    drawCircle(
        color = base,
        radius = size.minDimension * 0.22f,
        center = Offset(size.width * 0.68f, size.height * 0.56f)
    )
    drawRoundRect(
        color = base,
        topLeft = Offset(size.width * 0.22f, size.height * 0.54f),
        size = Size(size.width * 0.56f, size.height * 0.2f),
        cornerRadius = CornerRadius(size.minDimension * 0.1f)
    )
}

internal fun DrawScope.drawBalloon(colors: List<Color>) {
    val outlineColor = Color(0xFF7A7D96).copy(alpha = 0.68f)
    val ropeColor = Color(0xFF9AA2B1).copy(alpha = 0.92f)
    val balloonTop = Offset(size.width * 0.16f, size.height * 0.05f)
    val balloonSize = Size(size.width * 0.68f, size.height * 0.6f)

    drawOval(
        color = colors.first().copy(alpha = 0.98f),
        topLeft = balloonTop,
        size = balloonSize
    )

    val stripeWidth = balloonSize.width / 4f
    repeat(3) { index ->
        drawRoundRect(
            color = colors.last().copy(alpha = 0.96f),
            topLeft = Offset(balloonTop.x + stripeWidth * (index + 0.4f), balloonTop.y),
            size = Size(stripeWidth * 0.56f, balloonSize.height),
            cornerRadius = CornerRadius(stripeWidth * 0.3f)
        )
    }

    drawOval(
        color = Color.White.copy(alpha = 0.34f),
        topLeft = Offset(balloonTop.x + balloonSize.width * 0.14f, balloonTop.y + balloonSize.height * 0.08f),
        size = Size(balloonSize.width * 0.22f, balloonSize.height * 0.2f)
    )
    drawArc(
        color = outlineColor,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = balloonTop,
        size = balloonSize,
        style = Stroke(width = size.minDimension * 0.015f)
    )
    drawPath(
        path = androidx.compose.ui.graphics.Path().apply {
            moveTo(size.width * 0.18f, size.height * 0.33f)
            quadraticTo(size.width * 0.5f, size.height * 0.52f, size.width * 0.82f, size.height * 0.33f)
        },
        color = outlineColor,
        style = Stroke(width = size.minDimension * 0.013f)
    )
    drawLine(
        color = ropeColor,
        start = Offset(size.width * 0.3f, size.height * 0.6f),
        end = Offset(size.width * 0.4f, size.height * 0.84f),
        strokeWidth = size.minDimension * 0.018f
    )
    drawLine(
        color = ropeColor,
        start = Offset(size.width * 0.7f, size.height * 0.6f),
        end = Offset(size.width * 0.6f, size.height * 0.84f),
        strokeWidth = size.minDimension * 0.018f
    )
    drawLine(
        color = ropeColor,
        start = Offset(size.width * 0.5f, size.height * 0.64f),
        end = Offset(size.width * 0.5f, size.height * 0.84f),
        strokeWidth = size.minDimension * 0.018f
    )
    drawRoundRect(
        color = Color(0xFFD9A557).copy(alpha = 0.92f),
        topLeft = Offset(size.width * 0.36f, size.height * 0.84f),
        size = Size(size.width * 0.28f, size.height * 0.12f),
        cornerRadius = CornerRadius(size.minDimension * 0.05f)
    )
    drawLine(
        color = Color(0xFFA86E1D).copy(alpha = 0.85f),
        start = Offset(size.width * 0.38f, size.height * 0.88f),
        end = Offset(size.width * 0.62f, size.height * 0.88f),
        strokeWidth = size.minDimension * 0.018f
    )
    drawLine(
        color = Color(0xFFA86E1D).copy(alpha = 0.52f),
        start = Offset(size.width * 0.4f, size.height * 0.92f),
        end = Offset(size.width * 0.6f, size.height * 0.92f),
        strokeWidth = size.minDimension * 0.01f
    )
}

internal fun DrawScope.drawSparkle(colors: List<Color>) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val radius = size.minDimension * 0.28f
    drawLine(
        brush = Brush.linearGradient(listOf(Color(0xFFFFF3A8), Color(0xFFF7D91D), Color(0xFFFFF3A8))),
        start = Offset(center.x, center.y - radius),
        end = Offset(center.x, center.y + radius),
        strokeWidth = size.minDimension * 0.08f,
        pathEffect = PathEffect.cornerPathEffect(10f)
    )
    drawLine(
        brush = Brush.linearGradient(listOf(Color(0xFFFFF3A8), Color(0xFFF7D91D), Color(0xFFFFF3A8))),
        start = Offset(center.x - radius, center.y),
        end = Offset(center.x + radius, center.y),
        strokeWidth = size.minDimension * 0.08f,
        pathEffect = PathEffect.cornerPathEffect(10f)
    )
    drawCircle(color = Color(0xFFFFF3A8), radius = size.minDimension * 0.08f, center = center)
}

internal fun DrawScope.drawHeart(colors: List<Color>) {
    val topY = size.height * 0.28f
    val centerX = size.width * 0.5f
    val leftX = size.width * 0.28f
    val rightX = size.width * 0.72f
    val bottomY = size.height * 0.82f
    val controlY = size.height * 0.08f
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(centerX, bottomY)
        cubicTo(
            size.width * 0.08f,
            size.height * 0.56f,
            size.width * 0.08f,
            topY,
            leftX,
            topY
        )
        cubicTo(
            size.width * 0.36f,
            controlY,
            size.width * 0.46f,
            controlY,
            centerX,
            size.height * 0.28f
        )
        cubicTo(
            size.width * 0.54f,
            controlY,
            size.width * 0.64f,
            controlY,
            rightX,
            topY
        )
        cubicTo(
            size.width * 0.92f,
            topY,
            size.width * 0.92f,
            size.height * 0.56f,
            centerX,
            bottomY
        )
        close()
    }

    drawPath(
        path = path,
        brush = Brush.verticalGradient(
            listOf(
                colors.first().copy(alpha = 0.98f),
                colors.last().copy(alpha = 0.84f)
            )
        )
    )
}
