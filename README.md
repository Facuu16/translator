# Translator

Desktop application showcasing API integration, asynchronous processing, and extensible service design for real-time text translation.

## Key Features

- Strategy Pattern: Pluggable translation providers through interface abstraction
- Async Processing: Non-blocking translation requests
- Dual API Support: Switch between Google Cloud Translate and OpenAI GPT via environment configuration
- Multi-language: Support for 100+ languages

## Technical Highlights

- Dependency injection with `team.unnamed:inject`
- Strategy pattern for pluggable translation providers
- Clear separation between UI and service layer
- Lightweight service layer abstraction for external API integration
- Automatic API key detection via environment variables
- Shadow JAR packaging for single-file distribution

## Technologies Used

Component | Technology
----------|------------
Language | Java 8+
UI Framework | JavaFX 8
Build Tool | Gradle (Kotlin DSL)
HTTP Client | OkHttp3
DI Framework | team.unnamed:inject

## Getting Started

### Prerequisites
- Java 8 or higher
- At least one API key (Google Cloud or OpenAI)

### Configuration
Set one of the following environment variables:

# Option 1: Google Translate (takes priority)
export GOOGLE_KEY="your-google-api-key"

# Option 2: OpenAI GPT
export OPENAI_KEY="your-openai-api-key"

### Build & Run
./gradlew build    # Build project and create JAR
./gradlew run      # Run the application

### Output
The runnable JAR will be located at `build/libs/translator-1.0.0-all.jar`.

## Architecture

src/main/java/com/facuu16/translator/
├── TranslatorApplication.java    # Entry point
├── controller/
│   └── TranslatorController.java # JavaFX controller
├── enums/
│   └── Language.java             # Language enum
└── translate/
    ├── Translator.java           # Translator interface
    ├── GoogleTranslator.java     # Google implementation
    └── ChatGptTranslator.java    # OpenAI implementation

The `Translator` interface defines the contract for translation services. New translators can be added by implementing this interface and registering with the injector.

## Limitations

- Requires external API keys (no offline support)
- Translation quality depends on selected provider
- No request rate limiting or caching implemented
- UI is desktop-only (no web/mobile version)

## Why this project?

This project was built to explore:
- abstraction over multiple external APIs
- async request handling in UI applications
- clean integration patterns for third-party services

It also serves as a small example of designing extensible service layers for API-driven applications.