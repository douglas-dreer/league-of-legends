package io.github.riotgames.leagueoflegends.domain.exception

class ValidationException(
    message: String,
    val errors: List<String>? = null
) : RuntimeException(message)