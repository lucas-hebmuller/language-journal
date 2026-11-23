import API from "./axiosConfig";
import type { WeeklyGoalRequest, WeeklyGoalResponse } from "@/types/goal.types";

export const getGoals = async (): Promise<WeeklyGoalResponse[]> => {
    const response = await API.get<WeeklyGoalResponse[]>("/goals");
    return response.data;
}

export const upsertGoal = async (data: WeeklyGoalRequest): Promise<WeeklyGoalResponse> => {
    const response = await API.post<WeeklyGoalResponse>("/goals", data);
    return response.data;
}

export const deleteGoal = async (id: number): Promise<void> => {
    await API.delete(`/goals/${id}`);
}

