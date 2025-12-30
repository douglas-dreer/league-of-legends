package io.github.riotgames.leagueoflegends.domain.port.input.champion

import io.github.riotgames.leagueoflegends.domain.enums.OrderType
import io.github.riotgames.leagueoflegends.domain.model.Champion

interface FindAllChampionUseCase {
    /**
     * Executes the use case to find all champions with pagination and ordering.
     * @param pageSize The number of champions to return. Default is 50.
     * @param language The language locale for the champion data.
     * @param order The order type (ascending or descending). Default is ascending.
     * @return A list of champions according to the specified parameters.
     * @see OrderType
     * @see io.github.riotgames.leagueoflegends.domain.port.output.ChampionClientPort
     */
    fun execute(pageSize: Int = 50, language: String, order: OrderType = OrderType.ASC): List<Champion>
}