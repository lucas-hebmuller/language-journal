import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter as Router } from "react-router-dom";
import "./index.css";
import App from "./App.tsx";


/*
 * QueryClient is the central manager for all TanStack Query caching and fetching.
 * defaultOptions apply to every query in the app unless overridden individually. 
 */
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false, // Don't refetch data when the user switches back to the browser tab
      retry: 1, // Only retry a failed request once before showing an error
      staleTime: 5 * 60 * 1000, // Don't refresh data during a 5 min window
    },
  },
});

/*
 * QueryClientProvider makes TanStack Query available to all components
 * Router enables client-side routing (useNavigate, Link, Routes, etc.)
 */
createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <Router>
        <App />
      </Router>
    </QueryClientProvider>
  </StrictMode>,
);
