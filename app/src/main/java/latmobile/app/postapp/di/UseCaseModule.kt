package latmobile.app.postapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import latmobile.app.postapp.data.repositories.PostRepository
import latmobile.app.postapp.interactors.GetPostsUseCase

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

}