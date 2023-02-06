package latmobile.app.postapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import latmobile.app.postapp.domain.response.*
import latmobile.app.postapp.interactors.GetPostCommentsUseCase
import latmobile.app.postapp.interactors.GetPostImagesUseCase
import latmobile.app.postapp.interactors.GetPostsUseCase
import javax.inject.Inject

@HiltViewModel
class PostViewModel
@Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getPostImagesUseCase: GetPostImagesUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase
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
                    is ApiResponse.Success -> UIStatus.Success(response.data)
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
                }
            )
        }
    }

}