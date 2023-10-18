package com.filipmik.aidvisor.injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.filipmik.aidvisor.domain.model.RecipeFilter
import com.filipmik.aidvisor.tools.Constants
import com.filipmik.aidvisor.tools.serialization.RecipeFilterSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun provideRecipeFilterDataStore(
        @ApplicationContext context: Context
    ): DataStore<RecipeFilter> = DataStoreFactory.create(
        serializer = RecipeFilterSerializer,
        produceFile = {
            context.dataStoreFile(Constants.RecipeDataStore.RECIPE_DATA_STORE_FILE_NAME)
        }
    )
}
