package io.github.riotgames.leagueoflegends.infrastructure.output.client

import io.github.riotgames.leagueoflegends.infrastructure.output.response.ChampionApiResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@FeignClient(name = "champion-client", url = "\${ddragon.api.url}")
interface ChampionClient {
    /**
     * Busca os campeões do League of Legends com base na versão e idioma fornecidos.
     * @param version A versão dos dados dos campeões.
     * @param language O idioma dos dados dos campeões.
     * @return Um objeto contendo os dados dos campeões.
     */
    @GetMapping("/cdn/{version}/data/{language}/champion.json")
    fun findAllChampions(
        @PathVariable("version") version: String,
        @PathVariable("language") language: String
    ): ChampionApiResponse
}