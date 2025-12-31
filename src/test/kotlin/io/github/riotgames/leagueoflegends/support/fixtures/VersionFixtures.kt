package io.github.riotgames.leagueoflegends.support.fixtures

import io.github.riotgames.leagueoflegends.domain.model.Version

object VersionFixture {
    fun valid(
        id: Long? = null,
        number: String = "13.6.1",
        isCurrent: Boolean = false
    ) = Version(
        id = id,
        number = number,
        isCurrent = isCurrent
    )
}
