package id.mfr17.bookshelf.data

import id.mfr17.bookshelf.model.BookShelf
import id.mfr17.bookshelf.network.BookShelfApiService


interface BookShelfRepository {
    // Deklarasi fungsi untuk mendapatkan daftar buku berdasarkan query
    suspend fun getBooks(query: String): List<BookShelf>
}

class DefaultBookShelfRepository(
    // Injeksi dependensi BookShelfApiService
    private val bookShelfApiService: BookShelfApiService
) : BookShelfRepository {
    // Implementasi fungsi untuk mendapatkan daftar buku berdasarkan query
    override suspend fun getBooks(query: String): List<BookShelf> {
        // Panggil fungsi getBooks dari BookShelfApiService
        val response = bookShelfApiService.getBooks(query)

        // Mapping respons API ke model BookShelf
        return  response.items.map { bookItem ->
            BookShelf(
                id = bookItem.id,
                title = bookItem.volumeInfo.title,
                authors = bookItem.volumeInfo.authors ?: emptyList(),
                publisher = bookItem.volumeInfo.publisher ?: "Unknown Publisher",
                publishedDate = bookItem.volumeInfo.publishedDate ?: "Unknown Date",
                thumbnail = bookItem.volumeInfo.imageLinks?.smallThumbnail ?: "No Image",
            )
        }
    }
}