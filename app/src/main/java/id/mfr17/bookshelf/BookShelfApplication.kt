package id.mfr17.bookshelf

import android.app.Application
import id.mfr17.bookshelf.data.AppContainer
import id.mfr17.bookshelf.data.DefaultAppContainer

// Kelas BookShelfApplication yang mewarisi Application
class BookShelfApplication: Application() {
    // Kontainer yang digunakan untuk menyediakan dependensi
    lateinit var container: AppContainer
    // Fungsi yang dipanggil saat aplikasi dijalankan
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}