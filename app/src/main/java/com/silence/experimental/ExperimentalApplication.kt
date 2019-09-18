package com.silence.experimental

import android.app.Application
import com.silence.experimental.common.di.AppComponent
import com.silence.experimental.common.di.AppModule
import com.silence.experimental.common.di.DaggerAppComponent
import com.silence.experimental.common.di.NetworkModule

class ExperimentalApplication : Application() {

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}