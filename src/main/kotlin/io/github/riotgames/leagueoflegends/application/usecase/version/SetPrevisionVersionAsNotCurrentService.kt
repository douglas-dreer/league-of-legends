package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.domain.port.input.version.SetPrevisionVersionAsNotCurrentUseCase
import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SetPrevisionVersionAsNotCurrentService(
    private val repositoryPort: VersionRepositoryPort,
): SetPrevisionVersionAsNotCurrentUseCase {
    @Transactional(rollbackOn = [Exception::class])
    override fun execute() {
        repositoryPort.setPrevisionVersionAsNotCurrent()
    }
}