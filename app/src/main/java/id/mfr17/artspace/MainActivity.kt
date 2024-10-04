package id.mfr17.artspace
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.mfr17.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                ArtDisplayScreen()
            }
        }
    }
}

@Composable
fun ArtDisplayScreen() {
    var currentArtworkIndex by remember { mutableStateOf(0) }

    val artworks = listOf(
        ArtworkData(
            imageResId = R.drawable.digipren1,
            title = "Tugas Digipreneur (Adobe Ilustrator)",
            artist = "Dzikri",
            year = "2023"
        ),
        ArtworkData(
            imageResId = R.drawable.digipren2,
            title = "Tugas Digipreneur (Adobe Photoshop)",
            artist = "Dzikri",
            year = "2023"
        ),ArtworkData(
            imageResId = R.drawable.digipren3,
            title = "Tugas Digipreneur (Photopea)",
            artist = "Dzikri",
            year = "2023"
        ),ArtworkData(
            imageResId = R.drawable.mfr_light,
            title = "Logo MFR",
            artist = "Dzikri",
            year = "2017"
        ),
        // Tambahkan gambar lain jika diperlukan
    )

    // Menampilkan gambar berdasarkan indeks saat ini
    val artwork = artworks[currentArtworkIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Card untuk gambar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
//            Gambar
            Image(
                painter = painterResource(id = artwork.imageResId),
                contentDescription = artwork.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
//    Judul gambar
        Text(
            text = artwork.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
//        Deskripsi gambar
        Text(
            text = "${artwork.artist} (${artwork.year})",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Tampilkan tombol "Previous" hanya jika indeks tidak 0
            if (currentArtworkIndex > 0) {
                Button(
                    onClick = {
                        if (currentArtworkIndex > 0) {
                            currentArtworkIndex--
                        }
                    }
                ) {
                    Text(text = "Previous")
                }
            }
            // Tampilkan tombol "Next" hanya jika indeks belum mencapai akhir list
            if (currentArtworkIndex < artworks.size - 1) {
                Button(
                    onClick = {
                        if (currentArtworkIndex < artworks.size - 1) {
                            currentArtworkIndex++
                        }
                    }
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}

data class ArtworkData(
    val imageResId: Int,
    val title: String,
    val artist: String,
    val year: String
)
