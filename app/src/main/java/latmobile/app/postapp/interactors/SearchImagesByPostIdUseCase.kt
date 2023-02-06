package latmobile.app.postapp.interactors

import latmobile.app.postapp.data.repositories.PostRepository

class SearchImagesByPostIdUseCase(
    private val repository: PostRepository
) {

    fun invoke(idpost: Int) = repository.searchImagesByPostId(idpost)

}