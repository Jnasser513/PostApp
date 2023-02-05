package latmobile.app.postapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import latmobile.app.postapp.domain.response.ApiResponse
import latmobile.app.postapp.domain.response.PostResponse
import latmobile.app.postapp.domain.response.UIStatus
import latmobile.app.postapp.interactors.GetPostsUseCase
import javax.inject.Inject

@HiltViewModel
class PostViewModel
@Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
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

}