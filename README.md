# JustWeather
ReadMe will be updated once the project is somewhat finished and will showcase the use of it. Be patient, Rome wasn't built in a day but this isn't Rome anyways so it'll some extra time

Minimum SDK level 26

- 100% Kotlin based + Coroutines + Flow for asynchronous.
- JetPack
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct local database for offline caching

- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Bindables - Android DataBinding kit for notifying data changes to UI layers.
  - Repository and UseCase pattern
  - Koin - dependency injection

- Material Design & Animations
- Retrofit2 & Gson - constructing the REST API
- OkHttp3 - implementing interceptor, logging and mocking web server
- Glide - loading images
- Timber - logging
