package id.mfr17.logs.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import id.mfr17.logs.data.LogsRepository
import id.mfr17.logs.models.Logs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LogsUiState {
    object Loading : LogsUiState()
    data class Success(val logs: List<Logs>) : LogsUiState()
    data class Error(val exception: String) : LogsUiState()
}

class LogsViewModel(private val logsRepository: LogsRepository) : ViewModel() {
    //  MutableStateFlow untuk menyimpan status UI
    private val _uiState = MutableStateFlow<LogsUiState>(LogsUiState.Loading)
    val uiState: StateFlow<LogsUiState> = _uiState

    fun getLogs(authHeader: String) {
        //  viewModelScope untuk menjalankan coroutine dalam cakupan ViewModel
        viewModelScope.launch {
            //  Atur state UI menjadi Loading sebelum melakukan permintaan API
            _uiState.value = LogsUiState.Loading
            try {
                //  Melakukan permintaan API untuk mendapatkan data logs
                val response = logsRepository.getLogs(authHeader)
                //  Cek respon dari server
                if (response.isSuccessful) {
                    // Jika respon berhasil, atur state UI menjadi Success dengan data logs
                    _uiState.value = LogsUiState.Success(response.body() ?: emptyList())
                } else {
                    // Jika respon gagal, atur state UI menjadi Error dengan pesan kesalahan
                    _uiState.value = LogsUiState.Error("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                // Jika terjadi kesalahan saat melakukan permintaan API, atur state UI menjadi Error dengan pesan kesalahan
                _uiState.value = LogsUiState.Error("Error: ${e.message}")
            }
        }
    }

    companion object {
        fun provideFactory(repository: LogsRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(LogsViewModel::class.java)) {
                        return LogsViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}
