package io.github.riotgames.leagueoflegends.domain.port.output

import io.github.riotgames.leagueoflegends.domain.model.Champion

interface ChampionClientPort {
    fun findAllChampions(locale: String): List<Champion>
}