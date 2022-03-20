package com.takehometest.acmerouter.repo.common.source.remote

data class AcmeResponse(
    val shipments: List<String>,
    val drivers: List<String>,
)
