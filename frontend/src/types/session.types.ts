export interface StudySessionResponse {
    id: number;
    languageId: number;
    languageName: string;
    skillId: number;
    skillName: string;
    date: string;
    durationMinutes: number;
    notes: string;
    createdAt: string;
}

export interface StudySessionRequest {
    languageId: number;
    skillId: number;
    date: string;
    durationMinutes: number;
    notes: string;
}