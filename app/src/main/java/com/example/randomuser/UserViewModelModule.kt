package com.example.randomuser

import com.example.randomuser.domain.UserRepository
import com.example.randomuser.domain.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserViewModelModule() {

    @Singleton
    @Binds
    abstract fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository
}