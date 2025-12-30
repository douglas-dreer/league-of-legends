package io.github.riotgames.leagueoflegends.infrastructure.input.controller.error

import org.springframework.http.HttpStatus

data class HttpExceptionMetadata(
    val status: HttpStatus,
    val code: ErrorCode,
    val message: String?,
    val details: List<String>? = null
)