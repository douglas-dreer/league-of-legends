package io.github.riotgames.leagueoflegends.infrastructure.output.response

import io.github.riotgames.leagueoflegends.domain.model.ChampionImage
import io.github.riotgames.leagueoflegends.domain.model.ChampionInfo
import io.github.riotgames.leagueoflegends.domain.model.ChampionStats

data class ChampionDetailApiResponse(
    val id: String,
    val key: Long,
    val name: String,
    val title: String,
    val blurb: String,
    val tags: List<String>,
    val partype: String,
    val info: ChampionInfo,
    val image: ChampionImage,
    val stats: ChampionStats
)
