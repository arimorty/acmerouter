package com.takehometest.acmerouter.repo.common.source.remote

import android.content.Context
import okhttp3.*

/**
 * An interceptor that intercepts all calls and provides a single mocked response constructed from
 * an asset data file.
 */
class AssetRequestInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("")
            .code(200)
            .body(
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    context.readFileFromAssets("data.json")
                )
            ).build()
    }
}

fun Context.readFileFromAssets(filePath: String): String {
    return resources.assets.open(filePath).bufferedReader().use {
        it.readText()
    }
}
