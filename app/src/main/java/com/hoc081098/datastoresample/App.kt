package com.hoc081098.datastoresample

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Locator.initWith(this)
    }
}