/*
 * Auth API functions connect the website pages with the backend.
 * Each function wraps an axios call to a specific endpoint and returns typed data. 
 */

import API from "./axiosConfig";
import {
  type AuthResponse,
  type LoginRequest,
  type RegisterRequest,
} from "@/types/auth.types";

export const loginUser = async (data: LoginRequest) : Promise<AuthResponse> => {
    const response = await API.post<AuthResponse>("/auth/login", data);
    return response.data;
};

export const registerUser = async (data: RegisterRequest) : Promise<AuthResponse> => {
    const response = await API.post<AuthResponse>("/auth/register", data);
    return response.data;
};


