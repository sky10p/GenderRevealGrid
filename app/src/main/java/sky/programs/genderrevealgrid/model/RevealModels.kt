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
    val celebrationAccent: Color,
    val defaultMessages: ThemeDefaultMessageRes
)

@Immutable
data class ThemeDefaultMessageRes(
    @param:StringRes val boardTitleResId: Int,
    @param:StringRes val boardSubtitleResId: Int,
    @param:StringRes val boyCelebrationTitleResId: Int,
    @param:StringRes val boyCelebrationSubtitleResId: Int,
    @param:StringRes val girlCelebrationTitleResId: Int,
    @param:StringRes val girlCelebrationSubtitleResId: Int
)

enum class ThemeIcon {
    BALLOON,
    CLOUD,
    SPARKLES,
    SUPERHERO_BADGE,
    MAGIC_WAND,
}

enum class ThemeDecoration {
    CLOUD,
    BALLOON,
    STAR,
    HEART,
    LIGHTNING_BOLT,
    COMIC_BURST,
    CITY_SKYLINE,
    HERO_STAR,
    HERO_SILHOUETTE,
    OWL,
    FLOATING_CANDLE,
    MAGIC_WAND_TRAIL,
    CRESCENT_MOON,
    MAGIC_DUST,
    SCROLL,
    CASTLE_SILHOUETTE,
    MAGIC_RUNE,
}

