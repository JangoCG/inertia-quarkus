# ✅ Einfache Registry-Einreichung (nur ins Quarkus Extension Registry)

## Was du benötigst

Da du **nicht über Maven Central** veröffentlichen möchtest, ist das vollkommen möglich! Das Quarkus Extension Registry kann auch Extensions aus anderen Repositories (wie GitHub Packages, etc.) auflisten.

## 🚀 Schritt-für-Schritt Anleitung

### 1. Fork das Extension Catalog Repository
```bash
# Gehe zu: https://github.com/quarkusio/quarkus-extension-catalog
# Klicke auf "Fork" um deine eigene Kopie zu erstellen
```

### 2. Clone dein geforktes Repository
```bash
git clone https://github.com/DEIN-GITHUB-USERNAME/quarkus-extension-catalog.git
cd quarkus-extension-catalog
```

### 3. Erstelle einen Branch für deine Extension
```bash
git checkout -b add-inertia-extension
```

### 4. Erstelle die Extension-Datei

**Erstelle die Datei:** `extensions/quarkus-inertia.yaml`

```yaml
group-id: io.github.cengiz-io
artifact-id: quarkus-inertia
```

**Das war's!** Die minimale Konfiguration reicht - das Registry wird automatisch die restlichen Metadaten aus deiner Extension-JAR-Datei lesen.

### 5. Commit und Push
```bash
git add extensions/quarkus-inertia.yaml
git commit -m "Add Inertia.js extension to registry"
git push origin add-inertia-extension
```

### 6. Erstelle den Pull Request

Gehe zu deinem geforkten Repository auf GitHub und erstelle einen Pull Request mit:

**Titel:** `Add Quarkus Inertia.js Extension`

**Beschreibung:**
```markdown
## Adding Quarkus Inertia.js Extension

This PR adds the Quarkus Inertia.js extension to the community registry.

### Extension Details
- **Group ID:** `io.github.cengiz-io`
- **Artifact ID:** `quarkus-inertia`
- **Repository:** https://github.com/cengiz-io/quarkus-inertia
- **Description:** Enables modern full-stack development with server-side routing and client-side rendering using React, Vue, or Svelte

### What is Inertia.js?
Inertia.js allows you to quickly build modern single-page applications using classic server-side routing and controllers. It works great with React, Vue.js, and Svelte.

### Features
- 🔄 Seamless backend-frontend integration
- ⚛️ Support for React, Vue.js, and Svelte
- 🚀 Server-side routing with client-side navigation
- 📡 JSON data sharing between server and client
- 🔒 Built-in CSRF protection
- 🎯 Zero API required

### Documentation
The extension includes comprehensive documentation with examples for React and Vue.js integration.
```

## ⚠️ Wichtige Hinweise

### Repository-Anforderungen
Da du nicht über Maven Central veröffentlichst, musst du angeben, wo deine Extension verfügbar ist:

**Option 1: GitHub Packages**
```bash
# Veröffentliche über GitHub Packages
mvn deploy
```

**Option 2: Andere Maven-Repository**
Du kannst jedes öffentlich zugängliche Maven-Repository verwenden.

### Erweiterte Konfiguration (optional)
Falls das automatische Auslesen der Metadaten nicht funktioniert, kannst du auch eine erweiterte Konfiguration verwenden:

```yaml
group-id: io.github.cengiz-io
artifact-id: quarkus-inertia
repository: "https://maven.pkg.github.com/cengiz-io/quarkus-inertia"  # Falls nicht Maven Central
```

## 🎯 Was passiert nach der Einreichung?

1. **Review-Prozess:** Das Quarkus-Team reviewt deinen PR
2. **Automatische Tests:** Das Registry testet, ob deine Extension erreichbar ist
3. **Aufnahme:** Nach Approval wird deine Extension im Registry verfügbar
4. **Installation:** Benutzer können dann installieren mit:
   ```bash
   quarkus ext add inertia
   ```

## 🔧 Troubleshooting

### Häufige Probleme
1. **Extension nicht gefunden:** Stelle sicher, dass deine Extension in einem öffentlich zugänglichen Repository liegt
2. **Metadaten-Fehler:** Prüfe, ob deine `quarkus-extension.yaml` korrekt ist
3. **Build-Probleme:** Teste vorher mit `mvn clean install`

### Alternative: Quarkiverse
Falls die direkte Aufnahme schwierig ist, kannst du auch das **Quarkiverse** nutzen:

1. Gehe zu: https://github.com/quarkiverse/quarkiverse/discussions
2. Erstelle eine Discussion mit dem Titel: "Request to join Quarkiverse: Inertia.js Extension"
3. Das Quarkiverse-Team hilft bei der Aufnahme und Veröffentlichung

**Vorteile von Quarkiverse:**
- ✅ Einfachere Aufnahme
- ✅ Automatische CI/CD-Pipeline
- ✅ Automatische Veröffentlichung über Maven Central
- ✅ Community-Support

## 🚀 Nächste Schritte

1. **Teste deine Extension** lokal gründlich
2. **Erstelle eine Release-Version** (z.B. 1.0.0)
3. **Stelle sicher**, dass alle Abhängigkeiten verfügbar sind
4. **Folge der Anleitung oben** für die Registry-Einreichung

**Du brauchst NICHT Maven Central** - das Registry kann aus jedem öffentlichen Repository lesen!
