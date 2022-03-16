package eg.iti.weatherapp.main.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import eg.iti.weatherapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}