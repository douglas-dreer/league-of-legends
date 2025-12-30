package io.github.riotgames.leagueoflegends.application.exception

import io.github.riotgames.leagueoflegends.domain.exception.BusinessException

class VersionIsAlreadyRegisteredException(
    version: String
) : BusinessException("Version '$version' is already registered.")