package com.filipmik.aidvisor.injection

import android.content.Context
import android.content.res.Resources
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.filipmik.aidvisor.RecipeDatabase
import com.filipmik.aidvisor.data.database.RecipeDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun resources(
        @ApplicationContext context: Context
    ): Resources = context.resources

    @Provides
    @Singleton
    fun provideSqlDriver(
        @ApplicationContext context: Context
    ): SqlDriver = AndroidSqliteDriver(
        schema = RecipeDatabase.Schema,
        context = context,
        name = "recipe.db"
    )

    @Provides
    @Singleton
    fun provideRecipeDataSource(
        sqlDriver: SqlDriver
    ) : RecipeDataSourceImpl = RecipeDataSourceImpl(RecipeDatabase(sqlDriver))
}
