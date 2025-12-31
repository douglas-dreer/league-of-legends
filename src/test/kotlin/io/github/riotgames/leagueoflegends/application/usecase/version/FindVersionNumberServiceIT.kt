package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import io.github.riotgames.leagueoflegends.support.fixtures.VersionFixture
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
@Transactional
class FindVersionNumberServiceIT {
    @Autowired
    private lateinit var repository: VersionRepositoryPort

    @Autowired
    private lateinit var service: FindVersionByNumberService

    companion object {
        const val VERSION_NUMBER = "13.6.0"
        val version = VersionFixture.createVersionList(5)
    }

    @Test
    fun `should find version by number`() {
        prepareData()

        val result = service.execute(VERSION_NUMBER)
        assertThat(result).isNotNull
        assertThat(result?.number).isEqualTo(VERSION_NUMBER)
    }

    @Test
    fun `should not found version by number`() {
        prepareData()

        val result = service.execute("1.0.0")
        assertThat(result).isNull()
    }

    private fun prepareData() {
        version.forEach { repository.save(it) }
    }





}