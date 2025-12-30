package io.github.riotgames.leagueoflegends.infrastructure.input.controller

import io.github.riotgames.leagueoflegends.domain.port.input.version.CreateVersionUseCase
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.mapper.toDomain
import io.github.riotgames.leagueoflegends.infrastructure.input.controller.request.CreateVersionRequest
import io.github.riotgames.leagueoflegends.infrastructure.output.response.VersionApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/versions")
class CreateVersionController (
    private val createVersionUseCase: CreateVersionUseCase
) {
    @PostMapping
    fun execute(@Valid @RequestBody request: CreateVersionRequest): ResponseEntity<VersionApiResponse> {
        val versionSaved = createVersionUseCase.execute(request.toDomain())
        return ResponseEntity.created(URI.create("/versions/${versionSaved.id}")).build()
    }
}