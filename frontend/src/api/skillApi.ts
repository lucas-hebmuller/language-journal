import API from "./axiosConfig";
import type { SkillResponse } from "@/types/skill.types";

export const getSkills = async (): Promise<SkillResponse[]> => {
  const response = await API.get<SkillResponse[]>("/skills");
  return response.data;
};
