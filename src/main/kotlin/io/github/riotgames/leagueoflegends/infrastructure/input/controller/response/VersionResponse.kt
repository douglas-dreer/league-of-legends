package io.github.riotgames.leagueoflegends.infrastructure.input.controller.response

data class VersionResponse(
    val id: Long,
    val number: String,
    val isCurrent: Boolean
)
