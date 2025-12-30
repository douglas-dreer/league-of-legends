package io.github.riotgames.leagueoflegends.infrastructure.output.client

import io.github.riotgames.leagueoflegends.domain.model.Version
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "version-client", url = "\${ddragon.api.url}")
interface VersionClient {
    @GetMapping("/api/versions.json")
    fun findAllVersions(): List<String>
}