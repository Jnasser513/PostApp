package latmobile.app.postapp.interactors

import latmobile.app.postapp.data.repositories.PostRepository

class GetPostImagesUseCase(
    private val repository: PostRepository
) {

    suspend fun invoke(idpost: Int) = repository.getPostImages(idpost)

}