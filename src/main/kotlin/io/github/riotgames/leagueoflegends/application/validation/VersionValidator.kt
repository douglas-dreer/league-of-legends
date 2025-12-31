package io.github.riotgames.leagueoflegends.application.validation

import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import org.springframework.stereotype.Component

@Component
class VersionValidator(
    private val versionRepositoryPort: VersionRepositoryPort
) {
    fun filterAlreadyRegistered(version: String): Boolean {
        return versionRepositoryPort.existVersionByNumber(version)
    }

    fun filterAlreadyRegistered(versions: List<String>): List<String> {
        return versions.filter { version -> !filterAlreadyRegistered(version) }
    }

    fun existVersionById(versionId: Long): Boolean {
        return versionRepositoryPort.existVersionById(versionId)
    }
}