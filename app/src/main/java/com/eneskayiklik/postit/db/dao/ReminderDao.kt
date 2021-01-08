package com.eneskayiklik.postit.db.dao

import com.eneskayiklik.postit.db.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("Select * From reminder")
    fun getAllBirthday(): Flow<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReminder(data: Reminder)

    @Query("Delete From reminder")
    fun deleteAllReminders()
}