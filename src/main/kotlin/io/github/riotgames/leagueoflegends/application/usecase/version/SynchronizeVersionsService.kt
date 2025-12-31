package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.application.validation.VersionValidator
import io.github.riotgames.leagueoflegends.domain.model.Version
import io.github.riotgames.leagueoflegends.domain.model.VersionImportedEvent
import io.github.riotgames.leagueoflegends.domain.port.input.version.CreateVersionUseCase
import io.github.riotgames.leagueoflegends.domain.port.input.version.SynchronizeVersionsUseCase
import io.github.riotgames.leagueoflegends.domain.port.output.VersionClientPort
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class SynchronizeVersionsService(
    private val validator: VersionValidator,
    private val client: VersionClientPort,
    private val createVersionUseCase: CreateVersionUseCase,
    private val eventPublisher: ApplicationEventPublisher

) : SynchronizeVersionsUseCase {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional(rollbackOn = [Exception::class])
    override fun execute(): Long {
        logger.info("Starting synchronization...")

        val versionList = client.findAllVersions().let { versions ->
            validator.filterAlreadyRegistered(versions)
        }.reversed()


        if (versionList.isEmpty()) {
            logger.warn("No versions found to synchronize.")
            return 0L
        }

        val savedVersions = versionList.map { versionString ->
            logger.debug("Synchronizing version: {}", versionString)

            createVersionUseCase.execute(Version(number = versionString, isCurrent = false))
                .also { logger.info("Version {} synchronized successfully", it.number ) }
        }

        val currentVersion = savedVersions.last().copy(isCurrent = true)

        eventPublisher.publishEvent(VersionImportedEvent(currentVersion))
        logger.info("Set version {} as current.", currentVersion.number)
        return savedVersions.size.toLong()
    }
}