package io.github.riotgames.leagueoflegends.infrastructure.input.controller.factory

import io.github.riotgames.leagueoflegends.infrastructure.input.controller.error.ApiErrorResponse
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.error.ErrorCode
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class ApiErrorResponseFactory(
    private val env: Environment
) {

    fun build(
        status: HttpStatus,
        code: ErrorCode,
        message: String?,
        path: String,
        details: List<String>? = null
    ): ResponseEntity<ApiErrorResponse> {
        val isDevProfile = env.activeProfiles.contains("dev") || env.activeProfiles.contains("local")

        val safeDetails = if (isDevProfile) details else null

        val apiError = ApiErrorResponse(
            timestamp = OffsetDateTime.now(),
            status = status.value(),
            error = status.reasonPhrase,
            code = code,
            message = message,
            path = path,
            details = safeDetails
        )

        return ResponseEntity(apiError, status)
    }
}