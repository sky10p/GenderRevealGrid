package sky.programs.genderrevealgrid

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import java.util.Locale

object AppLocaleManager {
    @Volatile
    var overrideLanguageTag: String? = null

    fun wrap(context: Context): Context {
        val languageTag = overrideLanguageTag ?: return context
        val locale = Locale.forLanguageTag(languageTag)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocales(LocaleList(locale))
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }
}
