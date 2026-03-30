package sky.programs.genderrevealgrid.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sky.programs.genderrevealgrid.R
import sky.programs.genderrevealgrid.model.GameState
import sky.programs.genderrevealgrid.model.RevealCard
import sky.programs.genderrevealgrid.model.RevealSetupConfig
import sky.programs.genderrevealgrid.model.ThemeConfig
import sky.programs.genderrevealgrid.model.WinningGender
import sky.programs.genderrevealgrid.ui.theme.StageRoundedFamily

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.07f),
                            Color.Transparent,
                            Color.White.copy(alpha = 0.05f)
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            PlayfulMomentHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.GameHeader),
                eyebrow = stringResource(R.string.gameplay_header_eyebrow),
                title = setup.boardHeaderMessage.title,
                subtitle = setup.boardHeaderMessage.subtitle,
                theme = setup.theme
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.widthIn(max = 460.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    BoardPanel(
                        theme = setup.theme,
                        cards = gameState.cards,
                        onCardClick = onCardClick
                    )
                    RemainingCounter(
                        revealedGenders = gameState.revealedGenders,
                        boyRevealedCount = gameState.boyRevealedCount,
                        girlRevealedCount = gameState.girlRevealedCount,
                        hiddenCount = remainingCount,
                        theme = setup.theme
                    )
                }
            }
        }

        if (gameState.celebrationVisible) {
            CelebrationOverlay(setup = setup, onRestart = onRestart)
        }
    }
}

@Composable
private fun BoardPanel(
    theme: ThemeConfig,
    cards: List<RevealCard>,
    onCardClick: (RevealCard) -> Unit
) {
    val boardShape = RoundedCornerShape(42.dp)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestTags.GameBoard),
        contentAlignment = Alignment.Center
    ) {
        val boardSize = maxWidth.coerceAtMost(408.dp)
        Box(
            modifier = Modifier
                .size(boardSize)
                .shadow(28.dp, boardShape, clip = false)
                .clip(boardShape)
                .background(theme.boardPanelBrush())
                .border(10.dp, Color.White.copy(alpha = 0.92f), boardShape)
                .border(1.5.dp, theme.boardStrokeColor.copy(alpha = 0.9f), boardShape)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawRoundRect(
                    color = theme.boardStrokeColor.copy(alpha = 0.15f),
                    topLeft = androidx.compose.ui.geometry.Offset(size.width * 0.06f, size.height * 0.06f),
                    size = androidx.compose.ui.geometry.Size(size.width * 0.88f, size.height * 0.88f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(44.dp.toPx(), 44.dp.toPx())
                )
                drawLine(
                    color = Color.White.copy(alpha = 0.65f),
                    start = androidx.compose.ui.geometry.Offset(size.width * 0.1f, size.height * 0.12f),
                    end = androidx.compose.ui.geometry.Offset(size.width * 0.9f, size.height * 0.32f),
                    strokeWidth = 6.dp.toPx()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                                theme = theme,
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
}

@Composable
private fun RevealCardView(
    theme: ThemeConfig,
    card: RevealCard,
    labelTag: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val rotation = animateFloatAsState(
        targetValue = if (card.isRevealed) 180f else 0f,
        animationSpec = tween(durationMillis = 820, easing = FastOutSlowInEasing),
        label = "card-rotation"
    )
    val scale = animateFloatAsState(
        targetValue = when {
            isPressed && !card.isRevealed -> 0.95f
            card.isRevealed -> 1.02f
            else -> 1f
        },
        animationSpec = spring(dampingRatio = 0.62f, stiffness = 320f),
        label = "card-scale"
    )

    Box(
        modifier = modifier
            .testTag(TestTags.boardCard(card.id))
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                rotationY = rotation.value
                cameraDistance = 18f * density
            }
            .clip(CircleShape)
            .clickable(
                enabled = !card.isRevealed,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if (rotation.value <= 90f) {
            CircularFace(
                brush = Brush.linearGradient(
                    listOf(
                        Color.White,
                        theme.boardFrameColor.copy(alpha = 0.94f),
                        Color.White.copy(alpha = 0.98f)
                    )
                ),
                borderColor = Color.White.copy(alpha = 0.94f),
                rimColor = theme.boardStrokeColor.copy(alpha = 0.46f),
                label = "?",
                labelTag = labelTag,
                textColor = theme.celebrationAccent
            )
        } else {
            val revealedGender = requireNotNull(card.gender)
            CircularFace(
                modifier = Modifier.graphicsLayer { rotationY = 180f },
                brush = Brush.linearGradient(revealedGender.cardBrush()),
                borderColor = Color.White.copy(alpha = 0.9f),
                rimColor = revealedGender.accentColor().copy(alpha = 0.72f),
                label = stringResource(revealedGender.labelResId()),
                labelTag = labelTag,
                textColor = revealedGender.revealTextColor()
            )
        }
    }
}

@Composable
private fun CircularFace(
    brush: Brush,
    borderColor: Color,
    rimColor: Color,
    label: String,
    labelTag: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .shadow(8.dp, CircleShape, clip = false)
            .clip(CircleShape)
            .background(brush)
            .border(4.dp, borderColor, CircleShape)
            .border(1.5.dp, rimColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = Color.White.copy(alpha = 0.22f),
                radius = size.minDimension * 0.26f,
                center = androidx.compose.ui.geometry.Offset(size.width * 0.38f, size.height * 0.3f)
            )
        }
        Text(
            text = label,
            modifier = Modifier.testTag(labelTag),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = StageRoundedFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,
                lineHeight = 23.sp,
                color = textColor
            )
        )
    }
}

