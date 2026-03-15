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

        ThemeMotion.LIGHTNING_FLASH -> {
            val pulse = (0.35f + 0.65f * kotlin.math.abs(wave)).coerceIn(0f, 1f)
            DecorationPlacement(
                x = baseX + secondaryWave * driftXPx * 0.15f,
                y = baseY + wave * driftYPx * 0.18f,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * pulse,
                scale = 0.94f + 0.16f * pulse,
                rotation = spec.rotationDegrees + wave * 8f
            )
        }

        ThemeMotion.HERO_PULSE -> {
            val pulse = 0.5f + 0.5f * secondaryWave
            DecorationPlacement(
                x = baseX + wave * driftXPx * 0.2f,
                y = baseY + secondaryWave * driftYPx * 0.18f,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * pulse,
                scale = 0.86f + 0.34f * pulse,
                rotation = spec.rotationDegrees + wave * 10f
            )
        }

        ThemeMotion.HERO_FLYBY -> {
            val travelWidth = width + sizePx * 4f
            DecorationPlacement(
                x = -sizePx * 2f + progress * travelWidth,
                y = baseY + secondaryWave * driftYPx,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * (0.6f + 0.4f * progress),
                scale = 1f,
                rotation = spec.rotationDegrees + wave * 2f
            )
        }

        ThemeMotion.SKYLINE_GLIDE -> {
            val travelWidth = width + sizePx * 1.2f
            DecorationPlacement(
                x = -sizePx * 0.1f + progress * travelWidth * 0.18f,
                y = baseY + secondaryWave * driftYPx * 0.08f,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * 0.92f,
                scale = 1f,
                rotation = spec.rotationDegrees
            )
        }

        ThemeMotion.OWL_GLIDE -> {
            val travelWidth = width + sizePx * 2f
            DecorationPlacement(
                x = -sizePx + progress * travelWidth,
                y = baseY + secondaryWave * driftYPx * 0.28f,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * (0.6f + 0.4f * progress),
                scale = 0.92f + 0.08f * (0.5f + 0.5f * wave),
                rotation = spec.rotationDegrees + wave * 5f
            )
        }

        ThemeMotion.CANDLE_FLOAT -> DecorationPlacement(
            x = baseX + wave * driftXPx * 0.18f,
            y = baseY + secondaryWave * driftYPx * 0.45f,
            alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * (0.66f + 0.34f * progress),
            scale = 0.96f + 0.06f * (0.5f + 0.5f * secondaryWave),
            rotation = spec.rotationDegrees + wave * 3f
        )

        ThemeMotion.SPELL_SWIRL -> {
            val orbitX = kotlin.math.cos((progress * 2f * PI).toFloat()) * driftXPx * 0.45f
            val orbitY = kotlin.math.sin((progress * 2f * PI).toFloat()) * driftYPx * 0.35f
            val pulse = 0.5f + 0.5f * secondaryWave
            DecorationPlacement(
                x = baseX + orbitX,
                y = baseY + orbitY,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * pulse,
                scale = 0.9f + 0.16f * pulse,
                rotation = spec.rotationDegrees + progress * 42f
            )
        }

        ThemeMotion.DUST_ASCEND -> {
            val visibility = (1f - (kotlin.math.abs(progress - 0.5f) / 0.5f)).coerceIn(0f, 1f)
            DecorationPlacement(
                x = baseX + wave * driftXPx * 0.6f,
                y = baseY + driftYPx * 0.4f - progress * driftYPx * 1.6f,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * visibility,
                scale = 0.82f + 0.22f * visibility,
                rotation = spec.rotationDegrees + wave * 4f
            )
        }

        ThemeMotion.MOON_DRIFT -> DecorationPlacement(
            x = baseX + wave * driftXPx * 0.3f,
            y = baseY + secondaryWave * driftYPx * 0.18f,
            alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * (0.85f + 0.15f * progress),
            scale = 0.98f + 0.04f * (0.5f + 0.5f * secondaryWave),
            rotation = spec.rotationDegrees + wave * 2f
        )

        ThemeMotion.PARALLAX_SLIDE -> {
            val travelWidth = width + sizePx
            DecorationPlacement(
                x = -sizePx * 0.1f + progress * travelWidth * 0.14f,
                y = baseY + secondaryWave * driftYPx * 0.05f,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * 0.94f,
                scale = 1f,
                rotation = spec.rotationDegrees
            )
        }

        ThemeMotion.RUNE_GLOW -> {
            val pulse = 0.5f + 0.5f * wave
            DecorationPlacement(
                x = baseX,
                y = baseY + wave * driftYPx * 0.1f,
                alpha = spec.minAlpha + (spec.maxAlpha - spec.minAlpha) * pulse,
                scale = 0.95f + 0.1f * pulse,
                rotation = spec.rotationDegrees + wave * 5f
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
        ThemeMotion.LIGHTNING_FLASH -> 1.6f
        ThemeMotion.HERO_PULSE -> 1.45f
        ThemeMotion.HERO_FLYBY -> 1.25f
        ThemeMotion.SKYLINE_GLIDE -> 1.1f
        ThemeMotion.OWL_GLIDE -> 1.22f
        ThemeMotion.CANDLE_FLOAT -> 1.28f
        ThemeMotion.SPELL_SWIRL -> 1.34f
        ThemeMotion.DUST_ASCEND -> 1.22f
        ThemeMotion.MOON_DRIFT -> 1.12f
        ThemeMotion.PARALLAX_SLIDE -> 1.05f
        ThemeMotion.RUNE_GLOW -> 1.4f
        ThemeMotion.DRIFT -> 1.2f
    }

private fun ThemeDecorationSpec.scaleBoost(): Float =
    when (motion) {
        ThemeMotion.BALLOON_SWAY -> 1.08f
        ThemeMotion.CLOUD_SWEEP -> 1.12f
        ThemeMotion.HEART_RISE -> 1.16f
        ThemeMotion.TWINKLE -> 1.18f
        ThemeMotion.LIGHTNING_FLASH -> 1.18f
        ThemeMotion.HERO_PULSE -> 1.2f
        ThemeMotion.HERO_FLYBY -> 1.1f
        ThemeMotion.SKYLINE_GLIDE -> 1f
        ThemeMotion.OWL_GLIDE -> 1.06f
        ThemeMotion.CANDLE_FLOAT -> 1.08f
        ThemeMotion.SPELL_SWIRL -> 1.1f
        ThemeMotion.DUST_ASCEND -> 1.02f
        ThemeMotion.MOON_DRIFT -> 1.04f
        ThemeMotion.PARALLAX_SLIDE -> 1f
        ThemeMotion.RUNE_GLOW -> 1.15f
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
            ThemeDecoration.LIGHTNING_BOLT -> drawLightningBolt(colors)
            ThemeDecoration.COMIC_BURST -> drawComicBurst(colors)
            ThemeDecoration.CITY_SKYLINE -> drawCitySkyline(colors)
            ThemeDecoration.HERO_STAR -> drawHeroStar(colors)
            ThemeDecoration.HERO_SILHOUETTE -> drawHeroSilhouette(colors)
            ThemeDecoration.OWL -> drawOwl(colors)
            ThemeDecoration.FLOATING_CANDLE -> drawFloatingCandle(colors)
            ThemeDecoration.MAGIC_WAND_TRAIL -> drawMagicWandTrail(colors)
            ThemeDecoration.CRESCENT_MOON -> drawCrescentMoon(colors)
            ThemeDecoration.MAGIC_DUST -> drawMagicDust(colors)
            ThemeDecoration.SCROLL -> drawScroll(colors)
            ThemeDecoration.CASTLE_SILHOUETTE -> drawCastleSilhouette(colors)
            ThemeDecoration.MAGIC_RUNE -> drawMagicRune(colors)
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
