package io.github.riotgames.leagueoflegends.application.validation

import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import org.springframework.stereotype.Component

@Component
class VersionValidator(
    private val versionRepositoryPort: VersionRepositoryPort
) {
    fun isAlreadyRegistered(version: String): Boolean {
        return versionRepositoryPort.existVersionByNumber(version)
    }

    fun isAlreadyRegistered(versions: List<String>): List<String> {
        return versions.filter { version -> !isAlreadyRegistered(version) }
    }

    fun existVersionById(versionId: Long): Boolean {
        return versionRepositoryPort.existVersionById(versionId)
    }
}