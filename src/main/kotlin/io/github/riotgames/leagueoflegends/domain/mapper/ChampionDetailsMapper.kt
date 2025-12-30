package io.github.riotgames.leagueoflegends.domain.mapper

import io.github.riotgames.leagueoflegends.domain.model.Champion
import io.github.riotgames.leagueoflegends.infrastructure.output.response.ChampionDetailApiResponse

fun ChampionDetailApiResponse.toChampion(): Champion {
    val image = this.image.copy(sprite = "https://ddragon.leagueoflegends.com/cdn/${this.id}_0.jpg")
    return Champion(
        id = this.key,
        name = this.name,
        title = this.title,
        blurb = this.blurb,
        partype = this.partype,
        info = this.info,
        image = image,
        tags = this.tags,
        stats = this.stats,
    )
}