package sky.programs.genderrevealgrid.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import sky.programs.genderrevealgrid.R

@Immutable
data class ThemeConfig(
    val id: String,
    @param:StringRes val nameResId: Int,
    val backgroundColors: List<Color>,
    val icon: ThemeIcon,
    val decorations: List<ThemeDecorationSpec>,
    val isPremium: Boolean,
    val boardFrameColor: Color,
    val boardStrokeColor: Color,
    val celebrationColors: List<Color>,
    val celebrationAccent: Color
)

enum class ThemeIcon {
    BALLOON,
    CLOUD,
    SPARKLES,
}

enum class ThemeDecoration {
    CLOUD,
    BALLOON,
    STAR,
    HEART,
}

enum class ThemeMotion {
    DRIFT,
    BALLOON_SWAY,
    CLOUD_SWEEP,
    HEART_RISE,
    TWINKLE,
}

@Immutable
data class ThemeDecorationSpec(
    val type: ThemeDecoration,
    val xFraction: Float,
    val yFraction: Float,
    val size: Dp,
    val durationMillis: Int,
    val driftX: Dp,
    val driftY: Dp,
    val minAlpha: Float,
    val maxAlpha: Float,
    val motion: ThemeMotion = ThemeMotion.DRIFT,
    val phaseOffset: Float = 0f,
    val mirrorHorizontally: Boolean = false,
    val rotationDegrees: Float = 0f
)

enum class WinningGender {
    BOY,
    GIRL,
}

@Immutable
data class RevealMessageConfig(
    val title: String,
    val subtitle: String
)

@Immutable
data class RevealSetupConfig(
    val winningGender: WinningGender,
    val boardHeaderMessage: RevealMessageConfig,
    val celebrationMessage: RevealMessageConfig,
    val theme: ThemeConfig
)

@Immutable
data class RevealCard(
    val id: Int,
    val gender: WinningGender? = null,
    val isRevealed: Boolean = false
)

@Immutable
data class GameState(
    val cards: List<RevealCard>,
    val revealSequence: List<WinningGender>,
    val revealedGenders: List<WinningGender> = emptyList(),
    val revealedCount: Int = 0,
    val celebrationVisible: Boolean = false
) {
    val boyRevealedCount: Int
        get() = revealedGenders.count { it == WinningGender.BOY }

    val girlRevealedCount: Int
        get() = revealedGenders.count { it == WinningGender.GIRL }

    val hiddenCount: Int
        get() = cards.size - revealedCount
}

