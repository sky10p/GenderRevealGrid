# Architecture Reference

## Module structure

Single-module Android app: `:app`  
Package: `sky.programs.genderrevealgrid`

```
app/src/main/java/sky/programs/genderrevealgrid/
├── MainActivity.kt            — Single activity entry point, splash screen
├── model/
│   ├── RevealModels.kt        — Domain types: ThemeConfig, WinningGender, RevealCard, GameState
│   └── RevealCatalog.kt       — (within RevealModels) Theme catalogue + GameEngine
├── ui/
│   ├── GenderRevealApp.kt     — Root composable, state holder, in-memory navigation
│   ├── SetupScreen.kt         — Host configuration screen
│   ├── GameScreen.kt          — Gameplay screen (board, footer, celebration overlay)
│   ├── AppUi.kt               — App-level locale override + theme wrapper
│   ├── SharedComposables.kt   — HeroHeader, SectionCard, ThemeIconBadge, etc.
│   ├── Decorations.kt         — AnimatedDecorationLayer, per-theme decoration configs
│   ├── ThemeExtensions.kt     — Extension functions: colours, fonts, brushes per theme
│   ├── UiSupport.kt           — RevealCardView, RemainingCounter, ThemePicker, etc.
│   └── theme/                 — Material 3 typography + colour scheme
└── AppLocaleManager.kt        — Per-app locale management
```

---

## Navigation model

No `Navigation Compose` library is used. Navigation is a `mutableStateOf` in `GenderRevealApp`:

```kotlin
sealed interface AppDestination {
    data object Setup : AppDestination
    data class Game(val setup: SetupState) : AppDestination
}
```

- `Setup` → renders `SetupRoute` wrapping `SetupScreen`
- `Game(setup)` → renders `GameRoute` wrapping `GameScreen`
- Tapping **"Back to setup"** in the celebration overlay resets to `Setup`

---

## State

All UI state is held in `GenderRevealApp` using Compose state APIs:

| State | Type | Description |
|---|---|---|
| `destination` | `AppDestination` | Current screen |
| `selectedGender` | `WinningGender` | BOY or GIRL |
| `selectedThemeId` | `String` | Active theme ID |
| `boardTitle/Subtitle` | `String` | Board message fields |
| `celebrationTitle/Subtitle` | `String` | Celebration message fields |

`GameState` (cards + reveal sequence) is created fresh on each `Start reveal` tap and lives inside `GameRoute`.

---

## Game engine

`createRevealSequence(winner, cardCount)` in `RevealCatalog` / `GameEngine`:

1. Allocates 5 slots to the winner and 4 to the loser.
2. Forces slot 9 (index 8) to be the winner.
3. Uses random backtracking to fill slots 1–8 such that at no prefix does the winner gap exceed the remaining hidden count.
4. Returns an `IntArray` mapping card-tap order → `WinningGender`.

`revealCard(cardIndex)` maps the next item in the pre-computed sequence to the tapped card, then increments the pointer.

---

## Build variants

| Variant | `BuildConfig.DEBUG` | Theme picker visible |
|---|---|---|
| `debug` | `true` | Yes |
| `release` | `false` | No |

`buildConfig = true` is enabled in `app/build.gradle.kts` → `buildFeatures`.

---

## Testing

| Type | Location | Runner |
|---|---|---|
| Unit tests | `app/src/test/` | JUnit 4 |
| Instrumented UI tests | `app/src/androidTest/` | Compose test APIs (`createAndroidComposeRule`) |

Key test class: `GenderRevealFlowEnglishTest` — end-to-end flow covering setup strings, game screen, card flipping, and celebration.

Run unit tests:
```powershell
.\gradlew testDebugUnitTest
```

Run instrumented tests (requires a connected device/emulator):
```powershell
.\gradlew connectedDebugAndroidTest
```
