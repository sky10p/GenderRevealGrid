package sky.programs.genderrevealgrid

import java.util.Locale
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class LocaleTestRule(private val languageTag: String) : TestWatcher() {
    private var originalLanguageTag: String? = null

    override fun starting(description: Description) {
        originalLanguageTag = AppLocaleManager.overrideLanguageTag
        AppLocaleManager.overrideLanguageTag = languageTag
        Locale.setDefault(Locale.forLanguageTag(languageTag))
    }

    override fun finished(description: Description) {
        AppLocaleManager.overrideLanguageTag = originalLanguageTag
        Locale.setDefault(
            originalLanguageTag?.let(Locale::forLanguageTag) ?: Locale.getDefault()
        )
    }
}
