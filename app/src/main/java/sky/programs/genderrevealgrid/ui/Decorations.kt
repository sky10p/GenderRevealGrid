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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntOffset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import sky.programs.genderrevealgrid.model.ThemeConfig
import sky.programs.genderrevealgrid.model.ThemeDecoration
import sky.programs.genderrevealgrid.model.ThemeDecorationSpec
import sky.programs.genderrevealgrid.model.ThemeMotion

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
            val rawProgress by transition.animateFloat(
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
            val progress = (rawProgress + spec.phaseOffset) % 1f

            val wave = sin((progress * 2f * PI + index).toFloat())
            val secondaryWave = cos((progress * 2f * PI + index).toFloat())
            val driftXPx = with(density) { spec.driftX.toPx() }
            val driftYPx = with(density) { spec.driftY.toPx() }
            val sizePx = with(density) { spec.size.toPx() }

            val placement = decorationPlacement(
                spec = spec,
                width = width.toFloat(),
                height = height.toFloat(),
                sizePx = sizePx,
                progress = progress,
                wave = wave,
                secondaryWave = secondaryWave,
                driftXPx = driftXPx,
                driftYPx = driftYPx
            )

            BackgroundDecoration(
                spec = spec,
                colors = theme.backgroundColors,
                modifier = Modifier
                    .offset { IntOffset(placement.x.roundToInt(), placement.y.roundToInt()) }
                    .size(spec.size)
                    .graphicsLayer {
                        alpha = (placement.alpha * spec.visibilityBoost()).coerceIn(0f, 1f)
                        val scale = placement.scale * spec.scaleBoost()
                        scaleX = if (spec.mirrorHorizontally) -scale else scale
                        scaleY = scale
                        rotationZ = placement.rotation
                    }
            )
        }
    }
}

private data class DecorationPlacement(
    val x: Float,
    val y: Float,
    val alpha: Float,
    val scale: Float,
    val rotation: Float
)

private fun decorationPlacement(
    spec: ThemeDecorationSpec,
    width: Float,
    height: Float,
    sizePx: Float,
    progress: Float,
    wave: Float,
    secondaryWave: Float,
    driftXPx: Float,
    driftYPx: Float
): DecorationPlacement {
    val baseX = width * spec.xFraction
    val baseY = height * spec.yFraction

    return when (spec.motion) {
        ThemeMotion.BALLOON_SWAY -> DecorationPlacement(
            x = baseX + wave * driftXPx,
            y = baseY - secondaryWave * driftYPx,
            alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * (0.7f + 0.3f * progress),
            scale = 1.02f + 0.07f * (0.5f + 0.5f * wave),
            rotation = spec.rotationDegrees + wave * 5f
        )

        ThemeMotion.CLOUD_SWEEP -> {
            val travelWidth = width + sizePx * 2f
            DecorationPlacement(
                x = -sizePx + progress * travelWidth,
                y = baseY + secondaryWave * driftYPx * 0.25f,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * (0.82f + 0.18f * progress),
                scale = 1.06f + 0.05f * wave,
                rotation = spec.rotationDegrees
            )
        }

        ThemeMotion.HEART_RISE -> {
            val visibility = (1f - (kotlin.math.abs(progress - 0.5f) / 0.5f)).coerceIn(0f, 1f)
            DecorationPlacement(
                x = baseX + wave * driftXPx,
                y = height + sizePx - progress * (height + sizePx * 1.8f),
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * visibility,
                scale = 0.92f + 0.34f * visibility,
                rotation = spec.rotationDegrees + wave * 12f
            )
        }

        ThemeMotion.TWINKLE -> {
            val pulse = 0.5f + 0.5f * secondaryWave
            DecorationPlacement(
                x = baseX + wave * driftXPx * 0.25f,
                y = baseY + secondaryWave * driftYPx * 0.2f,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * pulse,
                scale = 0.92f + 0.34f * pulse,
                rotation = spec.rotationDegrees + wave * 6f
            )
        }

        ThemeMotion.DRIFT -> DecorationPlacement(
            x = baseX + wave * driftXPx * 2f,
            y = baseY + (-driftYPx + driftYPx * 2f * progress),
            alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * progress,
            scale = 1f,
            rotation = spec.rotationDegrees
        )
    }
}

private fun ThemeDecorationSpec.visibilityBoost(): Float =
    when (motion) {
        ThemeMotion.BALLOON_SWAY -> 1.35f
        ThemeMotion.CLOUD_SWEEP -> 1.3f
        ThemeMotion.HEART_RISE -> 1.45f
        ThemeMotion.TWINKLE -> 1.5f
        ThemeMotion.DRIFT -> 1.2f
    }

private fun ThemeDecorationSpec.scaleBoost(): Float =
    when (motion) {
        ThemeMotion.BALLOON_SWAY -> 1.08f
        ThemeMotion.CLOUD_SWEEP -> 1.12f
        ThemeMotion.HEART_RISE -> 1.16f
        ThemeMotion.TWINKLE -> 1.18f
        ThemeMotion.DRIFT -> 1.04f
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
internal fun ConfettiLayer(
    colors: List<Color> = listOf(
        Color(0xFFFF8FB8),
        Color(0xFF7CCAF2),
        Color(0xFFFFD36E),
        Color(0xFF8DE0C1),
        Color.White
    ),
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "confetti")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "confetti-progress"
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        repeat(72) { index ->
            val x = ((index * 53) % 100) / 100f * width
            val localPhase = (index * 0.11f) % 1f
            val y = (((progress + localPhase) % 1.2f) - 0.2f) * height
            val sway = sin((progress * 2f * PI + index * 0.8f).toFloat()) * (16f + (index % 4) * 4f)
            val color = colors[index % colors.size]
            if (index % 4 == 0) {
                drawCircle(
                    color = color,
                    radius = 5f + (index % 3) * 2.5f,
                    center = Offset(x + sway, y),
                    alpha = 0.82f
                )
            } else {
                drawRoundRect(
                    color = color,
                    topLeft = Offset(x + sway, y),
                    size = Size(8f + (index % 4) * 4f, 16f + (index % 3) * 5f),
                    cornerRadius = CornerRadius(6f, 6f),
                    alpha = 0.9f
                )
            }
        }
    }
}
