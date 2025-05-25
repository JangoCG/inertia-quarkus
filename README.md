# Quarkus Inertia.js Extension

[![CI](https://github.com/cengiz-io/quarkus-inertia/actions/workflows/ci.yml/badge.svg)](https://github.com/cengiz-io/quarkus-inertia/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.gurtus.inertia-quarkus/inertia.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.gurtus.inertia-quarkus%22%20AND%20a:%22inertia%22)

A Quarkus extension for [Inertia.js](https://inertiajs.com/) - the modern monolith approach to web development.

## What is Inertia.js?

Inertia.js is a new approach to building classic server-driven web apps. It allows you to create fully client-side rendered, single-page apps, without the complexity of modern SPAs.

## Features

- ✅ **Complete Inertia.js Protocol Support**
- ✅ **Asset Versioning** for cache-busting
- ✅ **Partial Reloads** for optimized performance
- ✅ **Server-Side Rendering (SSR)** Support
- ✅ **Qute Template Integration**
- ✅ **CDI Integration**
- ✅ **Native Image Support**
- ✅ **Hot Module Replacement** in dev mode

## Installation

### Using Maven

Add the extension to your Quarkus project:

```xml
<dependency>
    <groupId>com.gurtus.inertia-quarkus</groupId>
    <artifactId>inertia</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Using Quarkus CLI

```bash
quarkus extension add com.gurtus.inertia-quarkus:inertia
```

### Using Gradle

```gradle
implementation 'com.gurtus.inertia-quarkus:inertia:1.0.0'
```

## Configuration

```properties
# application.properties

# Template for Inertia pages (default: inertia.html)
quarkus.inertia.root-template=inertia.html

# Asset version for cache-busting
quarkus.inertia.version=1.0.0

# Application URL prefix
quarkus.inertia.url=/

# Enable Server-Side Rendering
quarkus.inertia.ssr-enabled=false
quarkus.inertia.ssr-url=http://127.0.0.1:13714
quarkus.inertia.ssr-timeout=30000
```

## Usage

## Usage

### 1. Create a Template

Create a Qute template at `src/main/resources/templates/inertia.html`:

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>My Inertia App</title>
    <link rel="stylesheet" href="/css/app.css" />
  </head>
  <body>
    <div id="app" data-page="{page|jsonify}"></div>
    <script src="/js/app.js"></script>
  </body>
</html>
```

### 2. Create a Controller

```java
@Path("/")
public class HomeController {

    @Inject
    InertiaService inertiaService;

    @GET
    @Path("/")
    public void home(@Context RoutingContext context) {
        inertiaService.render(context, "Home", Map.of(
            "message", "Welcome to Inertia.js with Quarkus!",
            "user", getCurrentUser()
        ));
    }

    @GET
    @Path("/about")
    public void about(@Context RoutingContext context) {
        inertiaService.render(context, "About", Map.of(
            "title", "About Us",
            "description", "A modern web app with Inertia.js"
        ));
    }
}
```

### 3. Frontend Setup

#### React Example

```bash
npm install @inertiajs/react react react-dom
```

```jsx
// main.jsx
import { createInertiaApp } from "@inertiajs/react";
import { createRoot } from "react-dom/client";

createInertiaApp({
  resolve: (name) => {
    const pages = import.meta.glob("./Pages/**/*.jsx", { eager: true });
    return pages[`./Pages/${name}.jsx`];
  },
  setup({ el, App, props }) {
    createRoot(el).render(<App {...props} />);
  },
});
```

```jsx
// Pages/Home.jsx
export default function Home({ message, user }) {
  return (
    <div>
      <h1>{message}</h1>
      <p>Welcome, {user.name}!</p>
    </div>
  );
}
```

#### Vue Example

```bash
npm install @inertiajs/vue3 vue@next
```

```js
// main.js
import { createApp, h } from "vue";
import { createInertiaApp } from "@inertiajs/vue3";

createInertiaApp({
  resolve: (name) => {
    const pages = import.meta.glob("./Pages/**/*.vue", { eager: true });
    return pages[`./Pages/${name}.vue`];
  },
  setup({ el, App, props, plugin }) {
    createApp({ render: () => h(App, props) })
      .use(plugin)
      .mount(el);
  },
});
```

```vue
<!-- Pages/Home.vue -->
<template>
  <div>
    <h1>{{ message }}</h1>
    <p>Welcome, {{ user.name }}!</p>
  </div>
</template>

<script>
export default {
  props: ["message", "user"],
};
</script>
```

## Advanced Features

### Form Handling

```java
@POST
@Path("/users")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public void createUser(@BeanParam UserForm form, @Context RoutingContext context) {
    try {
        userService.create(form);
        inertiaService.redirect(context, "/users");
    } catch (ValidationException e) {
        inertiaService.redirect(context, "/users/create")
            .withErrors(e.getErrors())
            .withInput(form);
    }
}
```

### Shared Data

```java
@ApplicationScoped
public class InertiaShareProvider {

    @InertiaShare
    public Map<String, Object> shareAuth(@Context SecurityContext securityContext) {
        return Map.of(
            "auth", Map.of(
                "user", getCurrentUser(securityContext)
            )
        );
    }
}
```

### Asset Versioning

```properties
quarkus.inertia.version=${git.commit.id.abbrev}
```

### Server-Side Rendering

Enable SSR in your configuration:

```properties
quarkus.inertia.ssr-enabled=true
quarkus.inertia.ssr-url=http://127.0.0.1:13714
```

## Examples

Check out the [integration tests](integration-tests/) for complete working examples with:

- React setup with Vite
- Vue.js integration
- Form handling and validation
- File uploads
- Server-side rendering

## Development

### Building from Source

```bash
git clone https://github.com/cengiz-io/quarkus-inertia.git
cd quarkus-inertia
./mvnw clean install
```

### Running Integration Tests

```bash
cd integration-tests
npm install
./mvnw quarkus:dev
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Development Setup

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Add tests for your changes
5. Ensure all tests pass (`./mvnw clean verify`)
6. Commit your changes (`git commit -m 'Add some amazing feature'`)
7. Push to the branch (`git push origin feature/amazing-feature`)
8. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

- 📖 [Documentation](https://github.com/cengiz-io/quarkus-inertia)
- 🐛 [Issue Tracker](https://github.com/cengiz-io/quarkus-inertia/issues)
- 💬 [Discussions](https://github.com/cengiz-io/quarkus-inertia/discussions)

## Acknowledgments

- [Inertia.js](https://inertiajs.com/) - The amazing frontend framework
- [Quarkus](https://quarkus.io/) - The supersonic, subatomic Java framework
- The Java and JavaScript communities

````

### 3. Frontend Setup

Installiere die Inertia.js Client-Bibliothek für dein Frontend-Framework:

```bash
# Für Vue.js
npm install @inertiajs/vue3

# Für React
npm install @inertiajs/react

# Für Svelte
npm install @inertiajs/svelte
````

### 4. Shared Props

Du kannst Props global für alle Inertia-Responses teilen:

```java
@ApplicationScoped
public class InertiaSetup {

    @Inject
    InertiaService inertiaService;

    @PostConstruct
    void setup() {
        // Globale Props für alle Seiten
        inertiaService.share("app", Map.of(
            "name", "Meine App",
            "version", "1.0.0"
        ));
    }
}
```

## Beispiel-Projekt

Das `integration-tests` Modul enthält ein vollständiges Beispiel mit:

- Verschiedenen Routen
- Shared Props
- Template-Konfiguration
- Frontend-Integration

## Vergleich mit dem Go Adapter

Diese Extension implementiert die gleiche Funktionalität wie der [Go Inertia Adapter](https://github.com/inertiajs/inertia-go):

| Feature          | Go Adapter | Quarkus Extension |
| ---------------- | ---------- | ----------------- |
| Page Rendering   | ✅         | ✅                |
| Asset Versioning | ✅         | ✅                |
| Partial Reloads  | ✅         | ✅                |
| Shared Props     | ✅         | ✅                |
| SSR Support      | ✅         | ✅                |
| Middleware       | ✅         | ✅ (Filter)       |

## Entwicklung

### Für Entwickler ohne Quarkus Extension Erfahrung

#### 1. Extension lokal bauen und installieren

```bash
# Extension komplett bauen und im lokalen Maven Repository installieren
mvn clean install -DskipTests
```

#### 2. Demo-Anwendung starten

```bash
# In das Integration-Tests Verzeichnis wechseln
cd integration-tests

# Demo-Anwendung im Dev-Modus starten
mvn quarkus:dev
```

#### 3. Im Browser testen

Öffne deinen Browser und gehe zu: **http://localhost:8080**

**🎉 Du solltest jetzt eine schöne Demo-Seite sehen mit:**

- ✅ Erfolgsmeldung der Extension
- 📊 Aktuelle Page-Daten (Component, URL, Version)
- 🔗 Klickbare Test-Links zu allen Routes
- 🧪 Interaktiver "Test XHR Request" Button
- 📋 JSON-Darstellung der Inertia Page-Daten

**Wichtige URLs zum Testen:**

- **Test-Route**: http://localhost:8080/test
  - Zeigt: "Inertia Extension is loaded!"
- **Inertia HTML-Responses** (normale Browser-Requests):
  - http://localhost:8080/ (Home)
  - http://localhost:8080/about (About)
  - http://localhost:8080/users (Users)
- **Inertia JSON-Responses** (für XHR/AJAX):

  ```bash
  # Home-Route als JSON
  curl -H "X-Inertia: true" http://localhost:8080/

  # About-Route als JSON
  curl -H "X-Inertia: true" http://localhost:8080/about

  # Users-Route als JSON
  curl -H "X-Inertia: true" http://localhost:8080/users
  ```

**🔥 Interaktiver Test im Browser:**
Klicke auf den "Test XHR Request" Button auf der Demo-Seite, um zu sehen, wie Inertia.js XHR-Requests funktionieren!

#### 4. Template-Problem beheben

Falls du "Error rendering template: null" siehst, liegt das daran, dass das Qute-Template nicht gefunden wird.

**Lösung**: Das Template `inertia.html` muss im richtigen Verzeichnis liegen:

```
integration-tests/src/main/resources/templates/inertia.html
```

#### 5. Vollständige Demo mit Frontend

Um eine echte Inertia.js Anwendung zu erstellen:

1. **Frontend Setup** (Vue.js Beispiel):

```bash
cd integration-tests
npm init -y
npm install @inertiajs/vue3 vue@next
npm install -D vite @vitejs/plugin-vue
```

2. **Vite Konfiguration** (`vite.config.js`):

```javascript
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
  plugins: [vue()],
  build: {
    outDir: "src/main/resources/META-INF/resources",
    rollupOptions: {
      input: "src/main/frontend/main.js",
    },
  },
});
```

3. **Frontend Code** (`src/main/frontend/main.js`):

```javascript
import { createApp, h } from "vue";
import { createInertiaApp } from "@inertiajs/vue3";

createInertiaApp({
  resolve: (name) => {
    const pages = import.meta.glob("./Pages/**/*.vue", { eager: true });
    return pages[`./Pages/${name}.vue`];
  },
  setup({ el, App, props, plugin }) {
    createApp({ render: () => h(App, props) })
      .use(plugin)
      .mount(el);
  },
});
```

#### 6. Debugging

**Quarkus Dev-Modus Features:**

- **Live Reload**: Änderungen werden automatisch übernommen
- **Dev UI**: http://localhost:8080/q/dev-ui
- **Health Check**: http://localhost:8080/q/health

**Logs anschauen:**

```bash
# Extension-Features anzeigen
curl http://localhost:8080/q/info

# CDI Beans anzeigen
curl http://localhost:8080/q/arc/beans | grep -i inertia
```

#### 7. Häufige Probleme

**Problem**: "Error rendering template: null"
**Lösung**: Template-Datei fehlt oder ist im falschen Verzeichnis

**Problem**: "404 - Resource Not Found"  
**Lösung**: Quarkus Dev-Modus neu starten:

```bash
# Alle Quarkus-Prozesse stoppen
pkill -f quarkus

# Neu starten
cd integration-tests
mvn clean compile quarkus:dev
```

**Problem**: InertiaService kann nicht injiziert werden
**Lösung**: Extension neu installieren:

```bash
cd ..
mvn clean install -DskipTests
cd integration-tests
mvn clean compile quarkus:dev
```

### Erweiterte Entwicklung

```bash
# Extension bauen
mvn clean install

# Tests ausführen
mvn test

# Integration-Tests starten
cd integration-tests
mvn quarkus:dev

# Native Image bauen (optional)
mvn clean package -Pnative
```

## Lizenz

MIT License
