# ============================================================================
# Script: update-docs-coverage.ps1
# Description: Executes tests, generates JaCoCo report, runs SonarQube analysis
#              and updates documentation with real coverage metrics
# Usage: .\scripts\update-docs-coverage.ps1
# ============================================================================

param(
    [string]$SonarToken = "sqp_a97ced22287fc31a48ac55ebb3027d2431c5d6f7",
    [string]$SonarProjectKey = "league-of-Legends",
    [string]$SonarProjectName = "League of Legends"
)

$ErrorActionPreference = "Stop"
$ProjectRoot = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
if (-not $ProjectRoot) {
    $ProjectRoot = Get-Location
}

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  League of Legends - Coverage Update Tool" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Step 1: Run tests and generate JaCoCo report
Write-Host "[1/4] Running tests and generating JaCoCo report..." -ForegroundColor Yellow
try {
    & .\gradlew.bat test jacocoTestReport --no-daemon
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Warning: Tests may have failed, but continuing..." -ForegroundColor Yellow
    }
} catch {
    Write-Host "Warning: Test execution issue: $_" -ForegroundColor Yellow
}

# Step 2: Run SonarQube analysis
Write-Host ""
Write-Host "[2/4] Running SonarQube analysis..." -ForegroundColor Yellow
try {
    & .\gradlew.bat sonar `
        "-Dsonar.projectKey=$SonarProjectKey" `
        "-Dsonar.projectName=$SonarProjectName" `
        "-Dsonar.token=$SonarToken" `
        --no-daemon
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Warning: SonarQube analysis may have issues, continuing with JaCoCo data..." -ForegroundColor Yellow
    }
} catch {
    Write-Host "Warning: SonarQube analysis issue: $_" -ForegroundColor Yellow
}

# Step 3: Parse JaCoCo XML report
Write-Host ""
Write-Host "[3/4] Parsing JaCoCo report..." -ForegroundColor Yellow

$JacocoReportPath = "build/reports/jacoco/test/jacocoTestReport.xml"

if (-not (Test-Path $JacocoReportPath)) {
    Write-Host "Error: JaCoCo report not found at $JacocoReportPath" -ForegroundColor Red
    exit 1
}

[xml]$JacocoXml = Get-Content $JacocoReportPath

# Extract coverage metrics from the root report element
$counters = $JacocoXml.report.counter

$metrics = @{}
foreach ($counter in $counters) {
    $type = $counter.type
    $missed = [int]$counter.missed
    $covered = [int]$counter.covered
    $total = $missed + $covered
    if ($total -gt 0) {
        $percentage = [math]::Round(($covered / $total) * 100, 2)
    } else {
        $percentage = 0
    }
    $metrics[$type] = @{
        Missed = $missed
        Covered = $covered
        Total = $total
        Percentage = $percentage
    }
}

Write-Host ""
Write-Host "Coverage Metrics:" -ForegroundColor Green
Write-Host "  Line Coverage:        $($metrics['LINE'].Percentage)% ($($metrics['LINE'].Covered)/$($metrics['LINE'].Total))"
Write-Host "  Branch Coverage:      $($metrics['BRANCH'].Percentage)% ($($metrics['BRANCH'].Covered)/$($metrics['BRANCH'].Total))"
Write-Host "  Instruction Coverage: $($metrics['INSTRUCTION'].Percentage)% ($($metrics['INSTRUCTION'].Covered)/$($metrics['INSTRUCTION'].Total))"
Write-Host "  Method Coverage:      $($metrics['METHOD'].Percentage)% ($($metrics['METHOD'].Covered)/$($metrics['METHOD'].Total))"
Write-Host "  Class Coverage:       $($metrics['CLASS'].Percentage)% ($($metrics['CLASS'].Covered)/$($metrics['CLASS'].Total))"
Write-Host "  Complexity Coverage:  $($metrics['COMPLEXITY'].Percentage)% ($($metrics['COMPLEXITY'].Covered)/$($metrics['COMPLEXITY'].Total))"

# Step 4: Update documentation files
Write-Host ""
Write-Host "[4/4] Updating documentation..." -ForegroundColor Yellow

$LineCoverage = $metrics['LINE'].Percentage
$BranchCoverage = $metrics['BRANCH'].Percentage
$InstructionCoverage = $metrics['INSTRUCTION'].Percentage
$MethodCoverage = $metrics['METHOD'].Percentage
$ClassCoverage = $metrics['CLASS'].Percentage

