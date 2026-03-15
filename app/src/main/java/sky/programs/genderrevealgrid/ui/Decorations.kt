package sky.programs.genderrevealgrid.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin
import sky.programs.genderrevealgrid.model.ThemeConfig
import sky.programs.genderrevealgrid.model.ThemeDecoration
import sky.programs.genderrevealgrid.model.ThemeDecorationSpec

@Composable
internal fun AnimatedDecorationLayer(theme: ThemeConfig) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TestTags.ThemeDecorationLayer)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight
        val density = LocalDensity.current

        theme.decorations.forEachIndexed { index, spec ->
            val transition = rememberInfiniteTransition(label = "decor-$index")
            val progress by transition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = spec.durationMillis,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "decor-drift-$index"
            )

            val horizontalWave = sin((progress * 2f * PI + index).toFloat())
            val driftXPx = with(density) { spec.driftX.toPx() }
            val driftYPx = with(density) { spec.driftY.toPx() }
            val xOffset = (
                width * spec.xFraction +
                    horizontalWave * driftXPx * 2f
                ).roundToInt()
            val yOffset = (
                height * spec.yFraction +
                    (-driftYPx + driftYPx * 2f * progress)
                ).roundToInt()
            val alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * progress

            BackgroundDecoration(
                spec = spec,
                colors = theme.backgroundColors,
                modifier = Modifier
                    .offset { IntOffset(xOffset, yOffset) }
                    .size(spec.size)
                    .alpha(alpha)
            )
        }
    }
}

@Composable
private fun BackgroundDecoration(
    spec: ThemeDecorationSpec,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        when (spec.type) {
            ThemeDecoration.CLOUD -> drawCloud(colors)
            ThemeDecoration.BALLOON -> drawBalloon(colors)
            ThemeDecoration.STAR -> drawSparkle(colors)
            ThemeDecoration.HEART -> drawHeart(colors)
        }
    }
}

@Composable
internal fun ConfettiLayer(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "confetti")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "confetti-progress"
    )

    val palette = listOf(
        Color(0xFFFF8FB8),
        Color(0xFF7CCAF2),
        Color(0xFFFFD36E),
        Color(0xFF8DE0C1)
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        repeat(42) { index ->
            val x = ((index * 37) % 100) / 100f * width
            val localPhase = (index * 0.07f) % 1f
            val y = (((progress + localPhase) % 1.2f) - 0.2f) * height
            val sway = sin((progress * 2f * PI + index).toFloat()) * 24f
            drawRoundRect(
                color = palette[index % palette.size],
                topLeft = Offset(x + sway, y),
                size = Size(10f + (index % 4) * 4f, 18f + (index % 3) * 5f),
                cornerRadius = CornerRadius(6f, 6f),
                alpha = 0.9f
            )
        }
    }
}
