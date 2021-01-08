package com.eneskayiklik.postit.ui.reminder.add

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.db.entity.Reminder
import com.eneskayiklik.postit.ui.reminder.ReminderViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.fragment_add_reminder.*

@AndroidEntryPoint
class AddReminderFragment : Fragment(R.layout.fragment_add_reminder) {
    private val birthdayViewModel by viewModels<ReminderViewModel>()
    private var selectedColor = Color.WHITE
    private val navArgs by navArgs<AddReminderFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupData()
        setupButtonsOnClick()
    }

    private fun setupData() {
        navArgs.currentReminder?.let {
            edtReminderTitle.setText(it.title)
            edtReminderDetail.setText(it.desc)
            rootLayout.setBackgroundColor(it.reminderColor)
        }
    }

    private fun setupButtonsOnClick() {

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
        R.id.addReminder -> {
            addReminder()
            true
        }
        R.id.addColor -> {
            pickColor()
            true
        }
        else -> false
    }

    private fun pickColor() {
        val colorList = intArrayOf(
            ContextCompat.getColor(this.requireContext(), R.color.colorNoteBackBlue),
            ContextCompat.getColor(this.requireContext(), R.color.colorNoteBackPink),
            ContextCompat.getColor(this.requireContext(), R.color.colorNoteBackYellow),
            ContextCompat.getColor(this.requireContext(), R.color.colorNoteBackPurple),
            ContextCompat.getColor(this.requireContext(), R.color.colorNoteBackOrange),
            ContextCompat.getColor(this.requireContext(), R.color.colorNoteBackCyan),
            ContextCompat.getColor(this.requireContext(), R.color.colorNoteBackRed)
        )

        ColorSheet().colorPicker(
            colorList,
            noColorOption = true
        ) {
            rootLayout.setBackgroundColor(it)
            selectedColor = it
        }.show(this.requireActivity().supportFragmentManager)
    }

    private fun addReminder() {

    }

    private fun saveBirthday() {
        birthdayViewModel.addReminder(
            Reminder(
                navArgs.currentReminder?.id ?: 0,
                edtReminderTitle.text.toString(),
                edtReminderDetail.text.toString(),
                selectedColor
            )
        )
        this.requireActivity().onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        rootLayout.clearFocus()
    }
}