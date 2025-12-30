package io.github.riotgames.leagueoflegends.infrastructure.output.response

data class ChampionApiResponse(
    val type: String,
    val format: String,
    val version: String,
    val data: Map<String, ChampionDetailApiResponse>
)