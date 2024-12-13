# Insider

Overview
This is a dynamic News App built with Jetpack Compose, Kotlin, and Retrofit. The app fetches real-time news articles from the News API and displays them in a user-friendly interface. The project is designed with scalability and maintainability in mind, employing modern Android development practices and architecture components.

Features
-> Fetches and displays top headlines from various countries.
-> Fetches news articles based on custom queries, sources, or keywords.
-> Robust architecture with ViewModel and MutableState for efficient state management and seamless user experience.
-> Error handling for network and data retrieval issues.

Technologies Used
-> Jetpack Compose: For building modern, declarative UI.
-> Kotlin: The primary programming language.
-> Retrofit: For API calls and data fetching.
-> ViewModel: For managing UI-related data in a lifecycle-aware way.
-> Coroutines: For managing asynchronous tasks.

Installation
1. Clone the repository: https://github.com/JatinJain123/Insider/
2. Open the project in Android Studio.
3. Add your API key from News API to the BuildConfig.NEWS_API_KEY in the code
4. Build and run the app on an emulator or physical device.

How It Works
1. Fetching Data
    API endpoints are defined in ApiService using Retrofit annotations.
    NewsRepository handles the API calls and provides data to the ViewModel.

2. State Management:
    LoadState manages the states (loading, success, error) of the app.
    MainViewModel interacts with the repository and updates the UI state.

3. Displaying Data:
    Jetpack Compose components render the news articles dynamically based on the state.

Future Enhancements
-> Advanced filtering and sorting options.
-> Addition of search functionality using effective data structures and algorithms.
-> Addition of more news categories and sources.
-> Improved UI/UX for better user interaction.
-> Offline support for caching news articles.
