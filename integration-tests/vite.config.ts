import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import { resolve } from "path";

export default defineConfig(({ command }) => {
  const isProduction = command === "build";

  return {
    plugins: [react()],
    root: isProduction ? undefined : "frontend",
    server: {
      port: 5173,
      host: true,
    },
    build: {
      manifest: true,
      outDir: isProduction
        ? "target/classes/META-INF/resources"
        : "../target/classes/META-INF/resources",
      emptyOutDir: true,
      rollupOptions: {
        input: isProduction
          ? resolve(__dirname, "frontend/main.jsx")
          : "./main.jsx",
      },
    },
  };
});
