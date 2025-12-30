package io.github.riotgames.leagueoflegends.domain.model

/**
 * Data class representing the image details of a champion.
 *
 * @param sprite The sprite sheet name where the image is located.
 * @param group The group name of the image.
 * @param x The x-coordinate of the image in the sprite sheet.
 * @param y The y-coordinate of the image in the sprite sheet.
 * @param w The width of the image.
 * @param h The height of the image.
 * @return ChampionImage object containing image details.
 *
 */
data class ChampionImage(
    val sprite: String,
    val group: String,
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int
)
