package com.example.wegotnext

import android.app.Application
import android.content.Context
import com.example.wegotnext.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeGotNextApp : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        startKoin {
            androidContext(this@WeGotNextApp)
            modules(viewModelModule)
        }
    }
}