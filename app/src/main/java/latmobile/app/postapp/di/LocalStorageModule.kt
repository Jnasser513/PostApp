package latmobile.app.postapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import latmobile.app.postapp.framework.databasemanager.PostDatabase
import latmobile.app.postapp.framework.databasemanager.dao.PostImageDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {

    @Singleton
    @Provides
    fun getAppDB(@ApplicationContext context: Context): PostDatabase {
        return PostDatabase.getDatabase(context)
    }
    
    @Singleton
    @Provides
    fun getPostImageDao(database: PostDatabase): PostImageDao {
        return database.postImageDao()
    }

}