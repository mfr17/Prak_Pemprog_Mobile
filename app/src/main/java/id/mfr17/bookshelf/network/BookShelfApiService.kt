package id.mfr17.bookshelf.network

import id.mfr17.bookshelf.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookShelfApiService {
    // Path ke endpoint yang akan digunakan
    @GET("volumes")
    suspend fun getBooks(
        // Query parameter yang akan dimasukkan ke URL
        @Query("q") query: String,
    ): BooksResponse // Tipe data yang diharapkan dari respons
}