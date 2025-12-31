package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.application.exception.VersionIsAlreadyRegisteredException
import io.github.riotgames.leagueoflegends.application.validation.VersionValidator
import io.github.riotgames.leagueoflegends.domain.model.Version
import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import kotlin.test.Test


@ExtendWith(MockitoExtension::class)
class CreateVersionServiceTest {
    @InjectMocks
    private lateinit var service: CreateVersionService

    @Mock
    private lateinit var repository: VersionRepositoryPort

    @Mock
    private lateinit var validator: VersionValidator

    companion object {
        private val version = Version(id = 1L, number = "1.0.0", isCurrent = true)
        private const val MESSAGE_ERROR = "Version '1.0.0' is already registered."
    }

    @Test
    fun `must return a version when create successfully`() {

        whenever(repository.save(any<Version>())).thenReturn(version)
        whenever(validator.filterAlreadyRegistered(version.number)).thenReturn(false)

        val result = service.execute(version)

        assertThat(result).isEqualTo(version)

        verify(repository, times(1)).save(any())
        verifyNoMoreInteractions(repository)
        verify(validator, times(1)).filterAlreadyRegistered(any<String>())
        verifyNoMoreInteractions(validator)
    }

    @Test
    fun `must throw exception when version is already registered`() {
        whenever(validator.filterAlreadyRegistered(any<String>())).thenReturn(true)

        val result = assertThrows<VersionIsAlreadyRegisteredException> { service.execute(version) }
        assertThat(result).isNotNull()
        assertThat(result.message).isEqualTo(MESSAGE_ERROR)

        verify(repository, never()).save(any())
        verify(validator, times(1)).filterAlreadyRegistered(any<String>())
    }
}