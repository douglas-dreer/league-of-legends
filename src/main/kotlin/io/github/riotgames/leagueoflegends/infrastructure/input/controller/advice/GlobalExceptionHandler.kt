package io.github.riotgames.leagueoflegends.infrastructure.input.controller.advice

import io.github.riotgames.leagueoflegends.infrastructure.input.controller.error.ApiErrorResponse
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.error.ErrorCode
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.mapper.ExceptionHttpMapper
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.error.HttpExceptionMetadata
import jakarta.servlet.http.HttpServletRequest
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleSpringValidation(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {

        val details = ex.bindingResult
            .allErrors
            .filterIsInstance<FieldError>()
            .map { "${it.field}: ${it.defaultMessage}" }

        val metadata = HttpExceptionMetadata(
            status = HttpStatus.BAD_REQUEST,
            code = ErrorCode.VALIDATION_ERROR,
            message = "Erro de validação nos campos enviados",
            details = details
        )

        val apiError = ApiErrorResponse(
            status = metadata.status.value(),
            error = metadata.status.reasonPhrase,
            code = metadata.code,
            message = metadata.message,
            path = request.requestURI,
            details = metadata.details
        )

        return ResponseEntity(apiError, metadata.status)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(
        ex: BadRequestException,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {
        val metadata = HttpExceptionMetadata(
            status = HttpStatus.BAD_REQUEST,
            code = ErrorCode.BUSINESS_ERROR,
            message = ex.message ?: "Bad Request",
            details = emptyList()
        )

        val apiError = ApiErrorResponse(
            status = metadata.status.value(),
            error = metadata.status.reasonPhrase,
            code = metadata.code,
            message = metadata.message,
            path = request.requestURI,
            details = metadata.details
        )

        return ResponseEntity(apiError, metadata.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleAnyException(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {
        val metadata = ExceptionHttpMapper.map(ex)

        val apiError = ApiErrorResponse(
            status = metadata.status.value(),
            error = metadata.status.reasonPhrase,
            code = metadata.code,
            message = metadata.message,
            path = request.requestURI,
            details = metadata.details
        )

        return ResponseEntity(apiError, metadata.status)
    }
}