import React, { useState } from "react";
import { Head, useForm } from "@inertiajs/react";
import Layout from "./Layout";

interface Message {
  name: string;
  email: string;
  message: string;
  timestamp: string;
}

interface Props {
  title: string;
  description: string;
  messages: Message[];
  timestamp: string;
  success?: string;
  errors?: {
    name?: string;
    email?: string;
    message?: string;
  };
  old?: {
    name?: string;
    email?: string;
    message?: string;
  };
}

export default function CSRFDemo({
  title,
  description,
  messages,
  timestamp,
  success,
  errors,
  old,
}: Props) {
  const { data, setData, post, processing, reset } = useForm({
    name: old?.name || "",
    email: old?.email || "",
    message: old?.message || "",
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    post("/csrf-demo/submit", {
      onSuccess: () => reset(),
    });
  };

  const clearMessages = () => {
    window.location.href = "/csrf-demo/clear";
  };

  return (
    <Layout>
      <Head title={title} />

      <div className="max-w-4xl mx-auto p-6">
        <h1 className="text-3xl font-bold text-gray-900 mb-4">🛡️ {title}</h1>

        <div className="bg-blue-50 border-l-4 border-blue-400 p-4 mb-6">
          <div className="flex">
            <div className="ml-3">
              <p className="text-sm text-blue-700">
                <strong>Automatischer CSRF-Schutz:</strong> Diese Demo zeigt,
                wie Inertia.js + Axios automatisch CSRF-Schutz handhaben.
                Quarkus setzt ein XSRF-TOKEN Cookie, und Axios fügt es
                automatisch als X-XSRF-TOKEN Header in Requests hinzu.
              </p>
            </div>
          </div>
        </div>

        <div className="bg-yellow-50 border border-yellow-200 rounded-md p-4 mb-6">
          <h3 className="text-sm font-medium text-yellow-800 mb-2">
            🔐 Wie es funktioniert:
          </h3>
          <ol className="text-sm text-yellow-700 list-decimal list-inside space-y-1">
            <li>
              Quarkus erstellt ein XSRF-TOKEN Cookie (für JavaScript zugänglich)
            </li>
            <li>Axios liest dieses Cookie automatisch</li>
            <li>
              Axios fügt den Token als X-XSRF-TOKEN Header in POST-Requests
              hinzu
            </li>
            <li>Quarkus verifiziert, dass Header und Cookie übereinstimmen</li>
          </ol>
          <p className="text-sm text-yellow-700 mt-2">
            <strong>Prüfe deine Browser-Entwicklertools:</strong> Du solltest
            ein XSRF-TOKEN Cookie sehen!
          </p>
        </div>

        {success && (
          <div className="bg-green-50 border border-green-200 rounded-md p-4 mb-6">
            <div className="flex">
              <div className="flex-shrink-0">
                <span className="text-green-400">✅</span>
              </div>
              <div className="ml-3">
                <p className="text-sm text-green-700">{success}</p>
              </div>
            </div>
          </div>
        )}

        <div className="bg-white shadow rounded-lg p-6 mb-6">
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label
                htmlFor="name"
                className="block text-sm font-medium text-gray-700"
              >
                Name *
              </label>
              <input
                type="text"
                id="name"
                value={data.name}
                onChange={(e) => setData("name", e.target.value)}
                className={`mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm ${
                  errors?.name ? "border-red-300" : ""
                }`}
              />
              {errors?.name && (
                <p className="mt-1 text-sm text-red-600">❌ {errors.name}</p>
              )}
            </div>

            <div>
              <label
                htmlFor="email"
                className="block text-sm font-medium text-gray-700"
              >
                Email *
              </label>
              <input
                type="email"
                id="email"
                value={data.email}
                onChange={(e) => setData("email", e.target.value)}
                className={`mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm ${
                  errors?.email ? "border-red-300" : ""
                }`}
              />
              {errors?.email && (
                <p className="mt-1 text-sm text-red-600">❌ {errors.email}</p>
              )}
            </div>

            <div>
              <label
                htmlFor="message"
                className="block text-sm font-medium text-gray-700"
              >
                Nachricht *
              </label>
              <textarea
                id="message"
                rows={4}
                value={data.message}
                onChange={(e) => setData("message", e.target.value)}
                placeholder="Gib deine Nachricht hier ein..."
                className={`mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm ${
                  errors?.message ? "border-red-300" : ""
                }`}
              />
              {errors?.message && (
                <p className="mt-1 text-sm text-red-600">❌ {errors.message}</p>
              )}
            </div>

            <div className="flex space-x-3">
              <button
                type="submit"
                disabled={processing}
                className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
              >
                {processing ? "⏳ Wird gesendet..." : "🚀 Nachricht senden"}
              </button>

              <button
                type="button"
                onClick={clearMessages}
                disabled={processing}
                className="inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
              >
                🗑️ Nachrichten löschen
              </button>
            </div>
          </form>
        </div>

        {messages.length > 0 && (
          <div className="bg-white shadow rounded-lg p-6 mb-6">
            <h3 className="text-lg font-medium text-gray-900 mb-4">
              📝 Aktuelle Nachrichten ({messages.length})
            </h3>
            <div className="space-y-4">
              {messages.map((msg, index) => (
                <div
                  key={index}
                  className="border-l-4 border-green-400 bg-green-50 p-4"
                >
                  <div className="flex">
                    <div className="ml-3">
                      <p className="text-sm font-medium text-green-800">
                        👤 {msg.name} ({msg.email})
                      </p>
                      <p className="text-xs text-green-600 mb-2">
                        📅 {msg.timestamp}
                      </p>
                      <p className="text-sm text-green-700">{msg.message}</p>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        <div className="bg-gray-50 border border-gray-200 rounded-md p-4">
          <h3 className="text-sm font-medium text-gray-800 mb-2">
            🧪 CSRF-Schutz testen:
          </h3>
          <ol className="text-sm text-gray-600 list-decimal list-inside space-y-1">
            <li>
              Öffne die Browser-Entwicklertools und gehe zu Application/Storage
              → Cookies
            </li>
            <li>Du solltest ein XSRF-TOKEN Cookie sehen</li>
            <li>
              Versuche das Cookie zu löschen und das Formular abzusenden - es
              sollte mit 400-Fehler fehlschlagen
            </li>
            <li>
              Lade die Seite neu, um einen neuen Token zu erhalten und versuche
              es erneut - es sollte funktionieren
            </li>
          </ol>
          <p className="text-sm text-gray-600 mt-2">
            <strong>🔍 Network Tab:</strong> Prüfe den Network Tab, um den
            X-XSRF-TOKEN Header zu sehen, der automatisch von Axios gesendet
            wird!
          </p>
        </div>
      </div>
    </Layout>
  );
}
