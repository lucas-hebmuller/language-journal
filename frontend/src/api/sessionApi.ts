import API from "./axiosConfig";
import type {
  SessionFilters,
  StudySessionRequest,
  StudySessionResponse,
} from "@/types/session.types";
import type { Page } from "@/types/api.types";

export const getSessions = async (
  filters: SessionFilters,
): Promise<Page<StudySessionResponse>> => {
  const response = await API.get<Page<StudySessionResponse>>("/sessions", {
    params: filters,
  });
  return response.data;
};

export const createSession = async (
  data: StudySessionRequest,
): Promise<StudySessionResponse> => {
  const response = await API.post<StudySessionResponse>("/sessions", data);
  return response.data;
};

export const updateSession = async (
  id: number,
  data: StudySessionRequest,
): Promise<StudySessionResponse> => {
  const response = await API.put<StudySessionResponse>(`/sessions/${id}`, data);
  return response.data;
};

export const deleteSession = async (id: number): Promise<void> => {
  await API.delete(`/sessions/${id}`);
};
