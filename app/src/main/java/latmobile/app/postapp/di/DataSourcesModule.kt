package latmobile.app.postapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import latmobile.app.postapp.data.datasource.RemotePostsDataSource
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

}