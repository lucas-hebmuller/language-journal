/*
 * useQuery — fetches and caches data (reads)
 * useMutation — handles create/update/delete operations (writes)
 * useQueryClient — access the cache to invalidate queries after mutations
 */
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import {
  getLanguages,
  createLanguage,
  updateLanguage,
  deleteLanguage,
} from "@/api/languageApi";
import type { LanguageRequest } from "@/types/language.types";

/*
 * Custom hook that encapsulates all language-related API operations.
 * Any component that needs language data just calls useLanguages()
 * instead of managing API calls, caching, and loading states manually.
 */
export function useLanguages() {
  const queryClient = useQueryClient();

  /*
   * Fetch all languages for the authenticated user.
   * queryKey: ["languages"] — unique cache key. TanStack Query uses this to
   * cache the result, deduplicate identical requests, and know what to refetch.
   * queryFn: the function that actually makes the API call.
   * This automatically provides: data, isLoading, error, and refetch behavior.
   */
  const languagesQuery = useQuery({
    queryKey: ["languages"],
    queryFn: getLanguages,
  });

  /*
   * Each mutation follows the same pattern:
   * - mutationFn: the API call to execute when .mutate() is called
   * - onSuccess: runs after the API call succeeds
   *
   * invalidateQueries marks the cached "languages" data as stale,
   * which triggers an automatic refetch. This is how the list stays
   * in sync with the database — no manual state updates needed.
   */

  const createMutation = useMutation({
    mutationFn: (data: LanguageRequest) => createLanguage(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["languages"] });
    },
  });

  const updateMutation = useMutation({
    mutationFn: (vars: { id: number; data: LanguageRequest }) =>
      updateLanguage(vars.id, vars.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["languages"] });
    },
  });

  const deleteMutation = useMutation({
    mutationFn: (id: number) => deleteLanguage(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["languages"] });
    },
  });

  /*
   * Return everything the page component needs:
   * - languages: the fetched array (undefined while loading)
   * - isLoading: true while the initial fetch is in progress
   * - error: any error from the fetch
   * - mutations: so the page can call .mutate() to trigger operations
   */
  return {
    languages: languagesQuery.data,
    isLoading: languagesQuery.isLoading,
    error: languagesQuery.error,
    createMutation,
    updateMutation,
    deleteMutation,
  };
}
