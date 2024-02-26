package gstv.sicredi.view

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import gstv.sicredi.core.di.appModule
import gstv.sicredi.core.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EventApplication : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        startKoin {
            androidContext(this@EventApplication)
            modules(appModule, retrofitModule)
        }
    }
}