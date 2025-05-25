import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
import { resolve } from "path";

export default defineConfig(({ command }) => {
  const isProduction = command === "build";

  return {
    plugins: [react(), tailwindcss()],
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
          ? resolve(__dirname, "frontend/main.tsx")
          : "./main.tsx",
      },
    },
  };
});