enum class ThemeMotion {
    DRIFT,
    BALLOON_SWAY,
    CLOUD_SWEEP,
    HEART_RISE,
    TWINKLE,
    LIGHTNING_FLASH,
    HERO_PULSE,
    HERO_FLYBY,
    SKYLINE_GLIDE,
    OWL_GLIDE,
    CANDLE_FLOAT,
    SPELL_SWIRL,
    DUST_ASCEND,
    MOON_DRIFT,
    PARALLAX_SLIDE,
    RUNE_GLOW,
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
            celebrationAccent = Color(0xFFF39AC5),
            defaultMessages = ThemeDefaultMessageRes(
                boardTitleResId = R.string.default_board_title,
                boardSubtitleResId = R.string.default_board_subtitle,
                boyCelebrationTitleResId = R.string.default_boy_title,
                boyCelebrationSubtitleResId = R.string.default_boy_subtitle,
                girlCelebrationTitleResId = R.string.default_girl_title,
                girlCelebrationSubtitleResId = R.string.default_girl_subtitle
            )
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
            celebrationAccent = Color(0xFFF0B248),
            defaultMessages = ThemeDefaultMessageRes(
                boardTitleResId = R.string.default_board_title,
                boardSubtitleResId = R.string.default_board_subtitle,
                boyCelebrationTitleResId = R.string.default_boy_title,
                boyCelebrationSubtitleResId = R.string.default_boy_subtitle,
                girlCelebrationTitleResId = R.string.default_girl_title,
                girlCelebrationSubtitleResId = R.string.default_girl_subtitle
            )
        ),
        ThemeConfig(
            id = "superhero",
            nameResId = R.string.theme_name_superhero,
            backgroundColors = listOf(Color(0xFF070E1F), Color(0xFF112046), Color(0xFF1A3068)),
            icon = ThemeIcon.SUPERHERO_BADGE,
            decorations = listOf(
                decorationSpec(
                    type = ThemeDecoration.CITY_SKYLINE,
                    xFraction = 0.0f,
                    yFraction = 0.82f,
                    size = 420.dp,
                    durationMillis = 42000,
                    driftX = 24.dp,
                    driftY = 0.dp,
                    minAlpha = 0.18f,
                    maxAlpha = 0.28f,
                    motion = ThemeMotion.SKYLINE_GLIDE,
                    phaseOffset = 0.0f
                ),
                decorationSpec(
                    type = ThemeDecoration.HERO_SILHOUETTE,
                    xFraction = 0.1f,
                    yFraction = 0.15f,
                    size = 120.dp,
                    durationMillis = 14000,
                    driftX = 300.dp,
                    driftY = 80.dp,
                    minAlpha = 0.1f,
                    maxAlpha = 0.45f,
                    motion = ThemeMotion.HERO_FLYBY,
                    phaseOffset = 0.1f
                ),
                decorationSpec(
                    type = ThemeDecoration.HERO_SILHOUETTE,
                    xFraction = 0.8f,
                    yFraction = 0.45f,
                    size = 90.dp,
                    durationMillis = 18000,
                    driftX = -320.dp,
                    driftY = -60.dp,
                    minAlpha = 0.08f,
                    maxAlpha = 0.35f,
                    motion = ThemeMotion.HERO_FLYBY,
                    phaseOffset = 0.6f,
                    mirrorHorizontally = true
                ),
                decorationSpec(
                    type = ThemeDecoration.LIGHTNING_BOLT,
                    xFraction = 0.05f,
                    yFraction = 0.1f,
                    size = 80.dp,
                    durationMillis = 2200,
                    driftX = 10.dp,
                    driftY = 15.dp,
                    minAlpha = 0.25f,
                    maxAlpha = 0.95f,
                    motion = ThemeMotion.LIGHTNING_FLASH,
                    phaseOffset = 0.15f,
                    rotationDegrees = -15f
                ),
                decorationSpec(
                    type = ThemeDecoration.LIGHTNING_BOLT,
                    xFraction = 0.9f,
                    yFraction = 0.2f,
                    size = 70.dp,
                    durationMillis = 1800,
                    driftX = 10.dp,
                    driftY = 15.dp,
                    minAlpha = 0.25f,
                    maxAlpha = 0.9f,
                    motion = ThemeMotion.LIGHTNING_FLASH,
                    phaseOffset = 0.7f,
                    mirrorHorizontally = true,
                    rotationDegrees = 12f
                ),
                decorationSpec(
                    type = ThemeDecoration.COMIC_BURST,
                    xFraction = 0.15f,
                    yFraction = 0.3f,
                    size = 130.dp,
                    durationMillis = 3500,
                    driftX = 15.dp,
                    driftY = 15.dp,
                    minAlpha = 0.15f,
                    maxAlpha = 0.55f,
                    motion = ThemeMotion.HERO_PULSE,
                    phaseOffset = 0.2f
                ),
                decorationSpec(
                    type = ThemeDecoration.COMIC_BURST,
                    xFraction = 0.85f,
                    yFraction = 0.35f,
                    size = 120.dp,
                    durationMillis = 3000,
                    driftX = 15.dp,
                    driftY = 15.dp,
                    minAlpha = 0.15f,
                    maxAlpha = 0.5f,
                    motion = ThemeMotion.HERO_PULSE,
                    phaseOffset = 0.8f,
                    rotationDegrees = 20f
                ),
                decorationSpec(
                    type = ThemeDecoration.HERO_STAR,
                    xFraction = 0.5f,
                    yFraction = 0.08f,
                    size = 50.dp,
                    durationMillis = 2500,
                    driftX = 10.dp,
                    driftY = 10.dp,
                    minAlpha = 0.4f,
                    maxAlpha = 1.0f,
                    motion = ThemeMotion.HERO_PULSE,
                    phaseOffset = 0.4f
                ),
                decorationSpec(
                    type = ThemeDecoration.HERO_STAR,
                    xFraction = 0.25f,
                    yFraction = 0.85f,
                    size = 40.dp,
                    durationMillis = 2800,
                    driftX = 10.dp,
                    driftY = 10.dp,
                    minAlpha = 0.35f,
                    maxAlpha = 0.85f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.25f
                ),
                decorationSpec(
                    type = ThemeDecoration.HERO_STAR,
                    xFraction = 0.75f,
                    yFraction = 0.8f,
                    size = 38.dp,
                    durationMillis = 3200,
                    driftX = 10.dp,
                    driftY = 10.dp,
                    minAlpha = 0.3f,
                    maxAlpha = 0.8f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.75f
                )
            ),
            isPremium = true,
            boardFrameColor = Color(0xFFFFF8E1),
            boardStrokeColor = Color(0xFFFFC107),
            celebrationColors = listOf(Color(0xFFE53935), Color(0xFFFFD54F), Color(0xFF42A5F5)),
            celebrationAccent = Color(0xFFFFC107),
            defaultMessages = ThemeDefaultMessageRes(
                boardTitleResId = R.string.default_superhero_board_title,
                boardSubtitleResId = R.string.default_superhero_board_subtitle,
                boyCelebrationTitleResId = R.string.default_superhero_boy_title,
                boyCelebrationSubtitleResId = R.string.default_superhero_boy_subtitle,
                girlCelebrationTitleResId = R.string.default_superhero_girl_title,
                girlCelebrationSubtitleResId = R.string.default_superhero_girl_subtitle
            )
        ),
        ThemeConfig(
            id = "magic-world",
            nameResId = R.string.theme_name_magic_world,
            backgroundColors = listOf(Color(0xFF0B0618), Color(0xFF160D3A), Color(0xFF28196B)),
            icon = ThemeIcon.MAGIC_WAND,
            decorations = listOf(
                decorationSpec(
                    type = ThemeDecoration.CRESCENT_MOON,
                    xFraction = 0.78f,
                    yFraction = 0.08f,
                    size = 150.dp,
                    durationMillis = 38000,
                    driftX = 15.dp,
                    driftY = 15.dp,
                    minAlpha = 0.2f,
                    maxAlpha = 0.4f,
                    motion = ThemeMotion.MOON_DRIFT,
                    phaseOffset = 0.0f
                ),
                decorationSpec(
                    type = ThemeDecoration.CASTLE_SILHOUETTE,
                    xFraction = 0.0f,
                    yFraction = 0.85f,
                    size = 450.dp,
                    durationMillis = 45000,
                    driftX = 25.dp,
                    driftY = 0.dp,
                    minAlpha = 0.1f,
                    maxAlpha = 0.22f,
                    motion = ThemeMotion.PARALLAX_SLIDE,
                    phaseOffset = 0.1f
                ),
                decorationSpec(
                    type = ThemeDecoration.MAGIC_RUNE,
                    xFraction = 0.12f,
                    yFraction = 0.25f,
                    size = 60.dp,
                    durationMillis = 6000,
                    driftX = 0.dp,
                    driftY = 0.dp,
                    minAlpha = 0.05f,
                    maxAlpha = 0.65f,
                    motion = ThemeMotion.RUNE_GLOW,
                    phaseOffset = 0.2f,
                    rotationDegrees = 15f
                ),
                decorationSpec(
                    type = ThemeDecoration.MAGIC_RUNE,
                    xFraction = 0.85f,
                    yFraction = 0.3f,
                    size = 55.dp,
                    durationMillis = 7500,
                    driftX = 0.dp,
                    driftY = 0.dp,
                    minAlpha = 0.05f,
                    maxAlpha = 0.55f,
                    motion = ThemeMotion.RUNE_GLOW,
                    phaseOffset = 0.6f,
                    rotationDegrees = -10f
                ),
                decorationSpec(
                    type = ThemeDecoration.OWL,
                    xFraction = 0.05f,
                    yFraction = 0.15f,
                    size = 95.dp,
                    durationMillis = 22000,
                    driftX = 25.dp,
                    driftY = 15.dp,
                    minAlpha = 0.25f,
                    maxAlpha = 0.48f,
                    motion = ThemeMotion.OWL_GLIDE,
                    phaseOffset = 0.15f
                ),
                decorationSpec(
                    type = ThemeDecoration.FLOATING_CANDLE,
                    xFraction = 0.2f,
                    yFraction = 0.12f,
                    size = 45.dp,
                    durationMillis = 5000,
                    driftX = 10.dp,
                    driftY = 20.dp,
                    minAlpha = 0.25f,
                    maxAlpha = 0.55f,
                    motion = ThemeMotion.CANDLE_FLOAT,
                    phaseOffset = 0.25f
                ),
                decorationSpec(
                    type = ThemeDecoration.FLOATING_CANDLE,
                    xFraction = 0.45f,
                    yFraction = 0.1f,
                    size = 40.dp,
                    durationMillis = 5800,
                    driftX = 10.dp,
                    driftY = 20.dp,
                    minAlpha = 0.25f,
                    maxAlpha = 0.5f,
                    motion = ThemeMotion.CANDLE_FLOAT,
                    phaseOffset = 0.55f
                ),
                decorationSpec(
                    type = ThemeDecoration.FLOATING_CANDLE,
                    xFraction = 0.8f,
                    yFraction = 0.2f,
                    size = 42.dp,
                    durationMillis = 5400,
                    driftX = 10.dp,
                    driftY = 20.dp,
                    minAlpha = 0.25f,
                    maxAlpha = 0.52f,
                    motion = ThemeMotion.CANDLE_FLOAT,
                    phaseOffset = 0.8f
                ),
                decorationSpec(
                    type = ThemeDecoration.MAGIC_WAND_TRAIL,
                    xFraction = 0.25f,
                    yFraction = 0.45f,
                    size = 130.dp,
                    durationMillis = 6000,
                    driftX = 25.dp,
                    driftY = 22.dp,
                    minAlpha = 0.22f,
                    maxAlpha = 0.52f,
                    motion = ThemeMotion.SPELL_SWIRL,
                    phaseOffset = 0.3f,
                    rotationDegrees = -10f
                ),
                decorationSpec(
                    type = ThemeDecoration.MAGIC_WAND_TRAIL,
                    xFraction = 0.75f,
                    yFraction = 0.55f,
                    size = 125.dp,
                    durationMillis = 6800,
                    driftX = 22.dp,
                    driftY = 22.dp,
                    minAlpha = 0.2f,
                    maxAlpha = 0.48f,
                    motion = ThemeMotion.SPELL_SWIRL,
                    phaseOffset = 0.75f,
                    mirrorHorizontally = true,
                    rotationDegrees = 15f
                ),
                decorationSpec(
                    type = ThemeDecoration.MAGIC_DUST,
                    xFraction = 0.25f,
                    yFraction = 0.8f,
                    size = 85.dp,
                    durationMillis = 9000,
                    driftX = 20.dp,
                    driftY = 30.dp,
                    minAlpha = 0.15f,
                    maxAlpha = 0.4f,
                    motion = ThemeMotion.DUST_ASCEND,
                    phaseOffset = 0.2f
                ),
                decorationSpec(
                    type = ThemeDecoration.MAGIC_DUST,
                    xFraction = 0.65f,
                    yFraction = 0.85f,
                    size = 90.dp,
                    durationMillis = 10000,
                    driftX = 20.dp,
                    driftY = 30.dp,
                    minAlpha = 0.15f,
                    maxAlpha = 0.42f,
                    motion = ThemeMotion.DUST_ASCEND,
                    phaseOffset = 0.5f
                ),
                decorationSpec(
                    type = ThemeDecoration.STAR,
                    xFraction = 0.5f,
                    yFraction = 0.25f,
                    size = 32.dp,
                    durationMillis = 3500,
                    driftX = 10.dp,
                    driftY = 10.dp,
                    minAlpha = 0.4f,
                    maxAlpha = 0.9f,
                    motion = ThemeMotion.TWINKLE,
                    phaseOffset = 0.35f
                )
            ),
            isPremium = true,
            boardFrameColor = Color(0xFFF0E6FF),
            boardStrokeColor = Color(0xFFFFD700),
            celebrationColors = listOf(Color(0xFF9C27B0), Color(0xFFFFD700), Color(0xFF26C6DA)),
            celebrationAccent = Color(0xFF8E24AA),
            defaultMessages = ThemeDefaultMessageRes(
                boardTitleResId = R.string.default_magic_world_board_title,
                boardSubtitleResId = R.string.default_magic_world_board_subtitle,
                boyCelebrationTitleResId = R.string.default_magic_world_boy_title,
                boyCelebrationSubtitleResId = R.string.default_magic_world_boy_subtitle,
                girlCelebrationTitleResId = R.string.default_magic_world_girl_title,
                girlCelebrationSubtitleResId = R.string.default_magic_world_girl_subtitle
            )
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
