package com.shinkatech.calortracker.di

import com.shinkatech.calortracker.data.remote.FirebaseDataAuth
import com.shinkatech.calortracker.data.repository.QuesDataUserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideFirebaseDataAuth(): FirebaseDataAuth {
        return FirebaseDataAuth()
    }

    @Provides
    @Singleton
    fun provideQuesDataUserRepo(firebaseDataAuth: FirebaseDataAuth): QuesDataUserRepo {
        return QuesDataUserRepo(firebaseDataAuth)
    }
}