package io.github.riotgames.leagueoflegends.infrastructure.input.controller.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.error.ErrorCode
import java.time.OffsetDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiResponse(
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val status: Int,
    val error: String?,
    val code: ErrorCode?,
    val message: String?,
    val path: String,
    val details: List<String>? = null
) {
}