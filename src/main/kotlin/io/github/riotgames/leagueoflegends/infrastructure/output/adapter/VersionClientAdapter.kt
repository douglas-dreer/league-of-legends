package io.github.riotgames.leagueoflegends.infrastructure.output.adapter

import io.github.resilience4j.retry.annotation.Retry
import io.github.riotgames.leagueoflegends.domain.port.output.VersionClientPort
import io.github.riotgames.leagueoflegends.infrastructure.output.client.VersionClient
import org.springframework.stereotype.Component

@Component
class VersionClientAdapter(
    private val feignClient: VersionClient
) : VersionClientPort {

    @Retry(name = "ddragon-client", fallbackMethod = "buscarVersaoFallback")
    override fun findAllVersions(): List<String> {
        return feignClient.findAllVersions()
    }
}