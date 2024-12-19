package id.mfr17.logs.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.mfr17.logs.data.LogsRepository
import id.mfr17.logs.network.ApiConfig
import id.mfr17.logs.ui.screen.LogsUiState
import id.mfr17.logs.ui.screen.LogsViewModel

// Komponen UI untuk menampilkan logs
@Composable
fun LogsScreen(username: String, password: String) {
    // Inisialisasi instance ApiService dengan menyertakan username dan password
    val apiService = ApiConfig.getApiService(username, password)
    val repository = LogsRepository(apiService)
    // Inisialisasi instance LogsViewModel dengan repository yang telah dibuat
    val viewModel: LogsViewModel = viewModel(factory = LogsViewModel.provideFactory(repository))
    // Mengambil state UI dari LogsViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Melakukan permintaan API saat komponen LogsScreen diinisialisasi
    LaunchedEffect(Unit) {
        val authHeader = "$username:$password"
        viewModel.getLogs(authHeader)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (uiState) {
            // Menampilkan indikator loading jika state UI adalah LogsUiState.Loading
            is LogsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
            // Menampilkan data logs jika state UI adalah LogsUiState.Success
            is LogsUiState.Success -> {
                val logs = (uiState as LogsUiState.Success).logs

                // Menampilkan logs dalam bentuk daftar yg dapat discroll
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(logs.reversed()) { log -> // Menampilkan logs dalam urutan terbalik
                        var isExapanded by remember { mutableStateOf(false) }
                        // Membuat card untuk setiap log
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { isExapanded = !isExapanded },

                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Menampilkan waktu dan topik logs
                                Text(
                                    text = "${log.time} : ${log.topics}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                // Menampilkan pesan jika logs di klik
                                if (isExapanded) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = log.message,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }

                    }
                }
            }
            // Menampilkan pesan error jika state UI adalah LogsUiState.Error
            is LogsUiState.Error -> {
                Text(
                    text = "Error: ${(uiState as LogsUiState.Error).exception}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

// Komponen UI untuk halaman login
@Composable
fun LoginScreen(onLogin: (String, String) -> Unit) {
    var username by remember { mutableStateOf("") } // Menyimpan state username
    var password by remember { mutableStateOf("") } // Menyimpan state password

    // Menampilkan form login
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),

        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),

        ) {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        Button(
            onClick = { onLogin(username, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}
