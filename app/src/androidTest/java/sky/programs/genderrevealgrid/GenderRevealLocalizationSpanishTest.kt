package sky.programs.genderrevealgrid

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Test
import sky.programs.genderrevealgrid.ui.TestTags

class GenderRevealLocalizationSpanishTest : BaseGenderRevealComposeTest(localeTag = "es") {
    @Test
    fun spanishStrings_areRenderedInSetupAndGame() {
        assertSetupVisible()
        assertTextDisplayed(R.string.setup_title)
        assertTextDisplayed(R.string.board_intro_title)
        assertTextDisplayed(R.string.final_reveal_title)
        assertTextDisplayed(R.string.start_reveal)
        composeRule.onNodeWithTag(TestTags.StartRevealButton, useUnmergedTree = true).assertIsDisplayed()

        clickTag(TestTags.StartRevealButton)
        waitForGameScreen()

        assertTextDisplayed(R.string.default_board_title)
        assertTextDisplayed(R.string.default_board_subtitle)
        assertTextDisplayed(R.string.revealed_progress_title)
        composeRule.onNodeWithTag(TestTags.GameBoard, useUnmergedTree = true).assertExists()
    }

    @Test
    fun spanishRevealedCard_showsFullWordWithoutEmojiOrAbbreviation() {
        clickTag(TestTags.StartRevealButton)
        waitForGameScreen()

        clickTag(TestTags.boardCard(0))

        assertTaggedNodeHasOneOfTexts(TestTags.boardCardLabel(0), "Niño", "Niña")
        composeRule.onAllNodesWithText("Ni").assertCountEquals(0)
        composeRule.onAllNodesWithText("👦").assertCountEquals(0)
        composeRule.onAllNodesWithText("👧").assertCountEquals(0)
    }
}
