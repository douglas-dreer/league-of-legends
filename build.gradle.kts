plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"

    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"

    jacoco
    id("org.sonarqube") version "7.2.0.6526"
}

val springCloudVersion by extra("2025.1.0")

group = "io.github.riot-games"
version = "1.3.0"
description = "league-of-legends"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("tools.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")

    testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")

    testImplementation("org.testcontainers:junit-jupiter:1.21.4")
    testImplementation("org.testcontainers:postgresql:1.21.4")

    testImplementation("org.mockito:mockito-core:5.21.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.assertj:assertj-core:3.25.3")

    runtimeOnly("org.postgresql:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "league-of-legends")
        property("sonar.projectName", "League of Legends")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", "sqp_9d23316fe9b01447787e00999e83b473b5b55da2")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

    finalizedBy(tasks.jacocoTestReport)

    try {
        jvmArgs(
            "-javaagent:${configurations.testRuntimeClasspath.get().files
                .first { it.name.contains("mockito-core") }}"
        )
    } catch (e: Exception) {
        println("⚠️ Aviso: Não foi possível configurar o javaagent do Mockito automaticamente.")
    }
}

tasks.withType<JacocoReport> {
    dependsOn(tasks.withType<Test>())

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

// ============================================================================
// Custom Task: updateDocsCoverage
// Updates documentation files with current JaCoCo coverage metrics
// Usage: ./gradlew updateDocsCoverage
// ============================================================================
tasks.register("updateDocsCoverage") {
    group = "documentation"
    description = "Updates documentation files with current JaCoCo coverage metrics"

    dependsOn("test", "jacocoTestReport")

    doLast {
        val jacocoReportFile = file("build/reports/jacoco/test/jacocoTestReport.xml")

        if (!jacocoReportFile.exists()) {
            throw GradleException("JaCoCo report not found. Run 'gradlew test jacocoTestReport' first.")
        }

        val xmlContent = jacocoReportFile.readText()

        fun extractMetric(type: String): Pair<Int, Int> {
            val regex = """<counter type="$type" missed="(\d+)" covered="(\d+)"/>""".toRegex()
            val matches = regex.findAll(xmlContent).toList()
            val lastMatch = matches.lastOrNull()
            return if (lastMatch != null) {
                Pair(lastMatch.groupValues[1].toInt(), lastMatch.groupValues[2].toInt())
            } else Pair(0, 0)
        }

        fun calculatePercentage(missed: Int, covered: Int): Double {
            val total = missed + covered
            return if (total > 0) Math.round((covered.toDouble() / total * 100) * 100.0) / 100.0 else 0.0
        }

        val (lineMissed, lineCovered) = extractMetric("LINE")
        val (branchMissed, branchCovered) = extractMetric("BRANCH")
        val (instrMissed, instrCovered) = extractMetric("INSTRUCTION")
        val (methodMissed, methodCovered) = extractMetric("METHOD")
        val (classMissed, classCovered) = extractMetric("CLASS")

        val lineCoverage = calculatePercentage(lineMissed, lineCovered)
        val branchCoverage = calculatePercentage(branchMissed, branchCovered)
        val instrCoverage = calculatePercentage(instrMissed, instrCovered)
        val methodCoverage = calculatePercentage(methodMissed, methodCovered)
        val classCoverage = calculatePercentage(classMissed, classCovered)

        println("\n========================================")
        println("  Coverage Metrics")
        println("========================================")
        println("  Line Coverage:        $lineCoverage% ($lineCovered/${lineMissed + lineCovered})")
        println("  Branch Coverage:      $branchCoverage% ($branchCovered/${branchMissed + branchCovered})")
        println("  Instruction Coverage: $instrCoverage% ($instrCovered/${instrMissed + instrCovered})")
        println("  Method Coverage:      $methodCoverage% ($methodCovered/${methodMissed + methodCovered})")
        println("  Class Coverage:       $classCoverage% ($classCovered/${classMissed + classCovered})")
        println("========================================\n")

        val badgeColor = when {
            lineCoverage >= 80 -> "brightgreen"
            lineCoverage >= 60 -> "green"
            lineCoverage >= 40 -> "yellow"
            lineCoverage >= 20 -> "orange"
            else -> "red"
        }

        // Update README.md
        val readmeFile = file("README.md")
        if (readmeFile.exists()) {
            var content = readmeFile.readText()
            content = content.replace(
                Regex("""\[\!\[Coverage\]\(https://img\.shields\.io/badge/Coverage-[\d.]+%25-\w+\?logo=codecov\)\]"""),
                "[![Coverage](https://img.shields.io/badge/Coverage-$lineCoverage%25-$badgeColor?logo=codecov)]"
            )
            content = content.replace(Regex("""\| \*\*Line Coverage\*\* \| [\d.]+% \|"""), "| **Line Coverage** | $lineCoverage% |")
            content = content.replace(Regex("""\| \*\*Branch Coverage\*\* \| [\d.]+% \|"""), "| **Branch Coverage** | $branchCoverage% |")
            content = content.replace(Regex("""\| \*\*Instruction Coverage\*\* \| [\d.]+% \|"""), "| **Instruction Coverage** | $instrCoverage% |")
            readmeFile.writeText(content)
            println("✓ Updated: README.md")
        }

        // Update docs/RELATORIO_ANALISE_TECNICA.md
        val relatorioFile = file("docs/RELATORIO_ANALISE_TECNICA.md")
        if (relatorioFile.exists()) {
            var content = relatorioFile.readText()
            content = content.replace(Regex("""\| \*\*Line Coverage\*\* \| [\d.]+% \|"""), "| **Line Coverage** | $lineCoverage% |")
            content = content.replace(Regex("""\| \*\*Branch Coverage\*\* \| [\d.]+% \|"""), "| **Branch Coverage** | $branchCoverage% |")
            content = content.replace(Regex("""\| \*\*Instruction Coverage\*\* \| [\d.]+% \|"""), "| **Instruction Coverage** | $instrCoverage% |")
            content = content.replace(Regex("""\| \*\*Method Coverage\*\* \| [\d.]+% \|"""), "| **Method Coverage** | $methodCoverage% |")
            content = content.replace(Regex("""\| \*\*Class Coverage\*\* \| [\d.]+% \|"""), "| **Class Coverage** | $classCoverage% |")
            relatorioFile.writeText(content)
            println("✓ Updated: docs/RELATORIO_ANALISE_TECNICA.md")
        }

        // Update CHANGELOG.md
        val changelogFile = file("CHANGELOG.md")
        if (changelogFile.exists()) {
            var content = changelogFile.readText()
            content = content.replace(Regex("""Line Coverage \([\d.]+%\)"""), "Line Coverage ($lineCoverage%)")
            content = content.replace(Regex("""Branch Coverage \([\d.]+%\)"""), "Branch Coverage ($branchCoverage%)")
            changelogFile.writeText(content)
            println("✓ Updated: CHANGELOG.md")
        }

        println("\n✅ Documentation updated successfully!")
    }
}

// Task to run full analysis with SonarQube and update docs
tasks.register("fullAnalysis") {
    group = "verification"
    description = "Runs tests, JaCoCo, SonarQube analysis, and updates documentation"

    dependsOn("test", "jacocoTestReport")
    finalizedBy("updateDocsCoverage")

    doLast {
        println("\n🔍 Running SonarQube analysis...")
    }
}
