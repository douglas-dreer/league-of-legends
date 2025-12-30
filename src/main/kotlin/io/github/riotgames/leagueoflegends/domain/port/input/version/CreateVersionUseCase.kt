package io.github.riotgames.leagueoflegends.domain.port.input.version

import io.github.riotgames.leagueoflegends.domain.model.Version

interface CreateVersionUseCase {
    /**
     * Creates a new version.
     * @param version The version to be created.
     * @return The created version.
     */
    fun execute(version: Version): Version
}