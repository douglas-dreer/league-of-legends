# 🎮 League of Legends API

API REST para consulta de dados de campeões e versões do **League of Legends**, utilizando a **Data Dragon API** da Riot Games.

![Versão](https://img.shields.io/badge/Versão-1.1.0-blue)
![Kotlin](https://img.shields.io/badge/Kotlin-2.2.21-purple?logo=kotlin)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-green?logo=springboot)
![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)
![Licença](https://img.shields.io/badge/Licença-MIT-green)

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Arquitetura](#-arquitetura)
- [Tecnologias](#-tecnologias)
- [Funcionalidades](#-funcionalidades)
- [Requisitos](#-requisitos)
- [Instalação](#-instalação)
- [Endpoints](#-endpoints)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Status do Desenvolvimento](#-status-do-desenvolvimento)
- [Changelog](#-changelog)

---

## 📖 Sobre o Projeto

Esta API consome dados da [Data Dragon API](https://developer.riotgames.com/docs/lol#data-dragon) da Riot Games para fornecer informações sobre:

- **Campeões**: Listagem com stats, imagens, tags e informações detalhadas
- **Versões**: Sincronização automática das versões do jogo

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
│  │  • Mappers                                               │   │
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
| **Build Tool** | Gradle (Kotlin DSL) | 8.x |
| **Banco de Dados** | PostgreSQL | 16 |
| **ORM** | Spring Data JPA / Hibernate | - |
| **HTTP Client** | Spring Cloud OpenFeign | 2025.1.0 |
| **Scheduler** | Spring Quartz | - |
| **Resiliência** | Resilience4j (Circuit Breaker) | - |
| **Serialização** | Jackson | - |
| **Containerização** | Docker / Docker Compose | - |

---

## ✨ Funcionalidades

### Implementadas ✅
- [x] Listagem de campeões com paginação e ordenação
- [x] Suporte a múltiplos idiomas (locale)
- [x] Sincronização automática de versões (diária às 04:00)
- [x] Sincronização manual de versões via endpoint
- [x] Sistema de eventos para atualização de versão corrente
- [x] Integração com Data Dragon API
- [x] **Tratamento global de erros** com respostas padronizadas
- [x] **Hierarquia de exceções** de domínio e aplicação
- [x] **Proteção de detalhes sensíveis** em produção

### Em Desenvolvimento 🚧
- [ ] Testes unitários e de integração
- [ ] Documentação OpenAPI/Swagger
- [ ] Cache de requisições
- [ ] Autenticação e autorização

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
| `GET` | `/api/v1/admin/versions/import` | Sincroniza versões manualmente |

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
    │   │   └── factory/                 # Factories
    │   │       └── ApiErrorResponseFactory.kt
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
| Sincronização Versões | ✅ Completo | Automática + Manual |
| PostgreSQL | ✅ Configurado | Docker Compose |
| Event-Driven | ✅ Completo | Spring Events |
| Tratamento de Erros | ✅ Completo | GlobalExceptionHandler |
| Testes | 🚧 Pendente | Apenas context test |
| Documentação API | 🚧 Pendente | - |
| CI/CD | 🚧 Pendente | - |

---

## 📋 Changelog

Para ver o histórico completo de alterações, consulte o arquivo [CHANGELOG.md](CHANGELOG.md).

### Versão Atual: 1.1.0

**Novidades desta versão:**
- ✅ Sistema global de tratamento de erros (`GlobalExceptionHandler`)
- ✅ Respostas de erro padronizadas (`ApiErrorResponse`)
- ✅ Hierarquia de exceções de domínio e aplicação
- ✅ Proteção de detalhes sensíveis em produção

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

---

<p align="center">
  Desenvolvido com ❤️ usando Kotlin e Spring Boot
</p>

