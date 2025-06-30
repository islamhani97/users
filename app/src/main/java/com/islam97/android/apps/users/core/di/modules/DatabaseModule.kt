package com.islam97.android.apps.users.core.di.modules

import android.content.Context
import androidx.room.Room
import com.islam97.android.apps.users.data.room.AppDatabase
import com.islam97.android.apps.users.data.room.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "USERS_DATABASE").build()
    }

    @Singleton
    @Provides
    fun provideUsersDao(database: AppDatabase): UsersDao {
        return database.usersDao()
    }
}