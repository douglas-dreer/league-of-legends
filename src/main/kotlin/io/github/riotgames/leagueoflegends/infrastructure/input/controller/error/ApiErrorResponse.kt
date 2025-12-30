package io.github.riotgames.leagueoflegends.infrastructure.input.controller.error

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.OffsetDateTime

data class ApiErrorResponse(
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val status: Int,
    val error: String,
    val code: ErrorCode,
    val message: String?,
    val path: String?,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val details: List<String>? = null
)