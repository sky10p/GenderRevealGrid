package sky.programs.genderrevealgrid.ui

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import sky.programs.genderrevealgrid.model.ThemeConfig
import sky.programs.genderrevealgrid.model.WinningGender
import sky.programs.genderrevealgrid.ui.theme.RoundedSansFamily
import sky.programs.genderrevealgrid.ui.theme.StageAccentFamily
import sky.programs.genderrevealgrid.ui.theme.StageRoundedFamily

private const val SuperheroThemeId = "superhero"
private const val MagicWorldThemeId = "magic-world"

// ---------------------------------------------------------------------------
// Brush helpers
// ---------------------------------------------------------------------------

internal fun ThemeConfig.backgroundBrush(): Brush =
    Brush.verticalGradient(backgroundColors)

internal fun ThemeConfig.boardPanelBrush(): Brush = when (id) {
    SuperheroThemeId -> Brush.linearGradient(
        listOf(
            Color(0xFF0E1A3A),
            Color(0xFF152850),
            Color(0xFF0E1A3A)
        )
    )
    MagicWorldThemeId -> Brush.linearGradient(
        listOf(
            Color(0xFF110A28),
            Color(0xFF1A1148),
            Color(0xFF110A28)
        )
    )
    else -> Brush.linearGradient(
        listOf(
            Color.White,
            boardFrameColor.copy(alpha = 0.7f),
            Color.White
        )
    )
}

internal fun ThemeConfig.celebrationBrush(): Brush = when (id) {
    SuperheroThemeId -> Brush.verticalGradient(
        listOf(
            Color(0xFFFFF8E1),
            Color(0xFFFFECB3),
            Color(0xFFFFF8E1)
        )
    )
    MagicWorldThemeId -> Brush.verticalGradient(
        listOf(
            Color(0xFFF3E5F5),
            Color(0xFFE8D0F8),
            Color(0xFFF3E5F5)
        )
    )
    else -> Brush.verticalGradient(
        listOf(
            boardFrameColor.copy(alpha = 0.4f),
            celebrationAccent.copy(alpha = 0.15f),
            boardFrameColor.copy(alpha = 0.3f)
        )
    )
}

internal fun ThemeConfig.buttonBrush(): Brush = when (id) {
    SuperheroThemeId -> Brush.horizontalGradient(
        listOf(
            Color(0xFFCC2D30),
            Color(0xFFE53935),
            Color(0xFFCC2D30)
        )
    )
    MagicWorldThemeId -> Brush.horizontalGradient(
        listOf(
            Color(0xFF5C1A8C),
            Color(0xFF8E24AA),
            Color(0xFF5C1A8C)
        )
    )
    else -> Brush.horizontalGradient(
        listOf(
            celebrationAccent,
            celebrationAccent.copy(alpha = 0.88f),
            celebrationAccent
        )
    )
}

// ---------------------------------------------------------------------------
// Setup screen helpers
// ---------------------------------------------------------------------------

internal fun ThemeConfig.setupBottomBarColor(): Color = when (id) {
    SuperheroThemeId -> Color(0xFF070E1F).copy(alpha = 0.96f)
    MagicWorldThemeId -> Color(0xFF0B0618).copy(alpha = 0.96f)
    else -> Color.White.copy(alpha = 0.92f)
}

// ---------------------------------------------------------------------------
// Theme card styling (setup picker)
// ---------------------------------------------------------------------------

internal fun ThemeConfig.themeCardStrokeColor(selected: Boolean): Color = when {
    selected && isPremium -> celebrationAccent
    selected -> boardStrokeColor
    else -> Color(0xFFE4E7F1)
}

internal fun ThemeConfig.themeCardVeilColor(selected: Boolean): Color =
    if (selected) Color.Transparent else Color.White.copy(alpha = 0.08f)

internal fun ThemeConfig.themeCardTitleFontFamily(): FontFamily = when (id) {
    SuperheroThemeId -> StageAccentFamily
    MagicWorldThemeId -> StageRoundedFamily
    else -> StageRoundedFamily
}

internal fun ThemeConfig.themeCardTitleColor(): Color = when (id) {
    SuperheroThemeId -> Color.White
    MagicWorldThemeId -> Color(0xFFE8C1FF)
    else -> Color(0xFF25283D)
}

internal fun ThemeConfig.themeCardSubtitleFontFamily(): FontFamily = when (id) {
    SuperheroThemeId -> StageRoundedFamily
    MagicWorldThemeId -> RoundedSansFamily
    else -> RoundedSansFamily
}

internal fun ThemeConfig.themeCardSubtitleColor(): Color = when (id) {
    SuperheroThemeId -> Color(0xFFFFD447)
    MagicWorldThemeId -> Color(0xFFF4D97A)
    else -> Color(0xFF6A7082)
}

// ---------------------------------------------------------------------------
// Celebration overlay styling
// ---------------------------------------------------------------------------

internal fun ThemeConfig.overlayTitleFontFamily(): FontFamily = when (id) {
    SuperheroThemeId -> StageAccentFamily
    MagicWorldThemeId -> StageRoundedFamily
    else -> StageRoundedFamily
}

internal fun ThemeConfig.overlaySubtitleFontFamily(): FontFamily = when (id) {
    SuperheroThemeId -> StageRoundedFamily
    MagicWorldThemeId -> StageAccentFamily
    else -> StageAccentFamily
}

internal fun ThemeConfig.overlaySubtitleFontWeight(): FontWeight = when (id) {
    SuperheroThemeId -> FontWeight.Bold
    MagicWorldThemeId -> FontWeight.Normal
    else -> FontWeight.Normal
}

// ---------------------------------------------------------------------------
// Gender accent color
// ---------------------------------------------------------------------------

internal fun WinningGender.accentColor(): Color =
    if (this == WinningGender.BOY) Color(0xFF42A5F5) else Color(0xFFEC407A)

