package io.github.riotgames.leagueoflegends.domain.port.output

import io.github.riotgames.leagueoflegends.domain.model.Version

interface VersionRepositoryPort {
    /**
     * Saves a new version to the repository.
     * @param version The version to be saved.
     * @return The saved version.
     */
    fun save(version: Version): Version

    /**
     * Retrieves all versions from the repository.
     * @return A list of all versions.
     */
    fun findAll(): List<Version>

    /**
     * Finds the current version in the repository.
     * @return The current version, or null if none is set.
     */
    fun findCurrentVersion(): Version?
    fun update(version: Version): Version
    fun findVersionByNumber(versionNumber: String): Version?
    fun existVersionById(versionId: Long): Boolean
    fun existVersionByNumber(versionNumber: String): Boolean
    fun setVersionAsCurrent(versionId: Long)
    fun setPrevisionVersionAsNotCurrent()
}