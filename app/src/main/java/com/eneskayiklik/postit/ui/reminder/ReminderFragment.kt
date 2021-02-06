package com.eneskayiklik.postit.ui.reminder

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.db.entity.Reminder
import com.eneskayiklik.postit.util.extensions.hide
import com.eneskayiklik.postit.util.extensions.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reminder.*

@AndroidEntryPoint
class ReminderFragment : Fragment(R.layout.fragment_reminder) {
    private val reminderClickListener: (Reminder) -> Unit = {
        findNavController().navigate(
            ReminderFragmentDirections.actionReminderFragmentToAddReminderFragment(
                it
            )
        )
    }
    private val reminderAdapter = ReminderAdapter(this.reminderClickListener)
    private val reminderViewModel by viewModels<ReminderViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupObserver()
        setupButtonsOnClick()
        recyclerViewReminders.apply {
            adapter = reminderAdapter
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private fun setupButtonsOnClick() {
        btnAddReminder.setOnClickListener {
            findNavController().navigate(R.id.action_reminderFragment_to_addReminderFragment)
        }
    }

    private fun setupObserver() {
        reminderViewModel.birthdays.observe(this.viewLifecycleOwner, Observer {
            reminderAdapter.submitList(it)
            if (it.isNotEmpty())
                emptyReminder.hide()
            else emptyReminder.show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.reminder_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.deleteAllReminders -> {
            deleteAllReminders()
            true
        }
        else -> false
    }

    private fun deleteAllReminders() {
        reminderViewModel.deleteAllReminders()
    }
}