package id.mfr17.bookshelf.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.mfr17.bookshelf.R
import id.mfr17.bookshelf.model.BookShelf
import id.mfr17.bookshelf.model.BookItem

// Fungsi untuk menampilkan layar utama aplikasi
@Composable
fun HomeScreen(
    // State dari ViewModel
    bookShelfUiState: BookUiState,
    // Fungsi yang akan dijalankan jika terjadi kesalahan
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    // Berdasarkan state, tampilkan konten yang sesuai
    when (bookShelfUiState) {
        // Jika sedang memuat, tampilkan LoadingScreen
        is BookUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        // Jika berhasil memuat, tampilkan BooksListScreen
        is BookUiState.Success -> BooksListScreen(
            // Daftar buku dari state
            books = bookShelfUiState.books,
            modifier = modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                ),
            contentPadding = contentPadding
        )
        // Jika terjadi kesalahan, tampilkan ErrorScreen
        else -> ErrorScreen(retryAction = retryAction, modifier = modifier)
    }
}

// Fungsi untuk menampilkan LoadingScreen
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    // Gambar loading yang ditampilkan sebagai indikator loading
    Image(
        // Menampilkan gambar dari drawable
        painter = painterResource(R.drawable.loading_img),
        // Menampilkan deskripsi konten jika gambar gagal dimuat
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

// Fungsi untuk menampilkan ErrorScreen
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    // Kolom yang berisi pesan kesalahan dan tombol retry
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Menampilkan tulisan dari string resources
        Text(stringResource(R.string.loading_failed))
        // Tombol retry yang akan memanggil fungsi retryAction ketika ditekan
        Button(onClick = retryAction) {
            // Menampilkan tulisan dari string resources
            Text(stringResource(R.string.retry))
        }
    }
}

// Fungsi untuk menampilkan item buku dalam daftar
@Composable
fun BooksGridScreen(bookShelf: BookShelf, modifier: Modifier = Modifier) {
    // Mengubah URL gambar menjadi HTTPS karena URL yang diberikan berupa HTTP
    val thumbnailUrl = convertToHttps(bookShelf.thumbnail)

    // Membuat card untuk menampilkan item buku
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        // Kolom yang berisi informasi buku
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Menampilkan judul buku
            Text(
                text = bookShelf.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            // Menampilkan gambar buku menggunakan AsyncImage dari url yg diperoleh dari API
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                // Menampilkan gambar jika tidak berhasil dimuat
                error = painterResource(id = R.drawable.ic_broken_image),
                // Menampilkan gambar saat sedang memuat
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            // Menampilkan penulis buku
            Text(
                text = bookShelf.publisher,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

// Fungsi untuk menampilkan daftar buku
@Composable
private fun BooksListScreen(
    // Daftar buku yang akan ditampilkan
    books: List<BookShelf>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    // Menggunakan LazyColumn untuk menampilkan daftar buku
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Menambahkan item untuk setiap buku dalam daftar
        items(
            items = books,
            key = { book -> book.id }
            // Menampilkan BooksGridScreen untuk setiap buku
        ) { book ->
            BooksGridScreen(bookShelf = book, modifier = Modifier.fillMaxWidth())
        }
    }
}

// Mapping BookItem ke BookShelf
fun BookItem.toBookShelf(): BookShelf {
    return BookShelf(
        id = this.id,
        title = this.volumeInfo.title,
        authors = this.volumeInfo.authors ?: emptyList(),
        publisher = this.volumeInfo.publisher ?: "Unknown Publisher",
        publishedDate = this.volumeInfo.publishedDate ?: "Unknown Date",
        thumbnail = this.volumeInfo.imageLinks?.thumbnail ?: ""
    )
}
// Fungsi untuk mengganti string HTTP ke HTTPS
fun convertToHttps(url: String): String {
    return if (url.startsWith("http://")) {
        url.replace("http://", "https://")
    } else {
        url //
    }
}
