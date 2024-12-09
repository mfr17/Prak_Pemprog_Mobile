package id.mfr17.bookshelf.model

// Kelas data untuk respons API
data class BookItem(
        val id: String,
        val volumeInfo: VolumeInfo
)
// Kelas data untuk informasi volume buku
data class VolumeInfo(
        val title: String,
        val authors: List<String>?,
        val publisher: String?,
        val publishedDate: String?,
        val imageLinks: ImageLinks?
)

// Kelas data untuk link gambar buku
data class ImageLinks(
        val smallThumbnail: String?,
        val thumbnail: String?
)
