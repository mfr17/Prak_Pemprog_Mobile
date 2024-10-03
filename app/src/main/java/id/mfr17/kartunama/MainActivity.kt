package id.mfr17.kartunama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.painter.Painter
import id.mfr17.kartunama.ui.theme.KartuNamaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KartuNamaTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF8dcfeb)) // Menggunakan warna latar belakang #8dcfeb
                ) { innerPadding ->
                    BusinessCard(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Profile Picture dari gambar (ganti dengan drawable atau URL)
        Image(
            painter = painterResource(id = R.drawable.profile_picture), // Ganti dengan gambar yang diinginkan
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFF7a7574), CircleShape), // Warna background abu-abu #7a7574
            contentScale = ContentScale.Crop // Agar gambar crop menjadi lingkaran
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Full Name dan Title
        Text(
            text = "Muhammad Dzikri Adi P",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF25a4da) // Warna teks utama #25a4da
        )

        Text(
            text = "IT Support",
            fontSize = 24.sp,
            color = Color(0xFF0c2c64), // Warna teks untuk jabatan #0c2c64
        )

        Spacer(modifier = Modifier.height(96.dp))

        // Informasi Kontak yang di-align ke center namun teks tetap left-aligned
        Column(
            horizontalAlignment = Alignment.Start // Align teks ke kiri dalam kolom yang ada di tengah
        ) {
            ContactInfo(icon = painterResource(id = R.drawable.ic_phone), info = "+62 8222 5378 894", iconColor = Color(0xFF7a7574))
            Spacer(modifier = Modifier.height(8.dp)) // Spasi antara kontak
            ContactInfo(icon = painterResource(id = R.drawable.ic_social_media), info = "@ole_sod", iconColor = Color(0xFF7a7574))
            Spacer(modifier = Modifier.height(8.dp)) // Spasi antara kontak
            ContactInfo(icon = painterResource(id = R.drawable.ic_email), info = "tanah422@gmail.com", iconColor = Color(0xFF7a7574))
        }
    }
}

@Composable
fun ContactInfo(icon: Painter, info: String, iconColor: Color) {
    Row(
        modifier = Modifier
            .width(IntrinsicSize.Max) // Sesuaikan lebar row ke konten agar tetap left-aligned
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Fit,
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFF343351)) // Warna ikon #343351
        )
        Text(
            text = info,
            fontSize = 16.sp,
            color = Color(0xFF343351) // Warna teks kontak #343351
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    KartuNamaTheme {
        BusinessCard()
    }
}
