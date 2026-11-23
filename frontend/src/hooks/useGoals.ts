import { deleteGoal, getGoals, upsertGoal } from "@/api/goalApi";
import type { WeeklyGoalRequest } from "@/types/goal.types";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

export function useGoals() {
  const queryClient = useQueryClient();

  const goalsQuery = useQuery({
    queryKey: ["goals"],
    queryFn: getGoals,
  });

  const upsertMutation = useMutation({
    mutationFn: (data: WeeklyGoalRequest) => upsertGoal(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["goals"] });
    },
  });

  const deleteMutation = useMutation({
    mutationFn: (id: number) => deleteGoal(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["goals"] });
    },
  });

  return {
    goals: goalsQuery.data,
    isLoading: goalsQuery.isLoading,
    error: goalsQuery.error,
    upsertMutation,
    deleteMutation
  }
}
