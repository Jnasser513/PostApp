package latmobile.app.postapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import latmobile.app.postapp.data.repositories.PostRepository
import latmobile.app.postapp.interactors.*

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetPosts(
        postRepository: PostRepository
    ): GetPostsUseCase {
        return GetPostsUseCase(postRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideGetPostImages(
        postRepository: PostRepository
    ): GetPostImagesUseCase {
        return GetPostImagesUseCase(postRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideGetPostComments(
        postRepository: PostRepository
    ): GetPostCommentsUseCase {
        return GetPostCommentsUseCase(postRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideInsertImages(
        postRepository: PostRepository
    ): InsertPostImagesUseCase {
        return InsertPostImagesUseCase(postRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideSearchImages(
        postRepository: PostRepository
    ): SearchImagesByPostIdUseCase {
        return SearchImagesByPostIdUseCase(postRepository)
    }

}