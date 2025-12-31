package io.github.riotgames.leagueoflegends.domain.port.output

import io.github.riotgames.leagueoflegends.domain.model.Version

interface VersionRepositoryPort {
    /**
     * Retrieves the last version from the repository.
     * @return The last version or null if none exists.
     */
    fun getLastVersion(): Version?
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
     * Updates an existing version in the repository.
     * @param version The version to be updated.
     * @return The updated version.
     */
    fun update(version: Version): Version

    /**
     * Finds a version by its ID.
     * @param versionId The ID of the version to be found.
     * @return The found version or null if not found.
     */
    fun findVersionByNumber(versionNumber: String): Version?
    /**
     * Checks if a version exists by its ID.
     * @param versionId The ID of the version to check.
     * @return True if the version exists, false otherwise.
     */
    fun existVersionById(versionId: Long): Boolean
    /**
     * Checks if a version exists by its number.
     * @param versionNumber The number of the version to check.
     * @return True if the version exists, false otherwise.
     */
    fun existVersionByNumber(versionNumber: String): Boolean
}