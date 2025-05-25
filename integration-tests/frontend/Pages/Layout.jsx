import React, { useState } from 'react'
import { Link, usePage } from '@inertiajs/react'

export default function Layout({ title, children }) {
  const { url } = usePage()
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false)

  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-br from-indigo-500 via-purple-500 to-purple-600">
      <header className="bg-white/95 backdrop-blur-md shadow-lg sticky top-0 z-50">
        <nav className="max-w-6xl mx-auto px-4 sm:px-8">
          <div className="flex justify-between items-center py-4">
            <div className="flex items-center">
              <Link href="/" className="flex items-center text-gray-800 hover:text-indigo-600 transition-colors font-bold text-xl">
                <span className="mr-2 text-2xl">⚡</span>
                <span>Inertia Quarkus</span>
              </Link>
            </div>
            
            {/* Desktop Navigation */}
            <div className="hidden md:flex gap-4">
              <Link 
                href="/" 
                className={`px-4 py-2 rounded-lg transition-all duration-300 hover:-translate-y-0.5 ${
                  url === '/' 
                    ? 'bg-indigo-500 text-white' 
                    : 'text-gray-600 hover:bg-indigo-500 hover:text-white'
                }`}
              >
                🏠 Home
              </Link>
              <Link 
                href="/about" 
                className={`px-4 py-2 rounded-lg transition-all duration-300 hover:-translate-y-0.5 ${
                  url === '/about' 
                    ? 'bg-indigo-500 text-white' 
                    : 'text-gray-600 hover:bg-indigo-500 hover:text-white'
                }`}
              >
                ℹ️ About
              </Link>
              <Link 
                href="/users" 
                className={`px-4 py-2 rounded-lg transition-all duration-300 hover:-translate-y-0.5 ${
                  url === '/users' 
                    ? 'bg-indigo-500 text-white' 
                    : 'text-gray-600 hover:bg-indigo-500 hover:text-white'
                }`}
              >
                👥 Users
              </Link>
            </div>

            {/* Mobile menu button */}
            <button 
              className="md:hidden p-2 rounded-lg text-gray-600 hover:bg-gray-100"
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            >
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
              </svg>
            </button>
          </div>

          {/* Mobile Navigation */}
          {mobileMenuOpen && (
            <div className="md:hidden pb-4">
              <div className="flex flex-col gap-2">
                <Link 
                  href="/" 
                  className={`px-4 py-2 rounded-lg transition-all duration-300 ${
                    url === '/' 
                      ? 'bg-indigo-500 text-white' 
                      : 'text-gray-600 hover:bg-indigo-500 hover:text-white'
                  }`}
                  onClick={() => setMobileMenuOpen(false)}
                >
                  🏠 Home
                </Link>
                <Link 
                  href="/about" 
                  className={`px-4 py-2 rounded-lg transition-all duration-300 ${
                    url === '/about' 
                      ? 'bg-indigo-500 text-white' 
                      : 'text-gray-600 hover:bg-indigo-500 hover:text-white'
                  }`}
                  onClick={() => setMobileMenuOpen(false)}
                >
                  ℹ️ About
                </Link>
                <Link 
                  href="/users" 
                  className={`px-4 py-2 rounded-lg transition-all duration-300 ${
                    url === '/users' 
                      ? 'bg-indigo-500 text-white' 
                      : 'text-gray-600 hover:bg-indigo-500 hover:text-white'
                  }`}
                  onClick={() => setMobileMenuOpen(false)}
                >
                  👥 Users
                </Link>
              </div>
            </div>
          )}
        </nav>
      </header>

      <main className="flex-1 py-8">
        <div className="max-w-6xl mx-auto px-4 sm:px-8">
          {children}
        </div>
      </main>

      <footer className="bg-white/95 backdrop-blur-md py-8 text-center">
        <div className="max-w-6xl mx-auto px-4 sm:px-8">
          <p className="text-gray-700">
            🚀 Powered by <strong>Quarkus</strong> + <strong>Inertia.js</strong> + <strong>React</strong>
          </p>
          <p className="text-gray-500 text-sm mt-2">
            Aktuelle Seite: <code className="bg-gray-100 px-2 py-1 rounded text-xs font-mono">{url}</code>
          </p>
        </div>
      </footer>
    </div>
  )
} 