package io.github.riotgames.leagueoflegends.support.fixtures

import io.github.riotgames.leagueoflegends.domain.model.Version

object VersionFixture {
    fun valid(
        id: Long? = null,
        number: String = "13.6.1"
    ) = Version(
        id = id,
        number = number
    )

    fun createVersionList(size: Int = 1): List<Version> {
        return (1..size).map { index ->
            valid(
                id = index.toLong(),
                number = "13.6.${index - 1}"
            )
        }
    }

    fun versionList(): List<String> {
        return listOf(
            "13.6.1",
            "13.6.0",
            "13.5.1",
            "13.5.0",
            "13.4.1",
            "13.4.0",
            "13.3.1",
            "13.3.0",
            "13.2.1",
            "13.2.0",
            "13.1.1",
            "13.1.0",
            "13.0.1",
            "13.0.0"
        )
}

}
