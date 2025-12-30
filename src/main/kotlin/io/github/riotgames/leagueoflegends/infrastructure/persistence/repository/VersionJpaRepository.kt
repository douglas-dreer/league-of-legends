package io.github.riotgames.leagueoflegends.infrastructure.persistence.repository

import io.github.riotgames.leagueoflegends.infrastructure.persistence.entity.VersionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface VersionJpaRepository: JpaRepository<VersionEntity, Long> {
    fun findByIsCurrentTrue(): VersionEntity?
    fun findByNumber(versionNumber: String): VersionEntity?
    fun existsVersionEntityByNumber(versionNumber: String): Boolean

    @Modifying
    @Query("UPDATE VersionEntity v SET v.isCurrent = true WHERE v.id = :versionId")
    fun setVersionAsCurrent(versionId: Long)

    @Modifying
    @Query("UPDATE VersionEntity v SET v.isCurrent = false WHERE v.isCurrent = true")
    fun setPrevisionVersionAsNotCurrent()
}