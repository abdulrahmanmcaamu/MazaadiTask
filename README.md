# MazaadiTask – Android Home Assignment

This project is an Android application developed as part of a technical assignment.  
It demonstrates the usage of **Jetpack Compose, Clean Architecture, and the MVI pattern** with **Apollo GraphQL** integration.



##  Features

• List of launches fetched from GraphQL backend  
• Launch detail screen with full information  
• Cursor-based pagination (load more on scroll)  
• Light and Dark theme support  
• Navigation between list and detail screens  
• Zero-crash stability with safe state handling  
• Localization ready (string resources structured)  
• Release-build configuration enabled


##  Tech Stack

• Kotlin  
• Jetpack Compose  
• MVI Architecture  
• Clean Architecture  
• Apollo GraphQL Client  
• Coroutines + Flow  
• ViewModel  
• Navigation Compose  
• Material 3



##  Architecture Overview

The project follows **Clean Architecture** with three layers:

###  Data Layer
• Apollo GraphQL API  
• DTOs  
• Repository implementation

### Domain Layer
• Use cases  
• Domain models

###  Presentation Layer
• MVI intents  
• UI state  
• ViewModels  
• Jetpack Compose screens

### ��� Unidirectional Data Flow

Intent → ViewModel → UseCase → Repository → GraphQL API  


##  API

GraphQL endpoint used in the app:

https://apollo-fullstack-tutorial.herokuapp.com/graphql

• Apollo client generates type-safe Kotlin models  
• Pagination implemented using cursor and hasMore flag



## Testing

The project includes:

• ViewModel tests  
• Business logic tests  
• Pagination logic validation

---

##  Build Types

• Debug  
• Release (minify enabled and proguard configured)

---

## Localization Ready

• All user-visible strings are placed in `strings.xml`  
• Jetpack Compose uses `stringResource()`

This allows easy addition of multiple languages in the future.

---

## Future Improvements (Optional)

• Offline mode using Room database  
• Apollo normalized cache  
• More Compose UI tests  
• Skeleton loading animations

---

##  Author

Abdul Rahman  

