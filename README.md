# 🎬 CinemaFlow

A modern Android TV streaming application built with Jetpack Compose for TV, featuring real-time movie data from TMDB API and a beautiful 10-foot user interface optimized for TV remote navigation.

![Platform](https://img.shields.io/badge/Platform-Android%20TV-green)
![Language](https://img.shields.io/badge/Language-Kotlin-blue)
![License](https://img.shields.io/badge/License-MIT-orange)

## 📺 Overview

CinemaFlow is a full-featured movie streaming application designed for Android TV and Fire TV devices. It demonstrates modern Android TV development practices with Jetpack Compose, TMDB API integration, and ExoPlayer video playback.

## ✨ Features

### Core Functionality
- **Browse Movies** - Popular, Top Rated, and Coming Soon categories
- **Real-time Search** - Search movies with TMDB API integration
- **Detailed Information** - View movie details including rating, runtime, genres, release year
- **Video Playback** - ExoPlayer integration for smooth video streaming
- **Theme System** - Light/Dark mode with persistent user preference

### UI/UX
- **Collapsible Menu** - Space-efficient sidebar navigation with logo
- **10-foot Interface** - Optimized for TV viewing distance
- **D-pad Navigation** - Full remote control support with proper focus management
- **Responsive Cards** - Movie cards with hover effects and backdrop images
- **Splash Screen** - Professional app launch experience

### Technical Features
- **TMDB API Integration** - Real movie data, posters, and metadata
- **State Management** - Proper Compose state handling
- **Navigation** - Multi-screen navigation with Jetpack Navigation Compose
- **Persistent Storage** - SharedPreferences for user settings
- **Error Handling** - Graceful loading states and error messages

## 🛠️ Tech Stack

### Frontend
- **Kotlin** - Primary programming language
- **Jetpack Compose for TV** - Modern declarative UI framework
- **Material Design 3** - TV-specific Material components
- **Coil** - Asynchronous image loading

### Backend & Data
- **TMDB API** - Movie data and metadata
- **Retrofit** - REST API client
- **Gson** - JSON serialization
- **OkHttp** - HTTP client with logging

### Media
- **ExoPlayer (Media3)** - Video playback
- **Coroutines** - Asynchronous programming

### Architecture
- **MVVM Pattern** - Clear separation of concerns
- **Repository Pattern** - Data abstraction layer
- **Navigation Component** - Screen navigation

## 📱 Supported Platforms

- ✅ Android TV (API 23+)
- ✅ Fire TV (tested on Fire TV Stick 4K Max)
- ✅ Android TV Emulator

## 🚀 Getting Started

### Prerequisites

- Android Studio Panda 2 (2025.3.2) or later
- JDK 8 or higher
- TMDB API Key ([Get one here](https://www.themoviedb.org/settings/api))

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/CinemaFlow-FireTV.git
   cd CinemaFlow-FireTV
   ```

2. **Add your TMDB API Key**

   Create a `secrets.properties` file in the project root:
   ```properties
   TMDB_API_KEY=your_api_key_here
   ```

3. **Open in Android Studio**
   - Open the project in Android Studio
   - Wait for Gradle sync to complete

4. **Run the app**
   - Connect your Android TV device or Fire TV via ADB
   - Click Run ▶ or press `Shift + F10`

### ADB Setup for Fire TV

```bash
# Enable ADB on Fire TV: Settings → My Fire TV → Developer Options → ADB Debugging
adb connect YOUR_FIRE_TV_IP:5555
adb devices
```

### Home Screen
Browse popular, top-rated, and upcoming movies with horizontal scrolling.

### Movie Details
View comprehensive movie information including rating, runtime, genres, and description.

### Search
Search for movies with real-time TMDB integration.

### Settings
Toggle between light and dark themes with persistent preferences.

## 🏗️ Project Structure

```
app/src/main/java/solutions/sgbrightkit/cinemaflow/
├── MainActivity.kt                 # Entry point
├── PlayerActivity.kt              # Video player
├── SplashActivity.kt              # Splash screen
├── Navigation.kt                  # Navigation graph
├── PreferencesManager.kt          # Settings persistence
├── Movie.kt                       # Movie data model
├── MovieList.kt                   # Sample data (deprecated)
│
├── data/
│   ├── RetrofitInstance.kt       # API client
│   ├── TmdbApiService.kt         # API endpoints
│   └── model/
│       ├── TmdbMovie.kt          # TMDB data models
│       └── MovieMapper.kt        # DTO to domain mapping
│
├── screens/
│   ├── MainMenuScreen.kt         # Navigation sidebar
│   ├── MainScreen.kt             # Browse screen
│   ├── SearchScreen.kt           # Search functionality
│   ├── DetailsScreen.kt          # Movie details
│   └── SettingsScreen.kt         # App settings
│
└── ui/theme/
    ├── Color.kt                  # Theme colors
    └── Theme.kt                  # Material theme
```

## 🎨 Theme Customization

CinemaFlow supports both light and dark themes. Colors are defined in `ui/theme/Color.kt`:

```kotlin
val BrandBackground = Color(0xFFF6F0EE)  // Light mode
val BrandPrimary = Color(0xFF0AA1B2)
val BrandSecondary = Color(0xFF0E2941)
```

## 🔑 API Keys & Configuration

This project uses TMDB API for movie data. You need to:
1. Create a free account at [TMDB](https://www.themoviedb.org/)
2. Generate an API key
3. Add it to `secrets.properties` (never commit this file!)

## 🎓 Learning Journey

This project was built as a learning exercise to expand expertise from Roku (BrightScript) to Android TV development. Key learnings include:

- Jetpack Compose declarative UI patterns
- Kotlin coroutines for asynchronous operations
- Android TV-specific UI/UX considerations
- ExoPlayer integration for video playback
- REST API integration with Retrofit
- State management in Compose
- Navigation Component implementation

## 📝 Known Limitations

- Video playback uses sample content (Big Buck Bunny, etc.) for demonstration
- TMDB API free tier has rate limits
- Some advanced ExoPlayer features (DRM, adaptive streaming) not implemented
- No offline mode or content caching

## 🚧 Future Enhancements

- [ ] Implement proper content licensing
- [ ] Add cast/crew information
- [ ] Watchlist/Favorites functionality
- [ ] Continue watching feature
- [ ] Parental controls
- [ ] Multi-profile support
- [ ] Recommendation engine
- [ ] Voice search integration
- [ ] Chromecast support

## 🤝 Contributing

This is a learning/portfolio project, but suggestions and feedback are welcome! Feel free to:
- Open issues for bugs or feature requests
- Submit pull requests for improvements
- Share your thoughts and experiences

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **TMDB** - Movie data and API
- **Google** - Android TV platform and Jetpack libraries
- **Blender Foundation** - Sample videos (Big Buck Bunny, Sintel, etc.)
- **Anthropic Claude** - Development assistance and guidance

## 👨‍💻 Developer

**Alex**  
Senior CTV/OTT Developer | Roku | Android TV | Fire TV

- 4.5+ years of Roku development experience
- Expanding expertise to Android TV/Fire TV ecosystem
- Creator of SGBrightKit framework for Roku

## 📞 Contact

- GitHub: [@alexlukhanin](https://github.com/alexlukhanin)
- LinkedIn: [Oleksandr Lukhanin](https://www.linkedin.com/in/oleksandrlukhanin/)
- Email: alex.lukhanin@hotmail.com

---

**Built with ❤️ for the CTV/OTT community**