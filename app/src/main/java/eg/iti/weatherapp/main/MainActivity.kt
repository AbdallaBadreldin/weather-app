package eg.iti.weatherapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.layoutDirection
import androidx.preference.PreferenceManager
import eg.iti.weatherapp.R
import eg.iti.weatherapp.databinding.ActivityMainBinding
import eg.iti.weatherapp.main.utils.LocaleUtil
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocaleUtil.applyLocalizedContext(this,PreferenceManager.getDefaultSharedPreferences(applicationContext)?.getString(getString(
            R.string.preference_language), getString(R.string.pref_en)).toString())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}