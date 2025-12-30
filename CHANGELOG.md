# 📋 Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/).

---

## 🔮 [Não Publicado]

### 🚧 Em Desenvolvimento
- 🧪 Testes unitários e de integração
- 📖 Documentação OpenAPI/Swagger
- ⚡ Cache de requisições
- 🔐 Autenticação e autorização
- 📊 Métricas e observabilidade avançada

---

## 🚀 [1.1.0] - 2025-12-30

### ✨ Adicionado
- **Sistema Global de Tratamento de Erros**
  - `GlobalExceptionHandler` - Handler centralizado com `@RestControllerAdvice`
  - `ApiErrorResponse` - DTO padronizado para respostas de erro
  - `ApiErrorResponseFactory` - Factory para criação de respostas de erro
  - `ErrorCode` - Enum com códigos de erro padronizados
  - `HttpExceptionMetadata` - Metadados para exceções HTTP

- **Exceções de Domínio**
  - `DomainException` - Exceção base abstrata do domínio
  - `BusinessException` - Exceções de regras de negócio (HTTP 422)
  - `ResourceNotFoundException` - Recurso não encontrado (HTTP 404)
  - `ValidationException` - Erros de validação com lista de detalhes
  - `ExternalServiceUnavailableException` - Serviço externo indisponível (HTTP 503)

- **Exceções de Aplicação**
  - `VersionIsAlreadyRegisteredException` - Versão já registrada
  - `VersionNotFoundRegisteredException` - Versão não encontrada no banco
  - `VersionNotFoundToSynchronizeException` - Versão não encontrada para sincronização

### 🔧 Alterado
- Melhoria na estrutura de controllers com separação de concerns
- Respostas de erro agora seguem padrão RFC 7807 (Problem Details)
- Detalhes de erro são exibidos apenas em perfil `dev` ou `local`

### 🛡️ Segurança
- Detalhes técnicos de exceções não são expostos em ambiente de produção

---

## 🎉 [1.0.0] - 2024-12-01

### ✨ Adicionado
- **Arquitetura Hexagonal (Ports and Adapters)**
  - Estrutura completa seguindo Clean Architecture
  - Separação clara entre Domain, Application e Infrastructure

- **Integração com Data Dragon API**
  - Consumo da API oficial da Riot Games
  - Cliente HTTP com Spring Cloud OpenFeign
  - Circuit Breaker com Resilience4j

- **Módulo de Campeões**
  - 📋 Listagem de campeões com paginação e ordenação
  - 🌐 Suporte a múltiplos idiomas (locale: pt_BR, en_US, etc.)
  - 📦 Modelos: `Champion`, `ChampionImage`, `ChampionInfo`, `ChampionStats`

- **Módulo de Versões**
  - ⏰ Sincronização automática (job diário às 04:00)
  - 🔄 Sincronização manual via endpoint administrativo
  - 📢 Sistema de eventos (`VersionImportedEvent`)

- **Infraestrutura**
  - 🐳 Docker e Docker Compose para PostgreSQL
  - ☕ Spring Boot 4.0.1 com Kotlin 2.2.21
  - 🗄️ Spring Data JPA para persistência
  - ⏱️ Spring Quartz para agendamento
  - 📊 Spring Actuator para monitoramento

- **Camadas da Aplicação**
  - `domain/` - Models, Ports, Enums, Mappers, Exceptions
  - `application/` - Use Cases, Validators, Exceptions
  - `infrastructure/` - Controllers, Listeners, Schedulers, Adapters

### ⚙️ Configurado
- Perfis de ambiente (`application.yml`, `application-dev.yml`)
- Validação de beans com Spring Validation
- Serialização JSON com Jackson Kotlin Module

---

## 🌱 [0.1.0] - 2024-11-15

### ✨ Adicionado
- 🎯 Configuração inicial do projeto Spring Boot
- 📁 Estrutura base de pastas (arquitetura hexagonal)
- 🔧 Configuração do Gradle com Kotlin DSL
- 🐳 Dockerfile e docker-compose.yml
- 📝 README.md com documentação inicial

---

## 📌 Links

[Não Publicado]: https://github.com/seu-usuario/league-of-legends/compare/v1.1.0...HEAD
[1.1.0]: https://github.com/seu-usuario/league-of-legends/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/seu-usuario/league-of-legends/compare/v0.1.0...v1.0.0
[0.1.0]: https://github.com/seu-usuario/league-of-legends/releases/tag/v0.1.0

