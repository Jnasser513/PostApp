package latmobile.app.postapp.data.repositories

import latmobile.app.postapp.data.datasource.RemotePostsDataSource

class PostRepository(
    private val remotePostsDataSource: RemotePostsDataSource
) {

    suspend fun getPosts() = remotePostsDataSource.getPosts()

}