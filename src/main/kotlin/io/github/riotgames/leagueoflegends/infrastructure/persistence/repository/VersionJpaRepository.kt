package io.github.riotgames.leagueoflegends.infrastructure.persistence.repository

import io.github.riotgames.leagueoflegends.infrastructure.persistence.entity.VersionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VersionJpaRepository: JpaRepository<VersionEntity, Long> {
    fun findFirstByOrderByIdDesc(): VersionEntity?
    fun findByNumber(versionNumber: String): VersionEntity?
    fun existsVersionEntityByNumber(versionNumber: String): Boolean
}