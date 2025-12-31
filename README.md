# 🎮 League of Legends API

API REST para consulta de dados de campeões e versões do **League of Legends**, utilizando a **Data Dragon API** da Riot Games.

![Versão](https://img.shields.io/badge/Versão-1.3.0-blue)
![Kotlin](https://img.shields.io/badge/Kotlin-2.2.21-purple?logo=kotlin)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-green?logo=springboot)
![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)
![Licença](https://img.shields.io/badge/Licença-MIT-green)

### 📊 Qualidade de Código

[![Quality Gate Status](https://img.shields.io/badge/Quality%20Gate-Passed-brightgreen?logo=sonarqube)](http://localhost:9000/dashboard?id=league-of-legends)
[![Coverage](https://img.shields.io/badge/Coverage-27.25%25-orange?logo=codecov)](http://localhost:9000/component_measures?id=league-of-legends&metric=coverage)
[![Bugs](https://img.shields.io/badge/Bugs-0-brightgreen?logo=sonarqube)](http://localhost:9000/project/issues?id=league-of-legends&resolved=false&types=BUG)
[![Code Smells](https://img.shields.io/badge/Code%20Smells-0-brightgreen?logo=sonarqube)](http://localhost:9000/project/issues?id=league-of-legends&resolved=false&types=CODE_SMELL)
[![Vulnerabilities](https://img.shields.io/badge/Vulnerabilities-0-brightgreen?logo=sonarqube)](http://localhost:9000/project/issues?id=league-of-legends&resolved=false&types=VULNERABILITY)
[![Security Hotspots](https://img.shields.io/badge/Security%20Hotspots-0-brightgreen?logo=sonarqube)](http://localhost:9000/security_hotspots?id=league-of-legends)
[![Duplicated Lines](https://img.shields.io/badge/Duplications-0%25-brightgreen?logo=sonarqube)](http://localhost:9000/component_measures?id=league-of-legends&metric=duplicated_lines_density)
[![Technical Debt](https://img.shields.io/badge/Technical%20Debt-0min-brightgreen?logo=sonarqube)](http://localhost:9000/component_measures?id=league-of-legends&metric=sqale_index)

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Arquitetura](#-arquitetura)
- [Tecnologias](#-tecnologias)
- [Funcionalidades](#-funcionalidades)
- [Requisitos](#-requisitos)
- [Instalação](#-instalação)
- [Endpoints](#-endpoints)
- [Tratamento de Erros](#-tratamento-de-erros)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Status do Desenvolvimento](#-status-do-desenvolvimento)
- [Testes](#-testes)
- [Qualidade de Código](#-qualidade-de-código)
- [Changelog](#-changelog)
- [Documentação Técnica](#-documentação-técnica)

---

## 📖 Sobre o Projeto

Esta API consome dados da [Data Dragon API](https://developer.riotgames.com/docs/lol#data-dragon) da Riot Games para fornecer informações sobre:

- **Campeões**: Listagem com stats, imagens, tags e informações detalhadas
- **Versões**: Sincronização automática das versões do jogo e gerenciamento manual

O projeto utiliza arquitetura **Hexagonal (Ports and Adapters)** para garantir separação de responsabilidades e facilitar a manutenção e testes.

---

## 🏛️ Arquitetura

O projeto segue a **Arquitetura Hexagonal** (Clean Architecture):

```
┌─────────────────────────────────────────────────────────────────┐
│                      INFRASTRUCTURE                             │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                    INPUT ADAPTERS                        │   │
│  │  • Controllers (REST API)                                │   │
│  │  • Schedulers (Jobs Agendados)                           │   │
│  │  • Event Listeners                                       │   │
│  └──────────────────────────────────────────────────────────┘   │
│                              │                                  │
│                              ▼                                  │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                     APPLICATION                          │   │
│  │  • Use Cases (Services)                                  │   │
│  │  • Validators                                            │   │
│  └──────────────────────────────────────────────────────────┘   │
│                              │                                  │
│                              ▼                                  │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                       DOMAIN                             │   │
│  │  • Models (Entities)                                     │   │
│  │  • Ports (Interfaces)                                    │   │
│  │  • Enums                                                 │   │
│  │  • Exceptions                                            │   │
│  └──────────────────────────────────────────────────────────┘   │
│                              │                                  │
│                              ▼                                  │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                    OUTPUT ADAPTERS                       │   │
│  │  • Feign Clients (API Externa)                           │   │
│  │  • JPA Repositories (Banco de Dados)                     │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Tecnologias

| Categoria | Tecnologia | Versão |
|-----------|------------|--------|
| **Linguagem** | Kotlin | 2.2.21 |
| **Runtime** | Java | 21 |
| **Framework** | Spring Boot | 4.0.1 |
| **Build Tool** | Gradle (Kotlin DSL) | 8.5 |
| **Banco de Dados** | PostgreSQL | 16 |
| **ORM** | Spring Data JPA / Hibernate | - |
| **HTTP Client** | Spring Cloud OpenFeign | 2025.1.0 |
| **Scheduler** | Spring Quartz | - |
| **Resiliência** | Resilience4j (Circuit Breaker) | - |
| **Serialização** | Jackson | - |
| **Containerização** | Docker / Docker Compose | - |
| **Testes** | JUnit 5 / Testcontainers 1.21.4 / Mockito 5.21.0 | - |
| **Qualidade de Código** | SonarQube | 7.2.0 |
| **Cobertura** | JaCoCo | - |

---

## ✨ Funcionalidades

### Implementadas ✅
- [x] Listagem de campeões com paginação e ordenação
- [x] Suporte a múltiplos idiomas (locale)
- [x] Sincronização automática de versões (diária às 04:00)
- [x] Sincronização manual de versões via endpoint
- [x] Criação manual de versões via API REST
- [x] Sistema de eventos para atualização de versão corrente
- [x] Integração com Data Dragon API
- [x] **Tratamento global de erros** com respostas padronizadas (RFC 7807)
- [x] **Hierarquia de exceções** de domínio e aplicação
- [x] **Proteção de detalhes sensíveis** em produção
- [x] **Testes unitários e de integração** com Testcontainers

### Em Desenvolvimento 🚧
- [ ] Documentação OpenAPI/Swagger
- [ ] Cache de requisições
- [ ] Autenticação e autorização
- [ ] Métricas e observabilidade avançada

---

## 📦 Requisitos

- **JDK 21+**
- **Docker & Docker Compose** (para banco de dados)
- **Gradle 8+** (opcional - wrapper incluso)

---

## 🚀 Instalação

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/league-of-legends.git
cd league-of-legends
```

### 2. Configure as variáveis de ambiente
Crie um arquivo `.env` na raiz do projeto:

```env
DATABASE_NAME=lol_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres
```

### 3. Inicie o banco de dados
```bash
docker-compose up -d
```

### 4. Execute a aplicação
```bash
# Windows
.\gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

A API estará disponível em: `http://localhost:8080`

---

## 📡 Endpoints

### Campeões

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/v1/champions` | Lista todos os campeões |

**Parâmetros de Query:**

| Parâmetro | Tipo | Default | Descrição |
|-----------|------|---------|-----------|
| `pageSize` | int | 50 | Quantidade de itens |
| `order` | string | ASC | Ordenação (ASC/DESC) |
| `language` | string | en_US | Idioma dos dados |

**Exemplo:**
```bash
curl "http://localhost:8080/api/v1/champions?pageSize=10&order=DESC&language=pt_BR"
```

### Versões

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/v1/admin/versions/import` | Sincroniza versões da API externa |
| `POST` | `/api/v1/versions` | Cria uma nova versão manualmente |

**Exemplo - Sincronizar versões:**
```bash
curl -X POST "http://localhost:8080/api/v1/admin/versions/import"
```

**Exemplo - Criar versão:**
```bash
curl -X POST "http://localhost:8080/api/v1/versions" \
  -H "Content-Type: application/json" \
  -d '{"number": "14.1.1"}'
```

---

## 🛡️ Tratamento de Erros

A API implementa um sistema global de tratamento de erros seguindo o padrão **RFC 7807 (Problem Details)**.

### Hierarquia de Exceções

```
RuntimeException
└── DomainException (abstract)
    ├── BusinessException (HTTP 422)
    │   └── VersionIsAlreadyRegisteredException
    └── ResourceNotFoundException (HTTP 404)
        ├── VersionNotFoundRegisteredException
        └── VersionNotFoundToSynchronizeException
```

### Formato de Resposta de Erro

```json
{
  "timestamp": "2025-12-31T10:30:00Z",
  "status": 422,
  "error": "Unprocessable Entity",
  "code": "BUSINESS_ERROR",
  "message": "Version 14.1.1 is already registered.",
  "path": "/api/v1/versions",
  "details": ["VersionIsAlreadyRegisteredException: ..."]
}
```

> **Nota:** O campo `details` só é exibido em ambientes `dev` ou `local` para proteger informações sensíveis em produção.

### Códigos de Erro

| Código | HTTP Status | Descrição |
|--------|-------------|-----------|
| `VALIDATION_ERROR` | 400 | Erro de validação nos campos |
| `BAD_REQUEST` | 400 | Requisição mal formatada |
| `RESOURCE_NOT_FOUND` | 404 | Recurso não encontrado |
| `BUSINESS_ERROR` | 422 | Violação de regra de negócio |
| `DEPENDENCY_ERROR` | 503 | Serviço externo indisponível |
| `UNEXPECTED_ERROR` | 500 | Erro interno não esperado |

---

## 📁 Estrutura do Projeto

```
src/main/kotlin/io/github/riotgames/leagueoflegends/
├── LeagueOfLegendsApplication.kt       # Classe principal
│
├── domain/                              # Camada de Domínio
│   ├── model/                           # Entidades de domínio
│   │   ├── Champion.kt
│   │   ├── ChampionImage.kt
│   │   ├── ChampionInfo.kt
│   │   ├── ChampionStats.kt
│   │   ├── Version.kt
│   │   └── VersionImportedEvent.kt
│   ├── port/                            # Interfaces (Portas)
│   │   ├── input/                       # Casos de uso
│   │   │   ├── champion/
│   │   │   └── version/
│   │   └── output/                      # Repositórios/Clients
│   ├── enums/
│   │   └── OrderType.kt
│   ├── exception/                       # Exceções de Domínio
│   │   ├── DomainException.kt
│   │   ├── BusinessException.kt
│   │   ├── ResourceNotFoundException.kt
│   │   ├── ValidationException.kt
│   │   └── ExternalServiceUnavailableException.kt
│   └── mapper/                          # Conversões de domínio
│
├── application/                         # Camada de Aplicação
│   ├── usecase/                         # Implementações dos casos de uso
│   │   ├── champion/
│   │   └── version/
│   ├── exception/                       # Exceções de Aplicação
│   │   ├── VersionIsAlreadyRegisteredException.kt
│   │   ├── VersionNotFoundRegisteredException.kt
│   │   └── VersionNotFoundToSynchronizeException.kt
│   └── validation/                      # Validadores
│
└── infrastructure/                      # Camada de Infraestrutura
    ├── input/                           # Adaptadores de entrada
    │   ├── controller/                  # REST Controllers
    │   │   ├── advice/                  # Exception Handlers
    │   │   │   └── GlobalExceptionHandler.kt
    │   │   ├── error/                   # DTOs de Erro
    │   │   │   ├── ApiErrorResponse.kt
    │   │   │   ├── ErrorCode.kt
    │   │   │   └── HttpExceptionMetadata.kt
    │   │   ├── factory/                 # Factories
    │   │   │   └── ApiErrorResponseFactory.kt
    │   │   ├── mapper/                  # Mappers de requisição
    │   │   ├── request/                 # DTOs de entrada
    │   │   └── response/                # DTOs de saída
    │   ├── listener/                    # Event Listeners
    │   └── schedule/                    # Jobs Agendados
    ├── output/                          # Adaptadores de saída (API externa)
    │   ├── adapter/                     # Implementações
    │   ├── client/                      # Feign Clients
    │   └── response/                    # DTOs de resposta
    └── persistence/                     # Adaptadores de persistência
        ├── adapter/                     # Implementações
        ├── entity/                      # Entidades JPA
        └── repository/                  # Spring Data Repositories
```

---

## 📊 Status do Desenvolvimento

| Componente | Status | Observação |
|------------|--------|------------|
| Arquitetura Hexagonal | ✅ Completo | Bem estruturado |
| CRUD Champions | ✅ Completo | Via API externa |
| Sincronização Versões | ✅ Completo | Automática + Manual (POST) |
| Criação de Versões | ✅ Completo | POST /api/v1/versions |
| PostgreSQL | ✅ Configurado | Docker Compose |
| Event-Driven | ✅ Completo | Spring Events |
| Tratamento de Erros | ✅ Completo | GlobalExceptionHandler |
| Testes | ✅ Completo | Unitários + Integração |
| SonarQube | ✅ Configurado | Análise de código |
| Documentação API | 🚧 Pendente | OpenAPI/Swagger |
| CI/CD | 🚧 Pendente | - |

---

## 🧪 Testes

O projeto possui uma suíte completa de testes:

### Testes Unitários
- `CreateVersionServiceTest` - Criação de versões
- `SynchronizeVersionsServiceTest` - Sincronização de versões
- `VersionRepositoryAdapterTest` - Adapter de repositório

### Testes de Integração (Testcontainers)
- `CreateVersionServiceIT` - Criação de versões
- `SynchronizeVersionsServiceIT` - Sincronização de versões
- `FindVersionNumberServiceIT` - Busca de versões
- `VersionClientAdapterIT` - Client de versões
- `VersionRepositoryAdapterIT` - Repositório de versões

**Executar testes:**
```bash
# Todos os testes
./gradlew test

# Apenas testes unitários
./gradlew test --tests "*Test"

# Apenas testes de integração
./gradlew test --tests "*IT"
```

---

## 📊 Qualidade de Código

O projeto utiliza **SonarQube** para análise estática de código e **JaCoCo** para cobertura de testes.

### Métricas Monitoradas

| Métrica | Valor Atual | Meta |
|---------|-------------|------|
| **Quality Gate** | ✅ Passed | Passed |
| **Line Coverage** | 27.25% | ≥ 80% |
| **Branch Coverage** | 15.22% | ≥ 80% |
| **Instruction Coverage** | 21.09% | ≥ 80% |
| **Bugs** | 0 | 0 |
| **Code Smells** | 0 | 0 |
| **Vulnerabilities** | 0 | 0 |
| **Security Hotspots** | 0 | 0 |
| **Duplicated Lines** | 0% | ≤ 3% |
| **Technical Debt** | 0min | 0min |

> ⚠️ **Nota:** A cobertura atual está abaixo da meta de 80%. Testes adicionais são necessários para melhorar a cobertura, especialmente em:
> - Controllers (`FindAllChampionsController`, `SynchronizeVersionsController`, `CreateVersionController`)
> - Exception Handlers (`GlobalExceptionHandler`)
> - Domain Models (`Champion`, `ChampionStats`, `ChampionImage`, `ChampionInfo`)

### SonarQube

| Configuração | Valor |
|--------------|-------|
| **Plugin Version** | 7.2.0.6526 |
| **Project Key** | `league-of-legends` |
| **Project Name** | `League of Legends API` |
| **Host URL** | `http://localhost:9000` |

**Executar análise:**
```bash
# Gerar relatório de cobertura e enviar para SonarQube
./gradlew test jacocoTestReport sonar

# Acessar dashboard
# http://localhost:9000/dashboard?id=league-of-legends
```

### JaCoCo

| Configuração | Valor |
|--------------|-------|
| **Report Format** | XML + HTML |
| **XML Report Path** | `build/reports/jacoco/test/jacocoTestReport.xml` |
| **HTML Report Path** | `build/reports/jacoco/test/html/index.html` |

**Gerar relatório de cobertura:**
```bash
# Executar testes e gerar relatório JaCoCo
./gradlew test jacocoTestReport

# Relatório HTML disponível em:
# build/reports/jacoco/test/html/index.html
```

### 🔄 Atualização Automática da Documentação

O projeto possui uma task Gradle personalizada que atualiza automaticamente os arquivos de documentação com as métricas de cobertura reais do JaCoCo:

```bash
# Executar testes + gerar relatório + atualizar documentação
./gradlew updateDocsCoverage

# Ou usar o script PowerShell completo (Windows)
.\scripts\update-docs-coverage.ps1
```

**Arquivos atualizados automaticamente:**
- `README.md` - Badges e tabela de métricas
- `CHANGELOG.md` - Métricas de cobertura na versão atual
- `docs/RELATORIO_ANALISE_TECNICA.md` - Tabela de métricas detalhada
```

---

## 📋 Changelog

Para ver o histórico completo de alterações, consulte o arquivo [CHANGELOG.md](CHANGELOG.md).

### Versão Atual: 1.3.0

**Novidades desta versão:**
- ✅ Endpoint de sincronização corrigido para `POST`
- ✅ Novos testes de integração para adapters
- ✅ Correção do Dockerfile para Java 21
- ✅ Correção do docker-compose.yml (network)
- ✅ SonarQube configurado para análise de código

---

## 📚 Documentação Técnica

Documentação técnica detalhada está disponível na pasta `/docs`:

| Documento | Descrição |
|-----------|-----------|
| [ARQUITETURA.md](docs/ARQUITETURA.md) | Diagramas de arquitetura com Mermaid |
| [API_REFERENCE.md](docs/API_REFERENCE.md) | Referência completa da API |
| [RELATORIO_ANALISE_TECNICA.md](docs/RELATORIO_ANALISE_TECNICA.md) | Análise técnica do projeto |

---

## 🤝 Contribuição

1. Faça um Fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

## 📝 Licença

Este projeto está sob a licença MIT.

---

## 📚 Referências

- [Data Dragon API - Riot Games](https://developer.riotgames.com/docs/lol#data-dragon)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/)

---

<p align="center">
  Desenvolvido com ❤️ usando Kotlin e Spring Boot
</p>
