package io.github.riotgames.leagueoflegends.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class representing the stats of a champion.
 * @param hp The base health points of the champion.
 * @param hpPerLevel The health points gained per level.
 * @param mp The base mana points of the champion.
 * @param mpPerLevel The mana points gained per level.
 * @param moveSpeed The movement speed of the champion.
 * @param armor The base armor of the champion.
 * @param armorPerLevel The armor gained per level.
 * @param spellBlock The base magic resist of the champion.
 * @param spellBlockPerLevel The magic resist gained per level.
 * @param attackRange The attack range of the champion.
 * @param hpregen The base health regeneration of the champion.
 * @param hpRegenPerLevel The health regeneration gained per level.
 * @param mpregen The base mana regeneration of the champion.
 * @param mpRegenPerLevel The mana regeneration gained per level.
 * @param crit The base critical strike chance of the champion.
 * @param critPerLevel The critical strike chance gained per level.
 * @param attackDamage The base attack damage of the champion.
 * @param attackDamagePerLevel The attack damage gained per level.
 * @param attackSpeedPerLevel The attack speed gained per level.
 * @param attackSpeed The base attack speed of the champion.
 */
data class ChampionStats(
    val hp: Double,

    @JsonProperty("hpperlevel")
    val hpPerLevel: Double,

    val mp: Double,

    @JsonProperty("mpperlevel")
    val mpPerLevel: Double,

    @JsonProperty("movespeed")
    val moveSpeed: Double,

    val armor: Double,

    @JsonProperty("armorperlevel")
    val armorPerLevel: Double,

    @JsonProperty("spellblock")
    val spellBlock: Double,

    @JsonProperty("spellblockperlevel")
    val spellBlockPerLevel: Double,

    @JsonProperty("attackrange")
    val attackRange: Double,

    val hpregen: Double,

    @JsonProperty("hpregenperlevel")
    val hpRegenPerLevel: Double,

    val mpregen: Double,

    @JsonProperty("mpregenperlevel")
    val mpRegenPerLevel: Double,

    val crit: Double,

    @JsonProperty("critperlevel")
    val critPerLevel: Double,

    @JsonProperty("attackdamage")
    val attackDamage: Double,

    @JsonProperty("attackdamageperlevel")
    val attackDamagePerLevel: Double,

    @JsonProperty("attackspeedperlevel")
    val attackSpeedPerLevel: Double,

    @JsonProperty("attackspeed")
    val attackSpeed: Double
)
