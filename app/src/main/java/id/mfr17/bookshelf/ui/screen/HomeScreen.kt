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

@Composable
fun HomeScreen(
    bookShelfUiState: BookUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (bookShelfUiState) {
        is BookUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is BookUiState.Success -> BooksListScreen(
            books = bookShelfUiState.books,
            modifier = modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                ),
            contentPadding = contentPadding
        )
        else -> ErrorScreen(retryAction = retryAction, modifier = modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun BooksGridScreen(bookShelf: BookShelf, modifier: Modifier = Modifier) {
    val thumbnailUrl = convertToHttps(bookShelf.thumbnail)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bookShelf.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
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
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Text(
                text = bookShelf.publisher,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@Composable
private fun BooksListScreen(
    books: List<BookShelf>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = books,
            key = { book -> book.id }
        ) { book ->
            BooksGridScreen(bookShelf = book, modifier = Modifier.fillMaxWidth())
        }
    }
}

// Mapping function for BookItem to BookShelf
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

fun convertToHttps(url: String): String {
    return if (url.startsWith("http://")) {
        url.replace("http://", "https://")
    } else {
        url // Return the URL unchanged if it is already using HTTPS
    }
}
