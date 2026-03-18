# FireTV Demo

A modern Android TV application built with Jetpack Compose for TV, demonstrating content browsing interface for Fire TV devices.

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose for TV
- **Architecture:** Compose declarative UI
- **Image Loading:** Coil
- **Target Platform:** Fire TV (Fire OS 7 - Android 9)

## 📱 Features

- ✅ Browse interface with categories
- ✅ Horizontal movie rows
- ✅ D-pad navigation support
- ✅ Focus-based TV UI

## 🚀 Getting Started

### Prerequisites

- Android Studio Panda 2 (2025.3.2) or later
- Fire TV Stick 4K Max (or Android TV emulator)
- ADB enabled on Fire TV device

### Setup

1. Clone the repository
2. Open in Android Studio
3. Connect to Fire TV:
   ```bash
   adb connect <FIRE_TV_IP>:5555
   ```
4. Run the app

### Fire TV Configuration

On your Fire TV:
1. Settings → My Fire TV → Developer Options
2. Enable **ADB Debugging**
3. Note your IP address (Settings → My Fire TV → About → Network)

## 📂 Project Structure

```
app/src/main/java/solutions/sgbrightkit/cinemaflow/
├── MainActivity.kt          # Entry point
├── MainScreen.kt            # Main Compose UI
├── Movie.kt                 # Data model
└── MovieList.kt             # Sample data
```

## 🎓 Learning Path

This project demonstrates:
- Jetpack Compose basics
- TV-specific UI patterns (LazyRow, LazyColumn)
- D-pad navigation
- Focus management
- Composable functions

## 🔜 Next Steps

- [ ] Add movie detail screen
- [ ] Implement search functionality
- [ ] Integrate video playback (ExoPlayer)
- [ ] Load data from API
- [ ] Add settings screen

## 📚 Resources

- [Compose for TV Documentation](https://developer.android.com/jetpack/compose/tv)
- [Android TV Design Guidelines](https://developer.android.com/design/ui/tv)
- [Fire TV Developer Docs](https://developer.amazon.com/docs/fire-tv/getting-started-developing-apps-and-games.html)

## 👨‍💻 Developer

Transitioning from Roku (4.5+ years) to Android TV development.

## 📝 License

Personal learning project.
