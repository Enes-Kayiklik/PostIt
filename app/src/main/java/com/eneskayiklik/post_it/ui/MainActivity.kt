package com.eneskayiklik.post_it.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.util.makeInvisible
import com.eneskayiklik.post_it.util.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_layout.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setOnDestinationChange()
        setupButtonsOnClick()
        setupActionBarWithNavController(findNavController(R.id.fragmentNavHost))
    }

    private fun setupButtonsOnClick() {
        imgDrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        drawerMenuMain.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.exportData -> Toast.makeText(this, "Selam", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Naber", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    private fun setOnDestinationChange() {
        findNavController(R.id.fragmentNavHost).addOnDestinationChangedListener { _, destination, _ ->
            toolbarTitle.text = destination.label
            when (destination.id) {
                R.id.addNoteFragment -> {
                    drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                        .also { imgDrawer.makeInvisible() }
                }
                else -> {
                    drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                        .also { imgDrawer.makeVisible() }
                }
            }
        }
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.fragmentNavHost).navigateUp() || super.onSupportNavigateUp()
}