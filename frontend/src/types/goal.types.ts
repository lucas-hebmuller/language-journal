export interface WeeklyGoalResponse {
    id: number;
    languageId: number;
    languageName: string;
    targetMinutes: number;
    createdAt: string;
}

export interface WeeklyGoalRequest {
    languageId: number;
    targetMinutes: number;
}