# Rocket Science

The Rocket Science app has all the requirements of the assignment.

## Architecture

The architecture of the app follows the best practices and recommended architecture for modern app development from the [android developer guide](https://developer.android.com/topic/architecture).

The overall architecture looks as shown on the image below:

<img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-overview.png" width="360" height="240">

The app has three main well-defined layers separated into packages, each package contains some child feature package with each feature's components.

- **Presentation**:
    - The Presentation layer, also known as UI layer, is responsible for displaying the application data on the screen and also serve as the primary entry point for user interaction.
    - Inside the Presentation layer the app has the MainActivity which adds the Fragments for each feature in the app. Even though we have only one screen to display the list of launches and company info if in the future there is a requirement for displaying another screen this could be easily done by implementing a new Fragment.
    - In this layer the app has the `Activities`, `Fragments` and its `ViewModels`, `Dialogs` and `Adapters` for the lists used.
- **Data**:
    - The Data layer contains the application data itself and connects to the SpaceX API.
    - Inside the Data layer the app has the implementation of the domain's repository contract to fetch the data. In this example data is fetched from the api which is accessed using the Retrofit library under the remote package. Finally there is a mapper which is responsible for mapping the response we get from the API, here the app uses a `DTO` and `Entity` types of classes to convert between remote, database and domain models in order for the domain models to not have any dependency to the remote implementation selected.
- **Domain**:
    - The domain Layer contains some core classes that are used on the App.
    - There is the `UiState` class under common package to represent the State of the UI. `DataResponse` used to pass the data response from data layer to presentation layer whereas Success is used after the execution returned successfully and Error is used when an error occurred during execution. And finally, a `StringUtils` with String extension functions.
    - Each feature has a package following the app architecture with its model and its repository contract.

Overall this architecture is very robust, follows the official android developer guideline and makes it easy to maintain and scale the application.

## Unit Tests

For unit testing there is a unit test for each feature view model (`CompanyViewModelTest` and `LaunchesViewModelTest`) which is where the business logic of the application is.

## Instruction to build the project

This is a standard Android application, there are no special setup need. Just import the project into Android Studio and run the App.

## Demo

TBD

## Libraries used

The project was developed using some of the Android Jetpack Libraries and some third-party libraries as well.

### Android Jetpack Libraries
- View Binding: Feature that allows to write code that interacts with views, replacing the `findViewById`. For more details see the [official docs](https://developer.android.com/topic/libraries/view-binding).
- Room: The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite. For more details see the [official docs](https://developer.android.com/jetpack/androidx/releases/room)

### Kotlin library:
- Kotlin coroutines: used to manage long-running tasks that might otherwise block the main thread. For more information see [The Kotlin coroutines official doc for Android](https://developer.android.com/kotlin/coroutines)
- Kotlin coroutines flow: used to emit multiple values sequentially from the data layer to the presentation layer. For more details check [Kotlin flows on Android](https://developer.android.com/kotlin/flow)

### Dependency Injection
- Hilt: dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in the project. For more details see the [Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

### Third-party libraries:
- Retrofit + OkHttp: Http clients to simplify the communication with the [r/SpaceX API Docs](https://documenter.getpostman.com/view/2025350/RWaEzAiG?version=latest#intro). [Official docs](https://square.github.io/retrofit/)
- Glide: Used to load the url images into the ImageView for the events. [Official docs](https://github.com/bumptech/glide)

### Testing libraries:
- Mockito: a mocking framework. [Official website](https://site.mockito.org/)
- Turbine: a library to test kotlinx.coroutines flows. [Official repository and docs](https://github.com/cashapp/turbine)
