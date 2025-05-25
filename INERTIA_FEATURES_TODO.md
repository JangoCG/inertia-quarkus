# Inertia Quarkus Extension - Feature Implementation TODO

Basierend auf dem Rails Inertia Adapter (`inertia-rails`) - alle Features die noch implementiert werden müssen.

## 🎯 Core Features

### ✅ Bereits implementiert

- [x] Basic Inertia Response (JSON/HTML)
- [x] Basic shared props
- [x] Basic asset versioning
- [x] SSR basic support
- [x] Basic middleware für version checking

### 🔄 In Arbeit

- [x] **Prop Types System** - Verschiedene Prop-Typen für erweiterte Funktionalität
- [x] **Partial Reloads** - Unterstützung für partial reloads mit Header-Parsing
- [x] **Enhanced Middleware** - Erweiterte Middleware-Features für besseres HTTP-Handling

### ❌ Noch zu implementieren

## 1. 📦 Prop Types System

- [x] `BaseProp` - Basis-Interface für alle Prop-Typen
- [x] `LazyProp` - Props die nur bei partial reloads geladen werden (deprecated, wird zu OptionalProp)
- [x] `OptionalProp` - Props die nur bei partial reloads geladen werden
- [x] `AlwaysProp` - Props die immer geladen werden, auch bei partial reloads
- [x] `DeferProp` - Props die in Gruppen deferred geladen werden können
- [x] `MergeProp` - Props die mit vorherigen Werten gemerged werden
- [x] `IgnoreOnFirstLoadProp` - Props die beim ersten Laden ignoriert werden
- [x] Prop evaluation im Controller-Kontext
- [x] `InertiaProps` Helper-Klasse für einfache Prop-Erstellung

## 2. 🔄 Partial Reloads

- [x] `X-Inertia-Partial-Data` Header Support
- [x] `X-Inertia-Partial-Component` Header Support
- [x] `X-Inertia-Partial-Except` Header Support
- [x] `X-Inertia-Reset` Header Support
- [x] Partial reload logic im Renderer
- [x] Path prefix matching für nested props

## 3. ⏳ Deferred Props

- [x] `deferredProps` im Page Object
- [x] Gruppierung von deferred props
- [x] Default group handling
- [x] Deferred props exclusion bei partial reloads

## 4. 🔀 Merge Props

- [x] `mergeProps` im Page Object
- [x] `deepMergeProps` im Page Object
- [x] Deep merging von shared data
- [x] Merge prop exclusion bei reset keys

## 5. 🌐 Enhanced Middleware

- [x] Automatische 303 Redirects für PUT/PATCH/DELETE requests
- [x] Bessere Asset-Versionierung mit Type-Coercion (Numeric vs String)
- [x] XSRF zu CSRF Token Kopierung
- [x] Flash message preservation bei 409 Conflicts
- [x] Bessere redirect status handling

## 6. 💾 Session Management

- [x] `inertia_errors` Session handling
- [x] `inertia_clear_history` Session handling
- [x] Session cleanup bei non-redirect responses
- [x] Error propagation über redirects

## 7. 🖥️ SSR Enhancements

- [x] SSR Head injection (`inertia_ssr_head`)
- [x] Bessere SSR Error Handling mit Fallback
- [x] SSR timeout handling
- [x] SSR response parsing und validation

## 8. ⚙️ Configuration Enhancements

- [ ] `deep_merge_shared_data` Option
- [ ] `default_render` Option
- [ ] `component_path_resolver` für dynamische Component-Pfade
- [ ] `encrypt_history` Option
- [ ] `clear_history` Option
- [ ] Environment variable support für alle Optionen

## 9. 🎮 Controller Integration

- [x] `inertia_share` für conditional shared data
- [x] Action filters (`only`, `except`, `if`, `unless`)
- [x] Instance props support (`use_inertia_instance_props`)
- [x] Automatic view assigns
- [x] Controller-specific configuration
- [x] Inheritance von shared data und config

## 10. 🛠️ Helper Functions

- [x] `inertia_rendering?` Helper
- [x] `inertia_ssr_head` Helper
- [x] Bessere Template Integration
- [x] View helpers für Inertia-spezifische Funktionen

## 11. 🔍 Action Filters

- [ ] `ActionFilter` Klasse für only/except logic
- [ ] Symbol zu Proc conversion
- [ ] Filter matching logic
- [ ] Conditional shared data basierend auf filters

## 12. 📄 Page Object Enhancements

- [x] `encryptHistory` Property
- [x] `clearHistory` Property
- [x] Vollständige Page Object Struktur wie im Rails Adapter

## 13. 🧪 Testing Support

- [ ] Test helpers für Inertia responses
- [ ] Mock support für SSR
- [ ] Assertion helpers für Inertia-spezifische Tests

## 14. 📚 Documentation & Examples

- [ ] Comprehensive README mit allen Features
- [ ] Code examples für alle Prop-Typen
- [ ] SSR setup guide
- [ ] Migration guide von basic zu advanced features

## 🚀 Implementation Priority

### Phase 1: Core Prop System (Höchste Priorität)

1. BaseProp Interface
2. OptionalProp, AlwaysProp, IgnoreOnFirstLoadProp
3. Basic partial reload support

### Phase 2: Enhanced Middleware & Session (✅ COMPLETE)

1. ✅ Enhanced middleware features
2. ✅ Session management
3. ✅ Better redirect handling
4. ✅ XSRF/CSRF token handling
5. ✅ Asset versioning with type coercion

### Phase 3: Controller Integration (✅ COMPLETE)

1. ✅ Shared data mit filters
2. ✅ Controller-specific configuration
3. ✅ Action-based conditional sharing
4. ✅ Dynamic and contextual data sharing
5. ✅ InertiaController base class

### Phase 4: SSR Enhancements (✅ COMPLETE)

1. ✅ SSR Service with HTTP client
2. ✅ SSR Head injection support
3. ✅ Helper functions (inertia_ssr_head, inertia_rendering)
4. ✅ Enhanced configuration with SSR options
5. ✅ SSR error handling with fallback

### Phase 5: SSR & Helpers

1. SSR enhancements
2. Helper functions
3. Testing support

---

**Status**: 🟢 Phase 4 Complete - SSR Enhancements & Helper Functions implemented!
**CDI Issue**: ✅ Resolved - All beans properly registered
**Server Status**: ✅ Running - All Phase 1, 2, 3 & 4 features working correctly
**Features Tested**: ✅ SSR service, helper functions, head injection, error handling
**SSR Features**: ✅ HTTP client, timeout handling, response parsing, fallback support
**Last Updated**: 2025-01-25
