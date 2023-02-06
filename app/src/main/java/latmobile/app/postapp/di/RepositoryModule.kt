package latmobile.app.postapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import latmobile.app.postapp.data.datasource.LocalPostsDataSource
import latmobile.app.postapp.data.datasource.RemotePostsDataSource
import latmobile.app.postapp.data.repositories.PostRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(
        remotePostsDataSource: RemotePostsDataSource,
        localPostsDataSource: LocalPostsDataSource
    ): PostRepository {
        return PostRepository(remotePostsDataSource, localPostsDataSource)
    }

}