// ============================================================================
// Custom Gradle Task: updateDocsCoverage
// Description: Runs tests, generates JaCoCo report, and updates documentation
//              with real coverage metrics
// Usage: ./gradlew updateDocsCoverage
// ============================================================================

// Register the custom task
tasks.register("updateDocsCoverage") {
    group = "documentation"
    description = "Updates documentation files with current JaCoCo coverage metrics"

    dependsOn("test", "jacocoTestReport")

    doLast {
        val jacocoReportFile = file("build/reports/jacoco/test/jacocoTestReport.xml")

        if (!jacocoReportFile.exists()) {
            throw GradleException("JaCoCo report not found. Run 'gradlew test jacocoTestReport' first.")
        }

        // Parse JaCoCo XML
        val xmlContent = jacocoReportFile.readText()

        // Extract metrics using regex
        fun extractMetric(type: String): Pair<Int, Int> {
            val regex = """<counter type="$type" missed="(\d+)" covered="(\d+)"/>""".toRegex()
            val matches = regex.findAll(xmlContent).toList()

            // Sum all counters of this type (they appear at package level and report level)
            var totalMissed = 0
            var totalCovered = 0

            // Get the last match which is the report-level summary
            val lastMatch = matches.lastOrNull()
            if (lastMatch != null) {
                totalMissed = lastMatch.groupValues[1].toInt()
                totalCovered = lastMatch.groupValues[2].toInt()
            }

            return Pair(totalMissed, totalCovered)
        }

        fun calculatePercentage(missed: Int, covered: Int): Double {
            val total = missed + covered
            return if (total > 0) {
                (covered.toDouble() / total * 100).let {
                    kotlin.math.round(it * 100) / 100
                }
            } else 0.0
        }

        // Extract all metrics
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

        // Determine badge color
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

            // Update Coverage badge
            content = content.replace(
                Regex("""\[\!\[Coverage\]\(https://img\.shields\.io/badge/Coverage-[\d.]+%25-\w+\?logo=codecov\)\]"""),
                "[![Coverage](https://img.shields.io/badge/Coverage-$lineCoverage%25-$badgeColor?logo=codecov)]"
            )

            // Update metrics in table
            content = content.replace(
                Regex("""\| \*\*Line Coverage\*\* \| [\d.]+% \|"""),
                "| **Line Coverage** | $lineCoverage% |"
            )
            content = content.replace(
                Regex("""\| \*\*Branch Coverage\*\* \| [\d.]+% \|"""),
                "| **Branch Coverage** | $branchCoverage% |"
            )
            content = content.replace(
                Regex("""\| \*\*Instruction Coverage\*\* \| [\d.]+% \|"""),
                "| **Instruction Coverage** | $instrCoverage% |"
            )

            readmeFile.writeText(content)
            println("✓ Updated: README.md")
        }

        // Update docs/RELATORIO_ANALISE_TECNICA.md
        val relatorioFile = file("docs/RELATORIO_ANALISE_TECNICA.md")
        if (relatorioFile.exists()) {
            var content = relatorioFile.readText()

            content = content.replace(
                Regex("""\| \*\*Line Coverage\*\* \| [\d.]+% \|"""),
                "| **Line Coverage** | $lineCoverage% |"
            )
            content = content.replace(
                Regex("""\| \*\*Branch Coverage\*\* \| [\d.]+% \|"""),
                "| **Branch Coverage** | $branchCoverage% |"
            )
            content = content.replace(
                Regex("""\| \*\*Instruction Coverage\*\* \| [\d.]+% \|"""),
                "| **Instruction Coverage** | $instrCoverage% |"
            )
            content = content.replace(
                Regex("""\| \*\*Method Coverage\*\* \| [\d.]+% \|"""),
                "| **Method Coverage** | $methodCoverage% |"
            )
            content = content.replace(
                Regex("""\| \*\*Class Coverage\*\* \| [\d.]+% \|"""),
                "| **Class Coverage** | $classCoverage% |"
            )

            relatorioFile.writeText(content)
            println("✓ Updated: docs/RELATORIO_ANALISE_TECNICA.md")
        }

        // Update CHANGELOG.md
        val changelogFile = file("CHANGELOG.md")
        if (changelogFile.exists()) {
            var content = changelogFile.readText()

            content = content.replace(
                Regex("""Line Coverage \([\d.]+%\)"""),
                "Line Coverage ($lineCoverage%)"
            )
            content = content.replace(
                Regex("""Branch Coverage \([\d.]+%\)"""),
                "Branch Coverage ($branchCoverage%)"
            )

            changelogFile.writeText(content)
            println("✓ Updated: CHANGELOG.md")
        }

        println("\n✅ Documentation updated successfully!")
    }
}

// Task to run full analysis with SonarQube
tasks.register("fullAnalysis") {
    group = "verification"
    description = "Runs tests, JaCoCo, SonarQube analysis, and updates documentation"

    dependsOn("test", "jacocoTestReport", "sonar", "updateDocsCoverage")
}

