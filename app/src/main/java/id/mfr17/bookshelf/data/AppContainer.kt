package id.mfr17.bookshelf.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import id.mfr17.bookshelf.network.BookShelfApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val bookShelfRepository: BookShelfRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://www.googleapis.com/books/v1/"

    // Initialize Gson
    private val gson: Gson = GsonBuilder().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson)) // Use GsonConverterFactory
        .build()

    private val retrofitService: BookShelfApiService by lazy {
        retrofit.create(BookShelfApiService::class.java)
    }

    override val bookShelfRepository: BookShelfRepository by lazy {
        DefaultBookShelfRepository(retrofitService)
    }
}
