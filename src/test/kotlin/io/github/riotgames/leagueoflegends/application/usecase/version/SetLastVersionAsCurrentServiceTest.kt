package io.github.riotgames.leagueoflegends.application.usecase.version

import io.github.riotgames.leagueoflegends.application.exception.VersionNotFoundRegisteredException
import io.github.riotgames.leagueoflegends.application.validation.VersionValidator
import io.github.riotgames.leagueoflegends.domain.port.output.VersionRepositoryPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class SetLastVersionAsCurrentServiceTest {
    @InjectMocks
    private lateinit var service: SetLastVersionAsCurrentService

    @Mock
    private lateinit var repository: VersionRepositoryPort

    @Mock
    private lateinit var validator: VersionValidator

    companion object {
        private const val VERSION_ID = 1L
        private const val MESSAGE_ERROR = "Version $VERSION_ID not found"
    }

    @Test
    fun `must set the last version as current successfully`() {
        whenever(validator.existVersionById(VERSION_ID)).thenReturn(true)

        assertDoesNotThrow { service.execute(VERSION_ID) }

        verify(validator, times(1)).existVersionById(VERSION_ID)
        verifyNoMoreInteractions(validator)
    }

    @Test
    fun `must throw exception when version does not exist`() {
        whenever(validator.existVersionById(VERSION_ID)).thenReturn(false)

        val result = assertThrows<VersionNotFoundRegisteredException> { service.execute(VERSION_ID) }
        assertThat(result.message).isEqualTo(MESSAGE_ERROR)

        verify(validator, times(1)).existVersionById(VERSION_ID)
        verifyNoMoreInteractions(validator)
    }
}