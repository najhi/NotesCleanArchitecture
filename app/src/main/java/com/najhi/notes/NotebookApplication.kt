package com.najhi.notes

import android.app.Application
import com.najhi.notes.di.appModule
import com.najhi.notes.di.dataStoreModule
import com.najhi.notes.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 05/08/2024
 */
class NotebookApplication : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        startKoin {
            androidContext(this@NotebookApplication)
            androidLogger()
            modules(appModule, repositoryModule, dataStoreModule)
        }
    }
}