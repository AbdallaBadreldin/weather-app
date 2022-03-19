package eg.iti.weatherapp.main.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.LocaleList
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.ConfigurationCompat
import androidx.core.text.layoutDirection
import androidx.preference.PreferenceManager
import eg.iti.weatherapp.R
import java.util.*

class LocaleUtil  {
    companion object {
        val supportedLocales = listOf("en", "ar")
        const val OPTION_PHONE_LANGUAGE = "sys_def"

        /**
         * returns the locale to use depending on the preference value
         * when preference value = "sys_def" returns the locale of current system
         * else it returns the locale code e.g. "en", "bn" etc.
         */
        fun getLocaleFromPrefCode(prefCode: String): Locale {
            val localeCode = if(prefCode != OPTION_PHONE_LANGUAGE) {
                prefCode
            } else {
                val systemLang = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).language
                if(systemLang in supportedLocales){
                    systemLang
                } else {
                    "en"
                }
            }
            return Locale(localeCode)
        }

        fun getLocalizedConfiguration(prefLocaleCode: String): Configuration {
            val locale = getLocaleFromPrefCode(prefLocaleCode)
            return getLocalizedConfiguration(locale)
        }

        fun getLocalizedConfiguration(locale: Locale): Configuration {
            val config = Configuration()
            return config.apply {
                config.setLayoutDirection(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    config.setLocale(locale)
                    val localeList = LocaleList(locale)
                    LocaleList.setDefault(localeList)
                    config.setLocales(localeList)
                } else {
                    config.setLocale(locale)
                }
            }
        }

        fun getLocalizedContext(baseContext: Context, prefLocaleCode: String): Context {
            val currentLocale = getLocaleFromPrefCode(prefLocaleCode)
            val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration)
            Locale.setDefault(currentLocale)
            return if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) {
                val config = getLocalizedConfiguration(currentLocale)
                baseContext.createConfigurationContext(config)
                baseContext
            } else {
                baseContext
            }
        }

        fun applyLocalizedContext(activity : Activity, prefLocaleCode: String) {
            var baseContext =activity.baseContext
            val currentLocale = getLocaleFromPrefCode(prefLocaleCode)
            val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration)
            Locale.setDefault(currentLocale)
            if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) {
                val config = getLocalizedConfiguration(currentLocale)
                baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
            }
            activity.window?.decorView?.layoutDirection = Locale.getDefault().layoutDirection
        }

        @Suppress("DEPRECATION")
        private fun getLocaleFromConfiguration(configuration: Configuration): Locale {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                configuration.locales.get(0)
            } else {
                configuration.locale
            }
        }

        fun getLocalizedResources(resources: Resources, prefLocaleCode: String): Resources {
            val locale = getLocaleFromPrefCode(prefLocaleCode)
            val config = resources.configuration
            @Suppress("DEPRECATION")
            config.locale = locale
            config.setLayoutDirection(locale)

            @Suppress("DEPRECATION")
            resources.updateConfiguration(config, resources.displayMetrics)
            return resources
        }

        fun translateEnglishToArabic( str :String,context: Context) :String{
            var result = ""
            var en = '0'
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
              if(context.getResources().getConfiguration().getLocales().get(0)!=Locale(context.getString(R.string.pref_ar)))
                  return str
            } else{
//                noinspection deprecation
                if(context.getResources().getConfiguration().locale!=Locale(context.getString(R.string.pref_ar)))
                    return str
            }
                for (ch in str) {
                    en = ch
                    when (ch) {
                        '0' -> en = '۰'
                        '1' -> en = '۱'
                        '2' -> en = '۲'
                        '3' -> en = '۳'
                        '4' -> en = '٤'
                        '5' -> en = '٥'
                        '6' -> en = '٦'
                        '7' -> en = '٧'
                        '8' -> en = '۸'
                        '9' -> en = '۹'
                        '.' -> en = ','
                    }
                    result = "${result}$en"
                }
            return result
        }

         fun getCityName(lat: Double, lon: Double ,context: Context ): String {
            var city :String
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)
            if (addresses.isNotEmpty()) {
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                city = "$state / $country"
                return city
            }
             else return context.getString(R.string.def)

        }
    }
}
