package io.github.riotgames.leagueoflegends.domain.port.input.version

interface SetLastVersionAsCurrentUseCase {
    fun execute(versionId: Long)
}