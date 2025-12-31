package io.github.riotgames.leagueoflegends.infrastructure.input.schedule

import io.github.riotgames.leagueoflegends.domain.port.input.version.SynchronizeVersionsUseCase
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Agendador responsável por manter as versões do jogo sincronizadas com a API da Riot.
 *
 * Atua como um **Input Adapter** na arquitetura, disparando o fluxo de atualização
 * periodicamente sem intervenção humana.
 *
 * @property useCase Caso de uso que orquestra a busca e persistência das versões.
 * @author Laiane (e você!)
 * @since 1.0.0
 */
@Component
class VersionSyncScheduler(
    private val useCase: SynchronizeVersionsUseCase
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Executes daily version synchronization.
     *
     * **Schedule:** Every day at 04:00 AM (Server Time).
     *
     * This method uses [runCatching] to ensure exceptions do not interrupt
     * the Spring scheduler thread, logging failures to the error log.
     *
     * @see SynchronizeVersionsUseCase.execute
     */
    @Scheduled(cron = "0 0 4 * * *")
    fun syncVersionsDaily() = runCatching {
        logger.info("⏰ Alarm triggered! Starting synchronization...")
        useCase.execute()
    }.onSuccess {
        logger.info("✅ Synchronization completed successfully!")
    }.onFailure {
        logger.error("❌ An error occurred during synchronization", it)
    }
}