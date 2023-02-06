package latmobile.app.postapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import latmobile.app.postapp.domain.response.*
import latmobile.app.postapp.framework.databasemanager.entity.PostImageEntity
import latmobile.app.postapp.framework.databasemanager.mappers.toPostImageEntityList
import latmobile.app.postapp.interactors.*
import javax.inject.Inject

@HiltViewModel
class PostViewModel
@Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getPostImagesUseCase: GetPostImagesUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val insertPostImagesUseCase: InsertPostImagesUseCase,
    private val searchImagesByPostIdUseCase: SearchImagesByPostIdUseCase
) : ViewModel() {

    private val _statusGetPosts = MutableLiveData<UIStatus<List<PostResponse>>?>()
    val statusGetPosts: LiveData<UIStatus<List<PostResponse>>?> get() = _statusGetPosts

    fun getPosts() {
        _statusGetPosts.value = UIStatus.Loading("Cargando...")
        viewModelScope.launch(Dispatchers.IO) {
            _statusGetPosts.postValue(
                when (val response = getPostsUseCase.invoke()) {
                    is ApiResponse.EmptyList -> UIStatus.EmptyList(response.data)
                    is ApiResponse.Error -> UIStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> UIStatus.Success(response.data)
                    else -> {
                        UIStatus.ErrorWithMessage("Error de conección")
                    }
                }
            )
        }
    }

    private val _statusGetPostImages = MutableLiveData<UIStatus<List<PostImageResponse>>?>()
    val statusGetPostImages: LiveData<UIStatus<List<PostImageResponse>>?> get() = _statusGetPostImages

    fun getPostImages(idpost: Int) {
        _statusGetPostImages.value = UIStatus.Loading("Cargando imagenes desde api")
        viewModelScope.launch(Dispatchers.IO) {
            _statusGetPostImages.postValue(
                when (val response = getPostImagesUseCase.invoke(idpost)) {
                    is ApiResponse.EmptyList -> UIStatus.EmptyList(response.data)
                    is ApiResponse.Error -> UIStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> {
                        insertImages(response.data!!.toPostImageEntityList())
                        delay(2000)
                        UIStatus.Success(response.data)
                    }
                    else -> {
                        UIStatus.ErrorWithMessage("Error de conección")
                    }
                }
            )
        }
    }

    private fun insertImages(images: List<PostImageEntity>) {
        _statusGetPostImages.postValue(UIStatus.Loading("Insertando imagenes en local"))
        insertPostImagesUseCase.invoke(images)
    }

    private val _statusGetPostImagesRoom = MutableLiveData<UIStatus<List<PostImageEntity>>?>()
    val statusGetPostImagesRoom: LiveData<UIStatus<List<PostImageEntity>>?> get() = _statusGetPostImagesRoom

    fun searchImages(idpost: Int) {
        _statusGetPostImagesRoom.value = UIStatus.Loading("Cargando Imagenes desde local")
        viewModelScope.launch(Dispatchers.IO) {
            _statusGetPostImagesRoom.postValue(
                when (val response = searchImagesByPostIdUseCase.invoke(idpost)) {
                    is RoomResponse.EmptyList -> UIStatus.EmptyList(response.data)
                    is RoomResponse.Error -> UIStatus.Error(response.exception)
                    is RoomResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is RoomResponse.Success -> {
                        Log.d("SUCCESS", response.data.toString())
                        UIStatus.Success(response.data)
                    }
                    else -> {
                        UIStatus.ErrorWithMessage("Error de conección")
                    }
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
                when (val response = getPostCommentsUseCase.invoke(idpost)) {
                    is ApiResponse.EmptyList -> UIStatus.EmptyList(response.data)
                    is ApiResponse.Error -> UIStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> UIStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> UIStatus.Success(response.data)
                    else -> {
                        UIStatus.ErrorWithMessage("Error de conección")
                    }
                }
            )
        }
    }

}