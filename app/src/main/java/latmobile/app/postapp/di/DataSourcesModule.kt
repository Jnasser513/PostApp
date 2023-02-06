package latmobile.app.postapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import latmobile.app.postapp.data.datasource.LocalPostsDataSource
import latmobile.app.postapp.data.datasource.RemotePostsDataSource
import latmobile.app.postapp.framework.databasemanager.dao.PostImageDao
import latmobile.app.postapp.framework.databasemanager.datasources.PostRoomDataSource
import latmobile.app.postapp.framework.requestmanager.APIServices
import latmobile.app.postapp.framework.requestmanager.datasource.PostsRetrofitDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourcesModule {

    @Singleton
    @Provides
    fun provideRemotePostsDataSource(service: APIServices): RemotePostsDataSource {
        return PostsRetrofitDataSource(service)
    }

    @Singleton
    @Provides
    fun provideLocalPostsDataSource(postImageDao: PostImageDao): LocalPostsDataSource {
        return PostRoomDataSource(postImageDao)
    }

}