package id.mfr17.bookshelf

import android.app.Application
import id.mfr17.bookshelf.data.AppContainer
import id.mfr17.bookshelf.data.DefaultAppContainer

class BookShelfApplication: Application() {
    lateinit var container: AppContainer // Corrected 'lateinit' instead of 'lateint'

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer() // Ensure container is initialized here
    }
}