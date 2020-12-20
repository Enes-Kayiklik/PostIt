package com.eneskayiklik.postit.ui.birthday

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.postit.db.dao.BirthdayDao
import com.eneskayiklik.postit.db.entity.Birthday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BirthdayViewModel @ViewModelInject constructor(
    private val birthdayDao: BirthdayDao
) : ViewModel() {
    private val _birthdays = birthdayDao.getAllBirthday()
    val birthdays = _birthdays.asLiveData()

    fun addBirthday(birthday: Birthday) = viewModelScope.launch(Dispatchers.IO) {
        birthdayDao.addBirthday(birthday)
    }
}