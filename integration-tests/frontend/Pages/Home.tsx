import { Link } from "@inertiajs/react";
import Layout from "./Layout";

interface HomeProps {
  message: string;
  timestamp: string;
}

export default function Home({ message, timestamp }: HomeProps) {
  return (
    <Layout title="Home">
      <div className="text-center bg-white/95 p-12 rounded-3xl mb-8 shadow-2xl">
        <h1 className="text-4xl font-bold mb-4 bg-gradient-to-r from-indigo-500 to-purple-600 bg-clip-text text-transparent">
          🎉 Welcome to Inertia.js + Quarkus + React!
        </h1>
        <p className="text-xl text-gray-600 mb-4">{message}</p>
        <p className="text-gray-500 text-sm">Generated at: {timestamp}</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-8">
        <div className="bg-white/95 p-8 rounded-2xl text-center shadow-lg hover:-translate-y-2 transition-all duration-300">
          <h3 className="text-xl font-semibold mb-4 text-gray-800">⚡ Fast</h3>
          <p className="text-gray-600">
            No full page reloads - only JSON updates
          </p>
        </div>
        <div className="bg-white/95 p-8 rounded-2xl text-center shadow-lg hover:-translate-y-2 transition-all duration-300">
          <h3 className="text-xl font-semibold mb-4 text-gray-800">
            🔧 Simple
          </h3>
          <p className="text-gray-600">
            Use your familiar server-side patterns
          </p>
        </div>
        <div className="bg-white/95 p-8 rounded-2xl text-center shadow-lg hover:-translate-y-2 transition-all duration-300">
          <h3 className="text-xl font-semibold mb-4 text-gray-800">
            🚀 Modern
          </h3>
          <p className="text-gray-600">SPA feeling without SPA complexity</p>
        </div>
      </div>

      <div className="bg-white/95 p-8 rounded-2xl text-center shadow-lg">
        <h2 className="text-2xl font-semibold mb-6 text-gray-800">
          Test navigation:
        </h2>
        <div className="flex flex-col sm:flex-row flex-wrap gap-4 justify-center">
          <Link
            href="/about"
            className="inline-flex items-center justify-center px-6 py-3 bg-indigo-500 text-white rounded-lg hover:bg-indigo-600 hover:-translate-y-1 transition-all duration-300 shadow-lg"
          >
            ℹ️ About
          </Link>
          <Link
            href="/users"
            className="inline-flex items-center justify-center px-6 py-3 bg-gray-500 text-white rounded-lg hover:bg-gray-600 hover:-translate-y-1 transition-all duration-300 shadow-lg"
          >
            👥 Users
          </Link>
        </div>
      </div>
    </Layout>
  );
}
