# M3 Expressive Showcase

An interactive, production-quality **Material 3 Expressive skeleton** built with Kotlin and Jetpack Compose. Use it as a visual reference, a starting point, or a small design-system laboratory.

## What is inside

- **Discover** — editorial hero layout, strong typographic hierarchy, shape contrast and tonal color.
- **Components** — linear and circular wavy progress, a connected split button, a connected toggle group, morphing icon toggles, morphing loading indicators and a floating toolbar.
- **Motion** — interactive shape morphing and a tunable spring playground. The app uses `MotionScheme.expressive()` globally.
- **Color** — three custom palettes, dynamic color on Android 12+, light/dark/system modes, live color roles, typography and shape specimens.
- Demo state (slider values, toggles, the chosen download format) survives rotation, process death and scrolling a section out of the list; each destination also keeps its scroll position when you switch tabs.
- Back walks the tab stack before leaving the app, snackbars replace each other instead of queueing, and the hero and palette grids reflow at large font scales.
- Selection rows, palette choices, segmented buttons and shape-morphing calls expose roles and state to TalkBack; decorative marks are hidden from it and headings are marked for navigation.

## Stack

| Layer | Choice |
|---|---|
| UI | Jetpack Compose |
| Design system | Material 3 `1.5.0-alpha04`, `MaterialExpressiveTheme` |
| Language / build | Kotlin 2.0, Gradle Kotlin DSL, AGP 8.7 |
| Android | minSdk 26 · target/compileSdk 35 |
| CI | GitHub Actions, unit tests and four per-ABI release APKs |

> Material 3 Expressive APIs are experimental and annotated with `ExperimentalMaterial3ExpressiveApi`. Pin and test your version when using this skeleton in production.

## Run locally

Requirements: JDK 17 and Android SDK 35.

```bash
./gradlew testDebugUnitTest
./gradlew assembleDebug
./gradlew assembleRelease
```

Open the project in a recent Android Studio and run the `app` configuration.

`testDebugUnitTest` covers more than the build script: it guards the ABI split configuration, fails if state is ever saved with a non-saveable specialized `MutableState`, and rejects any palette whose text roles drop below WCAG AA (4.5:1) contrast in light or dark.

## APK per architecture

ABI splits are enabled in `app/build.gradle.kts`; universal APK generation is deliberately disabled. A release build creates:

- `m3-expressive-1.0.0-arm64-v8a.apk` — most modern Android phones
- `m3-expressive-1.0.0-armeabi-v7a.apk` — older 32-bit ARM devices
- `m3-expressive-1.0.0-x86_64.apk` — 64-bit emulators and x86 devices
- `m3-expressive-1.0.0-x86.apk` — 32-bit x86 devices

Each push to `main`, pull request and manual workflow dispatch runs **Android APK per ABI**. After unit tests, four independent jobs upload one APK plus a SHA-256 checksum each. Download them from the workflow run's **Artifacts** section. Pushing a `v*` tag also attaches all APKs to a GitHub Release.

For immediate installability, the showcase release variant uses the standard debug signing key. Replace this with your private upload key before distributing a real product.

## Project map

```text
app/src/main/java/com/example/m3expressive/
├── MainActivity.kt
├── theme/
│   ├── Palettes.kt            # brand color schemes, light + dark, every role defined
│   └── Theme.kt               # type scale, shapes, expressive motion, resolved dark state
└── ui/
    ├── ShowcaseApp.kt         # shell, destinations, persisted preferences
    ├── components/            # reusable showcase primitives + max-width screen wrapper
    └── screens/               # Discover, Components, Motion, Color
.github/workflows/android-apk.yml
```

## Design principles

1. **Shape with intent:** sharp and generously rounded forms create focal tension.
2. **Color with courage:** vivid accents work with harmonious tonal containers.
3. **Motion with meaning:** springs explain spatial changes instead of decorating them.
4. **Type with hierarchy:** heavy editorial headlines contrast with readable body copy.

## License

MIT — see [LICENSE](LICENSE).
