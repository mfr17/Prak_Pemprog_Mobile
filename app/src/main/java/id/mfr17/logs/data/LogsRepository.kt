package id.mfr17.logs.data

import android.util.Log
import id.mfr17.logs.models.Logs
import id.mfr17.logs.network.ApiService
import retrofit2.Response

class LogsRepository(private val apiService: ApiService) {
    suspend fun getLogs(authHeader: String): Response<List<Logs>> {
        val response = apiService.getData(authHeader)
        return apiService.getData(authHeader)
    }
}