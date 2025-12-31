# 🏗️ Arquitetura do Projeto

Este documento descreve a arquitetura do projeto **League of Legends API**, incluindo diagramas de componentes, fluxos e classes.

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Arquitetura Hexagonal](#-arquitetura-hexagonal)
- [Diagrama de Componentes](#-diagrama-de-componentes)
- [Fluxos de Dados](#-fluxos-de-dados)
- [Hierarquia de Exceções](#-hierarquia-de-exceções)
- [Diagrama de Classes](#-diagrama-de-classes)
- [Diagrama de Sequência](#-diagrama-de-sequência)

---

## 🎯 Visão Geral

O projeto implementa uma **Arquitetura Hexagonal** (também conhecida como Ports and Adapters) que separa claramente as responsabilidades em três camadas principais:

```mermaid
graph TB
    subgraph "🌐 External World"
        HTTP[HTTP Clients]
        SCHEDULER[Scheduler]
        DDragon[Data Dragon API]
        PG[(PostgreSQL)]
    end

    subgraph "📦 Application"
        subgraph "Infrastructure Layer"
            Controllers[REST Controllers]
            Jobs[Scheduled Jobs]
            Listeners[Event Listeners]
            FeignClients[Feign Clients]
            JPA[JPA Repositories]
        end

        subgraph "Application Layer"
            UseCases[Use Cases / Services]
            Validators[Validators]
        end

        subgraph "Domain Layer"
            Models[Domain Models]
            Ports[Ports / Interfaces]
            Exceptions[Domain Exceptions]
        end
    end

    HTTP --> Controllers
    SCHEDULER --> Jobs
    Controllers --> UseCases
    Jobs --> UseCases
    UseCases --> Ports
    UseCases --> Validators
    Ports --> FeignClients
    Ports --> JPA
    FeignClients --> DDragon
    JPA --> PG
    UseCases --> Listeners

    style Controllers fill:#4CAF50,color:#fff
    style Jobs fill:#4CAF50,color:#fff
    style Listeners fill:#4CAF50,color:#fff
    style UseCases fill:#2196F3,color:#fff
    style Validators fill:#2196F3,color:#fff
    style Models fill:#9C27B0,color:#fff
    style Ports fill:#9C27B0,color:#fff
    style Exceptions fill:#9C27B0,color:#fff
    style FeignClients fill:#FF9800,color:#fff
    style JPA fill:#FF9800,color:#fff
```

---

## 🔷 Arquitetura Hexagonal

### Princípios Aplicados

| Princípio | Descrição | Implementação |
|-----------|-----------|---------------|
| **Inversão de Dependência** | O domínio não conhece a infraestrutura | Ports (interfaces) no domínio |
| **Separação de Concerns** | Cada camada tem responsabilidades claras | Domain, Application, Infrastructure |
| **Testabilidade** | Fácil substituição de implementações | Injeção de dependências via construtor |
| **Independência de Framework** | Domínio livre de anotações Spring | Models e Ports puros |

### Estrutura de Pastas

```mermaid
graph LR
    subgraph "📁 src/main/kotlin"
        subgraph "domain/"
            M[model/]
            P[port/]
            E[exception/]
            EN[enums/]
            MA[mapper/]
        end

        subgraph "application/"
            UC[usecase/]
            V[validation/]
            AE[exception/]
        end

        subgraph "infrastructure/"
            subgraph "input/"
                C[controller/]
                S[schedule/]
                L[listener/]
            end
            subgraph "output/"
                CL[client/]
                AD[adapter/]
            end
            subgraph "persistence/"
                PA[adapter/]
                EN2[entity/]
                RE[repository/]
            end
        end
    end

    style M fill:#9C27B0,color:#fff
    style P fill:#9C27B0,color:#fff
    style E fill:#9C27B0,color:#fff
    style UC fill:#2196F3,color:#fff
    style V fill:#2196F3,color:#fff
    style C fill:#4CAF50,color:#fff
    style S fill:#4CAF50,color:#fff
    style CL fill:#FF9800,color:#fff
    style PA fill:#FF9800,color:#fff
```

---

## 🧩 Diagrama de Componentes

```mermaid
flowchart TB
    subgraph INPUT["📥 Input Adapters"]
        direction TB
        FC[FindAllChampionsController]
        SVC[SynchronizeVersionsController]
        CVC[CreateVersionController]
        VSS[VersionSyncScheduler]
        VUL[VersionUpdateListener]
    end

    subgraph APP["⚙️ Application Layer"]
        direction TB
        FACS[FindAllChampionService]
        SVS[SynchronizeVersionsService]
        CVS[CreateVersionService]
        FVNS[FindVersionByNumberService]
        VV[VersionValidator]
    end

    subgraph DOMAIN["💎 Domain Layer"]
        direction TB
        subgraph Ports["Ports"]
            FACU[FindAllChampionUseCase]
            SVU[SynchronizeVersionsUseCase]
            CVU[CreateVersionUseCase]
            CCP[ChampionClientPort]
            VCP[VersionClientPort]
            VRP[VersionRepositoryPort]
        end
        subgraph Models["Models"]
            CH[Champion]
            V[Version]
            VIE[VersionImportedEvent]
        end
    end

    subgraph OUTPUT["📤 Output Adapters"]
        direction TB
        CCA[ChampionClientAdapter]
        VCA[VersionClientAdapter]
        VRA[VersionRepositoryAdapter]
    end

    subgraph EXTERNAL["🌐 External"]
        DD[(Data Dragon API)]
        PG[(PostgreSQL)]
    end

    FC --> FACU
    SVC --> SVU
    CVC --> CVU
    VSS --> SVU

    FACU --> FACS
    SVU --> SVS
    CVU --> CVS

    FACS --> CCP
    SVS --> VCP
    SVS --> CVU
    CVS --> VRP
    CVS --> VV
    VV --> VRP

    CCP --> CCA
    VCP --> VCA
    VRP --> VRA

    CCA --> DD
    VCA --> DD
    VRA --> PG

    SVS -.->|publishes| VIE
    VIE -.->|handles| VUL

    style INPUT fill:#4CAF50,color:#fff
    style APP fill:#2196F3,color:#fff
    style DOMAIN fill:#9C27B0,color:#fff
    style OUTPUT fill:#FF9800,color:#fff
```

---

## 🔄 Fluxos de Dados

### Fluxo: Buscar Campeões

```mermaid
sequenceDiagram
    participant Client as 🌐 HTTP Client
    participant Controller as 📥 FindAllChampionsController
    participant UseCase as ⚙️ FindAllChampionService
    participant Port as 💎 ChampionClientPort
    participant Adapter as 📤 ChampionClientAdapter
    participant Feign as 🔌 ChampionClient (Feign)
    participant API as 🎮 Data Dragon API

    Client->>Controller: GET /api/v1/champions
    Controller->>UseCase: execute(pageSize, order, language)
    UseCase->>Port: findAllChampions(version, language)
    Port->>Adapter: findAllChampions(version, language)
    Adapter->>Feign: findAllChampions(version, language)
    Feign->>API: GET /cdn/{version}/data/{language}/champion.json
    API-->>Feign: ChampionApiResponse
    Feign-->>Adapter: ChampionApiResponse
    Adapter-->>Port: List<Champion>
    Port-->>UseCase: List<Champion>
    UseCase->>UseCase: sort & paginate
    UseCase-->>Controller: List<Champion>
    Controller-->>Client: ApiResponse<List<Champion>>
```

### Fluxo: Sincronizar Versões

```mermaid
sequenceDiagram
    participant Scheduler as ⏰ VersionSyncScheduler
    participant UseCase as ⚙️ SynchronizeVersionsService
    participant Validator as 🔍 VersionValidator
    participant VersionClient as 📤 VersionClientPort
    participant CreateUC as ⚙️ CreateVersionService
    participant Repository as 📤 VersionRepositoryPort
    participant EventPub as 📢 ApplicationEventPublisher
    participant Listener as 👂 VersionUpdateListener

    Scheduler->>UseCase: execute()
    UseCase->>VersionClient: findAllVersions()
    VersionClient-->>UseCase: List<String>
    UseCase->>Validator: filterAlreadyRegistered(versions)
    Validator->>Repository: existsByNumber(version)
    Repository-->>Validator: Boolean
    Validator-->>UseCase: List<String> (new versions)
    
    loop For each new version
        UseCase->>CreateUC: execute(Version)
        CreateUC->>Validator: filterAlreadyRegistered(number)
        Validator-->>CreateUC: Boolean
        CreateUC->>Repository: save(Version)
        Repository-->>CreateUC: Version
        CreateUC-->>UseCase: Version
    end
    
    UseCase-->>Scheduler: Long (count)
```

### Fluxo: Criar Versão Manualmente

```mermaid
sequenceDiagram
    participant Client as 🌐 HTTP Client
    participant Controller as 📥 CreateVersionController
    participant UseCase as ⚙️ CreateVersionService
    participant Validator as 🔍 VersionValidator
    participant Repository as 📤 VersionRepositoryPort
    participant Adapter as 📤 VersionRepositoryAdapter
    participant JPA as 🗄️ VersionJpaRepository
    participant DB as 💾 PostgreSQL

    Client->>Controller: POST /api/v1/versions
    Note over Client,Controller: {"number": "14.1.1"}
    Controller->>Controller: @Valid validation
    Controller->>UseCase: execute(Version)
    UseCase->>Validator: filterAlreadyRegistered(number)
    Validator->>Repository: existsByNumber(number)
    Repository->>Adapter: existsByNumber(number)
    Adapter->>JPA: existsByNumber(number)
    JPA->>DB: SELECT EXISTS...
    DB-->>JPA: Boolean
    JPA-->>Adapter: Boolean
    Adapter-->>Repository: Boolean
    Repository-->>Validator: Boolean
    
    alt Version already exists
        Validator-->>UseCase: true
        UseCase-->>Controller: throw VersionIsAlreadyRegisteredException
        Controller-->>Client: 422 Unprocessable Entity
    else Version is new
        Validator-->>UseCase: false
        UseCase->>Repository: save(Version)
        Repository->>Adapter: save(Version)
        Adapter->>JPA: save(VersionEntity)
        JPA->>DB: INSERT INTO...
        DB-->>JPA: VersionEntity
        JPA-->>Adapter: VersionEntity
        Adapter-->>Repository: Version
        Repository-->>UseCase: Version
        UseCase-->>Controller: Version
        Controller-->>Client: 201 Created
    end
```

---

## ⚠️ Hierarquia de Exceções

```mermaid
classDiagram
    class RuntimeException {
        <<Java Standard>>
    }

    class DomainException {
        <<abstract>>
        +message: String
    }

    class BusinessException {
        +message: String
    }

    class ResourceNotFoundException {
        +message: String
    }

    class ValidationException {
        +message: String
        +errors: List~String~
    }

    class ExternalServiceUnavailableException {
        +message: String
    }

    class VersionIsAlreadyRegisteredException {
        +version: String
    }

    class VersionNotFoundRegisteredException {
        +version: String
    }

    class VersionNotFoundToSynchronizeException {
        +message: String
    }

    RuntimeException <|-- DomainException
    DomainException <|-- BusinessException
    DomainException <|-- ResourceNotFoundException
    RuntimeException <|-- ValidationException
    RuntimeException <|-- ExternalServiceUnavailableException
    
    BusinessException <|-- VersionIsAlreadyRegisteredException
    ResourceNotFoundException <|-- VersionNotFoundRegisteredException
    ResourceNotFoundException <|-- VersionNotFoundToSynchronizeException

    note for DomainException "Base exception for all domain errors"
    note for BusinessException "HTTP 422 - Business rule violation"
    note for ResourceNotFoundException "HTTP 404 - Resource not found"
```

### Mapeamento HTTP

| Exceção | HTTP Status | Código |
|---------|-------------|--------|
| `BusinessException` | 422 Unprocessable Entity | `BUSINESS_ERROR` |
| `ResourceNotFoundException` | 404 Not Found | `RESOURCE_NOT_FOUND` |
| `ValidationException` | 400 Bad Request | `VALIDATION_ERROR` |
| `ExternalServiceUnavailableException` | 503 Service Unavailable | `DEPENDENCY_ERROR` |
| `Exception` (genérica) | 500 Internal Server Error | `UNEXPECTED_ERROR` |

---

## 📊 Diagrama de Classes

### Domain Models

```mermaid
classDiagram
    class Champion {
        +id: Long
        +name: String
        +title: String
        +blurb: String
        +partype: String
        +info: ChampionInfo
        +image: ChampionImage
        +tags: List~String~
        +stats: ChampionStats
    }

    class ChampionInfo {
        +attack: Int
        +defense: Int
        +magic: Int
        +difficulty: Int
    }

    class ChampionImage {
        +full: String
        +sprite: String
        +group: String
        +x: Int
        +y: Int
        +w: Int
        +h: Int
    }

    class ChampionStats {
        +hp: Double
        +hpPerLevel: Double
        +mp: Double
        +mpPerLevel: Double
        +moveSpeed: Double
        +armor: Double
        +armorPerLevel: Double
        +spellBlock: Double
        +spellBlockPerLevel: Double
        +attackRange: Double
        +hpRegen: Double
        +hpRegenPerLevel: Double
        +mpRegen: Double
        +mpRegenPerLevel: Double
        +crit: Double
        +critPerLevel: Double
        +attackDamage: Double
        +attackDamagePerLevel: Double
        +attackSpeedPerLevel: Double
        +attackSpeed: Double
    }

    class Version {
        +id: Long?
        +number: String
        +current: Boolean
        +createdAt: LocalDateTime?
    }

    class VersionImportedEvent {
        +version: Version
    }

    Champion *-- ChampionInfo
    Champion *-- ChampionImage
    Champion *-- ChampionStats
```

### Use Cases (Ports)

```mermaid
classDiagram
    class FindAllChampionUseCase {
        <<interface>>
        +execute(pageSize: Int, order: OrderType, language: String): List~Champion~
    }

    class CreateVersionUseCase {
        <<interface>>
        +execute(version: Version): Version
    }

    class SynchronizeVersionsUseCase {
        <<interface>>
        +execute(): Long
    }

    class FindVersionByNumberUseCase {
        <<interface>>
        +execute(number: String): Version
    }

    class ExistVersionByNumberUseCase {
        <<interface>>
        +execute(number: String): Boolean
    }

    class ChampionClientPort {
        <<interface>>
        +findAllChampions(version: String, language: String): List~Champion~
    }

    class VersionClientPort {
        <<interface>>
        +findAllVersions(): List~String~
    }

    class VersionRepositoryPort {
        <<interface>>
        +save(version: Version): Version
        +existsByNumber(number: String): Boolean
        +findByNumber(number: String): Version?
        +findCurrentVersion(): Version?
    }
```

---

## 🔧 Configurações

### Profiles de Ambiente

```mermaid
graph LR
    subgraph "🔧 Profiles"
        DEV[dev]
        LOCAL[local]
        PROD[prod]
    end

    subgraph "⚙️ Configurações"
        DB_DEV[PostgreSQL Local]
        DB_PROD[PostgreSQL Cloud]
        LOG_DEV[DEBUG Level]
        LOG_PROD[INFO Level]
        DETAILS_DEV[Error Details: ON]
        DETAILS_PROD[Error Details: OFF]
    end

    DEV --> DB_DEV
    DEV --> LOG_DEV
    DEV --> DETAILS_DEV
    
    LOCAL --> DB_DEV
    LOCAL --> LOG_DEV
    LOCAL --> DETAILS_DEV
    
    PROD --> DB_PROD
    PROD --> LOG_PROD
    PROD --> DETAILS_PROD

    style DEV fill:#4CAF50,color:#fff
    style LOCAL fill:#4CAF50,color:#fff
    style PROD fill:#f44336,color:#fff
```

---

## 📚 Referências

- [Hexagonal Architecture - Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Clean Architecture - Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Domain-Driven Design - Eric Evans](https://domainlanguage.com/ddd/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)

---

> 📅 **Última atualização:** 31 de Dezembro de 2025

