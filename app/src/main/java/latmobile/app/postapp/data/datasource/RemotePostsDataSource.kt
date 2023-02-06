package latmobile.app.postapp.data.datasource

import latmobile.app.postapp.domain.response.ApiResponse
import latmobile.app.postapp.domain.response.PostCommentsResponse
import latmobile.app.postapp.domain.response.PostImageResponse
import latmobile.app.postapp.domain.response.PostResponse

interface RemotePostsDataSource {

    suspend fun getPosts(): ApiResponse<List<PostResponse>>

    suspend fun getPostImages(idpost: Int): ApiResponse<List<PostImageResponse>>

    suspend fun getPostComments(idpost: Int): ApiResponse<List<PostCommentsResponse>>

}