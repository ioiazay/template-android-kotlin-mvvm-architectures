package com.odlsoon.mvvm_template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.odlsoon.mvvm_template.arch.DataActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListeners()
    }

    private fun initListeners(){
        button_data.setOnClickListener {
            DataActivity.startActivity(this)
        }

        var isDarkMode = false
        button_dark_mode.setOnClickListener {
            if(isDarkMode){
                isDarkMode = false
                button_dark_mode.text = getString(R.string.dark_mode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }else{
                isDarkMode = true
                button_dark_mode.text = getString(R.string.sun_mode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}
