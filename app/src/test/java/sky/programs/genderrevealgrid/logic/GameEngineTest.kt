package sky.programs.genderrevealgrid.logic

import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import sky.programs.genderrevealgrid.model.RevealCatalog
import sky.programs.genderrevealgrid.model.RevealMessageConfig
import sky.programs.genderrevealgrid.model.RevealSetupConfig
import sky.programs.genderrevealgrid.model.WinningGender

class GameEngineTest {
    @Test
    fun createRevealSequence_generatesFiveGirlsAndFourBoys_whenGirlWins() {
        val deck = GameEngine.createRevealSequence(WinningGender.GIRL, Random(7))

        assertEquals(9, deck.size)
        assertEquals(5, deck.count { it == WinningGender.GIRL })
        assertEquals(4, deck.count { it == WinningGender.BOY })
    }

    @Test
    fun createRevealSequence_generatesFiveBoysAndFourGirls_whenBoyWins() {
        val deck = GameEngine.createRevealSequence(WinningGender.BOY, Random(11))

        assertEquals(9, deck.size)
        assertEquals(5, deck.count { it == WinningGender.BOY })
        assertEquals(4, deck.count { it == WinningGender.GIRL })
    }

    @Test
    fun createRevealSequence_keepsResultUncertainUntilLastCard() {
        val sequence = GameEngine.createRevealSequence(WinningGender.GIRL, Random(4))
        var boyCount = 0
        var girlCount = 0

        sequence.take(8).forEachIndexed { index, gender ->
            if (gender == WinningGender.BOY) boyCount++ else girlCount++
            val hidden = 8 - index
            assertTrue(kotlin.math.abs(boyCount - girlCount) <= hidden)
        }

        assertEquals(4, boyCount)
        assertEquals(4, girlCount)
        assertEquals(WinningGender.GIRL, sequence.last())
    }

    @Test
    fun createRevealSequence_canProduceVisibleRunsInsteadOfStrictAlternation() {
        val hasRun = (0..40).any { seed ->
            val sequence = GameEngine.createRevealSequence(WinningGender.BOY, Random(seed))
            sequence.windowed(2).any { it[0] == it[1] }
        }

        assertTrue(hasRun)
    }

    @Test
    fun revealCard_neverCelebratesBeforeNinthCard() {
        val setup = testSetup(WinningGender.GIRL)
        var state = GameEngine.startGame(setup, Random(3))

        repeat(8) { index ->
            state = GameEngine.revealCard(state, state.cards[index].id)
            assertEquals(index + 1, state.revealedCount)
            assertFalse(state.celebrationVisible)
        }
    }

    @Test
    fun revealCard_celebratesExactlyOnNinthCard() {
        val setup = testSetup(WinningGender.BOY)
        var state = GameEngine.startGame(setup, Random(5))

        state.cards.forEachIndexed { index, card ->
            state = GameEngine.revealCard(state, card.id)
            if (index < 8) {
                assertFalse(state.celebrationVisible)
            }
        }

        assertEquals(9, state.revealedCount)
        assertTrue(state.celebrationVisible)
        assertTrue(state.cards.all { it.isRevealed })
        assertEquals(WinningGender.BOY, state.revealedGenders.last())
    }

    @Test
    fun revealCard_ignoresRepeatedTapsOnSameCard() {
        val setup = testSetup(WinningGender.GIRL)
        val initialState = GameEngine.startGame(setup, Random(9))

        val firstReveal = GameEngine.revealCard(initialState, initialState.cards.first().id)
        val repeatedReveal = GameEngine.revealCard(firstReveal, firstReveal.cards.first().id)

        assertEquals(1, firstReveal.revealedCount)
        assertEquals(firstReveal, repeatedReveal)
    }

    @Test
    fun revealCard_assignsGenderOnlyWhenCardIsRevealed() {
        val setup = testSetup(WinningGender.BOY)
        val initialState = GameEngine.startGame(setup, Random(12))

        assertTrue(initialState.cards.all { it.gender == null && !it.isRevealed })

        val revealedState = GameEngine.revealCard(initialState, initialState.cards[4].id)

        assertTrue(revealedState.cards[4].isRevealed)
        assertTrue(revealedState.cards[4].gender != null)
        assertEquals(1, revealedState.revealedGenders.size)
    }

    @Test
    fun revealCard_keepsBalancedCountsEvenWhenCardsAreTappedOutOfOrder() {
        val setup = testSetup(WinningGender.GIRL)
        var state = GameEngine.startGame(setup, Random(1))
        val tapOrder = listOf(8, 2, 5, 0, 7, 1, 6, 3)

        tapOrder.forEachIndexed { index, cardId ->
            state = GameEngine.revealCard(state, cardId)
            val hidden = state.hiddenCount
            assertTrue(
                kotlin.math.abs(state.boyRevealedCount - state.girlRevealedCount) <= hidden
            )
            if (index == 7) {
                assertEquals(4, state.boyRevealedCount)
                assertEquals(4, state.girlRevealedCount)
                assertFalse(state.celebrationVisible)
            }
        }

        state = GameEngine.revealCard(state, 4)
        assertEquals(WinningGender.GIRL, state.revealedGenders.last())
        assertTrue(state.celebrationVisible)
    }

    private fun testSetup(gender: WinningGender): RevealSetupConfig {
        return RevealSetupConfig(
            winningGender = gender,
            boardHeaderMessage = RevealMessageConfig("Board title", "Board subtitle"),
            celebrationMessage = RevealMessageConfig("Title", "Subtitle"),
            theme = RevealCatalog.themes.first()
        )
    }
}
