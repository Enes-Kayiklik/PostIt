package com.eneskayiklik.postit.di

import android.content.Context
import androidx.room.Room
import com.eneskayiklik.postit.db.dao.ReminderDao
import com.eneskayiklik.postit.db.database.ReminderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ReminderModule {
    @Singleton
    @Provides
    fun provideReminderDatabase(
        @ApplicationContext context: Context
    ): ReminderDatabase =
        Room.databaseBuilder(context, ReminderDatabase::class.java, ReminderDatabase.DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideDao(
        reminderDatabase: ReminderDatabase
    ): ReminderDao = reminderDatabase.getDao()
}