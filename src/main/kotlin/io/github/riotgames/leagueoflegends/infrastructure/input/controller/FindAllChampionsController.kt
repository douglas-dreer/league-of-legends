package io.github.riotgames.leagueoflegends.infrastructure.input.controller

import io.github.riotgames.leagueoflegends.application.usecase.champion.FindAllChampionService
import io.github.riotgames.leagueoflegends.domain.model.Champion
import io.github.riotgames.leagueoflegends.domain.enums.OrderType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/champions")
class FindAllChampionsController(
    private final val findAllChampionsUseCase: FindAllChampionService
) {

    @GetMapping
    fun execute(
        @RequestParam(value = "pageSize", defaultValue = "50") pageSize: Int,
        @RequestParam(value = "order", defaultValue = "ASC") order: OrderType,
        @RequestParam(value = "language", defaultValue = "en_US") language: String,
    ): ResponseEntity<List<Champion>> {
        val champions = findAllChampionsUseCase.execute(pageSize, language, order)
        return ResponseEntity.ok(champions)
    }
}