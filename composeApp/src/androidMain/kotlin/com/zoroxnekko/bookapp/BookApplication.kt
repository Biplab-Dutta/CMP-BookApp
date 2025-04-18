package com.zoroxnekko.bookapp

import android.app.Application
import com.zoroxnekko.bookapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookApplication)
        }
    }
}