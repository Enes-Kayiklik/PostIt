package com.eneskayiklik.postit.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.helper.Exporter
import com.eneskayiklik.postit.ui.main.notes.NoteViewModel
import com.eneskayiklik.postit.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    @Inject
    lateinit var exporter: Exporter
    private val noteViewModel by viewModels<NoteViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exportData: Preference? = findPreference(this.getString(R.string.export_key))
        val importData: Preference? = findPreference(this.getString(R.string.import_key))
        val darkModeSwitch: SwitchPreference? =
            findPreference(this.getString(R.string.dark_mode_key))

        exportData?.setOnPreferenceClickListener {
            startExportDataIntent()
            true
        }

        importData?.setOnPreferenceClickListener {
            startImportDataIntent()
            true
        }

        /*darkModeSwitch?.setOnPreferenceClickListener {
            if (darkModeSwitch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }*/
    }

    private fun startImportDataIntent() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            startActivityForResult(this, Constants.READ_INTENT)
        }
    }

    private fun startExportDataIntent() {
        Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            type = "text/json"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_TITLE, exporter.name)
            startActivityForResult(this, Constants.WRITE_INTENT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constants.WRITE_INTENT -> noteViewModel.notes.observe(this, Observer { note ->
                    data?.data?.let { exporter.exportData(it, note) }
                })

                Constants.READ_INTENT -> data?.data?.let {
                    exporter.importData(it)?.forEach { note ->
                        noteViewModel.addNote(note)
                    }
                }
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
    }
}