package io.github.riotgames.leagueoflegends.domain.port.input.version

interface SynchronizeVersionsUseCase {
    /**
     * Executes the version synchronization process.
     * This method fetches the latest versions from an external source
     * and updates the local data store accordingly.
     * @throws Exception if any error occurs during the synchronization process.
     * @see io.github.riotgames.leagueoflegends.application.usecase.version.SynchronizeVersionsUseCaseImpl
     */
    fun execute(): Long
}