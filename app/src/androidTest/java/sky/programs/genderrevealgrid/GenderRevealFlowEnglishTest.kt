package sky.programs.genderrevealgrid

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Test
import sky.programs.genderrevealgrid.ui.TestTags

class GenderRevealFlowEnglishTest : BaseGenderRevealComposeTest(localeTag = "en") {
    @Test
    fun happyPath_reachesCelebrationAndReturnsToSetup_withSeparateBoardAndFinalMessages() {
        val boardTitle = "Surprise in the sky"
        val boardSubtitle = "What will our tiny explorer be?"
        val celebrationTitle = "Baby Mason is on the way"
        val celebrationSubtitle = "Nine flips later, the surprise is finally out."

        startRevealWithCustomMessages(
            boardTitle = boardTitle,
            boardSubtitle = boardSubtitle,
            celebrationTitle = celebrationTitle,
            celebrationSubtitle = celebrationSubtitle
        )

        composeRule.onNodeWithText(boardTitle).assertIsDisplayed()
        composeRule.onNodeWithText(boardSubtitle).assertIsDisplayed()
        flipCards(9)

        assertCelebrationVisible()
        composeRule.onNodeWithText(celebrationTitle).assertIsDisplayed()
        composeRule.onNodeWithText(celebrationSubtitle).assertIsDisplayed()

        clickTag(TestTags.RestartButton)
        assertSetupVisible()
    }

    @Test
    fun revealedCards_showFullWords_withoutShortSymbolsOrEmoji() {
        startRevealWithCustomMessages(
            boardTitle = "Full words only",
            boardSubtitle = "No short symbols should appear.",
            celebrationTitle = "Done",
            celebrationSubtitle = "Only complete labels are allowed."
        )

        clickTag(TestTags.boardCard(0))

        composeRule.waitUntil(timeoutMillis = 5_000) {
            val boyNodes = composeRule.onAllNodesWithText("Boy").fetchSemanticsNodes().size
            val girlNodes = composeRule.onAllNodesWithText("Girl").fetchSemanticsNodes().size
            boyNodes + girlNodes > 0
        }
        assertTaggedNodeHasOneOfTexts(TestTags.boardCardLabel(0), "Boy", "Girl")
        composeRule.onAllNodesWithText("B").assertCountEquals(0)
        composeRule.onAllNodesWithText("G").assertCountEquals(0)
        composeRule.onAllNodesWithText("👦").assertCountEquals(0)
        composeRule.onAllNodesWithText("👧").assertCountEquals(0)
    }

    @Test
    fun celebration_onlyAppearsAfterTheNinthCard() {
        startRevealWithCustomMessages(
            boardTitle = "Ready for the reveal",
            boardSubtitle = "The board should stay in play until the last tap.",
            celebrationTitle = "The answer is here",
            celebrationSubtitle = "The last circle unlocks everything."
        )

        flipCards(8)
        assertCelebrationHidden()

        clickTag(TestTags.boardCard(8))
        assertCelebrationVisible()
    }

    @Test
    fun englishStrings_andProgressSummaries_areRenderedInSetupAndGame() {
        assertSetupVisible()
        assertTextDisplayed(R.string.app_name)
        assertTextDisplayed(R.string.setup_title)
        assertTextDisplayed(R.string.board_intro_title)
        assertTextDisplayed(R.string.final_reveal_title)
        assertTextDisplayed(R.string.start_reveal)
        composeRule.onNodeWithTag(TestTags.SetupBottomCta, useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.ThemeDecorationLayer, useUnmergedTree = true).assertExists()

        clickTag(TestTags.StartRevealButton)
        waitForGameScreen()

        assertTextDisplayed(R.string.default_board_title)
        assertTextDisplayed(R.string.default_board_subtitle)
        assertTextDisplayed(R.string.revealed_progress_title)
        composeRule.onNodeWithTag(TestTags.GameBoard, useUnmergedTree = true).assertExists()
        composeRule.onNodeWithTag(TestTags.ThemeDecorationLayer, useUnmergedTree = true).assertExists()
    }

    @Test
    fun progressFooter_showsOnlyRevealedCountsAfterEightCards() {
        startRevealWithCustomMessages(
            boardTitle = "Tiny reveal",
            boardSubtitle = "Stay balanced until the last tap.",
            celebrationTitle = "It is time",
            celebrationSubtitle = "Now the answer can be celebrated."
        )

        flipCards(8)

        assertCelebrationHidden()
        composeRule.onNodeWithTag(TestTags.BoyProgressSummary, useUnmergedTree = true)
            .assertTextContains("4")
        composeRule.onNodeWithTag(TestTags.GirlProgressSummary, useUnmergedTree = true)
            .assertTextContains("4")
        composeRule.onNodeWithTag(TestTags.HiddenProgressSummary, useUnmergedTree = true)
            .assertTextContains("1")
    }

    @Test
    fun gameScreen_doesNotShowSelectedThemeName() {
        startRevealWithCustomMessages(
            boardTitle = "Make the guess",
            boardSubtitle = "The answer waits for the last tap.",
            celebrationTitle = "Big reveal",
            celebrationSubtitle = "Now everyone knows.",
            themeId = "celebration-pop"
        )

        composeRule.onAllNodesWithText(string(R.string.theme_name_celebration_pop))
            .assertCountEquals(0)
    }
}
