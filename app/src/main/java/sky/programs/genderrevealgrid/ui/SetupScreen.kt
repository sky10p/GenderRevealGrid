package sky.programs.genderrevealgrid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import sky.programs.genderrevealgrid.R
import sky.programs.genderrevealgrid.model.ThemeConfig
import sky.programs.genderrevealgrid.model.WinningGender

@Composable
internal fun SetupScreen(
    selectedGender: WinningGender,
    boardTitle: String,
    boardSubtitle: String,
    celebrationTitle: String,
    celebrationSubtitle: String,
    selectedTheme: ThemeConfig,
    themes: List<ThemeConfig>,
    onGenderSelected: (WinningGender) -> Unit,
    onBoardTitleChanged: (String) -> Unit,
    onBoardSubtitleChanged: (String) -> Unit,
    onCelebrationTitleChanged: (String) -> Unit,
    onCelebrationSubtitleChanged: (String) -> Unit,
    onThemeSelected: (ThemeConfig) -> Unit,
    onStartReveal: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(selectedTheme.backgroundBrush())
            .testTag(TestTags.SetupScreen)
    ) {
        AnimatedDecorationLayer(theme = selectedTheme)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.88f))
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                        .testTag(TestTags.SetupBottomCta)
                ) {
                    Button(
                        onClick = onStartReveal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .testTag(TestTags.StartRevealButton),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.start_reveal),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                HeroHeader(
                    eyebrow = stringResource(R.string.app_name),
                    title = stringResource(R.string.setup_title),
                    subtitle = stringResource(R.string.setup_subtitle),
                    theme = selectedTheme
                )
                GenderSelector(
                    selectedGender = selectedGender,
                    onSelected = onGenderSelected
                )
                BoardMessageFields(
                    title = boardTitle,
                    subtitle = boardSubtitle,
                    onTitleChanged = onBoardTitleChanged,
                    onSubtitleChanged = onBoardSubtitleChanged
                )
                CelebrationMessageFields(
                    title = celebrationTitle,
                    subtitle = celebrationSubtitle,
                    onTitleChanged = onCelebrationTitleChanged,
                    onSubtitleChanged = onCelebrationSubtitleChanged
                )
                ThemePicker(
                    themes = themes,
                    selectedTheme = selectedTheme,
                    onThemeSelected = onThemeSelected
                )
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }
}

@Composable
private fun GenderSelector(
    selectedGender: WinningGender,
    onSelected: (WinningGender) -> Unit
) {
    SectionCard(
        title = stringResource(R.string.winning_result_title),
        subtitle = stringResource(R.string.winning_result_subtitle)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            WinningGender.entries.forEach { gender ->
                val selected = gender == selectedGender
                AssistChip(
                    modifier = Modifier.testTag(
                        if (gender == WinningGender.BOY) TestTags.SetupGenderBoy else TestTags.SetupGenderGirl
                    ),
                    onClick = { onSelected(gender) },
                    label = {
                        Text(
                            text = stringResource(gender.labelResId()),
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (selected) gender.accentColor().copy(alpha = 0.22f) else Color.White,
                        labelColor = Color(0xFF2B2D42)
                    ),
                    border = AssistChipDefaults.assistChipBorder(
                        enabled = true,
                        borderColor = if (selected) gender.accentColor() else Color(0xFFE4E7F1)
                    )
                )
            }
        }
    }
}

@Composable
private fun BoardMessageFields(
    title: String,
    subtitle: String,
    onTitleChanged: (String) -> Unit,
    onSubtitleChanged: (String) -> Unit
) {
    SectionCard(
        title = stringResource(R.string.board_intro_title),
        subtitle = stringResource(R.string.board_intro_subtitle)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.BoardTitleInput),
                singleLine = true,
                label = { Text(stringResource(R.string.board_title_label)) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                shape = RoundedCornerShape(18.dp)
            )
            OutlinedTextField(
                value = subtitle,
                onValueChange = onSubtitleChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.BoardSubtitleInput),
                label = { Text(stringResource(R.string.board_subtitle_label)) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                shape = RoundedCornerShape(18.dp)
            )
        }
    }
}

@Composable
private fun CelebrationMessageFields(
    title: String,
    subtitle: String,
    onTitleChanged: (String) -> Unit,
    onSubtitleChanged: (String) -> Unit
) {
    SectionCard(
        title = stringResource(R.string.final_reveal_title),
        subtitle = stringResource(R.string.final_reveal_subtitle)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.CelebrationTitleInput),
                singleLine = true,
                label = { Text(stringResource(R.string.celebration_title_label)) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                shape = RoundedCornerShape(18.dp)
            )
            OutlinedTextField(
                value = subtitle,
                onValueChange = onSubtitleChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.CelebrationSubtitleInput),
                label = { Text(stringResource(R.string.celebration_subtitle_label)) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                shape = RoundedCornerShape(18.dp)
            )
        }
    }
}

@Composable
private fun ThemePicker(
    themes: List<ThemeConfig>,
    selectedTheme: ThemeConfig,
    onThemeSelected: (ThemeConfig) -> Unit
) {
    SectionCard(
        title = stringResource(R.string.theme_visual_title),
        subtitle = stringResource(R.string.theme_visual_subtitle)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            themes.chunked(2).forEach { rowThemes ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowThemes.forEach { theme ->
                        ThemeCard(
                            theme = theme,
                            selected = theme.id == selectedTheme.id,
                            modifier = Modifier.weight(1f),
                            onClick = { onThemeSelected(theme) }
                        )
                    }
                    if (rowThemes.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun ThemeCard(
    theme: ThemeConfig,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(theme.backgroundBrush())
            .clickable(onClick = onClick)
            .testTag(TestTags.themeCard(theme.id))
            .padding(14.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White.copy(alpha = if (selected) 0.18f else 0.06f))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(138.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ThemeIconBadge(
                    icon = theme.icon,
                    colors = theme.backgroundColors,
                    modifier = Modifier.size(44.dp)
                )
                if (theme.isPremium) {
                    PremiumBadge()
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = stringResource(theme.nameResId),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2B2D42)
                    )
                )
                Text(
                    text = stringResource(
                        if (theme.isPremium) R.string.theme_premium else R.string.theme_included
                    ),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF5F6678))
                )
            }
        }
    }
}
