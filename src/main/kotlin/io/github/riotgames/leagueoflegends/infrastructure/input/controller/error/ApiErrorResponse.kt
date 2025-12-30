package io.github.riotgames.leagueoflegends.infrastructure.input.controller.error

import java.time.OffsetDateTime

data class ApiErrorResponse(
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val status: Int,
    val error: String,
    val code: ErrorCode,
    val message: String?,
    val path: String?,
    val details: List<String>? = null
)