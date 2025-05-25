import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  root: 'src/main/frontend',
  server: {
    port: 5173,
    host: true
  },
  build: {
    manifest: true,
    outDir: '../../../target/classes/META-INF/resources',
    rollupOptions: {
      input: 'main.jsx'
    }
  }
})