package com.example.randomuser


import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.randomuser.data.local.UserDatabase
import com.example.randomuser.data.network.RandomUserApi
import com.example.randomuser.domain.User
import com.example.randomuser.domain.UserRepository
import com.example.randomuser.domain.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext appContext: Context): UserDatabase =
        Room.databaseBuilder(
            appContext,
            UserDatabase::class.java,
            "user_database"
        ).build()


    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase) = userDatabase.userDao()

    @Singleton
    @Provides
    fun providesOkHttpClient() = OkHttpClient()

    @Singleton
    @Provides
    fun providesRandomUserApi(client: OkHttpClient) = RandomUserApi(client)
}

