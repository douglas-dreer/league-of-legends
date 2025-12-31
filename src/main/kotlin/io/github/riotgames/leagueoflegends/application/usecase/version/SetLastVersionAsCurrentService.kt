package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.application.exception.VersionNotFoundRegisteredException
import io.github.riotgames.leagueoflegends.application.validation.VersionValidator
import io.github.riotgames.leagueoflegends.domain.port.input.version.SetLastVersionAsCurrentUseCase
import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SetLastVersionAsCurrentService(
    private val repository: VersionRepositoryPort,
    private val validator: VersionValidator
): SetLastVersionAsCurrentUseCase {
    @Transactional(rollbackOn = [Exception::class])
    override fun execute(versionId: Long) {

        if (!validator.existVersionById(versionId)) {
            throw VersionNotFoundRegisteredException(versionId)
        }

        repository.setVersionAsCurrent(versionId)
    }
}