package com.eneskayiklik.postit.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eneskayiklik.postit.db.entity.Birthday
import kotlinx.coroutines.flow.Flow

@Dao
interface BirthdayDao {
    @Query("Select * From birthday")
    fun getAllBirthday(): Flow<List<Birthday>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBirthday(data: Birthday)
}