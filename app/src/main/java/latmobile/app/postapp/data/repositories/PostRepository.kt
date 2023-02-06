package latmobile.app.postapp.data.repositories

import latmobile.app.postapp.data.datasource.LocalPostsDataSource
import latmobile.app.postapp.data.datasource.RemotePostsDataSource
import latmobile.app.postapp.framework.databasemanager.entity.PostImageEntity

class PostRepository(
    private val remotePostsDataSource: RemotePostsDataSource,
    private val localPostsDataSource: LocalPostsDataSource
) {

    suspend fun getPosts() = remotePostsDataSource.getPosts()

    suspend fun getPostImages(idpost: Int) = remotePostsDataSource.getPostImages(idpost)

    suspend fun getPostComments(idpost: Int) = remotePostsDataSource.getPostComments(idpost)

    fun insertPostImages(images: List<PostImageEntity>) = localPostsDataSource.insertPostImages(images)

}