# Determine badge color based on coverage
function Get-BadgeColor {
    param([double]$Coverage)
    if ($Coverage -ge 80) { return "brightgreen" }
    elseif ($Coverage -ge 60) { return "green" }
    elseif ($Coverage -ge 40) { return "yellow" }
    elseif ($Coverage -ge 20) { return "orange" }
    else { return "red" }
}

$BadgeColor = Get-BadgeColor -Coverage $LineCoverage

# Update README.md
$ReadmePath = "README.md"
if (Test-Path $ReadmePath) {
    $ReadmeContent = Get-Content $ReadmePath -Raw

    # Update Coverage badge
    $ReadmeContent = $ReadmeContent -replace `
        '\[\!\[Coverage\]\(https://img\.shields\.io/badge/Coverage-[\d\.]+%25-\w+\?logo=codecov\)\]', `
        "[![Coverage](https://img.shields.io/badge/Coverage-$LineCoverage%25-$BadgeColor`?logo=codecov)]"

    # Update metrics table in README
    $ReadmeContent = $ReadmeContent -replace `
        '\| \*\*Line Coverage\*\* \| [\d\.]+% \|', `
        "| **Line Coverage** | $LineCoverage% |"

    $ReadmeContent = $ReadmeContent -replace `
        '\| \*\*Branch Coverage\*\* \| [\d\.]+% \|', `
        "| **Branch Coverage** | $BranchCoverage% |"

    $ReadmeContent = $ReadmeContent -replace `
        '\| \*\*Instruction Coverage\*\* \| [\d\.]+% \|', `
        "| **Instruction Coverage** | $InstructionCoverage% |"

    Set-Content $ReadmePath -Value $ReadmeContent -NoNewline
    Write-Host "  Updated: README.md" -ForegroundColor Green
}

# Update docs/RELATORIO_ANALISE_TECNICA.md
$RelatorioPath = "docs/RELATORIO_ANALISE_TECNICA.md"
if (Test-Path $RelatorioPath) {
    $RelatorioContent = Get-Content $RelatorioPath -Raw

    # Update coverage metrics
    $RelatorioContent = $RelatorioContent -replace `
        '\| \*\*Line Coverage\*\* \| [\d\.]+% \|', `
        "| **Line Coverage** | $LineCoverage% |"

    $RelatorioContent = $RelatorioContent -replace `
        '\| \*\*Branch Coverage\*\* \| [\d\.]+% \|', `
        "| **Branch Coverage** | $BranchCoverage% |"

    $RelatorioContent = $RelatorioContent -replace `
        '\| \*\*Instruction Coverage\*\* \| [\d\.]+% \|', `
        "| **Instruction Coverage** | $InstructionCoverage% |"

    $RelatorioContent = $RelatorioContent -replace `
        '\| \*\*Method Coverage\*\* \| [\d\.]+% \|', `
        "| **Method Coverage** | $MethodCoverage% |"

    $RelatorioContent = $RelatorioContent -replace `
        '\| \*\*Class Coverage\*\* \| [\d\.]+% \|', `
        "| **Class Coverage** | $ClassCoverage% |"

    Set-Content $RelatorioPath -Value $RelatorioContent -NoNewline
    Write-Host "  Updated: docs/RELATORIO_ANALISE_TECNICA.md" -ForegroundColor Green
}

# Update CHANGELOG.md (latest version section)
$ChangelogPath = "CHANGELOG.md"
if (Test-Path $ChangelogPath) {
    $ChangelogContent = Get-Content $ChangelogPath -Raw

    $ChangelogContent = $ChangelogContent -replace `
        'Line Coverage \([\d\.]+%\)', `
        "Line Coverage ($LineCoverage%)"

    $ChangelogContent = $ChangelogContent -replace `
        'Branch Coverage \([\d\.]+%\)', `
        "Branch Coverage ($BranchCoverage%)"

    Set-Content $ChangelogPath -Value $ChangelogContent -NoNewline
    Write-Host "  Updated: CHANGELOG.md" -ForegroundColor Green
}

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  Coverage Update Complete!" -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Summary:" -ForegroundColor White
Write-Host "  Line Coverage:   $LineCoverage%" -ForegroundColor $(if ($LineCoverage -ge 80) { "Green" } else { "Yellow" })
Write-Host "  Branch Coverage: $BranchCoverage%" -ForegroundColor $(if ($BranchCoverage -ge 80) { "Green" } else { "Yellow" })
Write-Host ""
Write-Host "Documentation files updated successfully!" -ForegroundColor Green

