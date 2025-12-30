package io.github.riotgames.leagueoflegends.domain.model

data class Version(
    val id: Long? = 0L,
    val number: String,
    val isCurrent: Boolean
)
