import { create } from "zustand";
import { persist } from "zustand/middleware";

/*
 * Defines the shape of the auth store.
 * State: token and isAuthenticated.
 * Actions: login and logout.
 */
interface AuthState {
  token: string | null;
  isAuthenticated: boolean;

  login: (token: string) => void;
  logout: () => void;
}

/*
 * create<AuthState> creates a Zustand store with AuthState.
 * persist() is the middleware that automatically saves/restores state to localStorage.
 * Any component can call useAuthStore() to read state or trigger actions.
 */
export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      // Intial state:
      token: null,
      isAuthenticated: false,

      /*
       * Login called after a successfull auth login or register.
       * Saves the JWT to localStorage (so the Axios interceptor can read it)
       * and updates the store state so components re-render as "logged in".
       */
      login: (token) => {
        localStorage.setItem("token", token);
        set({
          token,
          isAuthenticated: true,
        });
      },

      /*
       * logout — clears the token from both localStorage and store state.
       * Components watching isAuthenticated will re-render as "logged out".
       */
      logout: () => {
        localStorage.removeItem("token");
        set({
          token: null,
          isAuthenticated: false,
        });
      },
    }),
    {
      // Key used in localStorage to persist this store's state
      name: "auth-storage",

      /*
       * partialize — controls which fields get saved to localStorage.
       * Without this, the login and logout functions would also be serialized,
       * which is unnecessary and would fail.
       */
      partialize: (state) => ({
        token: state.token,
        isAuthenticated: state.isAuthenticated,
      }),
    },
  ),
);
