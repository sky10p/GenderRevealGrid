package sky.programs.genderrevealgrid.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sky.programs.genderrevealgrid.model.ThemeConfig
import sky.programs.genderrevealgrid.model.ThemeIcon
import sky.programs.genderrevealgrid.ui.theme.RoundedSansFamily
import sky.programs.genderrevealgrid.ui.theme.StageAccentFamily
import sky.programs.genderrevealgrid.ui.theme.StageRoundedFamily

private const val SuperheroThemeId = "superhero"
private const val MagicWorldThemeId = "magic-world"

// ---------------------------------------------------------------------------
// ThemeIconBadge – renders the theme icon inside a Canvas
// ---------------------------------------------------------------------------

@Composable
internal fun ThemeIconBadge(
    icon: ThemeIcon,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        when (icon) {
            ThemeIcon.BALLOON -> drawBalloon(colors)
            ThemeIcon.CLOUD -> drawCloud(colors)
            ThemeIcon.SPARKLES -> drawSparkle(colors)
            ThemeIcon.SUPERHERO_BADGE -> drawSuperheroBadge(colors)
            ThemeIcon.MAGIC_WAND -> drawMagicWand(colors)
        }
    }
}

// ---------------------------------------------------------------------------
// PlayfulMomentHeader – compact header used on the gameplay screen
// ---------------------------------------------------------------------------

@Composable
internal fun PlayfulMomentHeader(
    eyebrow: String,
    title: String,
    subtitle: String,
    theme: ThemeConfig,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = eyebrow.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = StageRoundedFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = theme.headerEyebrowColor()
            )
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = theme.headerTitleFontFamily(),
                fontWeight = FontWeight.Bold,
                color = theme.headerTitleColor()
            )
        )
        if (subtitle.isNotBlank()) {
            Text(
                text = subtitle,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = theme.headerSubtitleFontFamily(),
                    color = theme.headerSubtitleColor()
                )
            )
        }
    }
}

// ---------------------------------------------------------------------------
// HeroHeader – larger header used on the setup screen
// ---------------------------------------------------------------------------

@Composable
internal fun HeroHeader(
    eyebrow: String,
    title: String,
    subtitle: String,
    theme: ThemeConfig,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = eyebrow.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = StageRoundedFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp,
                color = theme.headerEyebrowColor()
            )
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = theme.headerTitleFontFamily(),
                fontWeight = FontWeight.Bold,
                color = theme.headerTitleColor()
            )
        )
        if (subtitle.isNotBlank()) {
            Text(
                text = subtitle,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = theme.headerSubtitleFontFamily(),
                    color = theme.headerSubtitleColor()
                )
            )
        }
    }
}

// ---------------------------------------------------------------------------
// SectionCard – rounded section wrapper used on the setup screen
// ---------------------------------------------------------------------------

@Composable
internal fun SectionCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(26.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, shape, clip = false)
            .clip(shape)
            .background(Color.White.copy(alpha = 0.94f))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = StageRoundedFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF25283D)
                )
            )
            if (subtitle.isNotBlank()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = RoundedSansFamily,
                        color = Color(0xFF6A7082)
                    )
                )
            }
        }
        content()
    }
}

// ---------------------------------------------------------------------------
// PremiumBadge – small "⭐" chip shown on premium theme cards
// ---------------------------------------------------------------------------

@Composable
internal fun PremiumBadge(
    theme: ThemeConfig,
    modifier: Modifier = Modifier
) {
    val badgeShape = RoundedCornerShape(12.dp)
    Box(
        modifier = modifier
            .clip(badgeShape)
            .background(theme.celebrationAccent.copy(alpha = 0.22f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = "⭐",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp)
        )
    }
}

// ---------------------------------------------------------------------------
// ThemeCardPreview – mini 3×3 dot grid previewing the board
// ---------------------------------------------------------------------------

@Composable
internal fun ThemeCardPreview(
    theme: ThemeConfig,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { col ->
            Column(
                modifier = Modifier.padding(horizontal = 3.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(3) { row ->
                    val dotColor = if ((row + col) % 2 == 0) {
                        theme.boardStrokeColor.copy(alpha = 0.4f)
                    } else {
                        theme.celebrationAccent.copy(alpha = 0.3f)
                    }
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Private header-color helpers (avoid duplicating theme-id checks everywhere)
// ---------------------------------------------------------------------------

private fun ThemeConfig.headerEyebrowColor(): Color = when (id) {
    SuperheroThemeId -> Color(0xFFFFD447).copy(alpha = 0.8f)
    MagicWorldThemeId -> Color(0xFFF4D97A).copy(alpha = 0.8f)
    else -> Color(0xFF888888)
}

private fun ThemeConfig.headerTitleFontFamily() = when (id) {
    SuperheroThemeId -> StageAccentFamily
    MagicWorldThemeId -> StageRoundedFamily
    else -> StageRoundedFamily
}

private fun ThemeConfig.headerTitleColor(): Color = when (id) {
    SuperheroThemeId -> Color.White
    MagicWorldThemeId -> Color(0xFFE8C1FF)
    else -> Color(0xFF25283D)
}

private fun ThemeConfig.headerSubtitleFontFamily() = when (id) {
    SuperheroThemeId -> StageRoundedFamily
    MagicWorldThemeId -> RoundedSansFamily
    else -> RoundedSansFamily
}

private fun ThemeConfig.headerSubtitleColor(): Color = when (id) {
    SuperheroThemeId -> Color(0xFFFFD447).copy(alpha = 0.9f)
    MagicWorldThemeId -> Color(0xFFF4D97A).copy(alpha = 0.9f)
    else -> Color(0xFF6A7082)
}

