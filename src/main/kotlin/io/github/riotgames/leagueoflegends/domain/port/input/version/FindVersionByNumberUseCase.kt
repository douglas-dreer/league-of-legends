package io.github.riotgames.leagueoflegends.domain.port.input.version

import io.github.riotgames.leagueoflegends.domain.model.Version

interface FindVersionByNumberUseCase {
    fun execute(versionNumber: String): Version?
}