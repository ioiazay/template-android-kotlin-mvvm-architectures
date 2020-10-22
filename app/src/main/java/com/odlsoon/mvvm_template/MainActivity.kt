package com.odlsoon.mvvm_template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}
