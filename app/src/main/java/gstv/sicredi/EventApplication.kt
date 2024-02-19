package gstv.sicredi

import android.app.Application
import android.content.Context
import gstv.sicredi.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        startKoin {
            androidContext(this@AppApplication)
            modules(appModule)
        }
    }
}