package io.github.riotgames.leagueoflegends.domain.port.output

interface VersionClientPort {
    fun findAllVersions(): List<String>
}