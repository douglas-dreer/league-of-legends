package io.github.riotgames.leagueoflegends.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "versions")
class VersionEntity(

    @Column(nullable = false, unique = true)
    var number: String = "",

    @Column(name = "is_current")
    var isCurrent: Boolean = false

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VersionEntity) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "VersionEntity(id=$id, number='$number', isCurrent=$isCurrent)"
    }
}