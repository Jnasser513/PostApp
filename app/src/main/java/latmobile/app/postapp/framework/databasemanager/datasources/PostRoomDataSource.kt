package latmobile.app.postapp.framework.databasemanager.datasources

import androidx.lifecycle.Transformations
import latmobile.app.postapp.data.datasource.LocalPostsDataSource
import latmobile.app.postapp.domain.response.RoomResponse
import latmobile.app.postapp.framework.databasemanager.dao.PostImageDao
import latmobile.app.postapp.framework.databasemanager.entity.PostImageEntity
import okio.IOException

class PostRoomDataSource(
    private val postImagesDao: PostImageDao
): LocalPostsDataSource {

    override fun insertPostImages(images: List<PostImageEntity>) {
        try {
            postImagesDao.insertPostImages(images)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}