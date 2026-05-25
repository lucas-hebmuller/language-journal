import { z } from "zod";

export const loginSchema = z.object({
  email: z.string().email("Email must be valid"),
  password: z.string().min(1, "Password cannot be blank"),
});

// This creates a TypeScript type from the schema
export type LoginFormData = z.infer<typeof loginSchema>;
