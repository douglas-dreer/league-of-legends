package io.github.riotgames.leagueoflegends.infrastructure.persistence.adapter

import io.github.riotgames.leagueoflegends.domain.mapper.toDomain
import io.github.riotgames.leagueoflegends.domain.mapper.toEntity
import io.github.riotgames.leagueoflegends.domain.model.Version
import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import io.github.riotgames.leagueoflegends.infrastructure.persistence.repository.VersionJpaRepository
import org.springframework.stereotype.Component

@Component
class VersionRepositoryAdapter(
    private val repository: VersionJpaRepository
): VersionRepositoryPort {
    override fun save(version: Version): Version {
        return repository
            .save(version.toEntity())
            .toDomain()
    }

    override fun findAll(): List<Version> {
        return repository
            .findAll()
            .map { it.toDomain() }
    }

    override fun findCurrentVersion(): Version? {
        return repository
            .findByIsCurrentTrue()
            ?.toDomain()
    }

    override fun update(version: Version): Version {
       return repository
           .save(version.toEntity())
           .toDomain()
    }

    override fun findVersionByNumber(versionNumber: String): Version? {
        return repository
            .findByNumber(versionNumber)
            ?.toDomain()
    }

    override fun existVersionById(versionId: Long): Boolean {
        return repository
            .existsById(versionId)
    }

    override fun existVersionByNumber(versionNumber: String): Boolean {
        return repository
            .existsVersionEntityByNumber(versionNumber)
    }

    override fun setVersionAsCurrent(versionId: Long) {
        repository.setVersionAsCurrent(versionId)
    }

    override fun setPrevisionVersionAsNotCurrent() {
        repository.setPrevisionVersionAsNotCurrent()
    }
}