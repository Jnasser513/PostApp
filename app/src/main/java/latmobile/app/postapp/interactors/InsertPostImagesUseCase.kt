package latmobile.app.postapp.interactors

import latmobile.app.postapp.data.repositories.PostRepository
import latmobile.app.postapp.framework.databasemanager.entity.PostImageEntity

class InsertPostImagesUseCase(
    private val repository: PostRepository
) {

    fun invoke(images: List<PostImageEntity>) = repository.insertPostImages(images)

}