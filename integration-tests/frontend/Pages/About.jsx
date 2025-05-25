import React from 'react'
import { Link } from '@inertiajs/react'
import Layout from './Layout'

export default function About({ title, description, features }) {
  return (
    <Layout title="About">
      <div className="about-header">
        <h1>{title}</h1>
        <p className="lead">{description}</p>
      </div>

      <div className="about-content">
        <h2>🚀 Features dieser Extension:</h2>
        <ul className="feature-list">
          {features.map((feature, index) => (
            <li key={index} className="feature-item">
              <span className="feature-icon">✅</span>
              {feature}
            </li>
          ))}
        </ul>

        <div className="tech-stack">
          <h2>🛠️ Tech Stack:</h2>
          <div className="tech-badges">
            <span className="badge">Quarkus</span>
            <span className="badge">React</span>
            <span className="badge">Inertia.js</span>
            <span className="badge">Vite</span>
            <span className="badge">CDI</span>
          </div>
        </div>

        <div className="demo-info">
          <h2>🎮 Demo Features:</h2>
          <p>Diese Demo zeigt:</p>
          <ul>
            <li>Server-Side Rendering mit Quarkus</li>
            <li>Client-Side Navigation ohne Page Reloads</li>
            <li>JSON-basierte Datenübertragung</li>
            <li>React-Komponenten mit Inertia.js</li>
            <li>Asset-Versionierung für Cache-Busting</li>
          </ul>
        </div>
      </div>

      <div className="navigation">
        <Link href="/" className="btn btn-primary">
          🏠 Zurück zur Startseite
        </Link>
        <Link href="/users" className="btn btn-secondary">
          👥 Users ansehen
        </Link>
      </div>
    </Layout>
  )
} 