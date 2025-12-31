package io.github.riotgames.leagueoflegends.infrastructure.input.controller.mapper

import io.github.riotgames.leagueoflegends.domain.model.Version
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.request.CreateVersionRequest

fun CreateVersionRequest.toDomain() = Version(number = this.number)