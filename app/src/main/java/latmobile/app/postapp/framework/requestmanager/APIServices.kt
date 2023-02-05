package latmobile.app.postapp.framework.requestmanager

import latmobile.app.postapp.domain.response.PostResponse
import retrofit2.Response
import retrofit2.http.GET

interface APIServices {

    @GET(APIConstants.ENDPOINT_POSTS)
    suspend fun getPosts(): Response<List<PostResponse>>

}