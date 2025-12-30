package io.github.riotgames.leagueoflegends.application.usecase.champion

import io.github.riotgames.leagueoflegends.domain.enums.OrderType
import io.github.riotgames.leagueoflegends.domain.model.Champion
import io.github.riotgames.leagueoflegends.domain.port.input.champion.FindAllChampionUseCase
import io.github.riotgames.leagueoflegends.domain.port.output.ChampionClientPort
import org.springframework.stereotype.Service

@Service
class FindAllChampionService(
    private val championClientPort: ChampionClientPort
): FindAllChampionUseCase {
    override fun execute(
        pageSize: Int,
        language: String,
        order: OrderType
    ): List<Champion> {
        return championClientPort
            .findAllChampions(language)
            .let { orderById(it, order) }
            .take(pageSize)
            .toList()
    }

    /**
     * Orders the list of champions by their names in the specified order.
     * @param champions The list of champions to be ordered.
     * @param order The order type (ascending or descending).
     * @return A new list of champions ordered by their names.
     * @see OrderType
     */
    private fun orderById(champions: List<Champion>, order: OrderType): List<Champion> {
        return when(order) {
            OrderType.ASC -> champions.sortedBy { champion -> champion.name }
            OrderType.DESC -> champions.sortedByDescending { champion -> champion.name }
        }
    }
}

