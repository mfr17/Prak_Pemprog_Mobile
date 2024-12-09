package id.mfr17.bookshelf.data

import id.mfr17.bookshelf.model.BookShelf
import id.mfr17.bookshelf.network.BookShelfApiService

interface BookShelfRepository {
    suspend fun getBooks(query: String): List<BookShelf>
}

class DefaultBookShelfRepository(
    private val bookShelfApiService: BookShelfApiService
) : BookShelfRepository {
    override suspend fun getBooks(query: String): List<BookShelf> {
        val response = bookShelfApiService.getBooks(query)

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