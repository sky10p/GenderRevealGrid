# Gender Reveal Grid

Android app for revealing a baby's gender by uncovering 9 cards in a 3x3 grid.

## Current stack

- Kotlin + Jetpack Compose
- Material 3
- Single Activity (`MainActivity`)
- Compose state-driven navigation without `Navigation Compose`
- Pure Kotlin business logic to keep unit testing straightforward

## Implemented features

- Setup screen with gender selection, custom copy, and premium themes.
- Gameplay screen with a 3x3 board, 3D flip animation, animated decorations, and a bottom counter.
- Final celebration overlay with the configured message and animated confetti.
- Custom visual branding with a pastel balloon icon inside a medallion and a consistent splash screen from Android 7+.
- Full localization with English as the base in `app/src/main/res/values/strings.xml` and Spanish in `app/src/main/res/values-es/strings.xml`.
- All visible UI strings and default reveal messages are resolved from resources.
- Setup includes two independent message blocks:
  - board header
  - final celebration
- Fixed bottom CTA in setup so the reveal can start without relying on scrolling.
- Anti-spoiler logic: the sequence feels random, allows streaks, but preserves mathematical uncertainty until the ninth card.
- Board footer is based only on already revealed information: boys, girls, and hidden cards.
- The gameplay view does not show the theme name, only the configured message and the visual atmosphere.
- Each theme defines its own animated background decorations.

## Key logic

- The deck always contains 9 cards.
- Hidden cards do not expose a fixed gender in the UI before a tap; the gender is assigned when revealed.
- If `GIRL` wins, the safe sequence reveals 5 `GIRL` and 4 `BOY`.
- If `BOY` wins, the safe sequence reveals 5 `BOY` and 4 `GIRL`.
- Before the ninth card, the invariant `abs(revealed_boys - revealed_girls) <= hidden` always holds.
- The eighth reveal always leaves a visible `4-4` state.
- The celebration is triggered only after the ninth card is revealed.

## Tests

```powershell
.\gradlew testDebugUnitTest
```

```powershell
.\gradlew connectedDebugAndroidTest
```

The `androidTest` suite covers the Compose E2E flow, separate board and celebration customization, revealed-progress footer behavior, strict ninth-card logic, and English/Spanish rendering through locale override in instrumented tests.

## Acceptance Criteria

- The board header uses the text configured in setup.
- Setup allows editing the board intro and final celebration independently.
- The footer information never reveals future gender composition.
- The gameplay view does not show the selected theme name.
- After 8 reveals, the visible split is always `4-4`.
- The ninth card decides the outcome and triggers the celebration.
- `Start reveal` remains visible in setup without prior scrolling.
- `assembleDebug`, `testDebugUnitTest`, and `connectedDebugAndroidTest` must all pass.

Note: to simplify instrumented validation in this environment, the project may use a local debug keystore at `.android/debug.keystore` if that file exists.
