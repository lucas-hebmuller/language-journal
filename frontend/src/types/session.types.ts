export interface StudySessionResponse {
  id: number;
  languageId: number;
  languageName: string;
  skillId: number;
  skillName: string;
  date: string;
  durationMinutes: number;
  notes: string | null;
  createdAt: string;
}

export interface StudySessionRequest {
  languageId: number;
  skillId: number;
  date: string;
  durationMinutes: number;
  notes: string;
}

export interface SessionFilters {
  languageId?: number;
  skillId?: number;
  from?: string; // "YYYY-MM-DD"
  to?: string;
  page?: number; // 0-indexed
  size?: number;
}
