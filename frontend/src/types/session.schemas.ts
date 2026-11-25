import { z } from "zod";

export const sessionSchema = z.object({
  languageId: z.coerce.number().int().positive("Select a language"),
  skillId: z.coerce.number().int().positive("Select a skill"),
  date: z.string().min(1, "Date is required"),
  durationMinutes: z.coerce
    .number()
    .int()
    .min(1, "Duration must be at least 1 minute"),
  notes: z.string(),
});

export type SessionFormData = z.infer<typeof sessionSchema>;
