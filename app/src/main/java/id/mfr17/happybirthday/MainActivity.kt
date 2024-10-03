package id.mfr17.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.mfr17.happybirthday.ui.theme.HappyBirthdayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyBirthdayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingScreen()
                }
            }
        }
    }

    @Composable
    fun GreetingScreen() {
        var senderName by remember { mutableStateOf("Emma") }
        var nameInput by remember { mutableStateOf("") }

        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
//            input untuk merubah nama pengirim
            BasicTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                decorationBox = { innerTextField ->
                    if (nameInput.isEmpty()) {
                        Text("Enter your name", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    }
                    innerTextField()
                }
            )
//            tombol untuk memperbarui nama pengirim
            Button(onClick = { senderName = nameInput }) {
                Text("Update Name")
            }

            Spacer(modifier = Modifier.height(16.dp)) // Menambah jarak antara tombol dan pesan
            GreetingText(
                message = "Happy Birthday Muhammad Dzikri A.P!",
                from = "From $senderName",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

//  fungsi untuk menampilkan dan styling teks selamat ulang tahun dengan pesan dan pengirim yang diberikan.
    @Composable
    fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
//            Text pesan selamat ulang tahun
            Text(
                text = message,
                fontSize = 100.sp,
                lineHeight = 118.sp,
                textAlign = TextAlign.Center
            )
//            Text pengirim
            Text(
                text = from,
                fontSize = 36.sp,
                modifier = Modifier.padding(16.dp).align(Alignment.End)
            )
        }
    }
}
