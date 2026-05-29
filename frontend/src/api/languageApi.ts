import API from "./axiosConfig";
import {
  type LanguageRequest,
  type LanguageResponse,
} from "@/types/language.types";

export const getLanguages = async (): Promise<LanguageResponse[]> => {
  const response = await API.get<LanguageResponse[]>("/languages");
  return response.data;
};

export const createLanguage = async (
  data: LanguageRequest,
): Promise<LanguageResponse> => {
  const response = await API.post<LanguageResponse>("/languages", data);
  return response.data;
};

export const updateLanguage = async (
  id: number,
  data: LanguageRequest,
): Promise<LanguageResponse> => {
  const response = await API.put<LanguageResponse>(`/languages/${id}`, data);
  return response.data;
};

export const deleteLanguage = async (id: number): Promise<void> => {
  await API.delete(`/languages/${id}`);
};
