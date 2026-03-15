package sky.programs.genderrevealgrid.ui

import android.content.Context
import androidx.annotation.StringRes
import sky.programs.genderrevealgrid.R
import sky.programs.genderrevealgrid.model.RevealMessageConfig
import sky.programs.genderrevealgrid.model.WinningGender

object TestTags {
    const val SetupScreen = "setup_screen"
    const val SetupBottomCta = "setup_bottom_cta"
    const val SetupGenderBoy = "setup_gender_boy"
    const val SetupGenderGirl = "setup_gender_girl"
    const val BoardTitleInput = "board_title_input"
    const val BoardSubtitleInput = "board_subtitle_input"
    const val CelebrationTitleInput = "celebration_title_input"
    const val CelebrationSubtitleInput = "celebration_subtitle_input"
    const val StartRevealButton = "start_reveal_button"
    const val GameBoard = "game_board"
    const val GameHeader = "game_header"
    const val CelebrationOverlay = "celebration_overlay"
    const val RestartButton = "restart_button"
    const val RemainingCounter = "remaining_counter"
    const val ThemeDecorationLayer = "theme_decoration_layer"
    const val BoyProgressSummary = "boy_progress_summary"
    const val GirlProgressSummary = "girl_progress_summary"
    const val HiddenProgressSummary = "hidden_progress_summary"

    fun themeCard(themeId: String): String = "theme_card_$themeId"

    fun boardCard(cardId: Int): String = "board_card_$cardId"
    fun boardCardLabel(cardId: Int): String = "board_card_label_$cardId"

    fun progressDot(index: Int): String = "progress_dot_$index"
}

internal fun Context.defaultBoardHeaderMessages(): RevealMessageConfig {
    return RevealMessageConfig(
        title = getString(R.string.default_board_title),
        subtitle = getString(R.string.default_board_subtitle)
    )
}

internal fun Context.defaultCelebrationMessagesFor(gender: WinningGender): RevealMessageConfig {
    return RevealMessageConfig(
        title = getString(gender.defaultCelebrationTitleResId()),
        subtitle = getString(gender.defaultCelebrationSubtitleResId())
    )
}

internal fun Context.updatedCelebrationMessagesForGender(
    previousGender: WinningGender,
    newGender: WinningGender,
    currentMessages: RevealMessageConfig
): RevealMessageConfig {
    val oldDefaults = defaultCelebrationMessagesFor(previousGender)
    val newDefaults = defaultCelebrationMessagesFor(newGender)
    return RevealMessageConfig(
        title = if (currentMessages.title.isBlank() || currentMessages.title == oldDefaults.title) {
            newDefaults.title
        } else {
            currentMessages.title
        },
        subtitle = if (
            currentMessages.subtitle.isBlank() ||
            currentMessages.subtitle == oldDefaults.subtitle
        ) {
            newDefaults.subtitle
        } else {
            currentMessages.subtitle
        }
    )
}

@StringRes
internal fun WinningGender.labelResId(): Int =
    if (this == WinningGender.BOY) R.string.gender_boy else R.string.gender_girl

@StringRes
private fun WinningGender.defaultCelebrationTitleResId(): Int =
    if (this == WinningGender.BOY) R.string.default_boy_title else R.string.default_girl_title

@StringRes
private fun WinningGender.defaultCelebrationSubtitleResId(): Int =
    if (this == WinningGender.BOY) R.string.default_boy_subtitle else R.string.default_girl_subtitle
