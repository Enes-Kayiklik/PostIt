package com.eneskayiklik.postit.ui

import android.preference.PreferenceManager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.base.BaseActivity
import com.eneskayiklik.postit.databinding.ActivityMainBinding
import com.eneskayiklik.postit.util.ContextUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val navController by lazy {
        findNavController(R.id.fragmentNavHost)
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initLayout() {
        setupLanguage()
        setupActionBar()
        setOnDestinationChange()
    }

    private fun setupActionBar() {
        binder.apply {
            setSupportActionBar(mainAppBarLayout.toolbar)
            mainAppBarLayout.toolbar.setupWithNavController(navController, drawerLayout)
            drawerMenuMain.setupWithNavController(navController)
        }
    }

    private fun setOnDestinationChange() {
        findNavController(R.id.fragmentNavHost).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.addNoteFragment -> binder.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                else -> binder.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }

    private fun setupLanguage() {
        val language = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(this.getString(R.string.language_key), "")
        when (language) {
            "Türkçe" -> ContextUtils.changeLanguage("tr", this)
            "English" -> ContextUtils.changeLanguage("en", this)
        }
    }
}