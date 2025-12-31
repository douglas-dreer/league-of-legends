package io.github.riotgames.leagueoflegends.infrastructure.output.adapter

import io.github.riotgames.leagueoflegends.infrastructure.output.client.VersionClient
import io.github.riotgames.leagueoflegends.support.fixtures.VersionFixture
import org.assertj.core.api.Assertions.assertThat
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
@Transactional
class VersionClientAdapterIT {
    @Autowired
    private lateinit var adapter: VersionClientAdapter

    @MockitoBean
    private lateinit var versionClient: VersionClient

    companion object {
        val apiResponse = VersionFixture.versionList()
    }

    @Test
    fun `should fetch all versions from external api`() {
        whenever(versionClient.findAllVersions()).thenReturn(apiResponse)
        val result = adapter.findAllVersions()

        assertThat(result).isEqualTo(apiResponse)
        assertThat(result.size).isEqualTo(apiResponse.size)
    }

}