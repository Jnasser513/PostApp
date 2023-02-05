package latmobile.app.postapp.framework.requestmanager.datasource

import latmobile.app.postapp.data.datasource.RemotePostsDataSource
import latmobile.app.postapp.domain.response.ApiResponse
import latmobile.app.postapp.domain.response.PostResponse
import latmobile.app.postapp.framework.requestmanager.APIServices
import okio.IOException

class PostsRetrofitDataSource(
    private val service: APIServices
): RemotePostsDataSource {

    override suspend fun getPosts(): ApiResponse<List<PostResponse>> {
        return try {
            val call = service.getPosts()
            val response = call.body()

            if(call.isSuccessful) {
                if(response.isNullOrEmpty()) {
                    ApiResponse.EmptyList(response)
                } else {
                    ApiResponse.Success(response)
                }
            } else {
                when(call.code()) {
                    500 -> ApiResponse.ErrorWithMessage("Error de servidor")
                    else -> ApiResponse.ErrorWithMessage("Error de conección")
                }
            }
        } catch (e: IOException) {
            ApiResponse.Error(e)
        }
    }
}