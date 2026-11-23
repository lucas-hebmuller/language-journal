import {
  createSession,
  deleteSession,
  getSessions,
  updateSession,
} from "@/api/sessionApi";
import type {
  SessionFilters,
  StudySessionRequest,
} from "@/types/session.types";
import {
  keepPreviousData,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";

export function useSessions(filters: SessionFilters) {
  const queryClient = useQueryClient();

  const sessionsQuery = useQuery({
    queryKey: ["sessions", filters],
    queryFn: () => getSessions(filters),
    placeholderData: keepPreviousData,
  });

  const createMutation = useMutation({
    mutationFn: (data: StudySessionRequest) => createSession(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sessions"] });
    },
  });

  const updateMutation = useMutation({
    mutationFn: (vars: { id: number; data: StudySessionRequest }) =>
      updateSession(vars.id, vars.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sessions"] });
    },
  });

  const deleteMutation = useMutation({
    mutationFn: (id: number) => deleteSession(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sessions"] });
    },
  });

  return {
    sessions: sessionsQuery.data,
    isLoading: sessionsQuery.isLoading,
    error: sessionsQuery.error,
    isPlaceholderData: sessionsQuery.isPlaceholderData,
    createMutation,
    updateMutation,
    deleteMutation,
  };
}
