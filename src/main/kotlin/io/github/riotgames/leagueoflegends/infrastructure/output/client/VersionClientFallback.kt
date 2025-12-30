package io.github.riotgames.leagueoflegends.infrastructure.output.client

import io.github.riotgames.leagueoflegends.domain.exception.ExternalServiceUnavailableException
import org.springframework.stereotype.Component

@Component
class VersionClientFallback : VersionClient {
    override fun findAllVersions(): List<String> {
        throw ExternalServiceUnavailableException("O serviço da Riot (DDragon) está indisponível no momento.")
    }
}