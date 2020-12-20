package com.eneskayiklik.postit.di

import android.content.Context
import androidx.room.Room
import com.eneskayiklik.postit.db.dao.BirthdayDao
import com.eneskayiklik.postit.db.database.BirthdayDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object BirthdayModule {
    @Singleton
    @Provides
    fun provideBirthdayDatabase(
        @ApplicationContext context: Context
    ): BirthdayDatabase =
        Room.databaseBuilder(context, BirthdayDatabase::class.java, BirthdayDatabase.DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideDao(
        birthdayDatabase: BirthdayDatabase
    ): BirthdayDao = birthdayDatabase.getDao()
}