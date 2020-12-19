package com.eneskayiklik.postit.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.DropDownPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.helper.Exporter
import com.eneskayiklik.postit.ui.main.notes.NoteViewModel
import com.eneskayiklik.postit.util.Constants
import com.eneskayiklik.postit.util.ContextUtils.Companion.changeLanguage
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
        /*val darkModeSwitch: SwitchPreference? =
            findPreference(this.getString(R.string.dark_mode_key))*/
        val language: DropDownPreference? = findPreference(this.getString(R.string.language_key))

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

        language?.setOnPreferenceChangeListener { _, newValue ->
            when (newValue) {
                "Türkçe" -> changeLanguage(
                    "tr",
                    this.requireContext()
                ).also { this.requireActivity().recreate() }
                "English" -> changeLanguage(
                    "en",
                    this.requireContext()
                ).also { this.requireActivity().recreate() }
            }
            true
        }

        findPreference<Preference?>(resources.getString(R.string.share_this_app_key))?.setOnPreferenceClickListener {
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_text))
                type = "text/plain"
                startActivity(this)
            }
            true
        }

        findPreference<Preference?>(resources.getString(R.string.contact_us_key))?.setOnPreferenceClickListener {
            startSendEmailIntent()
            true
        }

        findPreference<Preference?>(resources.getString(R.string.send_feedback_key))?.setOnPreferenceClickListener {
            startSendEmailIntent()
            true
        }

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

    private fun startSendEmailIntent() {
        Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_mail)))
            type = "message/rfc822"
            startActivity(this)
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