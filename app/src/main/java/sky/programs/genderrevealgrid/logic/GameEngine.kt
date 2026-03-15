package sky.programs.genderrevealgrid.logic

import kotlin.random.Random
import sky.programs.genderrevealgrid.model.GameState
import sky.programs.genderrevealgrid.model.RevealCard
import sky.programs.genderrevealgrid.model.RevealSetupConfig
import sky.programs.genderrevealgrid.model.WinningGender
import sky.programs.genderrevealgrid.model.opposite

object GameEngine {
    fun createRevealSequence(
        winner: WinningGender,
        random: Random = Random.Default
    ): List<WinningGender> {
        val winnerTarget = 5
        val loserTarget = 4
        val loser = winner.opposite()

        fun buildSequence(
            revealedSoFar: List<WinningGender>,
            winnerUsed: Int,
            loserUsed: Int
        ): List<WinningGender>? {
            if (revealedSoFar.size == 9) {
                return if (
                    winnerUsed == winnerTarget &&
                    loserUsed == loserTarget &&
                    revealedSoFar.lastOrNull() == winner
                ) {
                    revealedSoFar
                } else {
                    null
                }
            }

            val candidateOrder = if (random.nextBoolean()) {
                listOf(winner, loser)
            } else {
                listOf(loser, winner)
            }

            candidateOrder.forEach { candidate ->
                val nextWinnerUsed = winnerUsed + if (candidate == winner) 1 else 0
                val nextLoserUsed = loserUsed + if (candidate == loser) 1 else 0
                if (nextWinnerUsed > winnerTarget || nextLoserUsed > loserTarget) return@forEach

                val nextSequence = revealedSoFar + candidate
                val boysRevealed = nextSequence.count { it == WinningGender.BOY }
                val girlsRevealed = nextSequence.size - boysRevealed
                val hidden = 9 - nextSequence.size

                if (hidden > 0 && kotlin.math.abs(boysRevealed - girlsRevealed) > hidden) {
                    return@forEach
                }

                val remainingWinner = winnerTarget - nextWinnerUsed
                val remainingLoser = loserTarget - nextLoserUsed
                if (hidden != remainingWinner + remainingLoser) {
                    return@forEach
                }

                val result = buildSequence(nextSequence, nextWinnerUsed, nextLoserUsed)
                if (result != null) return result
            }

            return null
        }

        return buildSequence(emptyList(), 0, 0)
            ?: error("Unable to generate a valid reveal sequence for $winner")
    }

    fun startGame(
        setup: RevealSetupConfig,
        random: Random = Random.Default
    ): GameState {
        val revealSequence = createRevealSequence(setup.winningGender, random)
        val cards = List(revealSequence.size) { index ->
            RevealCard(id = index)
        }
        return GameState(
            cards = cards,
            revealSequence = revealSequence
        )
    }

    fun revealCard(state: GameState, cardId: Int): GameState {
        if (state.celebrationVisible) return state

        val index = state.cards.indexOfFirst { it.id == cardId }
        if (index == -1) return state

        val target = state.cards[index]
        if (target.isRevealed) return state

        val nextGender = state.revealSequence.getOrNull(state.revealedCount) ?: return state
        val updatedCards = state.cards.toMutableList()
        updatedCards[index] = target.copy(
            gender = nextGender,
            isRevealed = true
        )
        val revealedCount = state.revealedCount + 1

        return state.copy(
            cards = updatedCards,
            revealedGenders = state.revealedGenders + nextGender,
            revealedCount = revealedCount,
            celebrationVisible = revealedCount == updatedCards.size
        )
    }
}
