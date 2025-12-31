package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.application.validation.VersionValidator
import io.github.riotgames.leagueoflegends.domain.port.input.version.CreateVersionUseCase
import io.github.riotgames.leagueoflegends.domain.port.output.VersionClientPort
import io.github.riotgames.leagueoflegends.support.fixtures.VersionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

import org.springframework.context.ApplicationEventPublisher
import kotlin.test.Test


@ExtendWith(MockitoExtension::class)
class SynchronizeVersionsServiceTest {

    @InjectMocks
    private lateinit var service: SynchronizeVersionsService

    @Mock
    private lateinit var validator: VersionValidator

    @Mock
    private lateinit var client: VersionClientPort

    @Mock
    private lateinit var createVersionUseCase: CreateVersionUseCase

    @Mock
    private lateinit var eventPublisher: ApplicationEventPublisher

    companion object {
        private const val VERSION_ID = 1L
        private const val VERSION_NUMEBER = "13.6.1"
        private val apiResponseList = listOf("13.6.1", "13.6.0", "13.5.1")
        private val version = VersionFixture.valid(VERSION_ID, VERSION_NUMEBER)
    }

    @Nested
    inner class WhenNoNewVersionsAreReturned {

        @Test
        fun `should return zero and not create versions`() {
            whenever(client.findAllVersions())
                .thenReturn(apiResponseList)

            whenever(validator.filterAlreadyRegistered(apiResponseList))
                .thenReturn(emptyList())

            val result = service.execute()

            assertThat(result).isEqualTo(0L)

            verify(createVersionUseCase, never()).execute(any())
            verify(eventPublisher, never()).publishEvent(any())
        }
    }

    @Nested
    inner class WhenNewVersionsAreReturned {

        @Test
        fun `should create all new versions`() {
            whenever(client.findAllVersions())
                .thenReturn(apiResponseList)

            whenever(validator.filterAlreadyRegistered(apiResponseList))
                .thenReturn(apiResponseList)

            whenever(createVersionUseCase.execute(any()))
                .thenReturn(version)

            val result = service.execute()

            assertThat(result).isEqualTo(apiResponseList.size.toLong())

            verify(createVersionUseCase, times(apiResponseList.size))
                .execute(any())
        }
    }
}