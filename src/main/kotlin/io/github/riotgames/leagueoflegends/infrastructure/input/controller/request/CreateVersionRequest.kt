package io.github.riotgames.leagueoflegends.infrastructure.input.controller.request

import jakarta.validation.constraints.NotBlank

data class CreateVersionRequest(
    @field:NotBlank
    val number: String
)
