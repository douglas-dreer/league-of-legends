package io.github.riotgames.leagueoflegends.infrastructure.input.controller.mapper

import io.github.riotgames.leagueoflegends.domain.exception.BusinessException
import io.github.riotgames.leagueoflegends.domain.exception.ResourceNotFoundException
import io.github.riotgames.leagueoflegends.domain.exception.ValidationException
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.error.ErrorCode
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.error.HttpExceptionMetadata
import org.springframework.http.HttpStatus

object ExceptionHttpMapper {

    fun map(ex: Throwable): HttpExceptionMetadata =
        when (ex) {
            is ValidationException ->
                HttpExceptionMetadata(
                    status = HttpStatus.BAD_REQUEST,
                    code = ErrorCode.VALIDATION_ERROR,
                    message = ex.message,
                    details = ex.errors
                )

            is BusinessException ->
                HttpExceptionMetadata(
                    status = HttpStatus.UNPROCESSABLE_ENTITY,
                    code = ErrorCode.BUSINESS_ERROR,
                    message = ex.message
                )

            is ResourceNotFoundException ->
                HttpExceptionMetadata(
                    status = HttpStatus.NOT_FOUND,
                    code = ErrorCode.RESOURCE_NOT_FOUND,
                    message = ex.message
                )

            else ->
                HttpExceptionMetadata(
                    status = HttpStatus.INTERNAL_SERVER_ERROR,
                    code = ErrorCode.UNEXPECTED_ERROR,
                    message = "Erro interno inesperado"
                )
        }
}