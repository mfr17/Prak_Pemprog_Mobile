package id.mfr17.bookshelf.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import id.mfr17.bookshelf.R
import id.mfr17.bookshelf.data.BookShelfRepository
import id.mfr17.bookshelf.ui.screen.BookViewModel
import id.mfr17.bookshelf.ui.screen.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
// Fungsi untuk menampilkan BookShelfApp
@Composable
fun BookShelfApp(bookShelfRepository: BookShelfRepository) {
    // Scaffold untuk menampilkan layout utama aplikasi
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        // TopBar untuk menampilkan judul aplikasi
        topBar = {
            TopAppBar(
                title = {
                    // Menampilkan judul aplikasi dari string resources
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        }
    ) {
        // Surface untuk menampilkan konten utama aplikasi
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // ViewModel untuk BookShelf
            val bookViewModel: BookViewModel = viewModel(factory = BookViewModel.provideFactory(bookShelfRepository))
            // Menampilkan konten utama aplikasi dari HomeScreen
            HomeScreen(
                // State dari ViewModel
                bookShelfUiState = bookViewModel.bookShelfUiState,
                retryAction = bookViewModel::getBooks,
                contentPadding = it
            )
        }
    }
}