# Inertia.js Quarkus Integration Tests

This demonstrates the Inertia.js Quarkus Extension with React, following a simple approach similar to Laravel.

## Quick Start

### Development

Start both servers in separate terminals:

```bash
# Terminal 1: Start Vite
npm install
npm run dev

# Terminal 2: Start Quarkus
mvn quarkus:dev
```

Visit `http://localhost:8080`

### Production

```bash
npm run build
mvn package
```

## How it Works

Just like Laravel's setup:

1. **Development**: Vite runs on port 5173 and serves assets with HMR
2. **Production**: Assets are built and served by Quarkus

The template automatically detects the environment and loads assets accordingly.

## Project Structure

```
src/main/
├── frontend/           # React app
│   ├── main.jsx
│   ├── style.css
│   └── Pages/         # Inertia pages
└── resources/
    └── templates/
        └── inertia.html
```

That's it! No complex scripts or configurations needed. Just like Laravel or Rails.

## 🚀 Entwicklung starten

### Option 1: Mit npm (empfohlen)

```bash
npm run dev
```

### Option 2: Mit dem Shell-Script

```bash
./start-dev.sh
```

Beide Optionen starten:

- **Quarkus Dev Server** auf `http://localhost:8080`
- **Vite Dev Server** auf `http://localhost:5173`

## 🔥 Hot Module Replacement (HMR)

Das Setup funktioniert ähnlich wie Laravel's Vite Integration:

1. **Entwicklungsmodus**: Vite Dev Server läuft parallel zu Quarkus

   - Frontend-Änderungen werden sofort ohne Reload übernommen
   - React-Komponenten werden hot-reloaded
   - CSS-Änderungen werden live injiziert

2. **Produktionsmodus**: Assets werden gebaut und von Quarkus serviert
   ```bash
   npm run build
   mvn quarkus:dev
   ```

## 📁 Projektstruktur

```
integration-tests/
├── src/main/frontend/          # React Frontend
│   ├── main.jsx               # Inertia App Entry Point
│   └── Pages/                 # React Komponenten
│       ├── Home.jsx
│       ├── About.jsx
│       └── Layout.jsx
├── src/main/resources/
│   └── templates/
│       └── inertia.html       # HTML Template (mit HMR Support)
├── package.json               # Frontend Dependencies
├── vite.config.js            # Vite Konfiguration
└── start-dev.sh              # Entwicklungs-Script
```

## 🛠️ Wie es funktioniert

### Template-Switching

Das `inertia.html` Template erkennt automatisch den Entwicklungsmodus:

```html
<!-- Entwicklung: Vite Dev Server -->
{#if isDevelopment}
<script type="module" src="http://localhost:5173/@vite/client"></script>
<script
  type="module"
  src="http://localhost:5173/src/main/frontend/main.jsx"
></script>
{#else}
<!-- Produktion: Gebaute Assets -->
<link rel="stylesheet" href="/css/app.css" />
<script type="module" src="/js/app.js"></script>
{/if}
```

### Proxy-Konfiguration

Vite proxied alle Requests (außer Assets) zu Quarkus:

```javascript
// vite.config.js
server: {
  proxy: {
    '^(?!/@vite|/src|/node_modules|/__vite_ping).*': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

## 📦 Scripts

- `npm run dev` - Startet Entwicklungsserver mit HMR
- `npm run build` - Baut Frontend für Produktion
- `npm run preview` - Vorschau der gebauten App

## 🔧 Konfiguration

### Quarkus (application.properties)

```properties
quarkus.inertia.root-template=inertia.html
quarkus.inertia.version=1.0.0
```

### Vite (vite.config.js)

```javascript
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    hmr: { port: 5173 },
  },
});
```

## 🎯 Vorteile

✅ **Echtes HMR** - Keine Browser-Reloads bei Frontend-Änderungen  
✅ **Laravel-ähnlicher Workflow** - Bekannte Entwicklungserfahrung  
✅ **Parallel Development** - Frontend und Backend laufen gleichzeitig  
✅ **Automatisches Asset-Switching** - Dev/Prod Modi werden automatisch erkannt  
✅ **Einfache Einrichtung** - Ein Befehl startet alles

## 🚦 Nächste Schritte

1. Starte die Entwicklung: `npm run dev`
2. Öffne `http://localhost:8080` im Browser
3. Bearbeite React-Komponenten in `src/main/frontend/Pages/`
4. Sieh die Änderungen sofort im Browser! 🎉

## Hot Module Replacement (HMR) Setup

The development environment now supports full Hot Module Replacement for React components:

### How it Works

1. **Vite Dev Server** runs on port 3000 and serves the frontend assets with HMR
2. **Quarkus Dev Server** runs on port 8080 and handles all backend requests
3. The Inertia HTML template automatically detects development mode and loads assets from Vite
4. All non-asset requests are proxied from Vite to Quarkus

### Features

- ✅ Instant React component updates without page reload
- ✅ Preserved component state during updates
- ✅ CSS hot reloading
- ✅ Automatic error overlay
- ✅ Quarkus hot reload for Java classes
- ✅ Synchronized frontend and backend development

### Configuration Details

#### Vite Configuration (`vite.config.js`)

- Configured to serve from port 3000
- Proxies all non-asset requests to Quarkus on port 8080
- WebSocket support for HMR
- Builds to Quarkus resources directory in production

#### Quarkus Configuration

- CORS enabled in dev mode to allow Vite requests
- Development mode detection passes `isDevelopment` flag to templates
- Live reload enabled for Java classes

#### Template Configuration

- The `inertia.html` template conditionally loads assets:
  - In development: loads from `http://localhost:3000`
  - In production: loads from bundled assets

### Troubleshooting HMR

If HMR is not working:

1. **Check Vite is running**: Ensure port 3000 is accessible
2. **Check browser console**: Look for WebSocket connection errors
3. **Clear browser cache**: Sometimes cached modules interfere
4. **Restart both servers**: Run `./dev-mode.sh` again
