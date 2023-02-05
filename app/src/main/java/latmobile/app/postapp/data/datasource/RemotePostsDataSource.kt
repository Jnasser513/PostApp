package latmobile.app.postapp.data.datasource

import latmobile.app.postapp.domain.response.ApiResponse
import latmobile.app.postapp.domain.response.PostResponse

interface RemotePostsDataSource {

    suspend fun getPosts(): ApiResponse<List<PostResponse>>

}