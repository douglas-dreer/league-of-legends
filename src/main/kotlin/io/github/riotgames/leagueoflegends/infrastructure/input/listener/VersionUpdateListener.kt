package io.github.riotgames.leagueoflegends.infrastructure.input.listener

import io.github.riotgames.leagueoflegends.domain.model.Version
import io.github.riotgames.leagueoflegends.domain.model.VersionImportedEvent
import io.github.riotgames.leagueoflegends.domain.port.input.version.SetLastVersionAsCurrentUseCase
import io.github.riotgames.leagueoflegends.domain.port.input.version.SetPrevisionVersionAsNotCurrentUseCase
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class VersionUpdateListener(
    private val setLastVersionAsCurrentUseCase: SetLastVersionAsCurrentUseCase,
    private val setPrevisionVersionAsNotCurrentUseCase: SetPrevisionVersionAsNotCurrentUseCase
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @EventListener
    fun handleVersionImported(event: VersionImportedEvent) {
        val version = event.version
        logger.info("Event received! Setting version {} as current.", version.toString())
        setPreviousVersionAsNotCurrent()
        setLastVersionAsCurrent(version)
    }

    private fun setPreviousVersionAsNotCurrent() {
        logger.info("Tasking to set previous current version as not current.")
        setPrevisionVersionAsNotCurrentUseCase.execute()
        logger.info("Previous current version set as not current successfully.")
    }

    private fun setLastVersionAsCurrent(version: Version) {
        logger.info("Tasking to set version {} as current.", version.toString())
        setLastVersionAsCurrentUseCase.execute(version.id?: throw IllegalArgumentException("Version id cannot be null"))
        logger.info("Version {} set as current successfully.", version.toString())
    }
}