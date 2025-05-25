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
- 🛡️ **Automatic CSRF Protection** - Built-in CSRF protection with Axios integration

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

### 4. Advanced Usage

Install the Inertia.js client library for your frontend framework:

```bash
# For Vue.js
npm install @inertiajs/vue3

# For React
npm install @inertiajs/react

# For Svelte
npm install @inertiajs/svelte
```

### 5. Shared Props

You can share props globally for all Inertia responses:

```java
@ApplicationScoped
public class InertiaSetup {

    @Inject
    InertiaService inertiaService;

    @PostConstruct
    void setup() {
        // Global props for all pages
        inertiaService.share("app", Map.of(
            "name", "My App",
            "version", "1.0.0"
        ));
    }
}
```

## Example Project

The `integration-tests` module contains a complete example with:

- Various routes
- Shared props
- Template configuration
- Frontend integration

## Development Guide

### For Developers New to Quarkus Extensions

#### 1. Build and Install Extension Locally

```bash
# Build extension completely and install in local Maven repository
mvn clean install -DskipTests
```

#### 2. Start Demo Application

```bash
# Navigate to integration tests directory
cd integration-tests

# Start demo application in dev mode
mvn quarkus:dev
```

#### 3. Test in Browser

Open your browser and go to: **http://localhost:8080**

**🎉 You should now see a beautiful demo page with:**

- ✅ Extension success message
- 📊 Current page data (Component, URL, Version)
- 🔗 Clickable test links to all routes
- 🧪 Interactive "Test XHR Request" button
- 📋 JSON representation of Inertia page data

**Important URLs for Testing:**

- **Test Route**: http://localhost:8080/test
  - Shows: "Inertia Extension is loaded!"
- **Inertia HTML Responses** (normal browser requests):
  - http://localhost:8080/ (Home)
  - http://localhost:8080/about (About)
  - http://localhost:8080/users (Users)
- **Inertia JSON Responses** (for XHR/AJAX):

  ```bash
  # Home route as JSON
  curl -H "X-Inertia: true" http://localhost:8080/

  # About route as JSON
  curl -H "X-Inertia: true" http://localhost:8080/about

  # Users route as JSON
  curl -H "X-Inertia: true" http://localhost:8080/users
  ```

**🔥 Interactive Test in Browser:**
Click the "Test XHR Request" button on the demo page to see how Inertia.js XHR requests work!

#### 4. Fix Template Issues

If you see "Error rendering template: null", this is because the Qute template is not found.

**Solution**: The template `inertia.html` must be in the correct directory:

```
integration-tests/src/main/resources/templates/inertia.html
```

#### 5. Complete Demo with Frontend

To create a real Inertia.js application:

1. **Frontend Setup** (Vue.js Example):

```bash
cd integration-tests
npm init -y
npm install @inertiajs/vue3 vue@next
npm install -D vite @vitejs/plugin-vue
```

2. **Vite Configuration** (`vite.config.js`):

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

**Quarkus Dev Mode Features:**

- **Live Reload**: Changes are automatically applied
- **Dev UI**: http://localhost:8080/q/dev-ui
- **Health Check**: http://localhost:8080/q/health

**View Logs:**

```bash
# Show extension features
curl http://localhost:8080/q/info

# Show CDI beans
curl http://localhost:8080/q/arc/beans | grep -i inertia
```

#### 7. Common Issues

**Problem**: "Error rendering template: null"
**Solution**: Template file is missing or in wrong directory

**Problem**: "404 - Resource Not Found"
**Solution**: Restart Quarkus dev mode:

```bash
# Stop all Quarkus processes
pkill -f quarkus

# Restart
cd integration-tests
mvn clean compile quarkus:dev
```

**Problem**: InertiaService cannot be injected
**Solution**: Reinstall extension:

```bash
cd ..
mvn clean install -DskipTests
cd integration-tests
mvn clean compile quarkus:dev
```

### Advanced Development

```bash
# Build extension
mvn clean install

# Run tests
mvn test

# Start integration tests
cd integration-tests
mvn quarkus:dev

# Build native image (optional)
mvn clean package -Pnative
```

## License

MIT License

## 🛡️ Automatic CSRF Protection

The extension automatically configures CSRF protection that works seamlessly with Inertia.js and Axios:

### What's Included

- **Automatic Configuration**: CSRF protection is enabled by default
- **Axios Integration**: Works automatically with Axios (no manual token handling required)
- **Cookie-based Tokens**: Uses `XSRF-TOKEN` cookies that Axios reads automatically
- **Header Validation**: Validates `X-XSRF-TOKEN` headers sent by Axios

### How It Works

1. **Server sets cookie**: Quarkus automatically sets an `XSRF-TOKEN` cookie
2. **Axios reads cookie**: Axios automatically reads the cookie value
3. **Axios sends header**: Axios includes `X-XSRF-TOKEN` header in POST/PUT/PATCH/DELETE requests
4. **Server validates**: Quarkus validates that cookie and header match

### Default Configuration

```properties
# These are automatically configured by the extension:
quarkus.rest-csrf.cookie-name=XSRF-TOKEN
quarkus.rest-csrf.cookie-http-only=false
quarkus.rest-csrf.token-header-name=X-XSRF-TOKEN
```

### Usage in Frontend

No additional configuration needed! Just use Inertia.js forms as usual:

```tsx
import { useForm } from "@inertiajs/react";

function ContactForm() {
  const { data, setData, post, processing } = useForm({
    name: "",
    email: "",
    message: "",
  });

  const submit = (e) => {
    e.preventDefault();
    // CSRF token is automatically included by Axios
    post("/contact");
  };

  return <form onSubmit={submit}>{/* Your form fields */}</form>;
}
```

## Advanced Features

### Prop Types

```java
@GET
public Response dashboard() {
    return inertia("Dashboard")
        .withOptional("stats", this::getStats)           // Only on partial reloads
        .withAlways("user", this::getCurrentUser)        // Always included
        .withMerge("notifications", this::getNotifications) // Client-side merge
        .withDefer("reports", this::getReports);         // Loaded separately
}
```

### Shared Data

```java
@ApplicationScoped
public class GlobalConfig {

    @Inject
    InertiaService inertiaService;

    void onStart(@Observes StartupEvent ev) {
        // Global shared data
        inertiaService.shareData("appName", "My App");

        // Conditional shared data
        inertiaService.shareDataIf(() -> isDebugMode(), "debug", true);

        // Action-specific data
        inertiaService.shareOnly(List.of("admin"), "adminData", this::getAdminData);
    }
}
```

### SSR Configuration

```properties
quarkus.inertia.ssr.enabled=true
quarkus.inertia.ssr.url=http://localhost:13714
quarkus.inertia.ssr.timeout=5000
```

## Configuration Options

| Property                        | Default                  | Description                     |
| ------------------------------- | ------------------------ | ------------------------------- |
| `quarkus.inertia.version`       | `1.0`                    | Asset version for cache busting |
| `quarkus.inertia.root-template` | `app.html`               | Root template file              |
| `quarkus.inertia.ssr.enabled`   | `false`                  | Enable server-side rendering    |
| `quarkus.inertia.ssr.url`       | `http://localhost:13714` | SSR server URL                  |
| `quarkus.inertia.ssr.timeout`   | `5000`                   | SSR request timeout (ms)        |

## Examples

Check out the `integration-tests` module for complete examples including:

- Basic Inertia.js setup
- CSRF protection demo
- Advanced prop types
- Shared data configuration
- SSR integration

## License

This project is licensed under the Apache License 2.0.
