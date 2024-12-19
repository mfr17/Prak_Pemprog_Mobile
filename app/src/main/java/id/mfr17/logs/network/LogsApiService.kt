package id.mfr17.logs.network

import id.mfr17.logs.models.Logs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


interface ApiService {
    // Path dari API yang digunakan
    @GET("log")
    suspend fun getData(
        @Header("Authorization") authHeader: String
    ): Response<List<Logs>>
}
