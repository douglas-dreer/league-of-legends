package io.github.riotgames.leagueoflegends.infrastructure.output.adapter

    import io.github.riotgames.leagueoflegends.domain.mapper.toChampion
import io.github.riotgames.leagueoflegends.domain.model.Champion
import io.github.riotgames.leagueoflegends.domain.port.output.ChampionClientPort
import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import io.github.riotgames.leagueoflegends.infrastructure.output.client.ChampionClient
import org.springframework.stereotype.Component

@Component
class ChampionClientAdapter(
    private val feignClient: ChampionClient,
    private val versionRepository: VersionRepositoryPort
): ChampionClientPort {
    override fun findAllChampions(locale: String): List<Champion> {
        val response = feignClient
            .findAllChampions(getLastVersion(),locale)
            .data
            .values
        return response
            .map { it.toChampion() }
    }

    /**
     * Get the last version from the version repository.
     * @return The last version as a String.
     * @throws IllegalStateException if the current version is not found.
     */
    private fun getLastVersion(): String {
        val versionCurrent =  versionRepository.findCurrentVersion()?.number
        return versionCurrent ?: throw IllegalStateException("Current version not found.")
    }
}