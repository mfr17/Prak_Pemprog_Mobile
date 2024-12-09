package id.mfr17.bookshelf.network

import id.mfr17.bookshelf.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookShelfApiService {
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
    ): BooksResponse
}