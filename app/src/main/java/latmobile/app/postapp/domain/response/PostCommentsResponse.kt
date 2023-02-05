package latmobile.app.postapp.domain.response

data class PostCommentsResponse(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)