# ✅ Ready to Publish: Inertia Quarkus 0.1.0

## 🎯 Korrekte URLs und Koordinaten

**Repository:** https://github.com/JangoCG/inertia-quarkus  
**Group ID:** `com.gurtus`  
**Artifact ID:** `inertia-quarkus`  
**Version:** `0.1.0`

## 🚀 Sofort publishing (2 Optionen)

### Option 1: JitPack (Empfohlen - Zero Config)

```bash
# 1. Alle Änderungen committen und Tag erstellen
cd /Users/cengiz/dev/inertia
git add .
git commit -m "Update URLs and group IDs for publishing 0.1.0"
git tag v0.1.0
git push origin main
git push origin v0.1.0
```

**In deiner SaaS-App verwenden:**
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.github.JangoCG</groupId>
    <artifactId>inertia-quarkus</artifactId>
    <version>v0.1.0</version>
  </dependency>
</dependencies>
```

**JitPack URL:** https://jitpack.io/#JangoCG/inertia-quarkus

---

### Option 2: GitHub Packages

```bash
# 1. GitHub Token erstellen mit "write:packages" Berechtigung
# 2. Maven Settings konfigurieren (~/.m2/settings.xml):
```

```xml
<settings>
  <servers>
    <server>
      <id>github</id>
      <username>JangoCG</username>
      <password>DEIN_GITHUB_TOKEN</password>
    </server>
  </servers>
</settings>
```

```bash
# 3. Extension veröffentlichen
mvn clean deploy
```

**In deiner SaaS-App verwenden:**
```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/JangoCG/inertia-quarkus</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.gurtus</groupId>
    <artifactId>inertia-quarkus</artifactId>
    <version>0.1.0</version>
  </dependency>
</dependencies>
```

## 🧪 Testen in deiner SaaS-App

Nach dem Publishing:

```bash
# In deiner SaaS-App
mvn clean compile
```

Falls alles funktioniert:
```bash
mvn quarkus:dev
```

## 📝 Registry-Einreichung (später)

**Registry-Datei:** `extensions/inertia-quarkus.yaml`
```yaml
group-id: com.gurtus
artifact-id: inertia-quarkus
```

**Repository für PR:** https://github.com/quarkusio/quarkus-extension-catalog

## 🎉 Das war's!

**JitPack ist der schnellste Weg** - einfach Tag pushen und sofort verwenden!
