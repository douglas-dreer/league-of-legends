package io.github.riotgames.leagueoflegends.support.factories

import io.github.riotgames.leagueoflegends.infrastructure.persistence.entity.VersionEntity

/**
 * Factory class for creating VersionEntity objects for testing purposes.
 */
class VersionEntityFactory {
    companion object {
        const val ID: Long = 1L
        const val NUMBER: String = "13.6.1"
    }

    /**
     * Creates a VersionEntity object with the specified parameters.
     * @param id The ID of the version entity. Default is 1L.
     * @param number The version number. Default is "13.6.1".
     * @return A VersionEntity object.
     */
    fun create(
        id: Long = ID,
        number: String = NUMBER,
    ): VersionEntity {
        return VersionEntity(
            number = number
        ).apply { this.id = id }
    }

    /**
     * Creates a list of VersionEntity objects with predefined version numbers.
     * @return A list of VersionEntity objects.
     */
    fun createList(): List<VersionEntity> {
        return listOf(
            create(number = "13.6.1"),
            create(number = "13.6.0"),
            create(number = "13.5.1"),
            create(number = "13.5.0"),
            create(number = "13.4.1"),
            create(number = "13.4.0"),
            create(number = "13.3.1"),
            create(number = "13.3.0"),
            create(number = "13.2.1"),
            create(number = "13.2.0"),
            create(number = "13.1.1"),
            create(number = "13.1.0"),
            create(number = "13.0.1"),
            create(number = "13.0.0")
        )
    }
}