package io.github.riotgames.leagueoflegends.domain.exception

abstract class DomainException(
    override val message: String
) : RuntimeException(message)