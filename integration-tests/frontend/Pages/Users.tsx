import { useState } from 'react'
import { Link } from '@inertiajs/react'
import Layout from './Layout'

interface User {
  id: number
  name: string
  email: string
  role: string
  active: boolean
  createdAt: string
}

interface UsersProps {
  users: User[]
  totalUsers: number
}

export default function Users({ users, totalUsers }: UsersProps) {
  const [selectedUser, setSelectedUser] = useState<User | null>(null)

  return (
    <Layout title="Users">
      <div className="text-center bg-white/95 p-8 rounded-2xl mb-8 shadow-lg">
        <h1 className="text-3xl font-bold mb-2 text-gray-800">👥 Benutzer-Verwaltung</h1>
        <p className="text-xl text-gray-600">Insgesamt {totalUsers} Benutzer gefunden</p>
      </div>

      <div className="bg-white/95 p-8 rounded-2xl mb-8 shadow-lg">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mb-8">
          {users.map((user) => (
            <div 
              key={user.id} 
              className={`bg-gray-50 border-2 rounded-xl p-4 cursor-pointer transition-all duration-300 flex items-center gap-4 hover:-translate-y-1 ${
                selectedUser?.id === user.id 
                  ? 'border-indigo-500 bg-blue-50' 
                  : 'border-transparent hover:border-indigo-500'
              }`}
              onClick={() => setSelectedUser(user)}
            >
              <div className="w-12 h-12 rounded-full bg-indigo-500 text-white flex items-center justify-center font-bold text-lg">
                {user.name.charAt(0).toUpperCase()}
              </div>
              <div className="flex-1">
                <h3 className="font-semibold text-gray-800 mb-1">{user.name}</h3>
                <p className="text-gray-600 text-sm mb-1">{user.email}</p>
                <p className="text-gray-500 text-xs">{user.role}</p>
              </div>
              <div>
                <span className={`px-2 py-1 rounded-full text-xs font-medium ${
                  user.active 
                    ? 'bg-green-100 text-green-800' 
                    : 'bg-red-100 text-red-800'
                }`}>
                  {user.active ? '🟢 Aktiv' : '🔴 Inaktiv'}
                </span>
              </div>
            </div>
          ))}
        </div>

        {selectedUser && (
          <div className="bg-gray-50 p-6 rounded-xl">
            <h2 className="text-xl font-semibold mb-4 text-gray-800">📋 Benutzer-Details</h2>
            <div className="bg-white p-4 rounded-lg">
              <div className="space-y-3">
                <div className="flex justify-between py-2 border-b border-gray-100">
                  <strong className="text-gray-700">ID:</strong> 
                  <span className="text-gray-600">{selectedUser.id}</span>
                </div>
                <div className="flex justify-between py-2 border-b border-gray-100">
                  <strong className="text-gray-700">Name:</strong> 
                  <span className="text-gray-600">{selectedUser.name}</span>
                </div>
                <div className="flex justify-between py-2 border-b border-gray-100">
                  <strong className="text-gray-700">E-Mail:</strong> 
                  <span className="text-gray-600">{selectedUser.email}</span>
                </div>
                <div className="flex justify-between py-2 border-b border-gray-100">
                  <strong className="text-gray-700">Rolle:</strong> 
                  <span className="text-gray-600">{selectedUser.role}</span>
                </div>
                <div className="flex justify-between py-2 border-b border-gray-100">
                  <strong className="text-gray-700">Erstellt:</strong> 
                  <span className="text-gray-600">{selectedUser.createdAt}</span>
                </div>
                <div className="flex justify-between py-2">
                  <strong className="text-gray-700">Status:</strong> 
                  <span className={`font-bold ${
                    selectedUser.active ? 'text-green-600' : 'text-red-600'
                  }`}>
                    {selectedUser.active ? 'Aktiv' : 'Inaktiv'}
                  </span>
                </div>
              </div>
            </div>
            <button 
              className="mt-4 px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition-colors"
              onClick={() => setSelectedUser(null)}
            >
              ❌ Details schließen
            </button>
          </div>
        )}
      </div>

      <div className="bg-white/95 p-8 rounded-2xl text-center shadow-lg">
        <div className="flex flex-col sm:flex-row flex-wrap gap-4 justify-center">
          <Link 
            href="/" 
            className="inline-flex items-center justify-center px-6 py-3 bg-indigo-500 text-white rounded-lg hover:bg-indigo-600 hover:-translate-y-1 transition-all duration-300 shadow-lg"
          >
            🏠 Startseite
          </Link>
          <Link 
            href="/about" 
            className="inline-flex items-center justify-center px-6 py-3 bg-gray-500 text-white rounded-lg hover:bg-gray-600 hover:-translate-y-1 transition-all duration-300 shadow-lg"
          >
            ℹ️ About
          </Link>
        </div>
      </div>
    </Layout>
  )
} 