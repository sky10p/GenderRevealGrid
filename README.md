# Gender Reveal Grid

Aplicacion Android para revelar el genero del bebe descubriendo 9 cartas en una cuadricula 3x3.

## Stack actual

- Kotlin + Jetpack Compose
- Material 3
- Single Activity (`MainActivity`)
- Navegacion por estado Compose, sin `Navigation Compose`
- Logica de negocio pura en Kotlin para facilitar tests unitarios

## Feature implementada

- Pantalla de configuracion con selector de genero, textos personalizados y temas premium.
- Pantalla de juego con tablero 3x3, giro 3D, decoraciones animadas y contador inferior.
- Overlay final de celebracion con el mensaje configurado y confeti animado.
- Branding visual con icono propio de globo pastel en medallon y splash screen consistente desde Android 7+.
- Localizacion completa con ingles base en `app/src/main/res/values/strings.xml` y espanol en `app/src/main/res/values-es/strings.xml`.
- Todos los textos visibles de la UI y los mensajes por defecto de la revelacion se resuelven desde recursos.
- Setup con dos bloques de mensaje independientes:
  - cabecera del tablero
  - celebracion final
- CTA fijo inferior en setup para lanzar la revelacion sin depender del scroll.
- Logica anti-spoiler: la secuencia parece random, admite rachas, pero mantiene la incertidumbre matematica hasta la novena carta.
- Footer del tablero basado solo en informacion ya descubierta: ninos, ninas y ocultas.
- En la vista de juego no se muestra el nombre del tema; solo el mensaje configurado y la atmosfera visual.
- Cada tema define sus propias decoraciones animadas de fondo.

## Logica clave

- El mazo siempre contiene 9 cartas.
- Las cartas ocultas no exponen genero fijo en UI antes del click; el genero se asigna al revelarse.
- Si gana `GIRL`, la secuencia segura revela 5 `GIRL` y 4 `BOY`.
- Si gana `BOY`, la secuencia segura revela 5 `BOY` y 4 `GIRL`.
- Antes de la novena carta siempre se cumple `abs(ninos_descubiertos - ninas_descubiertas) <= ocultas`.
- La octava revelacion deja siempre un estado visible `4-4`.
- La celebracion solo se activa al revelar la novena carta.

## Tests

```powershell
.\gradlew testDebugUnitTest
```

```powershell
.\gradlew connectedDebugAndroidTest
```

Los `androidTest` cubren flujo E2E Compose, personalizacion separada de tablero y celebracion, footer de progreso revelado, regla estricta de la novena carta y renderizado en ingles/espanol mediante override de locale para pruebas instrumentadas.

## Acceptance Criteria

- La cabecera del tablero usa los textos configurados en setup.
- Setup permite editar por separado la intro del tablero y la celebracion final.
- La informacion del footer nunca revela composicion futura por genero.
- La vista de juego no muestra el nombre del tema seleccionado.
- Tras 8 revelaciones el reparto visible siempre es `4-4`.
- La novena carta decide el resultado y dispara la celebracion.
- `Start reveal` permanece visible en setup sin scroll previo.
- `assembleDebug`, `testDebugUnitTest` y `connectedDebugAndroidTest` deben quedar en verde.

Nota: para facilitar la validacion instrumentada en este entorno, el proyecto puede usar un debug keystore local en `.android/debug.keystore` si ese archivo existe.
