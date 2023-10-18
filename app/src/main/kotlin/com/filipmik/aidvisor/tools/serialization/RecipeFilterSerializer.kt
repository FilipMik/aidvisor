package com.filipmik.aidvisor.tools.serialization

import androidx.datastore.core.Serializer
import com.filipmik.aidvisor.domain.model.RecipeFilter
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object RecipeFilterSerializer : Serializer<RecipeFilter> {
    override val defaultValue: RecipeFilter
        get() = RecipeFilter()

    override suspend fun readFrom(input: InputStream): RecipeFilter {
        return try {
            Json.decodeFromString(
                deserializer = RecipeFilter.serializer(),
                string = input.readBytes().toString()
            )
        } catch (e: Exception) {
            Timber.e(e)
            RecipeFilter()
        }
    }

    override suspend fun writeTo(t: RecipeFilter, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = RecipeFilter.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}
