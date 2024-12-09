package id.mfr17.bookshelf.model


data class BookShelf(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val publisher: String,
    val publishedDate: String?,
    val thumbnail: String
)

