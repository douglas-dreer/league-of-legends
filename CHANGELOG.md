# 📋 Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/).

---

## 🔮 [Não Publicado]

### 🚧 Em Desenvolvimento
- 📖 Documentação OpenAPI/Swagger
- ⚡ Cache de requisições com Redis/Caffeine
- 🔐 Autenticação e autorização com Spring Security
- 📊 Métricas e observabilidade avançada com Micrometer

---

## 🚀 [1.2.0] - 2025-12-31

### ✨ Adicionado
- **Documentação Técnica Completa**
  - 📐 `docs/ARQUITETURA.md` - Diagramas de arquitetura com Mermaid
  - 📚 `docs/API_REFERENCE.md` - Referência completa da API REST
  - 🔄 Diagramas de fluxo, sequência e classes

- **Testes Automatizados**
  - 🧪 Testes unitários para `CreateVersionService`
  - 🧪 Testes unitários para `SynchronizeVersionsService`
  - 🔬 Testes de integração com Testcontainers
  - 📦 Fixtures e factories para geração de dados de teste

### 🔧 Alterado
- **Padronização de Idioma**
  - 🌐 Todos os comentários KDoc traduzidos para inglês (en-EN)
  - 🌐 Mensagens de log padronizadas em inglês
  - 🌐 Mensagens de erro da API em inglês

- **Documentação**
  - 📝 README.md completamente atualizado
  - 📝 Adicionada seção de Tratamento de Erros
  - 📝 Adicionada seção de Documentação Técnica
  - 📝 Novo endpoint POST /api/v1/versions documentado

### 📚 Documentação
- Novo índice com links para documentação técnica
- Diagrama de hierarquia de exceções
- Tabela de códigos de erro HTTP

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

- **Novo Endpoint**
  - `POST /api/v1/versions` - Criação manual de versões
  - `CreateVersionController` - Controller para criação de versões
  - `CreateVersionRequest` - DTO de requisição com validação

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

## 📊 Legenda

| Emoji | Tipo de Mudança |
|-------|-----------------|
| ✨ | Nova funcionalidade |
| 🔧 | Alteração/Melhoria |
| 🐛 | Correção de bug |
| 🗑️ | Remoção |
| 🛡️ | Segurança |
| 📚 | Documentação |
| ⚙️ | Configuração |
| 🚧 | Em desenvolvimento |

---

> 📖 **Referência:** [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/) | [Semantic Versioning](https://semver.org/lang/pt-BR/)
