import { z } from "zod";

export const languageSchema = z.object({
  name: z.string().min(1, "Name cannot be blank"),
  colorHex: z.string().min(1, "Color cannot be blank"),
});

export type LanguageFormData = z.infer<typeof languageSchema>;
