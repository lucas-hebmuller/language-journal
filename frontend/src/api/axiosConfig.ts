/*
 * axios is the HTTP client library
 * AxiosError is the typed error object that axios throws on failed request
 * InternalAxiosRequestConfig is the type for the config object inside interceptors
 */
import axios, { type AxiosError, type InternalAxiosRequestConfig } from "axios";
import type { ApiError } from "@/types/api.types"; // Custom type matching ErrorResponse.java in the backend

/*
 * Create a reusable axios instance with default settigns.
 * Every request made with API.get(), API.post(), etc. will use these defaults.
 * Example: API.get(/"session") actually hits http://localhost:8080/api/sessions
 */
const API = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

/*
 * Request Interceptor.
 * Runs before every outgoing request, adds the JWT token to every API call. 
 */
API.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem("token");
    // If there is a token, add it to the auth header
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  },
);

/*
 * Response Interceptor.
 * Run after every response comes back, allows global error handling 
 */
API.interceptors.response.use(
  // If the response is successfull (2xx), just pass it through unchanged
  (response) => response,
  (error: AxiosError<ApiError>) => {
    // Don't redirect to login if the 401 came from /auth/ endpoints themselves
    const isAuthEndpoint = error.config?.url?.includes("/auth/");
    if (error.response?.status === 401 && !isAuthEndpoint) {
      // Token is invalid or expired, so clear it and send the user to login  
      localStorage.removeItem("token");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  },
);

export default API;
