package io.github.riotgames.leagueoflegends.application.exception

import io.github.riotgames.leagueoflegends.domain.exception.BusinessException

class VersionNotFoundToSynchronizeException(
    message: String
) : BusinessException(message) {
}