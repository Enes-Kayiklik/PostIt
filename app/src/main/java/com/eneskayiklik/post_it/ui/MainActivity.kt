package com.eneskayiklik.post_it.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eneskayiklik.post_it.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}