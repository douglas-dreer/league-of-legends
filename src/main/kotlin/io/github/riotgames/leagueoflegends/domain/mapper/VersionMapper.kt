package io.github.riotgames.leagueoflegends.domain.mapper

import io.github.riotgames.leagueoflegends.domain.model.Version
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.response.VersionResponse

import io.github.riotgames.leagueoflegends.infrastructure.persistence.entity.VersionEntity

fun VersionEntity.toDomain(): Version {
    return Version(
        id = this.id,
        number = this.number
    )
}

fun Version.toEntity(): VersionEntity {
    return VersionEntity(
        number = this.number
    )
}

fun Version.toResponse(): VersionResponse {
    return VersionResponse(
       id = this.id ?: 0,
       number = this.number
    )
}