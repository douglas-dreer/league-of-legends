package io.github.riotgames.leagueoflegends.infrastructure.input.controller

import io.github.riotgames.leagueoflegends.domain.port.input.version.SynchronizeVersionsUseCase
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.response.SyncApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin/versions/import")
class SynchronizeVersionsController(
    private final val useCase: SynchronizeVersionsUseCase
) {

    @PostMapping
    fun execute(): ResponseEntity<SyncApiResponse> {
        val quantitySynchronized = useCase.execute()

        if (quantitySynchronized == 0L) {
            val syncApiResponse = SyncApiResponse.noContent()
            return status(204).body(syncApiResponse)
        }

        val syncApiResponse = SyncApiResponse.success(quantity = quantitySynchronized)
        return ok(syncApiResponse)
    }
}