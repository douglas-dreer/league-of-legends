package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.application.exception.VersionIsAlreadyRegisteredException
import io.github.riotgames.leagueoflegends.application.validation.VersionValidator
import io.github.riotgames.leagueoflegends.domain.model.Version
import io.github.riotgames.leagueoflegends.domain.port.input.version.CreateVersionUseCase
import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import org.springframework.stereotype.Service

@Service
class CreateVersionService(
    private val repository: VersionRepositoryPort,
    private val validator: VersionValidator
): CreateVersionUseCase {

    override fun execute(version: Version): Version {
        if (validator.isAlreadyRegistered(version.number)) {
            throw VersionIsAlreadyRegisteredException(version.number)
        }
        return repository.save(version)
    }
}