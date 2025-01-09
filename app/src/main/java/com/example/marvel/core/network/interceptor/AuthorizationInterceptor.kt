package com.example.marvel.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Date
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(private val privateKey: String, private val publicKey: String ) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val timeStamp = Date()
        val hash = generateHash(timeStamp.time.toString() + privateKey + publicKey)
        val interceptedUrl = originalUrl.newBuilder()
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("ts", timeStamp.time.toString())
            .addQueryParameter("hash", hash)
            .build()
        val interceptedRequest = originalRequest.newBuilder()
            .url(interceptedUrl)
            .build()
        return chain.proceed(interceptedRequest)
    }

    private fun generateHash(input: String): String {
        val hashAlgorithm = MessageDigest.getInstance("MD5")
        return BigInteger(1, hashAlgorithm.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}