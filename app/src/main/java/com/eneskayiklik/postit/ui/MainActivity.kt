package com.eneskayiklik.postit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eneskayiklik.postit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_layout.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val navController by lazy {
        findNavController(R.id.fragmentNavHost)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
        setOnDestinationChange()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, drawerLayout)
        drawerMenuMain.setupWithNavController(navController)
    }

    private fun setOnDestinationChange() {
        findNavController(R.id.fragmentNavHost).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.addNoteFragment -> drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                else -> drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }
}