# 🌊 FocusFlow

**Elevate your productivity with intentional habit building and focused work sessions.**

FocusFlow is a premium Android application designed for users who want to master their routines. By combining scientifically-backed productivity techniques like Pomodoro with modern habit-tracking mechanics, FocusFlow helps you turn goals into lifelong streaks.

---

## 🌟 Key Features

### 📅 Advanced Habit Tracking
- **Smart Streaks:** Stay motivated with visual progress indicators and daily reminders.
- **Categorization:** Organize your life into Health, Study, Work, and more.
- **Offline First:** Seamlessly manage habits even without an internet connection.

### 🧘 Intentional Focus Sessions
- **Multiple Modes:** Choose between Pomodoro, Deep Work, or Custom sessions.
- **Background Persistence:** A robust Foreground Service ensures your timer never stops, providing real-time updates via notifications.
- **Live Visuals:** Experience your focus with dynamic progress bars and pulsing timer animations.

### 🎨 Personalization & Experience
- **Adaptive Theming:** Support for Light, Dark, and System Default modes, persisted via Jetpack DataStore.
- **Fluid Motion:** High-quality transitions and item animations powered by Jetpack Compose.
- **Modern UI:** Built entirely with Material 3 for a clean, accessible experience.

### 📊 Insightful Analytics
- **Firebase Integration:** Track your growth with detailed event analytics.
- **Productivity Scoring:** Understand your performance with our rule-based habit scoring logic.

---

## 🛠 Tech Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Kotlin (Coroutines + Flow) |
| **UI Framework** | Jetpack Compose (Material 3) |
| **Architecture** | Clean Architecture + MVVM |
| **DI** | Hilt |
| **Persistence** | Room (Local DB) + DataStore (Preferences) |
| **Background** | WorkManager + Foreground Services |
| **Cloud** | Firebase (Auth + Analytics) |

---

## 🏗 Architecture & Project Structure

FocusFlow follows **Clean Architecture** principles combined with **MVVM (Model-View-ViewModel)**. This ensures a clear separation of concerns, making the codebase scalable, maintainable, and highly testable.

### 📂 Directory Structure

```text
app/src/main/java/com/focusflow/
├── core/               # App-wide utilities, base classes, and timing logic
│   ├── timer/          # Foreground Service for background timing
│   └── util/           # Analytics, Notifications, and String helpers
├── data/               # Implementation layer (Local DB, API, Repositories)
│   ├── local/          # Room DB, DAOs, and Entities
│   ├── remote/         # Firebase integration and API clients
│   ├── repository/     # Concrete implementations of Domain repositories
│   └── mapper/         # Converters between Data and Domain models
├── di/                 # Hilt Modules for dependency injection
├── domain/             # Pure Kotlin Business Logic (Layer independent of Android)
│   ├── model/          # Domain models (POJOs)
│   ├── repository/     # Interface definitions for data access
│   └── usecase/        # Single-responsibility business logic classes
└── presentation/       # UI Layer (Jetpack Compose + ViewModels)
    ├── habits/         # Habit tracking feature
    ├── focus/          # Pomodoro timer and history
    ├── profile/        # User profile and theme settings
    ├── dashboard/      # Unified overview of daily progress
    ├── theme/          # Material 3 Design System implementation
    └── util/           # Navigation and Compose-specific utilities
```

### 📐 Architecture

```text
                               ┌─────────────────────────┐
                               │      Presentation       │
                               │ (Compose, ViewModels)   │
                               └───────────┬─────────────┘
                                           │
                                           ▼
      ┌────────────────┐       ┌─────────────────────────┐       ┌────────────────┐
      │      Data      │──────▶│         Domain          │◀──────│      Core      │
      │ (Room, Firebase)│       │ (UseCases, Repo Interfaces)     │ (Utilities,    │
      └────────────────┘       └─────────────────────────┘       │  Timer Service)│
                                                                 └────────────────┘

      Flow: UI Event ──▶ ViewModel ──▶ UseCase ──▶ Repository Interface ──▶ Repository Impl ──▶ DB/API
```

### 🧱 Architectural Layers

1.  **Domain Layer (The Core)**: Contains the application's business logic. It is independent of any other layer and contains **Use Cases** that represent the "What the app does."
2.  **Data Layer**: Responsible for managing data from different sources (Room, Firebase, DataStore). It implements the repository interfaces defined in the Domain layer.
3.  **Presentation Layer**: Responsible for showing information to the user. It uses **Jetpack Compose** for the UI and **StateFlow** within ViewModels to manage UI state in a lifecycle-aware manner.

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Koala+
- JDK 17
- Firebase project credentials (`google-services.json`)

### Installation
1. **Clone the repo:**

2. **Setup Firebase:**
   Place your `google-services.json` in the `app/` directory.
3. **Build & Run:**
   Open in Android Studio and hit **Run**, or use:
   ```bash
   ./gradlew installDebug
   ```

---

## 👨‍💻 Author

**Harsh Raghuwanshi**  

---
*FocusFlow – Flow into your focus.*
