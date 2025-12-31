package io.github.riotgames.leagueoflegends.infrastructure.output.client

import io.github.riotgames.leagueoflegends.infrastructure.output.response.ChampionApiResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@FeignClient(name = "champion-client", url = "\${ddragon.api.url}")
interface ChampionClient {
    /**
     * Fetches League of Legends champions based on the provided version and language.
     * @param version The version of the champion data.
     * @param language The language of the champion data.
     * @return An object containing the champion data.
     */
    @GetMapping("/cdn/{version}/data/{language}/champion.json")
    fun findAllChampions(
        @PathVariable("version") version: String,
        @PathVariable("language") language: String
    ): ChampionApiResponse
}