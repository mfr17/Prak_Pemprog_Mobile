package id.mfr17.bookshelf.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import id.mfr17.bookshelf.data.BookShelfRepository
import id.mfr17.bookshelf.model.BookShelf
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Sealed interface untuk state UI
sealed interface BookUiState {
    data class Success(val books: List<BookShelf>) : BookUiState
    object Error : BookUiState
    object Loading : BookUiState
}

// ViewModel untuk BookShelf
class BookViewModel(private val bookShelfRepository: BookShelfRepository) : ViewModel() {
    var bookShelfUiState: BookUiState by mutableStateOf(BookUiState.Loading)
        private set
    // Fungsi untuk mendapatkan daftar buku
    init {
        getBooks()
    }
    // Fungsi untuk mendapatkan daftar buku dari repository
    fun getBooks(){
        viewModelScope.launch {
            // Mengubah state UI menjadi Loading sebelum melakukan request
            bookShelfUiState = BookUiState.Loading
            // Mengubah state UI menjadi Success jika request berhasil dengan query "jazz history" atau mengembalikan Error jika terjadi kesalahan
            bookShelfUiState = try {
                BookUiState.Success(bookShelfRepository.getBooks(query = "jazz history")) // Pada bagian query dapat diganti sesuai kebutuhan namun harus sesuai ketentuan dari API yang digunakan
            } catch (e: IOException) {
                BookUiState.Error
            } catch (e: HttpException) {
                BookUiState.Error
            }
        }
    }


    companion object {
        // Fungsi untuk menyediakan factory untuk ViewModel
        fun provideFactory(repository: BookShelfRepository): ViewModelProvider.Factory {
            // Membuat instance ViewModelProvider.Factory
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                // Fungsi untuk membuat instance ViewModel
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    // Mengecek apakah modelClass sesuai dengan BookViewModel
                    if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
                        // Mengembalikan instance BookViewModel dengan repository yang diberikan
                        return BookViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}