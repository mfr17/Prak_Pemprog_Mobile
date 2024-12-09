package id.mfr17.bookshelf.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import id.mfr17.bookshelf.network.BookShelfApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface AppContainer {
    // Deklarasi properti untuk BookShelfRepository
    val bookShelfRepository: BookShelfRepository
}

// Kelas yang mengimplementasikan AppContainer
class DefaultAppContainer : AppContainer {
    // Base URL untuk API
    private val BASE_URL = "https://www.googleapis.com/books/v1/"

    // Inisialisasi Gson untuk parsing JSON
    private val gson: Gson = GsonBuilder().create()

    // Membuat instance Retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson)) // Use GsonConverterFactory
        .build()

    // Membuat instance BookShelfApiService menggunakan Retrofit
    private val retrofitService: BookShelfApiService by lazy {
        retrofit.create(BookShelfApiService::class.java)
    }

    // Properti untuk menyediakan BookShelfRepository
    override val bookShelfRepository: BookShelfRepository by lazy {
        DefaultBookShelfRepository(retrofitService)
    }
}
