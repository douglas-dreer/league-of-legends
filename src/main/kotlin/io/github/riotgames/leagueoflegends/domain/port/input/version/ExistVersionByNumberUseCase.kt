package io.github.riotgames.leagueoflegends.domain.port.input.version

interface ExistVersionByNumberUseCase {
    /**
     * Checks if a version with the specified version number exists.
     * @param versionNumber The version number to check.
     * @return True if the version exists, false otherwise.
     */
    fun execute(versionNumber: String): Boolean
}