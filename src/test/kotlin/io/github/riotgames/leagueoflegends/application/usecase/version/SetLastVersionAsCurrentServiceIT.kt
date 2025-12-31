package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import io.github.riotgames.leagueoflegends.support.AbstractIntegrationTest
import io.github.riotgames.leagueoflegends.support.fixtures.VersionFixture
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
@Transactional
class SetLastVersionAsCurrentServiceIT : AbstractIntegrationTest() {

    @Autowired
    lateinit var service: SetLastVersionAsCurrentService

    @Autowired
    lateinit var repository: VersionRepositoryPort

    @Test
    fun `should set last version as current`() {
        val lastIdRegistered = prepareData()
        service.execute(lastIdRegistered)

        val versionIsActived = repository.findCurrentVersion()
        assertThat(versionIsActived).isNotNull()
        assertThat(versionIsActived!!.id).isEqualTo(lastIdRegistered)
    }

    private fun prepareData(): Long {
        var lastId = 0L
        val quantity = 5

        repeat(quantity) { index ->
            val saved = repository.save(
                VersionFixture.valid(
                    id = null,
                    number = "13.0.$index",
                    isCurrent = false
                )
            )
            lastId = saved.id!!
        }

        return lastId
    }
}
