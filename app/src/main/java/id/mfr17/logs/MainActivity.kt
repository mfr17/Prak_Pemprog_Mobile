package id.mfr17.logs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import id.mfr17.logs.ui.LoginScreen
import id.mfr17.logs.ui.LogsScreen
import id.mfr17.logs.ui.theme.LogsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogsTheme {
                Surface {
                    // State untuk memantau status login
                    val (isLoggedIn, setLoggedIn) = remember { mutableStateOf(false) }
                    val username = remember { mutableStateOf("") }
                    val password = remember { mutableStateOf("") }

                    // Tampilkan LoginScreen jika belum login, dan LogsScreen setelah login
                    if (isLoggedIn) {
                        LogsScreen(username = username.value, password = password.value)
                    } else {
                        LoginScreen { user, pass ->
                            username.value = user
                            password.value = pass
                            // Ganti status login menjadi true setelah login sukses
                            setLoggedIn(true)
                        }
                    }
                }
            }
        }
    }
}
