
# ComposeRecipeApp

Recipe application build in Android with Jetpack Compose, Kotlin, Coroutines, Flow, Hilt, Retrofit, ViewModel using MVVM Architecture pattern. This app uses [Spoonacular Food API](https://spoonacular.com/food-api) to fetch recipe data.


## Features

- **Recipes screen** displays list of popular recipes. Clicking on any recipe item opens recipe details screen.
- **Recipe details screen** displays more information about recipe like cooking time, number of persons per servings, health score, ingredients information and steps involved in making the recipe.
- **Ingredients screen** allow users to choose ingredients and find recipes which uses those ingredients. 
- **Meal plan screen** displays list of popular meal plan templates. Clicking on any meal plan item opens meal plan details screen. 
- **Meal plan details screen** displays meal details for each day. This screen has nutrition summary along with recipes for breakfast, lunch and dinner.

| <img src="https://github.com/aravindrajpalani/ComposeRecipeApp/blob/main/screenshots/home_screen.jpg" width="200"> | <img src="https://github.com/aravindrajpalani/ComposeRecipeApp/blob/main/screenshots/recipe_details_screen.jpg" width="200"> | <img src="https://github.com/aravindrajpalani/ComposeRecipeApp/blob/main/screenshots/ingredients_screen.jpg" width="200"> |
| --- | --- | --- |
| Home Screen | Recipe Details Screen | Ingredients Screen |

<br/>

| <img src="https://github.com/aravindrajpalani/ComposeRecipeApp/blob/main/screenshots/selected_ingredients_screen.jpg" width="200"> | <img src="https://github.com/aravindrajpalani/ComposeRecipeApp/blob/main/screenshots/meal_plan_screen.jpg" width="200"> | <img src="https://github.com/aravindrajpalani/ComposeRecipeApp/blob/main/screenshots/meal_plan_details_screen.jpg" width="200"> |  
| --- | --- | --- |
| Selected Ingredients Screen | Meal Plan Screen | Meal Plan Details Screen |  


## Technologies
- [Jetpack Compose](https://developer.android.com/compose) – Android’s recommended modern toolkit for building native UI.  
- [Kotlin](https://kotlinlang.org/) – A modern, concise, and safe programming language for Android development.  
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) – Kotlin feature for asynchronous programming, simplifying background tasks.  
- [Flow](https://kotlinlang.org/docs/flow.html) – Kotlin’s reactive streams API for handling asynchronous data streams.  
- [Hilt](https://dagger.dev/hilt/) – Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.  
- [Retrofit](https://square.github.io/retrofit/) – Type-safe HTTP client for Android and Java to handle network requests.  
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) – Lifecycle-aware component to store and manage UI-related data.

## Architecture

This application follows the **MVVM (Model-View-ViewModel)** architecture pattern:

* **View (UI Layer)** - Jetpack Compose screens that observe ViewModel state and handle user interactions.
* **ViewModel** - Manages UI state with `UiState` sealed class and exposes data as Flow.
* **Repository** - Single source of truth that coordinates between LocalDataSource and RemoteDataSource.
* **Data Sources**
  * **LocalDataSource** - Reads `ingredients.json` from assets folder
  * **RemoteDataSource** - Fetches recipe data via RecipeAPIService
* **RecipeAPIService** - Retrofit interface for Spoonacular API endpoints.
* **Dependency Injection** - Hilt provides constructor injection for Repository, DataSources, and API Service.

<img src="https://github.com/aravindrajpalani/ComposeRecipeApp/blob/main/screenshots/recipe-app-architecture.png" width="700">

## Development Environment

- Android Studio Arctic Fox or later
- Minimum SDK: API 24 
- Target SDK: API 36 
- Kotlin 2.2.21
- Gradle 8.12.0

## Project Structure

```
└── dev
    └── aravindraj
        └── composerecipeapp
            ├── ComposeRecipeApplication.kt
            ├── MainActivity.kt
            ├── data
            │   ├── model
            │   ├── repository
            │   │   └── RecipeRepository.kt
            │   └── source
            │       ├── local
            │       │   └── LocalDataSource.kt
            │       └── remote
            │           ├── RecipeAPIService.kt
            │           └── RemoteDataSource.kt
            ├── di
            │   ├── Qualifiers.kt
            │   └── modules
            │       └── ApplicationModule.kt
            ├── navigation
            │   ├── RecipeAppNavGraph.kt
            │   └── RecipeAppNavigation.kt
            ├── ui
            │   ├── base
            │   │   └── UiState.kt
            │   ├── components
            │   │   ├── ErrorScreen.kt
            │   │   ├── ExpandableText.kt
            │   │   └── LoadingScreen.kt
            │   ├── home
            │   │   ├── HomeScreen.kt
            │   │   └── HomeViewModel.kt
            │   ├── ingredients
            │   │   ├── IngredientsScreen.kt
            │   │   └── IngredientsViewModel.kt
            │   ├── main
            │   │   └── MainScreen.kt
            │   ├── mealplan
            │   │   ├── MealPlanScreen.kt
            │   │   └── MealPlanViewModel.kt
            │   ├── mealplandetails
            │   │   ├── MealPlanDetailsScreen.kt
            │   │   └── MealPlanDetailsViewModel.kt
            │   ├── recipebyingredients
            │   │   ├── RecipeByIngredientsScreen.kt
            │   │   └── RecipeByIngredientsViewModel.kt
            │   ├── recipedetail
            │   │   └── RecipeDetailsScreen.kt
            │   └── theme
            │       ├── Color.kt
            │       ├── Theme.kt
            │       └── Type.kt
            └── utils
                ├── AppConstants.kt
                └── Extensions.kt
```

## Setup

Follow these steps to run the project locally:

1. **Clone the repository**
   ```bash
   git clone https://github.com/aravindrajpalani/ComposeRecipeApp.git
   cd ComposeRecipeApp
   ```

2. **Get your API key**
   - Visit [spoonacular.com](https://spoonacular.com/food-api)
   - Sign up for a free account
   - Copy your API key from the dashboard

3. **Configure API key**
   - Open `native-lib.cpp` file inside src/main/cpp folder
   - Add your API key:
   ```
   std::string apiKey = "YOUR_API_KEY";
   ```

4. **Build and Run**
   - Sync Gradle files
   - Run the app on an emulator or physical device




