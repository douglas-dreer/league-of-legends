# Scripts de Automação

Esta pasta contém scripts para automação de tarefas do projeto.

## 📊 update-docs-coverage.ps1

Script PowerShell para executar análise de cobertura e atualizar a documentação.

### Uso

```powershell
# Uso básico (usa configurações padrão)
.\scripts\update-docs-coverage.ps1

# Com token SonarQube personalizado
.\scripts\update-docs-coverage.ps1 -SonarToken "seu-token-aqui"

# Com todas as opções
.\scripts\update-docs-coverage.ps1 `
  -SonarToken "sqp_xxx" `
  -SonarProjectKey "league-of-Legends" `
  -SonarProjectName "League of Legends"
```

### O que o script faz

1. **Executa os testes** (`gradlew test`)
2. **Gera relatório JaCoCo** (`gradlew jacocoTestReport`)
3. **Executa análise SonarQube** (`gradlew sonar`)
4. **Lê métricas do JaCoCo XML**
5. **Atualiza os arquivos de documentação**:
   - `README.md` - Badges e tabela de métricas
   - `CHANGELOG.md` - Métricas na versão atual
   - `docs/RELATORIO_ANALISE_TECNICA.md` - Tabela detalhada

---

## 🐘 Gradle Tasks

Alternativamente, use as tasks Gradle diretamente:

```bash
# Atualizar documentação com métricas de cobertura
./gradlew updateDocsCoverage

# Análise completa (testes + SonarQube + atualização docs)
./gradlew fullAnalysis
```

### Tasks Disponíveis

| Task | Grupo | Descrição |
|------|-------|-----------|
| `updateDocsCoverage` | documentation | Atualiza docs com métricas JaCoCo |
| `fullAnalysis` | verification | Executa análise completa |

---

## 📈 Métricas Atualizadas

As seguintes métricas são extraídas do relatório JaCoCo e atualizadas nos documentos:

| Métrica | Descrição |
|---------|-----------|
| **Line Coverage** | Cobertura de linhas de código |
| **Branch Coverage** | Cobertura de branches (if/else/switch) |
| **Instruction Coverage** | Cobertura de instruções bytecode |
| **Method Coverage** | Cobertura de métodos |
| **Class Coverage** | Cobertura de classes |

---

## 🎨 Cores das Badges

A cor da badge de cobertura é determinada automaticamente:

| Cobertura | Cor |
|-----------|-----|
| ≥ 80% | 🟢 brightgreen |
| ≥ 60% | 🟢 green |
| ≥ 40% | 🟡 yellow |
| ≥ 20% | 🟠 orange |
| < 20% | 🔴 red |

