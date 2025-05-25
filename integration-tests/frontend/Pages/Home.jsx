import React from 'react'
import { Link } from '@inertiajs/react'
import Layout from './Layout'

export default function Home({ message, timestamp }) {
  return (
    <Layout title="Home">
      <div className="hero">
        <h1>🎉 Willkommen bei Inertia.js + Quarkus + React! Works111</h1>
        <p className="lead">{message}</p>
        <p className="timestamp">Generiert um: {timestamp}</p>
      </div>

      <div className="features">
        <div className="feature-card">
          <h3>⚡ 123 Schnell</h3>
          <p>Keine vollständigen Seitenreloads - nur JSON-Updates</p>
        </div>
        <div className="feature-card">
          <h3>🔧 Einfach</h3>
          <p>Nutze deine gewohnten Server-Side Patterns</p>
        </div>
        <div className="feature-card">
          <h3>🚀 Modern</h3>
          <p>SPA-Feeling ohne SPA-Komplexität</p>
        </div>
      </div>

      <div className="navigation">
        <h2>Navigation testen:</h2>
        <div className="nav-buttons">
          <Link href="/about" className="btn btn-primary">
            ℹ️ About
          </Link>
          <Link href="/users" className="btn btn-secondary">
            👥 Users
          </Link>
          <Link href="/test" className="btn btn-success">
            🧪 Test
          </Link>
        </div>
      </div>
    </Layout>
  )
} 