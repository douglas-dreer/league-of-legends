package io.github.riotgames.leagueoflegends.domain.model

/**
 * Data class representing a Champion in the game.
 * @param id Unique identifier for the champion.
 * @param title The title of the champion.
 * @param blurb A brief description of the champion.
 * @param partype The type of resource the champion uses (e.g., Mana, Energy
 * etc.).
 * @param info General information about the champion.
 * @param image URL or path to the champion's image.
 * @param tags List of tags categorizing the champion (e.g., Fighter, Mage).
 * @param stats Statistical data related to the champion's abilities and attributes.
 */
data class Champion(
    val id: Long,
    val name: String,
    val title: String,
    val blurb: String,
    val partype: String,

    val info: ChampionInfo,
    val image: ChampionImage,

    val tags: List<String>,
    val stats: ChampionStats,
)
