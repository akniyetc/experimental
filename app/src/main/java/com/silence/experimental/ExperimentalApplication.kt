package com.silence.experimental

import android.app.Application
import com.silence.experimental.common.di.*

class ExperimentalApplication : Application() {

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}