object RevealCatalog {
    val themes: List<ThemeConfig> = listOf(
        ThemeConfig(
            id = "cotton-sky",
            nameResId = R.string.theme_name_cotton_sky,
            backgroundColors = listOf(Color(0xFFFFF6EF), Color(0xFFFFE8F4), Color(0xFFD9F1FF)),
            icon = ThemeIcon.BALLOON,
            decorations = listOf(
                decorationSpec(
                    type = ThemeDecoration.BALLOON,
                    xFraction = 0.06f,
                    yFraction = 0.12f,
                    size = 156.dp,
                    durationMillis = 9200,
                    driftX = 18.dp,
                    driftY = 28.dp,
                    minAlpha = 0.46f,
                    maxAlpha = 0.72f,
                    motion = ThemeMotion.BALLOON_SWAY,
                    rotationDegrees = -6f
                ),
                decorationSpec(
                    type = ThemeDecoration.BALLOON,
                    xFraction = 0.88f,
                    yFraction = 0.34f,
                    size = 136.dp,
                    durationMillis = 9800,
                    driftX = 16.dp,
                    driftY = 26.dp,
                    minAlpha = 0.4f,
                    maxAlpha = 0.66f,
                    motion = ThemeMotion.BALLOON_SWAY,
                    phaseOffset = 0.42f,
                    mirrorHorizontally = true,
                    rotationDegrees = 4f
                ),
                decorationSpec(
                    type = ThemeDecoration.CLOUD,
                    xFraction = 0.1f,
                    yFraction = 0.18f,
                    size = 138.dp,
                    durationMillis = 32000,
                    driftX = 0.dp,
                    driftY = 8.dp,
                    minAlpha = 0.54f,
                    maxAlpha = 0.82f,
                    motion = ThemeMotion.CLOUD_SWEEP,
                    phaseOffset = 0.12f
                ),
                decorationSpec(
                    type = ThemeDecoration.CLOUD,
                    xFraction = 0.12f,
                    yFraction = 0.72f,
                    size = 126.dp,
                    durationMillis = 28000,
                    driftX = 0.dp,
                    driftY = 8.dp,
                    minAlpha = 0.48f,
                    maxAlpha = 0.76f,
                    motion = ThemeMotion.CLOUD_SWEEP,
                    phaseOffset = 0.56f
                ),
                decorationSpec(
                    type = ThemeDecoration.HEART,
                    xFraction = 0.2f,
                    yFraction = 0.78f,
                    size = 42.dp,
                    durationMillis = 17000,
                    driftX = 28.dp,
                    driftY = 24.dp,
                    minAlpha = 0.24f,
                    maxAlpha = 0.56f,
                    motion = ThemeMotion.HEART_RISE,
                    phaseOffset = 0.3f,
                    rotationDegrees = -10f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.64f,
                    yFraction = 0.84f,
                    size = 38.dp,
                    durationMillis = 3600,
                    driftX = 8.dp,
                    driftY = 8.dp,
                    minAlpha = 0.36f,
                    maxAlpha = 0.76f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.18f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.86f,
                    yFraction = 0.08f,
                    size = 34.dp,
                    durationMillis = 3300,
                    driftX = 8.dp,
                    driftY = 8.dp,
                    minAlpha = 0.34f,
                    maxAlpha = 0.72f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.64f
                ),
                decorationSpec(
                    type = ThemeDecoration.HEART,
                    xFraction = 0.74f,
                    yFraction = 0.9f,
                    size = 38.dp,
                    durationMillis = 17600,
                    driftX = 24.dp,
                    driftY = 22.dp,
                    minAlpha = 0.22f,
                    maxAlpha = 0.5f,
                    motion = ThemeMotion.HEART_RISE,
                    phaseOffset = 0.72f,
                    rotationDegrees = 12f
                )
            ),
            isPremium = false,
            boardFrameColor = Color(0xFFFFFDFB),
            boardStrokeColor = Color(0xFFFFD6E6),
            celebrationColors = listOf(Color(0xFFF7B8D2), Color(0xFF9EDCFF)),
            celebrationAccent = Color(0xFFF39AC5)
        ),
        ThemeConfig(
            id = "golden-dust",
            nameResId = R.string.theme_name_golden_dust,
            backgroundColors = listOf(Color(0xFFFFFBF2), Color(0xFFFFF1C9), Color(0xFFFFD8E0)),
            icon = ThemeIcon.SPARKLES,
            decorations = listOf(
                decorationSpec(
                    type = ThemeDecoration.BALLOON,
                    xFraction = 0.08f,
                    yFraction = 0.14f,
                    size = 136.dp,
                    durationMillis = 9600,
                    driftX = 14.dp,
                    driftY = 24.dp,
                    minAlpha = 0.4f,
                    maxAlpha = 0.68f,
                    motion = ThemeMotion.BALLOON_SWAY,
                    rotationDegrees = -4f
                ),
                decorationSpec(
                    type = ThemeDecoration.CLOUD,
                    xFraction = 0.12f,
                    yFraction = 0.22f,
                    size = 124.dp,
                    durationMillis = 30000,
                    driftX = 0.dp,
                    driftY = 6.dp,
                    minAlpha = 0.46f,
                    maxAlpha = 0.76f,
                    motion = ThemeMotion.CLOUD_SWEEP,
                    phaseOffset = 0.08f
                ),
                decorationSpec(
                    type = ThemeDecoration.HEART,
                    xFraction = 0.8f,
                    yFraction = 0.82f,
                    size = 44.dp,
                    durationMillis = 16800,
                    driftX = 18.dp,
                    driftY = 22.dp,
                    minAlpha = 0.28f,
                    maxAlpha = 0.54f,
                    motion = ThemeMotion.HEART_RISE,
                    phaseOffset = 0.5f,
                    rotationDegrees = 8f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.2f,
                    yFraction = 0.72f,
                    size = 40.dp,
                    durationMillis = 3200,
                    driftX = 8.dp,
                    driftY = 8.dp,
                    minAlpha = 0.38f,
                    maxAlpha = 0.8f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.14f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.82f,
                    yFraction = 0.16f,
                    size = 36.dp,
                    durationMillis = 3400,
                    driftX = 8.dp,
                    driftY = 8.dp,
                    minAlpha = 0.36f,
                    maxAlpha = 0.76f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.42f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.58f,
                    yFraction = 0.88f,
                    size = 34.dp,
                    durationMillis = 3000,
                    driftX = 6.dp,
                    driftY = 6.dp,
                    minAlpha = 0.36f,
                    maxAlpha = 0.72f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.72f
                ),
                decorationSpec(
                    type = ThemeDecoration.HEART,
                    xFraction = 0.16f,
                    yFraction = 0.88f,
                    size = 40.dp,
                    durationMillis = 16400,
                    driftX = 22.dp,
                    driftY = 20.dp,
                    minAlpha = 0.24f,
                    maxAlpha = 0.48f,
                    motion = ThemeMotion.HEART_RISE,
                    phaseOffset = 0.24f,
                    rotationDegrees = -12f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.68f,
                    yFraction = 0.1f,
                    size = 34.dp,
                    durationMillis = 3000,
                    driftX = 8.dp,
                    driftY = 8.dp,
                    minAlpha = 0.34f,
                    maxAlpha = 0.74f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.82f
                )
            ),
            isPremium = false,
            boardFrameColor = Color(0xFFFFFEF7),
            boardStrokeColor = Color(0xFFF9E1A6),
            celebrationColors = listOf(Color(0xFFFFD98C), Color(0xFFFFC1D6)),
            celebrationAccent = Color(0xFFF0B248)
        ),
        ThemeConfig(
            id = "twilight-clouds",
            nameResId = R.string.theme_name_twilight_clouds,
            backgroundColors = listOf(Color(0xFFF7F4FF), Color(0xFFE0E8FF), Color(0xFFFFE1EE)),
            icon = ThemeIcon.CLOUD,
            decorations = listOf(
                decorationSpec(
                    type = ThemeDecoration.CLOUD,
                    xFraction = 0.08f,
                    yFraction = 0.16f,
                    size = 148.dp,
                    durationMillis = 34000,
                    driftX = 0.dp,
                    driftY = 8.dp,
                    minAlpha = 0.44f,
                    maxAlpha = 0.74f,
                    motion = ThemeMotion.CLOUD_SWEEP,
                    phaseOffset = 0.16f
                ),
                decorationSpec(
                    type = ThemeDecoration.CLOUD,
                    xFraction = 0.2f,
                    yFraction = 0.72f,
                    size = 124.dp,
                    durationMillis = 30000,
                    driftX = 0.dp,
                    driftY = 8.dp,
                    minAlpha = 0.38f,
                    maxAlpha = 0.66f,
                    motion = ThemeMotion.CLOUD_SWEEP,
                    phaseOffset = 0.62f
                ),
                decorationSpec(
                    type = ThemeDecoration.BALLOON,
                    xFraction = 0.84f,
                    yFraction = 0.22f,
                    size = 122.dp,
                    durationMillis = 10200,
                    driftX = 12.dp,
                    driftY = 28.dp,
                    minAlpha = 0.36f,
                    maxAlpha = 0.58f,
                    motion = ThemeMotion.BALLOON_SWAY,
                    phaseOffset = 0.33f,
                    mirrorHorizontally = true,
                    rotationDegrees = 6f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.82f,
                    yFraction = 0.14f,
                    size = 34.dp,
                    durationMillis = 3000,
                    driftX = 6.dp,
                    driftY = 6.dp,
                    minAlpha = 0.4f,
                    maxAlpha = 0.78f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.12f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.58f,
                    yFraction = 0.86f,
                    size = 32.dp,
                    durationMillis = 3400,
                    driftX = 6.dp,
                    driftY = 6.dp,
                    minAlpha = 0.38f,
                    maxAlpha = 0.72f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.52f
                ),
                decorationSpec(
                    type = ThemeDecoration.HEART,
                    xFraction = 0.18f,
                    yFraction = 0.84f,
                    size = 38.dp,
                    durationMillis = 18000,
                    driftX = 20.dp,
                    driftY = 20.dp,
                    minAlpha = 0.22f,
                    maxAlpha = 0.44f,
                    motion = ThemeMotion.HEART_RISE,
                    phaseOffset = 0.45f,
                    rotationDegrees = -8f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.34f,
                    yFraction = 0.24f,
                    size = 34.dp,
                    durationMillis = 3200,
                    driftX = 8.dp,
                    driftY = 8.dp,
                    minAlpha = 0.34f,
                    maxAlpha = 0.72f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.3f
                ),
                decorationSpec(
                    type = ThemeDecoration.HEART,
                    xFraction = 0.8f,
                    yFraction = 0.9f,
                    size = 36.dp,
                    durationMillis = 17400,
                    driftX = 22.dp,
                    driftY = 20.dp,
                    minAlpha = 0.2f,
                    maxAlpha = 0.42f,
                    motion = ThemeMotion.HEART_RISE,
                    phaseOffset = 0.68f,
                    rotationDegrees = 10f
                )
            ),
            isPremium = true,
            boardFrameColor = Color(0xFFFDFBFF),
            boardStrokeColor = Color(0xFFD7DDFE),
            celebrationColors = listOf(Color(0xFFC1CFFF), Color(0xFFF8B7D1)),
            celebrationAccent = Color(0xFF90A8F8)
        ),
        ThemeConfig(
            id = "celebration-pop",
            nameResId = R.string.theme_name_celebration_pop,
            backgroundColors = listOf(Color(0xFFFFF3F1), Color(0xFFE7F8FF), Color(0xFFFFE0F2)),
            icon = ThemeIcon.BALLOON,
            decorations = listOf(
                decorationSpec(
                    type = ThemeDecoration.BALLOON,
                    xFraction = 0.06f,
                    yFraction = 0.14f,
                    size = 162.dp,
                    durationMillis = 9000,
                    driftX = 18.dp,
                    driftY = 30.dp,
                    minAlpha = 0.48f,
                    maxAlpha = 0.76f,
                    motion = ThemeMotion.BALLOON_SWAY,
                    rotationDegrees = -5f
                ),
                decorationSpec(
                    type = ThemeDecoration.BALLOON,
                    xFraction = 0.9f,
                    yFraction = 0.22f,
                    size = 140.dp,
                    durationMillis = 9800,
                    driftX = 16.dp,
                    driftY = 28.dp,
                    minAlpha = 0.42f,
                    maxAlpha = 0.68f,
                    motion = ThemeMotion.BALLOON_SWAY,
                    phaseOffset = 0.38f,
                    mirrorHorizontally = true,
                    rotationDegrees = 5f
                ),
                decorationSpec(
                    type = ThemeDecoration.CLOUD,
                    xFraction = 0.1f,
                    yFraction = 0.18f,
                    size = 132.dp,
                    durationMillis = 30000,
                    driftX = 0.dp,
                    driftY = 8.dp,
                    minAlpha = 0.52f,
                    maxAlpha = 0.82f,
                    motion = ThemeMotion.CLOUD_SWEEP,
                    phaseOffset = 0.2f
                ),
                decorationSpec(
                    type = ThemeDecoration.HEART,
                    xFraction = 0.22f,
                    yFraction = 0.82f,
                    size = 46.dp,
                    durationMillis = 16200,
                    driftX = 26.dp,
                    driftY = 24.dp,
                    minAlpha = 0.28f,
                    maxAlpha = 0.58f,
                    motion = ThemeMotion.HEART_RISE,
                    phaseOffset = 0.24f,
                    rotationDegrees = -10f
                ),
                decorationSpec(
                    type = ThemeDecoration.HEART,
                    xFraction = 0.78f,
                    yFraction = 0.92f,
                    size = 40.dp,
                    durationMillis = 17800,
                    driftX = 20.dp,
                    driftY = 22.dp,
                    minAlpha = 0.24f,
                    maxAlpha = 0.48f,
                    motion = ThemeMotion.HEART_RISE,
                    phaseOffset = 0.62f,
                    rotationDegrees = 12f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.56f,
                    yFraction = 0.86f,
                    size = 40.dp,
                    durationMillis = 3100,
                    driftX = 8.dp,
                    driftY = 8.dp,
                    minAlpha = 0.4f,
                    maxAlpha = 0.82f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.48f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.16f,
                    yFraction = 0.24f,
                    size = 36.dp,
                    durationMillis = 3200,
                    driftX = 8.dp,
                    driftY = 8.dp,
                    minAlpha = 0.38f,
                    maxAlpha = 0.8f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.84f
                ),
                decorationSpec(
                    type = ThemeDecoration.HEART,
                    xFraction = 0.66f,
                    yFraction = 0.88f,
                    size = 42.dp,
                    durationMillis = 16800,
                    driftX = 24.dp,
                    driftY = 22.dp,
                    minAlpha = 0.26f,
                    maxAlpha = 0.56f,
                    motion = ThemeMotion.HEART_RISE,
                    phaseOffset = 0.4f,
                    rotationDegrees = 12f
                )
            ),
            isPremium = true,
            boardFrameColor = Color(0xFFFFFCFB),
            boardStrokeColor = Color(0xFFFFD3E4),
            celebrationColors = listOf(Color(0xFFF8AFCB), Color(0xFF7DCDF3)),
            celebrationAccent = Color(0xFFF07FB5)
        )
    )

    fun themeById(id: String): ThemeConfig = themes.firstOrNull { it.id == id } ?: themes.first()
}

fun WinningGender.opposite(): WinningGender =
    if (this == WinningGender.BOY) WinningGender.GIRL else WinningGender.BOY

private fun decorationSpec(
    type: ThemeDecoration,
    xFraction: Float,
    yFraction: Float,
    size: Dp,
    durationMillis: Int,
    driftX: Dp,
    driftY: Dp,
    minAlpha: Float,
    maxAlpha: Float,
    motion: ThemeMotion = ThemeMotion.DRIFT,
    phaseOffset: Float = 0f,
    mirrorHorizontally: Boolean = false,
    rotationDegrees: Float = 0f
): ThemeDecorationSpec = ThemeDecorationSpec(
    type = type,
    xFraction = xFraction,
    yFraction = yFraction,
    size = size,
    durationMillis = durationMillis,
    driftX = driftX,
    driftY = driftY,
    minAlpha = minAlpha,
    maxAlpha = maxAlpha,
    motion = motion,
    phaseOffset = phaseOffset,
    mirrorHorizontally = mirrorHorizontally,
    rotationDegrees = rotationDegrees
)
