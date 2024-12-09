package id.mfr17.bookshelf.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import id.mfr17.bookshelf.BookShelfApplication
import id.mfr17.bookshelf.data.BookShelfRepository
import id.mfr17.bookshelf.model.BookShelf
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookUiState {
    data class Success(val books: List<BookShelf>) : BookUiState
    object Error : BookUiState
    object Loading : BookUiState
}

class BookViewModel(private val bookShelfRepository: BookShelfRepository) : ViewModel() {
    var bookShelfUiState: BookUiState by mutableStateOf(BookUiState.Loading)
        private set

    init {
        getBooks()
    }

    fun getBooks(){
        viewModelScope.launch {
            bookShelfUiState = BookUiState.Loading
            bookShelfUiState = try {
                BookUiState.Success(bookShelfRepository.getBooks(query = "jazz history"))
            } catch (e: IOException) {
                BookUiState.Error
            } catch (e: HttpException) {
                BookUiState.Error
            }
        }
    }

    companion object {
        fun provideFactory(repository: BookShelfRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
                        return BookViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}