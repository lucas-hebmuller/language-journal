import { z } from "zod";

export const loginSchema = z.object({
  email: z.string().email("Email must be valid"),
  password: z.string().min(1, "Password cannot be blank"),
});

// This creates a TypeScript type from the schema
export type LoginFormData = z.infer<typeof loginSchema>;

export const registerSchema = z.object({
  name: z.string().min(2, "Name must be at least 2 characters"),
  email: z.string().email("Email must be valid"),
  password: z.string().min(8, "Password must be at least 8 characters"),
  confirmPassword: z.string().min(1, "Please confirm your password"),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords do not match",
  path: ["confirmPassword"], // attaches the error to the confirmPassword field
});

export type RegisterFormData = z.infer<typeof registerSchema>;