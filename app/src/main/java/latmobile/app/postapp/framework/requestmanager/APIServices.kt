package latmobile.app.postapp.framework.requestmanager

import latmobile.app.postapp.domain.response.PostImageResponse
import latmobile.app.postapp.domain.response.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIServices {

    @GET(APIConstants.ENDPOINT_POSTS)
    suspend fun getPosts(): Response<List<PostResponse>>

    @GET(APIConstants.ENDPOINT_POST_IMAGES)
    suspend fun getPostImages(@Path("idpost") idpost: Int): Response<List<PostImageResponse>>

}