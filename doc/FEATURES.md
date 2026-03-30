# App Features Reference

## Setup Screen

The setup screen is the host-only configuration step taken before handing the device to guests. It is always the first screen shown on launch.

### Sections

**Gender selector**
- Two `FilterChip` options: **Boy** (blue accent `#42A5F5`) and **Girl** (pink accent `#EC407A`).
- The selected value is the secret winning gender. Guests must not see this selection before the reveal.

**Board intro messages**
- **Board title** and **Board subtitle** — free-text fields displayed above the 3×3 card grid during gameplay.
- Pre-populated with theme-specific and gender-neutral defaults (e.g., _"Surprise! / What will baby be?"_).
- Changing the gender or theme auto-replaces the defaults only if the user has not manually edited the fields.

**Celebration messages**
- **Celebration title** and **Celebration subtitle** — shown in the win overlay once the ninth card is revealed.
- Pre-populated with gender-specific and theme-specific defaults (e.g., _"It's a girl! / The family grows with a beautiful girl."_).

**Theme picker (debug builds only)**
- A grid of theme cards for selecting the visual theme.
- Hidden in release builds to avoid premature complexity before themes are fully polished.

**Start reveal button**
- Sticky bottom bar. Tapping it transitions to the Game screen.

---

## Game Screen

### Header
- Small eyebrow label: _"A paper-sweet little mystery"_
- Configurable board title and subtitle below the eyebrow.

### 3×3 Board
- Panel up to 408 dp wide, heavily rounded corners, drop shadow.
- Contains 9 circular reveal cards.

**Hidden card face**
- White/theme gradient, `"?"` label in the theme's accent colour.
- Subtle specular highlight circle for a glassy look.

**Revealed card face — Boy**
- Gradient: `#EAF8FF → #BAE6FD → #E7F7FF`
- Label "Boy" in `#0D6990`

**Revealed card face — Girl**
- Gradient: `#FFF0F7 → #FBCFE8 → #FFE6F1`
- Label "Girl" in `#AD3C73`

**Card flip animation**
- 180° Y-axis rotation over 820 ms (`FastOutSlowIn` easing).
- Press feedback: scale to 0.95 (spring animation).
- Post-reveal: scale up to 1.02.

### Progress Footer (RemainingCounter)

A pill-shaped panel beneath the board showing:
1. **Title** — "Reveal progress"
2. **Instruction** — how many circles remain, with a spoiler-safe plural string.
3. **Progress dots** — 9 coloured dots (blue = boy revealed, pink = girl revealed, grey = hidden).
4. **Stats row** — Boy count · Girl count · Hidden count.

The footer only reflects cards already revealed and never hints at what is hidden.

### Game Engine (spoiler-safe sequence)

- The winning gender always receives **5 cards**; the losing gender receives **4**.
- The **9th card is always the winner**.
- After any partial reveal the gap between boy and girl counts never exceeds the number of remaining hidden cards, maintaining a balanced, suspenseful experience.
- The sequence is computed once on `Start reveal` using a random backtracking algorithm.

---

## Celebration Overlay

Shown after the 9th card is tapped.

- Full-screen dark scrim (`#171827` at 60% alpha).
- **Confetti layer** — animated particles in theme + winner accent colours.
- Floating card containing:
  - Theme icon badge (72 dp)
  - Celebration title (44 sp, bold)
  - Celebration subtitle (30 sp, winner accent colour)
  - **"Back to setup"** button — resets to the Setup screen.

---

## Visual Themes

| ID | Name (EN) | Name (ES) | Premium |
|---|---|---|---|
| `cotton-sky` | Cotton Sky | Cielo de algodón | No |
| `golden-dust` | Golden Dust | Polvo dorado | No |
| `superhero` | Superhero | Superhero | Yes ⭐ |
| `magic-world` | Magic world | Magic world | Yes ⭐ |

Each theme defines:
- Background gradient
- Board panel colour
- Card face colours
- Header fonts and colours
- Bottom bar colour
- Icon badge
- Animated decoration set (clouds, balloons, stars, lightning, owls, candles, etc.)

Theme decorations are drawn behind all UI content using `AnimatedDecorationLayer`. Each particle has an assigned motion type (balloon sway, cloud sweep, confetti rise, hero flyby, moon drift, etc.).

> **Note:** The theme picker is only visible in debug builds.

---

## Localisation

The app ships with **English** (default) and **Spanish** string resources.

- Default: `res/values/strings.xml`
- Spanish: `res/values-es/strings.xml`

Per-app locale can be changed programmatically via `AppLocaleManager`, which wraps the context in `attachBaseContext`. On Android 13+ the system per-app locale API is also used.
