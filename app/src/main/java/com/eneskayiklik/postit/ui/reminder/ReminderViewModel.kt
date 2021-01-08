package com.eneskayiklik.postit.ui.reminder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.postit.db.dao.ReminderDao
import com.eneskayiklik.postit.db.entity.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderViewModel @ViewModelInject constructor(
    private val reminderDao: ReminderDao
) : ViewModel() {
    private val _birthdays = reminderDao.getAllBirthday()
    val birthdays = _birthdays.asLiveData()

    fun addReminder(reminder: Reminder) = viewModelScope.launch(Dispatchers.IO) {
        reminderDao.addReminder(reminder)
    }

    fun deleteAllReminders() = viewModelScope.launch(Dispatchers.IO) {
        reminderDao.deleteAllReminders()
    }
}