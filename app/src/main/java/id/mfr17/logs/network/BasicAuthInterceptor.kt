import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Request

// Interceptor yang dimodifikasi untuk menambahkan header autentikasi Basic Auth
class BasicAuthInterceptor(private val username: String, private val password: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        // Menggabungkan username dan password dengan titik dua
        val credentials = "$username:$password"
        // Encode credentials ke Base64
        val encodedCredentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        // Membuat request baru dengan menambahkan header autentikasi
        val request: Request = chain.request().newBuilder()
            // Menghapus header autentikasi sebelumnya
            .removeHeader("Authorization")
            // Menambahkan header autentikasi baru dengan format BasicAuth yg telah diencode ke Base64
            .addHeader("Authorization", "Basic $encodedCredentials")
            .build()

        return chain.proceed(request)
    }
}
