import React, { useState } from 'react'
import { Link } from '@inertiajs/react'
import Layout from './Layout'

export default function Users({ users, totalUsers }) {
  const [selectedUser, setSelectedUser] = useState(null)

  return (
    <Layout title="Users">
      <div className="users-header">
        <h1>👥 Benutzer-Verwaltung</h1>
        <p className="lead">Insgesamt {totalUsers} Benutzer gefunden</p>
      </div>

      <div className="users-content">
        <div className="users-grid">
          {users.map((user) => (
            <div 
              key={user.id} 
              className={`user-card ${selectedUser?.id === user.id ? 'selected' : ''}`}
              onClick={() => setSelectedUser(user)}
            >
              <div className="user-avatar">
                {user.name.charAt(0).toUpperCase()}
              </div>
              <div className="user-info">
                <h3>{user.name}</h3>
                <p className="user-email">{user.email}</p>
                <p className="user-role">{user.role}</p>
              </div>
              <div className="user-status">
                <span className={`status-badge ${user.active ? 'active' : 'inactive'}`}>
                  {user.active ? '🟢 Aktiv' : '🔴 Inaktiv'}
                </span>
              </div>
            </div>
          ))}
        </div>

        {selectedUser && (
          <div className="user-details">
            <h2>📋 Benutzer-Details</h2>
            <div className="details-card">
              <div className="detail-row">
                <strong>ID:</strong> {selectedUser.id}
              </div>
              <div className="detail-row">
                <strong>Name:</strong> {selectedUser.name}
              </div>
              <div className="detail-row">
                <strong>E-Mail:</strong> {selectedUser.email}
              </div>
              <div className="detail-row">
                <strong>Rolle:</strong> {selectedUser.role}
              </div>
              <div className="detail-row">
                <strong>Erstellt:</strong> {selectedUser.createdAt}
              </div>
              <div className="detail-row">
                <strong>Status:</strong> 
                <span className={`status-text ${selectedUser.active ? 'active' : 'inactive'}`}>
                  {selectedUser.active ? 'Aktiv' : 'Inaktiv'}
                </span>
              </div>
            </div>
            <button 
              className="btn btn-secondary"
              onClick={() => setSelectedUser(null)}
            >
              ❌ Details schließen
            </button>
          </div>
        )}
      </div>

      <div className="navigation">
        <Link href="/" className="btn btn-primary">
          🏠 Startseite
        </Link>
        <Link href="/about" className="btn btn-secondary">
          ℹ️ About
        </Link>
      </div>
    </Layout>
  )
} 