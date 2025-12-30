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
     * Executa a sincronização diária das versões.
     *
     * **Cronograma:** Todos os dias às 04:00 AM (Horário do Servidor).
     *
     * O método utiliza [runCatching] para garantir que exceções não interrompam
     * a thread do agendador do Spring, registrando falhas no log de erro.
     *
     * @see SynchronizeVersionsUseCase.execute
     */
    @Scheduled(cron = "0 0 4 * * *")
    fun syncVersionsDaily() = runCatching {
        logger.info("⏰ Despertador tocou! Sincronizando...")
        useCase.execute()
    }.onSuccess {
        logger.info("✅ Tudo limpo e sincronizado!")
    }.onFailure {
        logger.error("❌ Ocorreu um erro na sincronização", it)
    }
}