@Composable
private fun RemainingCounter(
    revealedGenders: List<WinningGender>,
    boyRevealedCount: Int,
    girlRevealedCount: Int,
    hiddenCount: Int,
    theme: ThemeConfig
) {
    val pillShape = RoundedCornerShape(30.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(16.dp, pillShape, clip = false)
            .clip(pillShape)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color.White.copy(alpha = 0.88f),
                        theme.boardFrameColor.copy(alpha = 0.9f),
                        Color.White.copy(alpha = 0.82f)
                    )
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.8f), pillShape)
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.RemainingCounter),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = stringResource(R.string.revealed_progress_title),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = StageRoundedFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF25283D)
                )
            )
            Text(
                text = pluralStringResource(
                    R.plurals.reveal_remaining_instruction,
                    hiddenCount,
                    hiddenCount
                ),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = StageRoundedFamily,
                    color = Color(0xFF6A7082)
                )
            )
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
                                    null -> Color(0xFFD9DDE8)
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
                fontFamily = StageRoundedFamily,
                fontWeight = FontWeight.SemiBold,
                color = textColor.copy(alpha = 0.82f)
            )
        )
        Text(
            text = value,
            modifier = Modifier.testTag(valueTag),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = StageRoundedFamily,
                fontWeight = FontWeight.Bold,
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
    val overlayShape = RoundedCornerShape(36.dp)
    val contentShape = RoundedCornerShape(30.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF171827).copy(alpha = 0.6f))
            .testTag(TestTags.CelebrationOverlay)
    ) {
        ConfettiLayer(
            colors = setup.theme.celebrationColors + listOf(setup.winningGender.accentColor(), Color.White),
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                )
                .clearAndSetSemantics { }
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp)
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .shadow(28.dp, overlayShape, clip = false)
                .clip(overlayShape)
                .background(Color.White.copy(alpha = 0.98f))
                .border(1.dp, Color.White.copy(alpha = 0.96f), overlayShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(contentShape)
                        .background(setup.theme.celebrationBrush())
                        .border(1.dp, setup.theme.boardStrokeColor.copy(alpha = 0.34f), contentShape)
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(14.dp)
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
                                fontFamily = setup.theme.overlayTitleFontFamily(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 44.sp,
                                lineHeight = 46.sp,
                                color = Color(0xFF25283D)
                            )
                        )
                        Text(
                            text = setup.celebrationMessage.subtitle,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = setup.theme.overlaySubtitleFontFamily(),
                                fontWeight = setup.theme.overlaySubtitleFontWeight(),
                                fontSize = 30.sp,
                                lineHeight = 34.sp,
                                color = setup.theme.celebrationAccent
                            )
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(22.dp))
                                .background(setup.theme.buttonBrush())
                                .clickable(onClick = onRestart)
                                .testTag(TestTags.RestartButton)
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.back_to_setup),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = StageRoundedFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(overlayShape)
                        .border(1.dp, setup.theme.boardStrokeColor.copy(alpha = 0.2f), overlayShape)
                )
            }
        }
    }
}

private fun WinningGender.cardBrush(): List<Color> =
    if (this == WinningGender.BOY) {
        listOf(Color(0xFFEAF8FF), Color(0xFFBAE6FD), Color(0xFFE7F7FF))
    } else {
        listOf(Color(0xFFFFF0F7), Color(0xFFFBCFE8), Color(0xFFFFE6F1))
    }

private fun WinningGender.revealTextColor(): Color =
    if (this == WinningGender.BOY) Color(0xFF0D6990) else Color(0xFFAD3C73)
