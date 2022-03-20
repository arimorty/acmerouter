package com.takehometest.acmerouter.repo.common.source.remote

import retrofit2.http.GET

interface AcmeService {

    @GET("/all")
    suspend fun getAll(): AcmeResponse
}
