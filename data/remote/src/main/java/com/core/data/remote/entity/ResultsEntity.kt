package com.core.data.remote.entity

data class ResultsEntity<T : Any>(
    val results: List<T>,
    val info: InfoEntity
)

data class InfoEntity(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)
