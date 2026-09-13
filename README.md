# Theme Studio 🎨📱

Aplicación de temas para Android con iconos personalizados, widgets interactivos y fondos de pantalla en resolución 8K Ultra HD.

---

## 🚀 Cómo compilar y descargar el APK en GitHub

El proyecto ya cuenta con un flujo automatizado de Integración Continua (**GitHub Actions**) configurado en `.github/workflows/build-apk.yml`.

### Opción 1: Compilación Automática (Al hacer Push)
1. Conecta o sube este repositorio a tu cuenta de GitHub (mediante la opción **Push to GitHub** en el menú de ajustes de AI Studio o usando `git push`).
2. Cada vez que hagas `push` a la rama `main` o `master`, GitHub Actions compilará automáticamente el APK.

### Opción 2: Compilación Manual (Un solo clic)
1. En tu repositorio de GitHub, haz clic en la pestaña **Actions** en la parte superior.
2. En la lista de flujos a la izquierda, selecciona **Compilar APK Android**.
3. Haz clic en el botón desplegable **Run workflow**.
4. Selecciona la rama y haz clic en el botón verde **Run workflow**.

---

## 📥 Dónde descargar el archivo APK una vez compilado

1. En la pestaña **Actions**, entra en la ejecución más reciente del flujo (tendrá un círculo verde ✅ cuando termine).
2. Desplázate hasta el final de la página a la sección **Artifacts** (Artefactos).
3. Haz clic sobre **ThemeStudio-Debug-APK** para descargar el archivo ZIP que contiene tu APK listo para instalar en cualquier teléfono Android.

---

## 🛠️ Tecnologías Utilizadas
- **Android Gradle Plugin (AGP)** 9.1.1
- **Gradle** 9.3.1
- **Java / JDK** 21 (Temurin)
- **Kotlin** 2.2.10
- **Jetpack Compose** & **Material Design 3**
- **Room Database** (Persistencia local)
- **AppWidgetManager** & **ShortcutManager** (Integración con el launcher de Android)
