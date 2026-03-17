package sky.programs.genderrevealgrid

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import sky.programs.genderrevealgrid.ui.TestTags

abstract class BaseGenderRevealComposeTest(localeTag: String) {
    protected val composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity> =
        createAndroidComposeRule<MainActivity>()

    private val localeRule = LocaleTestRule(localeTag)

    @get:Rule
    val ruleChain: TestRule = RuleChain.outerRule(localeRule).around(composeRule)

    protected fun string(@StringRes resId: Int): String =
        composeRule.activity.getString(resId)

    protected fun startRevealWithCustomMessages(
        boardTitle: String,
        boardSubtitle: String,
        celebrationTitle: String,
        celebrationSubtitle: String,
        themeId: String = "superhero"
    ) {
        clickTag(TestTags.SetupGenderBoy)
        composeRule.onNodeWithTag(TestTags.BoardTitleInput, useUnmergedTree = true)
            .performTextReplacement(boardTitle)
        composeRule.onNodeWithTag(TestTags.BoardSubtitleInput, useUnmergedTree = true)
            .performTextReplacement(boardSubtitle)
        composeRule.onNodeWithTag(TestTags.CelebrationTitleInput, useUnmergedTree = true)
            .performTextReplacement(celebrationTitle)
        composeRule.onNodeWithTag(TestTags.CelebrationSubtitleInput, useUnmergedTree = true)
            .performTextReplacement(celebrationSubtitle)
        clickTag(TestTags.themeCard(themeId))
        clickTag(TestTags.StartRevealButton)
        waitForGameScreen()
    }

    protected fun flipCards(count: Int) {
        repeat(count) { index ->
            clickTag(TestTags.boardCard(index))
        }
    }

    protected fun assertSetupVisible() {
        composeRule.onNodeWithTag(TestTags.SetupScreen, useUnmergedTree = true).assertIsDisplayed()
    }

    protected fun assertCelebrationVisible() {
        composeRule.onNodeWithTag(TestTags.CelebrationOverlay, useUnmergedTree = true).assertExists()
    }

    protected fun assertCelebrationHidden() {
        composeRule.onNodeWithTag(TestTags.CelebrationOverlay, useUnmergedTree = true).assertDoesNotExist()
    }

    protected fun assertTextDisplayed(@StringRes resId: Int) {
        composeRule.onNodeWithText(string(resId), ignoreCase = true).assertIsDisplayed()
    }

    protected fun assertTaggedNodeHasOneOfTexts(tag: String, vararg expectedTexts: String) {
        val node = composeRule.onNodeWithTag(tag, useUnmergedTree = true).fetchSemanticsNode()
        val actualText = if (SemanticsProperties.Text in node.config) {
            node.config[SemanticsProperties.Text].joinToString(separator = " ") { value -> value.text }
        } else {
            ""
        }

        check(expectedTexts.any { it == actualText }) {
            "Expected node '$tag' to contain one of ${expectedTexts.toList()}, but was '$actualText'."
        }
    }

    protected fun waitForGameScreen() {
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag(
                TestTags.GameBoard,
                useUnmergedTree = true
            ).fetchSemanticsNodes().isNotEmpty()
        }
    }

    protected fun clickTag(tag: String) {
        composeRule.onNodeWithTag(tag, useUnmergedTree = true)
            .performSemanticsAction(SemanticsActions.OnClick)
    }
}
