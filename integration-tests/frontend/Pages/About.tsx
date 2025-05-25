import { Link } from "@inertiajs/react";
import Layout from "./Layout";

interface AboutProps {
  title: string;
  description: string;
  features: string[];
}

export default function About({ title, description, features }: AboutProps) {
  return (
    <Layout title="About">
      <div className="text-center bg-white/95 p-12 rounded-3xl mb-8 shadow-2xl">
        <h1 className="text-4xl font-bold mb-4 bg-gradient-to-r from-indigo-500 to-purple-600 bg-clip-text text-transparent">
          {title}
        </h1>
        <p className="text-xl text-gray-600">{description}</p>
      </div>

      <div className="bg-white/95 p-8 rounded-2xl mb-8 shadow-lg">
        <h2 className="text-2xl font-semibold mb-6 text-gray-800">
          🚀 Features of this Extension:
        </h2>
        <ul className="space-y-3">
          {features.map((feature, index) => (
            <li key={index} className="flex items-center text-gray-700">
              <span className="text-green-500 mr-3 text-lg">✅</span>
              {feature}
            </li>
          ))}
        </ul>

        <div className="mt-8">
          <h2 className="text-2xl font-semibold mb-4 text-gray-800">
            🛠️ Tech Stack:
          </h2>
          <div className="flex flex-wrap gap-2">
            <span className="bg-indigo-500 text-white px-3 py-1 rounded-full text-sm font-medium">
              Quarkus
            </span>
            <span className="bg-indigo-500 text-white px-3 py-1 rounded-full text-sm font-medium">
              React
            </span>
            <span className="bg-indigo-500 text-white px-3 py-1 rounded-full text-sm font-medium">
              Inertia.js
            </span>
            <span className="bg-indigo-500 text-white px-3 py-1 rounded-full text-sm font-medium">
              Vite
            </span>
            <span className="bg-indigo-500 text-white px-3 py-1 rounded-full text-sm font-medium">
              CDI
            </span>
          </div>
        </div>

        <div className="mt-8">
          <h2 className="text-2xl font-semibold mb-4 text-gray-800">
            🎮 Demo Features:
          </h2>
          <p className="text-gray-600 mb-4">This demo shows:</p>
          <ul className="space-y-2 text-gray-700">
            <li className="flex items-start">
              <span className="text-indigo-500 mr-2">•</span>
              Server-Side Rendering with Quarkus
            </li>
            <li className="flex items-start">
              <span className="text-indigo-500 mr-2">•</span>
              Client-Side Navigation without Page Reloads
            </li>
            <li className="flex items-start">
              <span className="text-indigo-500 mr-2">•</span>
              JSON-based Data Transfer
            </li>
            <li className="flex items-start">
              <span className="text-indigo-500 mr-2">•</span>
              React Components with Inertia.js
            </li>
            <li className="flex items-start">
              <span className="text-indigo-500 mr-2">•</span>
              Asset Versioning for Cache-Busting
            </li>
          </ul>
        </div>
      </div>

      <div className="bg-white/95 p-8 rounded-2xl text-center shadow-lg">
        <div className="flex flex-col sm:flex-row flex-wrap gap-4 justify-center">
          <Link
            href="/"
            className="inline-flex items-center justify-center px-6 py-3 bg-indigo-500 text-white rounded-lg hover:bg-indigo-600 hover:-translate-y-1 transition-all duration-300 shadow-lg"
          >
            🏠 Back to Homepage
          </Link>
          <Link
            href="/users"
            className="inline-flex items-center justify-center px-6 py-3 bg-gray-500 text-white rounded-lg hover:bg-gray-600 hover:-translate-y-1 transition-all duration-300 shadow-lg"
          >
            👥 View Users
          </Link>
        </div>
      </div>
    </Layout>
  );
}
