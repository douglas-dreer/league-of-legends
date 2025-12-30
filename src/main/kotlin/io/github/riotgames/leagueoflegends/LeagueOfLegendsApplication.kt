package io.github.riotgames.leagueoflegends

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableFeignClients(basePackages = ["io.github.riotgames.leagueoflegends.infrastructure.output.client"])
@EnableScheduling
class LeagueOfLegendsApplication

fun main(args: Array<String>) {
    runApplication<LeagueOfLegendsApplication>(*args)
}
