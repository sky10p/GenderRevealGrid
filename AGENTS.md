# AGENTS.md

## Project overview

`Gender Reveal Grid` is a single-module Android app built with Kotlin and Jetpack Compose.
The app reveals the final result through a 3x3 card grid where the ninth card determines the outcome.

## Stack

- Kotlin
- Jetpack Compose
- Material 3
- Single Activity architecture
- Compose state-driven navigation without `Navigation Compose`
- Unit tests with JUnit
- Instrumented UI tests with Compose test APIs

## Repository structure

- `app/src/main/java/sky/programs/genderrevealgrid/`: app code
- `app/src/main/res/`: Android resources
- `app/src/androidTest/`: instrumented tests
- `app/src/test/`: local unit tests

## Working conventions

- Keep documentation, comments, and developer-facing text in English.
- Keep the base app strings in English under `app/src/main/res/values/strings.xml`.
- Add localized user-facing strings in locale-specific resource folders such as `values-es` when needed.
- Preserve the existing state-driven Compose approach; do not introduce `Navigation Compose` unless explicitly requested.
- Keep the reveal flow spoiler-safe:
  - hidden cards should not expose a fixed gender before reveal
  - the visible state after 8 reveals should remain `4-4`
  - the ninth card should determine the final result
- The gameplay screen should not show the selected theme name.
- The footer should only reflect already revealed information.

## UI and animation notes

- Be careful with Compose units:
  - `Dp` values must be converted through density before mixing with pixel-based constraints or offsets
  - use `LocalDensity` when applying `Dp` to `IntOffset`, canvas coordinates, or layout constraints
- Keep theme-specific decorations and atmosphere consistent with the selected theme.

## Testing

Run these commands after meaningful changes:

```powershell
.\gradlew testDebugUnitTest
```

```powershell
.\gradlew connectedDebugAndroidTest
```

Use this when you need a debug build:

```powershell
.\gradlew assembleDebug
```

## Environment notes

- The app module is `:app`.
- The package name is `sky.programs.genderrevealgrid`.
- A local debug keystore may exist at `.android/debug.keystore` and can be used for debug builds in this workspace.
