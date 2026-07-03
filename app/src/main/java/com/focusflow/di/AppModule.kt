package com.focusflow.di

import android.app.Application
import androidx.room.Room
import com.focusflow.data.local.dao.FocusSessionDao
import com.focusflow.data.local.dao.HabitDao
import com.focusflow.data.local.database.FocusFlowDatabase
import com.google.firebase.auth.FirebaseAuth
import com.focusflow.data.repository.AuthRepositoryImpl
import com.focusflow.data.repository.FocusRepositoryImpl
import com.focusflow.data.repository.HabitRepositoryImpl
import com.focusflow.data.repository.ThemeRepositoryImpl
import com.focusflow.domain.repository.AuthRepository
import com.focusflow.domain.repository.FocusRepository
import com.focusflow.domain.repository.HabitRepository
import com.focusflow.domain.repository.ThemeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * this is where we define how to create all the major components Hilt needs to inject.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideFocusFlowDatabase(app: Application): FocusFlowDatabase {
        return Room.databaseBuilder(
            app,
            FocusFlowDatabase::class.java,
            "focus_flow_db"
        )
        .fallbackToDestructiveMigration() // easier for now while we're still tweaking the schema
        .build()
    }

    @Provides
    @Singleton
    fun provideHabitDao(db: FocusFlowDatabase): HabitDao = db.habitDao

    @Provides
    @Singleton
    fun provideFocusSessionDao(db: FocusFlowDatabase): FocusSessionDao = db.focusSessionDao

    @Provides
    @Singleton
    fun provideHabitRepository(dao: HabitDao): HabitRepository {
        return HabitRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideFocusRepository(dao: FocusSessionDao): FocusRepository {
        return FocusRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideThemeRepository(impl: ThemeRepositoryImpl): ThemeRepository = impl
}
