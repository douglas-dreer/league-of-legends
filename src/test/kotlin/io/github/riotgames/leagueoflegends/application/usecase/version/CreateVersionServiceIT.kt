package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import io.github.riotgames.leagueoflegends.support.AbstractIntegrationTest
import io.github.riotgames.leagueoflegends.support.fixtures.VersionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
@Transactional
class CreateVersionServiceIT: AbstractIntegrationTest() {

    @Autowired
    lateinit var service: CreateVersionService

    @Autowired
   lateinit var repository: VersionRepositoryPort

    companion object {
        const val VERSION_NUMBER = "13.6.1"
        val domain = VersionFixture.valid(id = null, number = VERSION_NUMBER)
    }

    @Test
    fun `should persist version in database`() {
        val createdVersion = service.execute(domain)

        assertEquals(VERSION_NUMBER, createdVersion.number)
        assertThat(createdVersion.id).isNotNull()

        val persisted = repository.findVersionByNumber(VERSION_NUMBER)
        assertThat(persisted).isNotNull()
    }
}