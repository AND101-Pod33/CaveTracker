# 🏔️ Caveman Productivity App

A unique Android productivity app with a **CAVEMAN AESTHETIC** theme built using Kotlin and Jetpack Compose. Track habits, manage tasks, focus with timers, and get daily wisdom - all with a primitive cave-dwelling twist!

## 🎨 Features

### Core Functionality
- **🔥 Habit Cave**: Create and track habits with fire streak counters
- **🗿 Task Rocks**: Manage tasks with stone priority markers (Small Rock → Boulder)
- **🌙 Moon Calendar**: View tasks and habits in a stone circle calendar
- **🔥 Fire Focus**: Pomodoro timer with animated campfire + Stopwatch mode
- **📜 Daily Cave Wisdom**: Motivational quotes from Quotable API with offline fallback

### Caveman Aesthetic Design
- **Color Palette**: Earth tones (saddle brown, chocolate, tomato, wheat, burlywood)
- **UI Elements**: Stone tablet cards, cave painting icons, rock textures
- **Typography**: Primitive fonts with rough styling
- **Animations**: Fire effects, stone slide transitions
- **Theme**: Complete caveman immersion with bone toggles and chisel dividers

## 🛠️ Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM with Repository pattern
- **Database**: Room for local storage
- **API**: Retrofit + Moshi for Quotable API integration
- **Navigation**: Navigation Compose with custom transitions
- **Async**: Coroutines and Flow
- **DI**: Hilt for dependency injection
- **Notifications**: WorkManager for habit/task reminders

## 🏗️ Project Structure

```
app/src/main/java/com/cavemanproductivity/
├── data/
│   ├── api/           # Quotable API integration
│   ├── database/      # Room database, DAOs, converters
│   ├── model/         # Data models (Habit, Task, DailyQuote)
│   └── repository/    # Repository pattern implementation
├── di/                # Hilt dependency injection modules
├── navigation/        # Navigation setup and destinations
├── ui/
│   ├── components/    # Custom caveman-themed UI components
│   ├── screens/       # Main app screens
│   └── theme/         # Caveman aesthetic theme (colors, typography, shapes)
├── CavemanApplication.kt
└── MainActivity.kt
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or newer
- Android SDK API 24+ (Android 7.0)
- Kotlin 1.9.22+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd "AND101 Group Project"
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory and select it

3. **Sync dependencies**
   - Android Studio will automatically prompt to sync Gradle
   - Wait for the sync to complete

4. **Build and run**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press Shift+F10

## 📱 App Structure

### Main Screens
1. **🏔️ Cave Dashboard** - Overview with stats and daily wisdom
2. **🔥 Habit Cave** - Habit management with streak tracking
3. **🗿 Task Rocks** - Task management with priority levels
4. **🌙 Moon Calendar** - Calendar view with integrated tasks/habits
5. **🔥 Fire Focus** - Pomodoro and stopwatch timers
6. **👑 Chief Settings** - App preferences and settings

### Data Models
```kotlin
// Habit with frequency and streak tracking
data class Habit(
    val id: String,
    val name: String,
    val frequency: HabitFrequency, // DAILY, WEEKLY, MONTHLY, CUSTOM
    val streak: Int,
    val category: String,
    // ... other fields
)

// Task with priority levels
data class Task(
    val id: String,
    val title: String,
    val priority: Priority, // LOW, MEDIUM, HIGH, URGENT (Small Rock → Boulder)
    val category: String,
    val dueDate: String?,
    // ... other fields
)

// Daily wisdom quotes
data class DailyQuote(
    val date: String,
    val content: String,
    val author: String,
    val isFromApi: Boolean
)
```

## 🎨 Caveman Theme Details

### Color Scheme
- **Primary**: Saddle Brown (#8B4513)
- **Secondary**: Chocolate (#D2691E)
- **Accent**: Tomato/Fire (#FF6347)
- **Background**: Wheat/Cave Wall (#F5DEB3)
- **Surface**: Burlywood/Stone (#DEB887)

### Custom Components
- **StoneTablet**: Card component with stone texture and shadow
- **StoneButton**: Button with rock-like appearance and press effects
- **CaveIcons**: Hand-drawn cave painting style icons
- **RockChip**: Priority indicators as different sized rocks

### Cave Painting Icons
- 🔥 Fire (habits, focus)
- 🗿 Rock (tasks, priorities)
- 🌙 Moon (calendar, time)
- 🏹 Spear (focus, tools)
- 🏔️ Cave (home, dashboard)
- 👤 Stick Figure (user, settings)

## 🌐 API Integration

### Quotable API
- **Endpoint**: `https://api.quotable.io/random`
- **Purpose**: Daily motivational quotes
- **Fallback**: Custom caveman wisdom for offline use
- **Caching**: Stores daily quotes in Room database

### Sample Cave Wisdom (Offline Fallback)
- *"Strong cave person make fire every day, not just when cold."*
- *"Many small rocks build big mountain. Many small habits build strong caveman."*
- *"Hunt mammoth one step at a time, or mammoth hunt you."*

## 🔔 Notifications

Uses WorkManager for:
- **Habit Reminders**: "🔥 Time to keep your [habit] fire burning!"
- **Task Deadlines**: "🗿 Don't forget about: [task]"
- **Daily Wisdom**: Morning delivery of cave wisdom

## 🧪 Development Status

### ✅ Completed
- Project structure and dependencies
- Data models and Room database
- Quotable API integration with offline fallback
- Caveman aesthetic theme and custom components
- Navigation setup with stone-slide transitions
- Dashboard with stats and daily wisdom
- Basic screen layouts for all main features

### 🚧 In Progress / Coming Soon
- Full habit tracker implementation
- Complete task manager with CRUD operations
- Interactive calendar view
- Pomodoro timer with fire animations
- Settings screen with preferences
- WorkManager notifications
- Advanced animations and transitions

## 🤝 Contributing

This is a group project for AND101. To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-cave-feature`)
3. Commit your changes (`git commit -m 'Add amazing cave feature'`)
4. Push to the branch (`git push origin feature/amazing-cave-feature`)
5. Open a Pull Request

## 📄 License

This project is created for educational purposes as part of AND101 coursework.

## 🎯 Future Enhancements

- **Habit Categories**: Organize habits by activity type
- **Task Subtasks**: Break down large rocks into pebbles
- **Progress Analytics**: Cave paintings showing progress charts
- **Social Features**: Tribe sharing and challenges
- **Widget Support**: Home screen cave dashboard
- **Dark Mode**: Night cave theme
- **Backup/Sync**: Cloud storage for cross-device sync

---

*Built with ❤️ by caveman developers for modern productivity warriors!*

🏔️ **"Strong habits make strong caveman. Strong caveman build strong tribe!"** 🔥