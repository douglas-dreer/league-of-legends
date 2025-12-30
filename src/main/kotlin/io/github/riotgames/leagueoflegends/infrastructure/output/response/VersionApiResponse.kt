package io.github.riotgames.leagueoflegends.infrastructure.output.response

data class VersionApiResponse(
    val currentVersion: String,
    val versions: List<String>
)
