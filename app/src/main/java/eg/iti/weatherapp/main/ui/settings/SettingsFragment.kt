package eg.iti.weatherapp.main.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.layoutDirection
import androidx.preference.PreferenceFragmentCompat
import eg.iti.weatherapp.R
import eg.iti.weatherapp.main.MainActivity
import eg.iti.weatherapp.main.utils.LocaleUtil
import java.util.*


class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        LocaleUtil.applyLocalizedContext(requireActivity(),preferenceManager.sharedPreferences?.getString(getString(R.string.preference_language), getString(R.string.pref_en)).toString())
        setPreferencesFromResource(R.xml.root_preferences, rootKey)


    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
//        if( == "en") {
//            PreferencesHelper.writeLanguageCode("he")
//        } else {
//            PreferencesHelper.writeLanguageCode("en")
//        }
//        (activity as? MainActivity)?.recreate()

        if (p1 == getString(R.string.preference_language)) {
//            val myLocale = Locale(p0?.getString(p1, "en"))
//            val res = resources
//            val dm = res.displayMetrics
//            val conf = res.configuration
//            conf.locale = myLocale
//            res.updateConfiguration(conf, dm)
//            (activity as? MainActivity)?.recreate()
            LocaleUtil.applyLocalizedContext(requireActivity(),p0?.getString(p1, getString(R.string.pref_en)).toString())
            (activity as? MainActivity)?.recreate()
//            activity?.window?.decorView?.layoutDirection = Locale.getDefault().layoutDirection
        }


    }
}

