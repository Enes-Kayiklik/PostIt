package com.eneskayiklik.post_it.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.helper.Exporter
import com.eneskayiklik.post_it.ui.main.notes.NoteViewModel
import com.eneskayiklik.post_it.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val noteViewModel by viewModels<NoteViewModel>()

    @Inject
    lateinit var exporter: Exporter

    @Inject
    lateinit var fileName: String
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

    private fun startImportDataIntent() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
        }
        startActivityForResult(intent, Constants.READ_INTENT)
    }

    private fun startExportDataIntent() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            type = "text/json"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_TITLE, fileName)
        }
        startActivityForResult(intent, Constants.WRITE_INTENT)
    }

    private fun setOnDestinationChange() {
        findNavController(R.id.fragmentNavHost).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.addNoteFragment -> drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                else -> drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constants.WRITE_INTENT -> {
                    noteViewModel.notes.observe(this, Observer { note ->
                        data?.data?.let { exporter.exportData(it, note, fileName) }
                    })
                }
                Constants.READ_INTENT -> {
                    data?.data?.let {
                        exporter.importData(it)?.forEach { note ->
                            noteViewModel.addNote(note)
                            drawerLayout.closeDrawer(GravityCompat.START)
                        }
                    }
                }
            }
        }
    }
}