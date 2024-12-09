package id.mfr17.bookshelf.model

// Kelas data untuk model buku
data class BookShelf(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val publisher: String,
    val publishedDate: String?,
    val thumbnail: String
)

