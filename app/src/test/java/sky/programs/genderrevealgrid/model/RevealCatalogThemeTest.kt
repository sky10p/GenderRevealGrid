package sky.programs.genderrevealgrid.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import sky.programs.genderrevealgrid.R

class RevealCatalogThemeTest {
    @Test
    fun premiumThemes_areSuperheroAndMagicWorld() {
        val premiumThemes = RevealCatalog.themes.filter { it.isPremium }

        assertEquals(listOf("superhero", "magic-world"), premiumThemes.map { it.id })
        assertEquals(
            listOf(R.string.theme_name_superhero, R.string.theme_name_magic_world),
            premiumThemes.map { it.nameResId }
        )
    }

    @Test
    fun premiumThemes_exposeThemeSpecificDefaultMessages() {
        val superhero = RevealCatalog.themeById("superhero")
        val magicWorld = RevealCatalog.themeById("magic-world")

        assertEquals(R.string.default_superhero_board_title, superhero.defaultMessages.boardTitleResId)
        assertEquals(R.string.default_superhero_girl_subtitle, superhero.defaultMessages.girlCelebrationSubtitleResId)
        assertEquals(R.string.default_magic_world_board_title, magicWorld.defaultMessages.boardTitleResId)
        assertEquals(R.string.default_magic_world_boy_title, magicWorld.defaultMessages.boyCelebrationTitleResId)
        assertTrue(superhero.icon == ThemeIcon.SUPERHERO_BADGE)
        assertTrue(magicWorld.icon == ThemeIcon.MAGIC_WAND)
    }
}
