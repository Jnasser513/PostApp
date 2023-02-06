package latmobile.app.postapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import latmobile.app.postapp.domain.response.*
import latmobile.app.postapp.framework.databasemanager.entity.PostImageEntity
import latmobile.app.postapp.framework.databasemanager.mappers.toPostImageEntityList
import latmobile.app.postapp.interactors.GetPostCommentsUseCase
import latmobile.app.postapp.interactors.GetPostImagesUseCase
import latmobile.app.postapp.interactors.GetPostsUseCase
import latmobile.app.postapp.interactors.InsertPostImagesUseCase
import javax.inject.Inject

@HiltViewModel
class PostViewModel
@Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getPostImagesUseCase: GetPostImagesUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val insertPostImagesUseCase: InsertPostImagesUseCase
): ViewModel() {

    private val _statusGetPosts = MutableLiveData<UIStatus<List<PostResponse>>?>()
    val statusGetPosts: LiveData<UIStatus<List<PostResponse>>?> get() = _statusGetPosts

    fun getPosts() {
        _statusGetPosts.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(Dispatchers.IO) {
            _statusGetPosts.postValue(
                when(val response = getPostsUseCase.invoke()) {
                    is ApiResponse.EmptyList -> UIStatus.EmptyList(response.data)
                    is ApiResponse.Error -> UIStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> UIStatus.Success(response.data)
                    else -> {UIStatus.ErrorWithMessage("Error de conección")}
                }
            )
        }
    }

    private val _statusGetPostImages = MutableLiveData<UIStatus<List<PostImageResponse>>?>()
    val statusGetPostImages: LiveData<UIStatus<List<PostImageResponse>>?> get() = _statusGetPostImages

    fun getPostImages(idpost: Int) {
        _statusGetPostImages.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(Dispatchers.IO) {
            _statusGetPostImages.postValue(
                when(val response = getPostImagesUseCase.invoke(idpost)) {
                    is ApiResponse.EmptyList -> UIStatus.EmptyList(response.data)
                    is ApiResponse.Error -> UIStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> {
                        insertImages(response.data!!.toPostImageEntityList())
                        UIStatus.Success(response.data)
                    }
                    else -> {UIStatus.ErrorWithMessage("Error de conección")}
                }
            )
        }
    }

    private val _statusGetPostComments = MutableLiveData<UIStatus<List<PostCommentsResponse>>?>()
    val statusGetPostComments: LiveData<UIStatus<List<PostCommentsResponse>>?> get() = _statusGetPostComments

    fun getPostComments(idpost: Int) {
        _statusGetPostComments.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(Dispatchers.IO) {
            _statusGetPostComments.postValue(
                when(val response = getPostCommentsUseCase.invoke(idpost)) {
                    is ApiResponse.EmptyList -> UIStatus.EmptyList(response.data)
                    is ApiResponse.Error -> UIStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> UIStatus.Success(response.data)
                    else -> {UIStatus.ErrorWithMessage("Error de conección")}
                }
            )
        }
    }

    private fun insertImages(images: List<PostImageEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ENTRO", "INSERT")
            insertPostImagesUseCase.invoke(images)
        }
    }

}