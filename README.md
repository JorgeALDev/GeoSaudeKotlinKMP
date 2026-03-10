# 🦟 GeoSaúde KMP

Sistema multiplataforma (Android + Desktop) para agentes de combate a endemias, focado em registro e acompanhamento de visitas domiciliares para controle de dengue e outras arboviroses.

## Stack

<p align="left">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Compose_Multiplatform-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Compose"/>
  <img src="https://img.shields.io/badge/Koin-F78C40?style=for-the-badge&logoColor=white" alt="Koin"/>
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android"/>
  <img src="https://img.shields.io/badge/Desktop_(JVM)-007396?style=for-the-badge&logo=openjdk&logoColor=white" alt="Desktop"/>
</p>

| Tecnologia | Uso |
|---|---|
| Kotlin Multiplatform | Código compartilhado entre Android e Desktop |
| Compose Multiplatform | Interface gráfica responsiva |
| Koin | Injeção de dependências |
| ViewModel + StateFlow | Gerenciamento de estado reativo |

## Funcionalidades

- Login e Cadastro de agentes (matrícula + senha)
- Recuperação de senha
- Formulário de Nova Visita com 5 seções expansíveis
- Layout responsivo (mobile e desktop)

## Estrutura do projeto

```
composeApp/src/
├── commonMain/     → Código compartilhado (telas, ViewModels, modelos)
├── androidMain/    → Navegação e entry point Android
└── jvmMain/        → Entry point Desktop (JVM)
```

## Setup local

### Pré-requisitos
- Android Studio Ladybug (2024.2+) ou IntelliJ IDEA
- JDK 17+
- Android SDK (API 24+)

### Rodar o projeto

```bash
# Clonar
git clone https://github.com/JorgeALDev/GeoSaudeKotlinKMP.git
cd GeoSaudeKotlinKMP

# Desktop
./gradlew :composeApp:run

# Android
./gradlew :composeApp:assembleDebug
```

Tempo estimado do primeiro build: ~5-10 minutos (download de dependências).

## Branches

| Branch | Finalidade |
|---|---|
| `main` | Versão estável (protegida, requer PR) |
| `develop` | Desenvolvimento ativo |

## Equipe

| Membro | Papel |
|---|---|
| [@JorgeALDev](https://github.com/JorgeALDev) | Desenvolvedor |
| [@RuanVianaBatista](https://github.com/RuanVianaBatista) | Desenvolvedor |
