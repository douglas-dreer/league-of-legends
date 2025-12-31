package io.github.riotgames.leagueoflegends.infrastructure.persistence.adapter

import io.github.riotgames.leagueoflegends.domain.mapper.toDomain
import io.github.riotgames.leagueoflegends.support.factories.VersionEntityFactory
import io.github.riotgames.leagueoflegends.support.fixtures.VersionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
@Transactional
class VersionRepositoryAdapterIT {
    @Autowired
    private lateinit var versionRepositoryAdapter: VersionRepositoryAdapter

    companion object {
        const val VERSION_NUMBER = "13.6.9"
    }

    @BeforeEach
    fun setUp() {
        val versionList = VersionFixture.createVersionList(10)
        versionList.forEach { version -> versionRepositoryAdapter.save(version) }
    }

    @Test
    fun `should new version`() {
        val versionFromSave = VersionEntityFactory().create(number = "14.1.0")
        val savedVersion = versionRepositoryAdapter.save(versionFromSave.toDomain())

        assertThat(savedVersion)
            .extracting { it.number }
            .isEqualTo("14.1.0")
            .isNotNull
    }

    @Test
    fun `should get last version`() {
        val lastVersion = versionRepositoryAdapter.getLastVersion()

        assertThat(lastVersion)
            .extracting { it?.number }
            .isEqualTo(VERSION_NUMBER)
            .isNotNull
    }

    @Test
    fun `should list all versions`() {
        val versions = versionRepositoryAdapter.findAll()
        val quantity = versions.size

        assertThat(versions)
            .hasSize(quantity)
    }

    @Test
    fun `should find version by number`() {
        val version = versionRepositoryAdapter.findVersionByNumber(VERSION_NUMBER)

        assertThat(version)
            .extracting { it?.number }
            .isEqualTo(VERSION_NUMBER)
            .isNotNull
    }

    @Test
    fun `should exist version by id`() {
        val versionId = versionRepositoryAdapter.findAll().first().id
        val exists = versionRepositoryAdapter.existVersionById(versionId ?: 0L)

        assertThat(exists).isTrue
    }

    @Test
    fun `should exist version by number`() {
        val exists = versionRepositoryAdapter.existVersionByNumber(VERSION_NUMBER)

        assertThat(exists).isTrue
    }

    @Test
    fun `should update version`() {
        val versionUpdated = VersionEntityFactory().create(id = 1L, number = "14.1.1")

        val updatedVersion = versionRepositoryAdapter.update(versionUpdated.toDomain())
        assertThat(updatedVersion)
            .extracting { it.number }
            .isEqualTo("14.1.1")
            .isNotNull
    }
}