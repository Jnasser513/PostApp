package latmobile.app.postapp.domain.response

data class PostImageResponse(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)