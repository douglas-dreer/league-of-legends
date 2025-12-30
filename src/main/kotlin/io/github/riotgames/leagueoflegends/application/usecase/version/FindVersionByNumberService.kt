package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.domain.port.input.version.ExistVersionByNumberUseCase
import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import org.springframework.stereotype.Service

@Service
class FindVersionByNumberService(
    private val versionRepositoryPort: VersionRepositoryPort
): ExistVersionByNumberUseCase {
    override fun execute(versionNumber: String): Boolean {
        return versionRepositoryPort.existVersionByNumber(versionNumber)
    }
}