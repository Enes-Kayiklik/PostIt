package com.eneskayiklik.post_it.db

import android.content.Context
import androidx.room.Room
import com.eneskayiklik.post_it.db.NoteDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NoteModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideDao(noteDatabase: NoteDatabase): NoteDao =
        noteDatabase.getDao()
}