package com.app.template_compose_mvvm

import android.app.Application
import com.app.template_compose_mvvm.data.source.remote.network.networkModule
import com.app.template_compose_mvvm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    appModule,
                    networkModule
                )
            )
        }
    }
}