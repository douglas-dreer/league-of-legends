package io.github.riotgames.leagueoflegends.domain.port.input.version

interface SynchronizeVersionsUseCase {
    /** Synchronizes versions from an external source and returns the count of new versions added.
     *
     * @return The number of new versions synchronized.
     */
    fun execute(): Long
}