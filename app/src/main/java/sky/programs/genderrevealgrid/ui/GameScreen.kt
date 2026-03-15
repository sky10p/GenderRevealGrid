package sky.programs.genderrevealgrid.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sky.programs.genderrevealgrid.R
import sky.programs.genderrevealgrid.model.GameState
import sky.programs.genderrevealgrid.model.RevealCard
import sky.programs.genderrevealgrid.model.RevealSetupConfig
import sky.programs.genderrevealgrid.model.WinningGender
import sky.programs.genderrevealgrid.ui.theme.AccentScriptFamily
import sky.programs.genderrevealgrid.ui.theme.RoundedSansFamily

@Composable
internal fun GameScreen(
    setup: RevealSetupConfig,
    gameState: GameState,
    onCardClick: (RevealCard) -> Unit,
    onRestart: () -> Unit
) {
    val remainingCount = gameState.cards.size - gameState.revealedCount

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(setup.theme.backgroundBrush())
    ) {
        AnimatedDecorationLayer(theme = setup.theme)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            PlayfulMomentHeader(
                modifier = Modifier.testTag(TestTags.GameHeader),
                eyebrow = null,
                title = setup.boardHeaderMessage.title,
                subtitle = setup.boardHeaderMessage.subtitle,
                theme = setup.theme
            )
            BoardPanel(
                cards = gameState.cards,
                onCardClick = onCardClick
            )
            RemainingCounter(
                revealedGenders = gameState.revealedGenders,
                boyRevealedCount = gameState.boyRevealedCount,
                girlRevealedCount = gameState.girlRevealedCount,
                hiddenCount = remainingCount
            )
        }

        if (gameState.celebrationVisible) {
            CelebrationOverlay(setup = setup, onRestart = onRestart)
        }
    }
}

@Composable
private fun BoardPanel(
    cards: List<RevealCard>,
    onCardClick: (RevealCard) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestTags.GameBoard),
        shape = RoundedCornerShape(34.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.78f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            cards.chunked(3).forEach { rowCards ->
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    rowCards.forEach { card ->
                        RevealCardView(
                            card = card,
                            labelTag = TestTags.boardCardLabel(card.id),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            onClick = { onCardClick(card) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RevealCardView(
    card: RevealCard,
    labelTag: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val rotation = animateFloatAsState(
        targetValue = if (card.isRevealed) 180f else 0f,
        animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
        label = "card-rotation"
    )

    Box(
        modifier = modifier
            .testTag(TestTags.boardCard(card.id))
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            }
            .clip(CircleShape)
            .clickable(enabled = !card.isRevealed, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (rotation.value <= 90f) {
            CircularFace(
                backgroundColor = Color.White,
                borderColor = Color(0xFFE7EBF4),
                accentColor = Color(0xFFF4B8D5),
                label = "?",
                labelTag = labelTag
            )
        } else {
            val revealedGender = requireNotNull(card.gender)
            CircularFace(
                modifier = Modifier.graphicsLayer { rotationY = 180f },
                backgroundColor = revealedGender.softColor(),
                borderColor = revealedGender.accentColor(),
                accentColor = revealedGender.accentColor(),
                label = stringResource(revealedGender.labelResId()),
                labelTag = labelTag
            )
        }
    }
}

@Composable
private fun CircularFace(
    backgroundColor: Color,
    borderColor: Color,
    accentColor: Color,
    label: String,
    labelTag: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(CircleShape)
            .background(backgroundColor)
            .border(2.dp, borderColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            modifier = Modifier.testTag(labelTag),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                color = accentColor
            )
        )
    }
}

@Composable
private fun RemainingCounter(
    revealedGenders: List<WinningGender>,
    boyRevealedCount: Int,
    girlRevealedCount: Int,
    hiddenCount: Int
) {
    SectionCard(
        title = stringResource(R.string.revealed_progress_title),
        subtitle = stringResource(R.string.revealed_progress_subtitle)
    ) {
        Column(
            modifier = Modifier.testTag(TestTags.RemainingCounter),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(9) { index ->
                    val revealedGender = revealedGenders.getOrNull(index)
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .testTag(TestTags.progressDot(index))
                            .clip(CircleShape)
                            .background(
                                when (revealedGender) {
                                    WinningGender.BOY -> WinningGender.BOY.accentColor()
                                    WinningGender.GIRL -> WinningGender.GIRL.accentColor()
                                    null -> Color(0xFFD4D9E6)
                                }
                            )
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProgressSummaryLine(
                    modifier = Modifier.weight(1f),
                    valueTag = TestTags.BoyProgressSummary,
                    label = stringResource(R.string.boys_revealed_label),
                    value = boyRevealedCount.toString(),
                    textColor = Color(0xFF245168)
                )
                ProgressSummaryLine(
                    modifier = Modifier.weight(1f),
                    valueTag = TestTags.GirlProgressSummary,
                    label = stringResource(R.string.girls_revealed_label),
                    value = girlRevealedCount.toString(),
                    textColor = Color(0xFF7B395A)
                )
                ProgressSummaryLine(
                    modifier = Modifier.weight(1f),
                    valueTag = TestTags.HiddenProgressSummary,
                    label = stringResource(R.string.hidden_revealed_label),
                    value = hiddenCount.toString(),
                    textColor = Color(0xFF4A5161)
                )
            }
        }
    }
}

@Composable
private fun ProgressSummaryLine(
    label: String,
    value: String,
    textColor: Color,
    valueTag: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.wrapContentWidth(Alignment.CenterHorizontally),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label ",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = RoundedSansFamily,
                fontWeight = FontWeight.SemiBold,
                color = textColor.copy(alpha = 0.82f)
            )
        )
        Text(
            text = value,
            modifier = Modifier.testTag(valueTag),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = RoundedSansFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                lineHeight = 24.sp,
                color = textColor
            )
        )
    }
}

@Composable
private fun CelebrationOverlay(
    setup: RevealSetupConfig,
    onRestart: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E2032).copy(alpha = 0.48f))
            .testTag(TestTags.CelebrationOverlay)
    ) {
        ConfettiLayer(modifier = Modifier.matchParentSize())
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            shape = RoundedCornerShape(36.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.94f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ThemeIconBadge(
                    icon = setup.theme.icon,
                    colors = setup.theme.backgroundColors,
                    modifier = Modifier.size(72.dp)
                )
                Text(
                    text = setup.celebrationMessage.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = RoundedSansFamily,
                        fontSize = 46.sp,
                        lineHeight = 48.sp,
                        color = Color(0xFF25283D)
                    )
                )
                Text(
                    text = setup.celebrationMessage.subtitle,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = AccentScriptFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        color = Color(0xFFF39AC5)
                    )
                )
                Button(
                    onClick = onRestart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.RestartButton),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text = stringResource(R.string.back_to_setup),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
