package id.mfr17.logs.network

import BasicAuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    // URL dari server API yg digunakan
    private const val BASE_URL = "https://api.ostore.my.id/rest/"

    fun getApiService(username: String, password: String): ApiService {
        // Membuat interceptor baru yg menggunkan BasicAuthInterceptor untuk menambahkan header autentikasi
        val authInterceptor = BasicAuthInterceptor(username, password)

        // Membuat OkHttpClient yg akan digunakan oleh Retrofit
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
        // Membuat Retrofit instance dan konfigurasi yg digunakan
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // URL dari server API
            .addConverterFactory(GsonConverterFactory.create()) // Konverter JSON yg digunakan
            .client(client) // OkHttpClient yg telah dikonfigurasi
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
