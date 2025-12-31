package io.github.riotgames.leagueoflegends.infrastructure.persistence.adapter

import io.github.riotgames.leagueoflegends.infrastructure.persistence.repository.VersionJpaRepository
import io.github.riotgames.leagueoflegends.support.factories.VersionEntityFactory
import io.github.riotgames.leagueoflegends.support.fixtures.VersionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class VersionRepositoryAdapterTest {
    @InjectMocks
    private lateinit var adapter: VersionRepositoryAdapter

    @Mock
    private lateinit var repository: VersionJpaRepository

    companion object {
        private const val VERSION_ID = 1L
        private const val VERSION_NUMBER = "11.24.1"
        private val versionResponse = VersionEntityFactory().create(id = VERSION_ID, number = VERSION_NUMBER)
        private val versionDomain = VersionFixture.valid(number = VERSION_NUMBER)
    }

    @Test
    fun `should get last version`() {
        whenever(repository.findFirstByOrderByIdDesc()).thenReturn(versionResponse)

        val result = adapter.getLastVersion()

        assertThat(result).isNotNull
        assertThat(result?.number).isEqualTo(VERSION_NUMBER)


        verify(repository, times(1)).findFirstByOrderByIdDesc()
    }

    @Test
    fun `should save new version`() {
        whenever(repository.save(any())).thenReturn(versionResponse)

        val result = adapter.save(versionDomain)

        assertThat(result).isNotNull
        assertThat(result.number).isEqualTo(VERSION_NUMBER)

        verify(repository, times(1)).save(any())
    }

    @Test
    fun `should find all versions`() {
        whenever(repository.findAll()).thenReturn(listOf(versionResponse))

        val result = adapter.findAll()

        assertThat(result)
            .isNotEmpty
            .hasSize(1)
            .allMatch { it.number == VERSION_NUMBER }

        verify(repository, times(1)).findAll()
    }

    @Test
    fun `should find version by number`() {
        whenever(repository.findByNumber(VERSION_NUMBER)).thenReturn(versionResponse)

        val result = adapter.findVersionByNumber(VERSION_NUMBER)

        assertThat(result).isNotNull
        assertThat(result?.number).isEqualTo(VERSION_NUMBER)

        verify(repository, times(1)).findByNumber(VERSION_NUMBER)
    }

    @Test
    fun `should check if version exists by id`() {
        whenever(repository.existsById(VERSION_ID)).thenReturn(true)

        val result = adapter.existVersionById(VERSION_ID)

        assertThat(result).isTrue

        verify(repository, times(1)).existsById(VERSION_ID)
    }

    @Test
    fun `should update version`() {
        whenever(repository.save(any())).thenReturn(versionResponse)

        val versionUpdated = versionDomain.copy(number = "11.25.1")

        val result = adapter.update(versionUpdated)

        assertThat(result).isNotNull
        assertThat(result.number).isEqualTo(VERSION_NUMBER)

        verify(repository, times(1)).save(any())
    }

}