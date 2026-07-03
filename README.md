# 🚀 FocusFlow – Habit Builder & Focus Tracker

FocusFlow is a modern Android productivity application designed to help users build consistent habits, improve focus, and maintain long-term productivity through structured routines and focus sessions.

The application combines **habit tracking**, **focus management**, **smart reminders**, and **productivity insights** into a single experience while following scalable Android engineering practices used in production environments.

---

## ✨ Features

### 📌 Habit Management

- Create, update, and delete habits
- Organize habits by categories:
  - 🏃 Health
  - 📚 Study
  - 💼 Work
  - 🧘 Meditation
  - 🏋 Fitness
  - 📖 Reading
- Daily and weekly progress tracking
- Habit completion history
- Streak tracking system
- Progress visualization

---

### 📊 Dashboard

Get a quick overview of your productivity:

- Today's habits
- Completed habits
- Pending habits
- Completion percentage
- Active streak count
- Daily progress indicators

---

### 🔐 Authentication

Integrated with Firebase Authentication:

- Email & Password Login
- User Registration
- Session Persistence
- Logout functionality
- Automatic login for returning users

---

### ⏳ Focus System

Built to support deep work and intentional focus.

Supported session types:

- 🍅 Pomodoro (25 min focus + 5 min break)
- 💻 Deep Work Session
- 🧘 Silent Meditation Session
- 📚 Study Session
- ⚙ Custom Session

Features:

- Start / Pause / Resume / Stop
- Background execution
- Notification support
- Session tracking
- Focus history persistence

---

### 🔔 Smart Reminders & Engagement

Built using WorkManager and Notification Channels:

- Daily reminders
- Streak reminders
- Missed habit reminders
- Weekly summaries
- User engagement notifications

Examples:

```text
🎯 Time for your Study habit

🔥 You're one step away from maintaining your streak

🧘 Meditation session completed
```

---

### 🧠 Smart Habit Insights

FocusFlow uses a rule-based recommendation system to generate productivity insights.

Scoring Logic:

```kotlin
habitScore =
0.4 * completionRate +
0.3 * streakStrength +
0.2 * focusSessions +
0.1 * consistency
```

Generated insights include:

- Peak productivity periods
- Most consistent habit categories
- Focus session trends
- Completion patterns

---

### 📈 Analytics

Integrated with Firebase Analytics for tracking user interactions and feature engagement.

Tracked events:

- `habit_created`
- `habit_completed`
- `focus_started`
- `focus_completed`
- `session_duration`
- `category_selected`
- `reminder_opened`
- `streak_maintained`

---

## 🏗 Architecture

The project follows **MVVM + Clean Architecture** with proper separation of concerns.

```text
presentation/
    habits/
    focus/
    dashboard/
    profile/
    authentication/

domain/
    model/
    repository/
    usecase/

data/
    remote/
        api/
        dto/

    local/
        database/
        dao/
        entity/

    mapper/
    repository/

core/
    util/
    notification/
    timer/

di/
```

---

## ⚙ Tech Stack

| Category | Technology |
|------------|------------|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| Dependency Injection | Hilt |
| Local Database | Room Database |
| Networking | Retrofit |
| Background Tasks | WorkManager |
| Authentication | Firebase Authentication |
| Analytics | Firebase Analytics |
| State Management | Coroutines + Flow |
| Navigation | Navigation Compose |

---

## 📱 Screens

- 🏠 Dashboard Screen
- 📌 Habit Management Screen
- ⏳ Focus Session Screen
- 🔐 Login / Signup Screen
- 📈 Analytics Screen
- 👤 Profile Screen
- 🔔 Notification & Reminder Screens

---

## 🔥 Highlights

✔ Production-style Android architecture  
✔ Offline-first local persistence  
✔ Background task execution using WorkManager  
✔ Firebase Authentication integration  
✔ Analytics event tracking  
✔ Scalable state management with StateFlow  
✔ Modular and reusable code structure  
✔ Jetpack Compose UI system  
✔ Notification-driven engagement system  

---

## 🚀 Getting Started

### Prerequisites

Make sure you have:

- Android Studio Koala or newer
- Android SDK 24+
- Firebase Project setup
- Kotlin installed

---

### Clone Repository

```bash
git clone https://github.com/yourusername/FocusFlow.git
```

Move into project:

```bash
cd FocusFlow
```

---

### Firebase Setup

1. Create a Firebase project

2. Download:

```text
google-services.json
```

3. Place the file inside:

```text
app/google-services.json
```

4. Enable:

- Firebase Authentication → Email/Password
- Firebase Analytics

---

### Build & Run

Run from terminal:

```bash
./gradlew installDebug
```

Or run directly from Android Studio:

```text
Run → Run 'app'
```

---


<div align="center">

### 👨‍💻 Built by Harsh Raghuwanshi

Android Developer • Kotlin • Jetpack Compose • Clean Architecture

</div>
