package io.github.riotgames.leagueoflegends.application.exception

import io.github.riotgames.leagueoflegends.domain.exception.ResourceNotFoundException

class VersionNotFoundRegisteredException(versionId: Long) : ResourceNotFoundException("Version $versionId not found") {
}