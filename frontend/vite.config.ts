import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import path from "path";

export default defineConfig({
  // Enable React support (JSX transform, fast refresh during development)
  plugins: [react()],
  resolve: {
    alias: {
      /*
       * Tell Vite how to resolve the "@" alias at build time.
       * tsconfig.json handles it for TypeScript's type checker,
       * but Vite needs its own config since it's the actual bundler.
       * __dirname = the directory this config file lives in.
       */
      "@": path.resolve(__dirname, "./src"),
    },
  },
});
