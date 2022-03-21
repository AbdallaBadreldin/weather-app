package eg.iti.weatherapp.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.text.layoutDirection
import androidx.preference.PreferenceManager
import eg.iti.weatherapp.R
import eg.iti.weatherapp.databinding.ActivityMainBinding
import eg.iti.weatherapp.main.utils.LocaleUtil
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
private lateinit  var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =PreferenceManager.getDefaultSharedPreferences(applicationContext)

        if(sharedPreferences.getString(getString(R.string.preference_language),getString(R.string.def))==getString(R.string.def)) {
            var editor =sharedPreferences.edit()
            editor.putString(getString(R.string.preference_language),Locale.getDefault().language)
            editor.putString(getString(R.string.preference_windSpeed),getString(R.string.pref_meterPerSecond))
            editor.apply()
        }
        else {
            LocaleUtil.applyLocalizedContext(
                this, sharedPreferences.getString(
                    getString(
                        R.string.preference_language
                    ), getString(R.string.pref_en)
                ).toString()
            )
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}