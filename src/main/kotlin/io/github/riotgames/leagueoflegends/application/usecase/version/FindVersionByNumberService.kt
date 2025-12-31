package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.domain.port.input.version.FindVersionByNumberUseCase
import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import org.springframework.stereotype.Service

@Service
class FindVersionByNumberService(
    private val versionRepositoryPort: VersionRepositoryPort
): FindVersionByNumberUseCase {
    override fun execute(versionNumber: String) =
        versionRepositoryPort.findVersionByNumber(versionNumber)
}