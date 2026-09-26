import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    // Forward /api/* requests to the Java backend, so React can just call fetch('/api/cars')
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
})
