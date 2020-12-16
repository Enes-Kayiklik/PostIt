package com.eneskayiklik.postit.di

import android.content.Context
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.helper.Exporter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object Export {

    @Provides
    @Singleton
    fun provideExporter(
        @ApplicationContext context: Context,
        fileName: String
    ): Exporter = Exporter(context, fileName)

    @Provides
    fun providesRes(
        @ApplicationContext context: Context
    ): String = context.resources.getString(R.string.export_file_name)
}