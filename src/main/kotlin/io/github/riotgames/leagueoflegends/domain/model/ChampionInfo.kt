package io.github.riotgames.leagueoflegends.domain.model

/**
 * Data class representing the information of a champion.
 *
 * @property attack The attack rating of the champion.
 * @property defense The defense rating of the champion.
 * @property magic The magic rating of the champion.
 * @property difficulty The difficulty rating of the champion.
 */
data class ChampionInfo(
    val attack: Int,
    val defense: Int,
    val magic: Int,
    val difficulty: Int
)
