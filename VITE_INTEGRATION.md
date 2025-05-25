# Vite Asset Integration - Laravel Style

Diese Implementierung bietet Laravel-ähnliche Vite Asset Integration für Quarkus Inertia.js.

## Template Verwendung

### 1. Standard Ansatz (Empfohlen) - Injizierte ViteAssets

Der `InertiaService` injiziert automatisch ein `viteAssets` Objekt in jedes Template:

```html
<!-- Laravel @vite('main.tsx') equivalent -->
{#if viteAssets.isDevelopment}
<!-- Development: Vite HMR + React Refresh -->
{#if viteAssets.reactRefreshScript} {viteAssets.reactRefreshScript|raw} {/if}
<script type="module" src="{viteAssets.viteClient}"></script>
{#for entry in viteAssets.entries}
<script type="module" src="{entry.value}"></script>
{/for} {#else}
<!-- Production: Compiled assets with versioning -->
{#if viteAssets.hasCss} {#for cssFile in viteAssets.cssFiles}
<link rel="stylesheet" href="{cssFile}" />
{/for} {/if} {#if viteAssets.hasImports}
<!-- Preload module imports for better performance -->
{#for importFile in viteAssets.imports}
<link rel="modulepreload" href="{importFile}" />
{/for} {/if}

<!-- Entry scripts -->
{#for entry in viteAssets.entries}
<script type="module" src="{entry.value}"></script>
{/for} {/if}
```

### 2. One-Liner Ansatz - HTML Generation

Für einfache Anwendungen können Sie alle HTML Tags auf einmal generieren:

```html
<!-- Generiert automatisch alle notwendigen Tags -->
{viteHtml('main.tsx')|raw}

<!-- Für mehrere Entrypoints -->
{viteHtml2('main.tsx', 'app.css')|raw} {viteHtml3('main.tsx', 'app.css',
'admin.tsx')|raw}
```

### 3. Namespace Extensions

```html
<!-- Mit vite: namespace -->
{vite:html('main.tsx')|raw}

<!-- Einzelne Asset URLs -->
<script type="module" src="{vite:url('main.tsx')}"></script>

<!-- ViteAssets Objekt abrufen -->
{#let assets = vite:assets('main.tsx')} {#if assets.isDev}
<!-- Development mode -->
{/if} {/let}
```

### 4. Global Template Functions

```html
<!-- Direct global access -->
{viteAssets().toHtml|raw}

<!-- Spezifische Entrypoints -->
{viteAssets('main.tsx').toHtml|raw} {viteAssets2('main.tsx',
'app.css').toHtml|raw}
```

### 5. ViteAssets Object Properties

Das `ViteAssets` Objekt bietet folgende Properties:

```html
<!-- Entwicklungsumgebung prüfen -->
{#if viteAssets.isDevelopment}
<!-- Development mode -->
{#else}
<!-- Production mode -->
{/if}

<!-- Assets vorhanden prüfen -->
{#if viteAssets.hasCss}
<!-- CSS files vorhanden -->
{/if} {#if viteAssets.hasImports}
<!-- Import files vorhanden -->
{/if} {#if viteAssets.hasEntries}
<!-- Entry files vorhanden -->
{/if}

<!-- Direkter Zugang zu Listen -->
{#for cssFile in viteAssets.cssFiles}
<link rel="stylesheet" href="{cssFile}" />
{/for} {#for importFile in viteAssets.imports}
<link rel="modulepreload" href="{importFile}" />
{/for} {#for entry in viteAssets.entries}
<script type="module" src="{entry.value}"></script>
{/for}

<!-- Einzelne Werte -->
<script type="module" src="{viteAssets.viteClient}"></script>
{viteAssets.reactRefreshScript|raw}
<script type="module" src="{viteAssets.mainEntry}"></script>
```

### 6. Extension Methods

Mit `ViteExtension` können Sie auch diese Methoden verwenden:

```html
{#let assets = viteAssets} {#if assets.isDev}
<!-- Development -->
{/if} {#if assets.isProd}
<!-- Production -->
{/if} {#if assets.hasCSS}
<!-- CSS vorhanden -->
{/if} {#if assets.hasImports}
<!-- Imports vorhanden -->
{/if}

<!-- Alle Tags auf einmal -->
{assets.allTags|raw} {/let}
```

## Entwicklung vs. Produktion

### Entwicklungsmodus (Development)

- Automatische Erkennung des Vite Dev Servers
- React Refresh Support
- Hot Module Replacement (HMR)
- Direkte Asset URLs zum Dev Server

### Produktionsmodus (Production)

- Verwendung des Vite Manifest
- Asset Versioning / Cache Busting
- CSS Extraktion
- Preload Directives für bessere Performance
- Integrity Hashes (falls vorhanden)

## Laravel Ähnlichkeiten

Diese Implementierung orientiert sich stark an Laravel's Vite Integration:

| Laravel                  | Quarkus Inertia                        |
| ------------------------ | -------------------------------------- |
| `@vite('main.js')`       | `{viteHtml('main.tsx')\|raw}`          |
| `Vite::asset('main.js')` | `{vite:url('main.tsx')}`               |
| Development HMR          | Automatische Vite Dev Server Erkennung |
| Production Manifest      | Vite Manifest Support                  |
| CSS Extraction           | Automatische CSS Links                 |
| Preloading               | Module Preload Directives              |

## Konfiguration

Die Vite Integration läuft vollautomatisch. Der Dev Server wird automatisch erkannt durch:

1. `.vite/hot` File (Laravel style)
2. `VITE_URL` Environment Variable
3. Auto-Detection auf Common Ports (5173, 5174, 5175, 3000, 3001)

## Best Practices

1. **Verwenden Sie den Standard Ansatz** mit injiziertem `viteAssets` für maximale Kontrolle
2. **Nutzen Sie `viteHtml()`** für einfache Anwendungen
3. **Prüfen Sie auf Development/Production** für unterschiedliche Verhaltensweisen
4. **Verwenden Sie Preloading** in Production für bessere Performance
5. **Nutzen Sie die Helper Methods** (`hasCss`, `hasImports`, etc.) für sauberen Code

Diese Implementierung bietet die gleiche Flexibilität und Benutzerfreundlichkeit wie Laravel's Vite Integration, angepasst für Quarkus und Qute Templates.
