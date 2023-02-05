package latmobile.app.postapp.interactors

import latmobile.app.postapp.data.repositories.PostRepository

class GetPostsUseCase(
    private val repository: PostRepository
) {

    suspend fun invoke() = repository.getPosts()

}