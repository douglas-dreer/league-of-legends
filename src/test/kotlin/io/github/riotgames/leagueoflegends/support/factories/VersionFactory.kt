package io.github.riotgames.leagueoflegends.support.factories

import io.github.riotgames.leagueoflegends.infrastructure.persistence.entity.VersionEntity

class VersionEntityFactory {
    companion object {
        const val ID: Long = 1L
        const val NUMBER: String = "13.6.1"
        const val IS_CURRENT_VERSION: Boolean = true
    }

    fun create(
        id: Long = ID,
        number: String = NUMBER,
        isCurrentVersion: Boolean = IS_CURRENT_VERSION
    ): VersionEntity {
        return VersionEntity(
            number = number,
            isCurrent = isCurrentVersion
        ).apply { this.id = id }
    }
}