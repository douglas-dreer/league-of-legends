package io.github.riotgames.leagueoflegends.domain.port.input.version

import io.github.riotgames.leagueoflegends.domain.model.Version

interface FindVersionByNumberUseCase {
    /**
     * Finds a version by its version number.
     * @param versionNumber The version number to search for.
     * @return The found version, or null if not found.
     */
    fun execute(versionNumber: String): Version?
}