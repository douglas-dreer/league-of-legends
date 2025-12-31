package io.github.riotgames.leagueoflegends.infrastructure.input.controller.advice

import io.github.riotgames.leagueoflegends.domain.exception.BusinessException
import io.github.riotgames.leagueoflegends.domain.exception.ExternalServiceUnavailableException
import io.github.riotgames.leagueoflegends.domain.exception.ResourceNotFoundException
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.error.ApiErrorResponse
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.error.ErrorCode
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.factory.ApiErrorResponseFactory
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val apiErrorFactory: ApiErrorResponseFactory
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleSpringValidation(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {
        val details = ex.bindingResult.fieldErrors
            .map { "${it.field}: ${it.defaultMessage}" }

        return apiErrorFactory.build(
            status = HttpStatus.BAD_REQUEST,
            code = ErrorCode.VALIDATION_ERROR,
            message = "Erro de validação nos campos da requisição.",
            path = request.requestURI,
            details = details

        )
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(
        ex: BusinessException,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {

        return apiErrorFactory.build(
            status = HttpStatus.UNPROCESSABLE_ENTITY,
            code = ErrorCode.BUSINESS_ERROR,
            message = ex.message,
            path = request.requestURI,
            details = listOf(ex.toString())
        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(
        ex: ResourceNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {
        return apiErrorFactory.build(
            status = HttpStatus.NOT_FOUND,
            code = ErrorCode.RESOURCE_NOT_FOUND,
            message = ex.message,
            path = request.requestURI,
            details = listOf(ex.toString())
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonErrors(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {
        return apiErrorFactory.build(
            status = HttpStatus.BAD_REQUEST,
            code = ErrorCode.BAD_REQUEST,
            message = "O corpo da requisição está inválido ou mal formatado. Verifique os campos obrigatórios e tipos de dados.",
            path = request.requestURI,
            details = listOf(ex.toString())
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {
        logger.error("Erro não tratado: ", ex)

        return apiErrorFactory.build(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            code = ErrorCode.UNEXPECTED_ERROR,
            message = "Internal server error. Please contact the administrator.",
            path = request.requestURI,
            details = listOf(ex.toString())
        )
    }

    @ExceptionHandler(ExternalServiceUnavailableException::class)
    fun handleExternalServiceError(
        ex: ExternalServiceUnavailableException,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {

        return apiErrorFactory.build(
            status = HttpStatus.SERVICE_UNAVAILABLE, // 503!
            code = ErrorCode.DEPENDENCY_ERROR,
            message = ex.message,
            path = request.requestURI
        )
    }
}