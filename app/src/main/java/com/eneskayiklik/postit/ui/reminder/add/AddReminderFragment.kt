package com.eneskayiklik.postit.ui.reminder.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.db.entity.Reminder
import com.eneskayiklik.postit.ui.reminder.ReminderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_reminder_header.*
import kotlinx.android.synthetic.main.add_reminder_settings.*
import kotlinx.android.synthetic.main.fragment_add_reminder.*

@AndroidEntryPoint
class AddReminderFragment : Fragment(R.layout.fragment_add_reminder) {
    private val birthdayViewModel by viewModels<ReminderViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupButtonsOnClick()
    }

    private fun setupButtonsOnClick() {
        btnSelectReminderTime.setOnClickListener {
            Toast.makeText(this.requireContext(), "Select Time", Toast.LENGTH_SHORT).show()
        }

        btnShowReminder.setOnClickListener {
            Toast.makeText(this.requireContext(), "Show Reminder", Toast.LENGTH_SHORT).show()
        }

        switchEnableNotification.setOnClickListener {
            Toast.makeText(
                this.requireContext(),
                "${switchEnableNotification.isChecked}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_reminder_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.addBirthdayNote -> {
            saveBirthday()
            true
        }
        else -> false
    }

    private fun saveBirthday() {
        birthdayViewModel.addBirthday(
            Reminder(
                0,
                edtReminderTitle.text.toString(),
                edtReminderDesc.text.toString()
            )
        )
        this.requireActivity().onBackPressed()
    }


    override fun onPause() {
        super.onPause()
        rootLayout.clearFocus()
    }
}