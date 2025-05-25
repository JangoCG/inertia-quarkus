import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'

export default defineConfig({
  plugins: [react()],
  root: 'frontend',
  server: {
    port: 5173,
    host: true
  },
  build: {
    manifest: true,
    outDir: '../target/classes/META-INF/resources',
    emptyOutDir: true,
    rollupOptions: {
      input: resolve(__dirname, 'frontend/main.jsx')
    }
  }
})