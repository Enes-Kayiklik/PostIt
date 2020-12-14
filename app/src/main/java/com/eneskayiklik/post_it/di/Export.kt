package com.eneskayiklik.post_it.di

import android.content.Context
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.helper.Exporter
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
        @ApplicationContext context: Context
    ): Exporter = Exporter(context)

    @Provides
    fun providesRes(
        @ApplicationContext context: Context
    ): String = context.resources.getString(R.string.export_file_name)
}