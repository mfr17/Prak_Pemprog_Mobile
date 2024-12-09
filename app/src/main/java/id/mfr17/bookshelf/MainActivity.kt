package id.mfr17.bookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import id.mfr17.bookshelf.data.BookShelfRepository
import id.mfr17.bookshelf.ui.BookShelfApp
import id.mfr17.bookshelf.ui.theme.BookShelfTheme

class MainActivity : ComponentActivity() {
    // Kontainer yang digunakan untuk menyediakan dependensi
    private lateinit var bookShelfRepository: BookShelfRepository
    // Fungsi yang dipanggil saat aktivitas dijalankan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as BookShelfApplication
        bookShelfRepository = app.container.bookShelfRepository
        setContent {
            BookShelfTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Menampilkan BookShelfApp dengan menyediakan dependensi BookShelfRepository
                    BookShelfApp(bookShelfRepository)
                }
            }
        }
    }
}
