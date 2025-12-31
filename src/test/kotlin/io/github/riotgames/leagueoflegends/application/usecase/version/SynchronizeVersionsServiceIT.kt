package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.application.validation.VersionValidator
import io.github.riotgames.leagueoflegends.domain.port.output.VersionClientPort
import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import io.github.riotgames.leagueoflegends.support.AbstractIntegrationTest
import io.github.riotgames.leagueoflegends.support.fixtures.VersionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test


@SpringBootTest
@Transactional
class SynchronizeVersionsServiceIT: AbstractIntegrationTest() {
    @Autowired
    lateinit var service: SynchronizeVersionsService

    @Autowired
    private lateinit var repository: VersionRepositoryPort

    @MockitoBean
    private lateinit var client: VersionClientPort

    @MockitoBean
    private lateinit var validator: VersionValidator


    companion object {
        val apiResonse = VersionFixture.versionList()
    }

    @Nested
    inner class WhenNoNewVersionsAreReturned {

        @Test
        fun `should return zero and not create versions`() {
            whenever(client.findAllVersions())
                .thenReturn(apiResonse)

            whenever(validator.filterAlreadyRegistered(apiResonse))
                .thenReturn(emptyList())

            val result = service.execute()

            assertThat(result).isZero()
        }
    }

    @Test
    fun `should create all new versions`() {
        whenever(client.findAllVersions()).thenReturn(apiResonse)
        whenever(validator.filterAlreadyRegistered(apiResonse)).thenReturn(apiResonse)
        val quantitySynchronized = service.execute()


        assertThat(quantitySynchronized).isEqualTo(apiResonse.size.toLong())

        val result = repository.findAll()
        assertThat(result)
            .hasSize(apiResonse.size)
            .extracting("number")
            .containsExactlyInAnyOrderElementsOf(apiResonse)

    }
}