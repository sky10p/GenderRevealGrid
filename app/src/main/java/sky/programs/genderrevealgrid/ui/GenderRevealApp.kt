package sky.programs.genderrevealgrid.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import sky.programs.genderrevealgrid.logic.GameEngine
import sky.programs.genderrevealgrid.model.RevealCatalog
import sky.programs.genderrevealgrid.model.RevealMessageConfig
import sky.programs.genderrevealgrid.model.RevealSetupConfig
import sky.programs.genderrevealgrid.model.WinningGender

private sealed interface AppDestination {
    data object Setup : AppDestination
    data class Game(val setup: RevealSetupConfig) : AppDestination
}

@Composable
fun GenderRevealApp() {
    var destination by remember { mutableStateOf<AppDestination>(AppDestination.Setup) }

    when (val currentDestination = destination) {
        AppDestination.Setup -> SetupRoute(
            onStart = { setup ->
                destination = AppDestination.Game(setup)
            }
        )

        is AppDestination.Game -> GameRoute(
            setup = currentDestination.setup,
            onRestart = { destination = AppDestination.Setup }
        )
    }
}

@Composable
private fun SetupRoute(onStart: (RevealSetupConfig) -> Unit) {
    val context = LocalContext.current
    var selectedGender by rememberSaveable { mutableStateOf(WinningGender.GIRL.name) }
    var selectedThemeId by rememberSaveable { mutableStateOf(RevealCatalog.themes.first().id) }

    val gender = remember(selectedGender) { WinningGender.valueOf(selectedGender) }
    val defaultBoardMessages = remember(context.resources.configuration) {
        context.defaultBoardHeaderMessages()
    }
    val defaultCelebrationMessages = remember(gender, context.resources.configuration) {
        context.defaultCelebrationMessagesFor(gender)
    }

    var boardTitle by rememberSaveable { mutableStateOf(defaultBoardMessages.title) }
    var boardSubtitle by rememberSaveable { mutableStateOf(defaultBoardMessages.subtitle) }
    var celebrationTitle by rememberSaveable { mutableStateOf(defaultCelebrationMessages.title) }
    var celebrationSubtitle by rememberSaveable { mutableStateOf(defaultCelebrationMessages.subtitle) }

    val selectedTheme = remember(selectedThemeId) { RevealCatalog.themeById(selectedThemeId) }

    SetupScreen(
        selectedGender = gender,
        boardTitle = boardTitle,
        boardSubtitle = boardSubtitle,
        celebrationTitle = celebrationTitle,
        celebrationSubtitle = celebrationSubtitle,
        selectedTheme = selectedTheme,
        themes = RevealCatalog.themes,
        onGenderSelected = { newGender ->
            if (newGender == gender) return@SetupScreen
            val updatedMessages = context.updatedCelebrationMessagesForGender(
                previousGender = gender,
                newGender = newGender,
                currentMessages = RevealMessageConfig(
                    title = celebrationTitle,
                    subtitle = celebrationSubtitle
                )
            )
            selectedGender = newGender.name
            celebrationTitle = updatedMessages.title
            celebrationSubtitle = updatedMessages.subtitle
        },
        onBoardTitleChanged = { boardTitle = it },
        onBoardSubtitleChanged = { boardSubtitle = it },
        onCelebrationTitleChanged = { celebrationTitle = it },
        onCelebrationSubtitleChanged = { celebrationSubtitle = it },
        onThemeSelected = { selectedThemeId = it.id },
        onStartReveal = {
            onStart(
                RevealSetupConfig(
                    winningGender = gender,
                    boardHeaderMessage = RevealMessageConfig(
                        title = boardTitle.trim().ifEmpty { context.defaultBoardHeaderMessages().title },
                        subtitle = boardSubtitle.trim()
                            .ifEmpty { context.defaultBoardHeaderMessages().subtitle }
                    ),
                    celebrationMessage = RevealMessageConfig(
                        title = celebrationTitle.trim()
                            .ifEmpty { context.defaultCelebrationMessagesFor(gender).title },
                        subtitle = celebrationSubtitle.trim()
                            .ifEmpty { context.defaultCelebrationMessagesFor(gender).subtitle }
                    ),
                    theme = selectedTheme
                )
            )
        }
    )
}

@Composable
private fun GameRoute(
    setup: RevealSetupConfig,
    onRestart: () -> Unit
) {
    var gameState by remember(setup) { mutableStateOf(GameEngine.startGame(setup)) }

    GameScreen(
        setup = setup,
        gameState = gameState,
        onCardClick = { card ->
            gameState = GameEngine.revealCard(gameState, card.id)
        },
        onRestart = onRestart
    )
}
