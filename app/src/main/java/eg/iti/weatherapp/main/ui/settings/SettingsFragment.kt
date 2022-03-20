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

        if (p1 == getString(R.string.preference_language)) {
            LocaleUtil.applyLocalizedContext(requireActivity(),p0?.getString(p1, getString(R.string.pref_en)).toString())
        }
        (activity as? MainActivity)?.recreate()


    }
}

