# FocusFlow – Habit Builder & Focus Tracker

FocusFlow is a comprehensive Android application designed to help users build better habits and maintain deep focus. It combines a robust habit tracking system with a dedicated focus timer, all built using modern Android development practices.

## Features

### Phase 1: Core Habit System
- **Habit Management**: Create, edit, and delete habits across various categories (Health, Study, Work, Meditation, Fitness, Reading).
- **Tracking**: Daily and weekly tracking with streak counts and progress visualization.
- **Dashboard**: A unified view of today's habits, showing completed vs. pending tasks.
- **Authentication**: Secure Firebase authentication for account management and session persistence.

### Phase 2: Focus System (Coming Soon)
- **Session Types**: Pomodoro, Deep Work, Silent Meditation, and Study Sessions.
- **Persistence**: Focus sessions are stored locally using Room Database.
- **Timer**: Background execution support with notifications.

### Phase 3: Engagement & Reminders
- **Smart Reminders**: Daily, streak-based, and weekly summary notifications via WorkManager.

### Phase 4: Smart Habit Insights
- **Habit Scoring**: A consistency-based algorithm to calculate your "Habit Score".
- **Insights**: Productivity period and category-based performance analysis.

### Phase 5: Analytics
- **Firebase Analytics**: Wired events to track user engagement and feature usage.

### Phase 6: UI/UX
- **Modern Design**: Built entirely with Jetpack Compose and Material 3.
- **Personalization**: Supports Dark Mode.
- **Data Visualization**: Weekly charts and calendar heatmaps.

## Architecture & Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt
- **Local Database**: Room
- **Networking**: Retrofit
- **Background Tasks**: WorkManager
- **Backend**: Firebase (Auth, Analytics)
- **Concurrency**: Coroutines & Flow

## Setup

1. Clone the repository.
2. Open in Android Studio (Koala or newer recommended).
3. Connect your Firebase project and add `google-services.json`.
4. Build and run!
