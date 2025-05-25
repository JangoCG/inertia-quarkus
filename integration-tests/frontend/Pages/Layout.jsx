import React from 'react'
import { Link, usePage } from '@inertiajs/react'

export default function Layout({ title, children }) {
  const { url } = usePage()

  return (
    <div className="app">
      <header className="header">
        <nav className="navbar">
          <div className="nav-brand">
            <Link href="/" className="brand-link">
              <span className="brand-icon">⚡</span>
              <span className="brand-text">Inertia Quarkus</span>
            </Link>
          </div>
          <div className="nav-links">
            <Link 
              href="/" 
              className={`nav-link ${url === '/' ? 'active' : ''}`}
            >
              🏠 Home
            </Link>
            <Link 
              href="/about" 
              className={`nav-link ${url === '/about' ? 'active' : ''}`}
            >
              ℹ️ About
            </Link>
            <Link 
              href="/users" 
              className={`nav-link ${url === '/users' ? 'active' : ''}`}
            >
              👥 Users
            </Link>
          </div>
        </nav>
      </header>

      <main className="main">
        <div className="container">
          {children}
        </div>
      </main>

      <footer className="footer">
        <div className="container">
          <p>
            🚀 Powered by <strong>Quarkus</strong> + <strong>Inertia.js</strong> + <strong>React</strong>
          </p>
          <p className="footer-info">
            Aktuelle Seite: <code>{url}</code>
          </p>
        </div>
      </footer>
    </div>
  )
} 