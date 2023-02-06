package latmobile.app.postapp.interactors

import latmobile.app.postapp.data.repositories.PostRepository

class GetPostCommentsUseCase(
    private val repository: PostRepository
) {

    suspend fun invoke(idpost: Int) = repository.getPostComments(idpost)

}