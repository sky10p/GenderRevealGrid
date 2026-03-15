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
    val isPremium: Boolean
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
    val maxAlpha: Float
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
                decorationSpec(ThemeDecoration.CLOUD, 0.08f, 0.12f, 78.dp, 4200, 12.dp, 22.dp, 0.26f, 0.52f),
                decorationSpec(ThemeDecoration.BALLOON, 0.78f, 0.14f, 92.dp, 5200, 10.dp, 30.dp, 0.22f, 0.44f),
                decorationSpec(ThemeDecoration.CLOUD, 0.14f, 0.72f, 62.dp, 4600, 14.dp, 20.dp, 0.24f, 0.46f),
                decorationSpec(ThemeDecoration.BALLOON, 0.82f, 0.74f, 58.dp, 5000, 9.dp, 26.dp, 0.2f, 0.42f),
                decorationSpec(ThemeDecoration.STAR, 0.62f, 0.84f, 36.dp, 3600, 8.dp, 14.dp, 0.22f, 0.48f)
            ),
            isPremium = false
        ),
        ThemeConfig(
            id = "golden-dust",
            nameResId = R.string.theme_name_golden_dust,
            backgroundColors = listOf(Color(0xFFFFFBF2), Color(0xFFFFF1C9), Color(0xFFFFD8E0)),
            icon = ThemeIcon.SPARKLES,
            decorations = listOf(
                decorationSpec(ThemeDecoration.STAR, 0.1f, 0.16f, 40.dp, 3500, 18.dp, 16.dp, 0.3f, 0.6f),
                decorationSpec(ThemeDecoration.HEART, 0.8f, 0.12f, 42.dp, 3900, 12.dp, 18.dp, 0.24f, 0.52f),
                decorationSpec(ThemeDecoration.STAR, 0.18f, 0.72f, 34.dp, 3300, 16.dp, 14.dp, 0.28f, 0.58f),
                decorationSpec(ThemeDecoration.HEART, 0.76f, 0.78f, 38.dp, 4200, 14.dp, 16.dp, 0.22f, 0.48f),
                decorationSpec(ThemeDecoration.STAR, 0.56f, 0.88f, 30.dp, 3000, 10.dp, 10.dp, 0.28f, 0.56f)
            ),
            isPremium = false
        ),
        ThemeConfig(
            id = "twilight-clouds",
            nameResId = R.string.theme_name_twilight_clouds,
            backgroundColors = listOf(Color(0xFFF7F4FF), Color(0xFFE0E8FF), Color(0xFFFFE1EE)),
            icon = ThemeIcon.CLOUD,
            decorations = listOf(
                decorationSpec(ThemeDecoration.CLOUD, 0.06f, 0.12f, 88.dp, 5200, 10.dp, 24.dp, 0.22f, 0.46f),
                decorationSpec(ThemeDecoration.STAR, 0.84f, 0.16f, 28.dp, 3100, 18.dp, 12.dp, 0.26f, 0.6f),
                decorationSpec(ThemeDecoration.CLOUD, 0.16f, 0.78f, 70.dp, 4700, 12.dp, 22.dp, 0.2f, 0.42f),
                decorationSpec(ThemeDecoration.BALLOON, 0.78f, 0.7f, 54.dp, 5400, 8.dp, 28.dp, 0.18f, 0.38f),
                decorationSpec(ThemeDecoration.STAR, 0.58f, 0.86f, 26.dp, 3200, 14.dp, 14.dp, 0.26f, 0.58f)
            ),
            isPremium = true
        ),
        ThemeConfig(
            id = "celebration-pop",
            nameResId = R.string.theme_name_celebration_pop,
            backgroundColors = listOf(Color(0xFFFFF3F1), Color(0xFFE7F8FF), Color(0xFFFFE0F2)),
            icon = ThemeIcon.BALLOON,
            decorations = listOf(
                decorationSpec(ThemeDecoration.HEART, 0.08f, 0.14f, 44.dp, 3300, 18.dp, 18.dp, 0.3f, 0.6f),
                decorationSpec(ThemeDecoration.BALLOON, 0.8f, 0.12f, 86.dp, 5000, 12.dp, 30.dp, 0.22f, 0.46f),
                decorationSpec(ThemeDecoration.HEART, 0.18f, 0.74f, 40.dp, 3600, 16.dp, 18.dp, 0.26f, 0.54f),
                decorationSpec(ThemeDecoration.BALLOON, 0.82f, 0.76f, 62.dp, 4600, 10.dp, 28.dp, 0.22f, 0.44f),
                decorationSpec(ThemeDecoration.STAR, 0.56f, 0.86f, 34.dp, 3100, 18.dp, 14.dp, 0.28f, 0.6f)
            ),
            isPremium = true
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
    maxAlpha: Float
): ThemeDecorationSpec = ThemeDecorationSpec(
    type = type,
    xFraction = xFraction,
    yFraction = yFraction,
    size = size,
    durationMillis = durationMillis,
    driftX = driftX,
    driftY = driftY,
    minAlpha = minAlpha,
    maxAlpha = maxAlpha
)
