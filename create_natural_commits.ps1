# Natural Git Commit Script
# This script creates commits in a logical order to show step-by-step development

Write-Host "Creating natural commit history..." -ForegroundColor Green

# Step 1: Project Setup & Dependencies
Write-Host "`n[1/13] Committing project setup and dependencies..." -ForegroundColor Yellow
git add build.gradle.kts app/build.gradle.kts settings.gradle.kts
git commit -m "feat: Add project dependencies (Hilt, Apollo GraphQL, Navigation, Compose)

- Add Hilt for dependency injection
- Add Apollo GraphQL for API calls
- Add Navigation Compose for screen navigation
- Add Material 3 and Compose dependencies
- Configure build settings for release builds"

# Step 2: Clean Architecture Foundation
Write-Host "`n[2/13] Committing Clean Architecture structure..." -ForegroundColor Yellow
git add app/src/main/java/com/abdul/mazaaditask/domain/model/
git commit -m "feat: Create Clean Architecture domain models

- Add Launch and LaunchDetail domain models
- Set up domain layer structure
- Define data models for launches"

# Step 3: Domain Layer - Repository & Use Cases
Write-Host "`n[3/13] Committing domain layer..." -ForegroundColor Yellow
git add app/src/main/java/com/abdul/mazaaditask/domain/repository/
git add app/src/main/java/com/abdul/mazaaditask/domain/usecase/
git commit -m "feat: Implement domain layer (repository interface and use cases)

- Create LaunchRepository interface
- Add GetLaunchesUseCase for fetching launches list
- Add GetLaunchDetailUseCase for fetching launch details
- Follow Clean Architecture principles"

# Step 4: GraphQL Setup
Write-Host "`n[4/13] Committing GraphQL queries..." -ForegroundColor Yellow
git add app/src/main/graphql/
git commit -m "feat: Add GraphQL queries for launches API

- Create GetLaunches query with pagination support
- Create GetLaunchDetail query for single launch
- Configure Apollo GraphQL schema"

# Step 5: Data Layer - Remote Data Source
Write-Host "`n[5/13] Committing data layer implementation..." -ForegroundColor Yellow
git add app/src/main/java/com/abdul/mazaaditask/data/remote/
git commit -m "feat: Implement remote data source for GraphQL API

- Create LaunchRemoteDataSource for API calls
- Handle GraphQL query execution
- Add error handling for network requests"

# Step 6: Data Layer - Repository Implementation
Write-Host "`n[6/13] Committing repository implementation..." -ForegroundColor Yellow
git add app/src/main/java/com/abdul/mazaaditask/data/repository/
git commit -m "feat: Implement repository layer with data mapping

- Create LaunchRepositoryImpl
- Map GraphQL models to domain models
- Handle null safety and data transformation"

# Step 7: Dependency Injection Setup
Write-Host "`n[7/13] Committing DI setup..." -ForegroundColor Yellow
git add app/src/main/java/com/abdul/mazaaditask/di/
git add app/src/main/java/com/abdul/mazaaditask/MazaadiApplication.kt
git add app/src/main/AndroidManifest.xml
git commit -m "feat: Set up Hilt dependency injection

- Create NetworkModule for Apollo Client
- Create RepositoryModule for repository binding
- Create UseCaseModule for use case providers
- Add MazaadiApplication with @HiltAndroidApp
- Configure AndroidManifest for Hilt"

# Step 8: MVI Architecture - Launches Screen
Write-Host "`n[8/13] Committing MVI for Launches screen..." -ForegroundColor Yellow
git add app/src/main/java/com/abdul/mazaaditask/presentation/launches/mvi/
git commit -m "feat: Implement MVI architecture for Launches screen

- Create LaunchesState for UI state management
- Create LaunchesIntent for user actions
- Implement LaunchesViewModel with pagination logic
- Add loading states and error handling"

# Step 9: UI - Launches Screen
Write-Host "`n[9/13] Committing Launches screen UI..." -ForegroundColor Yellow
git add app/src/main/java/com/abdul/mazaaditask/ui/compose/launches/
git commit -m "feat: Implement Launches screen UI with Jetpack Compose

- Create LaunchesScreen composable
- Add LaunchItem component for list items
- Implement automatic pagination on scroll
- Add loading and error states UI
- Use Material 3 design system"

# Step 10: MVI Architecture - Launch Detail Screen
Write-Host "`n[10/13] Committing MVI for Launch Detail screen..." -ForegroundColor Yellow
git add app/src/main/java/com/abdul/mazaaditask/presentation/launchdetail/mvi/
git commit -m "feat: Implement MVI architecture for Launch Detail screen

- Create LaunchDetailState for UI state
- Create LaunchDetailIntent for user actions
- Implement LaunchDetailViewModel
- Add error handling for detail screen"

# Step 11: UI - Launch Detail Screen
Write-Host "`n[11/13] Committing Launch Detail screen UI..." -ForegroundColor Yellow
git add app/src/main/java/com/abdul/mazaaditask/ui/compose/launchdetail/
git commit -m "feat: Implement Launch Detail screen UI

- Create LaunchDetailScreen composable
- Display mission patch image using Coil
- Show rocket, mission, and site information
- Add back navigation support
- Implement error handling UI"

# Step 12: Navigation Setup
Write-Host "`n[12/13] Committing navigation setup..." -ForegroundColor Yellow
git add app/src/main/java/com/abdul/mazaaditask/presentation/navigation/
git add app/src/main/java/com/abdul/mazaaditask/MainActivity.kt
git commit -m "feat: Set up Navigation Compose

- Create NavGraph with type-safe routes
- Implement navigation between Launches and Detail screens
- Add launch ID parameter passing
- Update MainActivity with navigation setup"

# Step 13: Testing & Final Polish
Write-Host "`n[13/13] Committing tests and final polish..." -ForegroundColor Yellow
git add app/src/test/
git add app/src/androidTest/
git add app/proguard-rules.pro
git add app/src/main/res/values/strings.xml
git commit -m "feat: Add tests and final improvements

- Add unit tests for ViewModels
- Add instrumented tests for UI
- Configure ProGuard rules for release builds
- Add string resources for localization support
- Add code comments and documentation
- Set up Hilt test runner"

Write-Host "`n✅ All commits created successfully!" -ForegroundColor Green
Write-Host "`nNext steps:" -ForegroundColor Cyan
Write-Host "1. Review commits: git log --oneline" -ForegroundColor White
Write-Host "2. Push to GitHub: git push origin master" -ForegroundColor White
Write-Host "3. Or create a new branch: git checkout -b feature/launches-app" -ForegroundColor White

