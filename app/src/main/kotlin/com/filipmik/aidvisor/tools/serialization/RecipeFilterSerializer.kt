package com.filipmik.aidvisor.tools.serialization

import androidx.datastore.core.Serializer
import com.filipmik.aidvisor.domain.model.RecipeFilter
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class RecipeFilterSerializer @Inject constructor(
    private val json: Json
) : Serializer<RecipeFilter> {

    override val defaultValue: RecipeFilter
        get() = RecipeFilter()

    override suspend fun readFrom(input: InputStream): RecipeFilter {
        return try {
            json.decodeFromString(
                deserializer = RecipeFilter.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            Timber.e(e)
            RecipeFilter()
        }
    }

    override suspend fun writeTo(t: RecipeFilter, output: OutputStream) {
        try {
            output.write(
                json.encodeToString(
                    serializer = RecipeFilter.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}
