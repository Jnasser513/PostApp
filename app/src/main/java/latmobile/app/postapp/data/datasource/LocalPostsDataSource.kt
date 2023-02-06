package latmobile.app.postapp.data.datasource

import latmobile.app.postapp.domain.response.PostImageResponse
import latmobile.app.postapp.domain.response.RoomResponse
import latmobile.app.postapp.framework.databasemanager.entity.PostImageEntity

interface LocalPostsDataSource {

    fun insertPostImages(images: List<PostImageEntity>)

}