import { z } from "zod";
import type { LanguageResponse } from "@/types/language.types";
import { sessionSchema, type SessionFormData } from "@/types/session.schemas";
import type { StudySessionResponse } from "@/types/session.types";
import type { SkillResponse } from "@/types/skill.types";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";

interface SessionFormProps {
  onSubmit: (data: SessionFormData) => void;
  onCancel: () => void;
  initialData?: StudySessionResponse;
  isPending: boolean;
  languages: LanguageResponse[];
  skills: SkillResponse[];
}

function SessionForm({
  onSubmit,
  onCancel,
  initialData,
  isPending,
  languages,
  skills,
}: SessionFormProps) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<
    z.input<typeof sessionSchema>, // fields hold the pre-coercion (string) shape
    unknown, // context — unused
    z.output<typeof sessionSchema> // handleSubmit delivers the coerced (number) shape
  >({
    resolver: zodResolver(sessionSchema),
    defaultValues: {
      languageId: initialData?.languageId ?? 0,
      skillId: initialData?.skillId ?? 0,
      date: initialData?.date ?? new Date().toISOString().slice(0, 10), // today
      durationMinutes: initialData?.durationMinutes ?? 0,
      notes: initialData?.notes ?? "",
    },
  });

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <div className="form-group">
        <label htmlFor="languageId">Language</label>
        <select id="languageId" {...register("languageId")}>
          <option value="">Select a language...</option>
          {languages.map((lang) => (
            <option key={lang.id} value={lang.id}>
              {lang.name}
            </option>
          ))}
        </select>
        {errors.languageId && (
          <span className="field-error">{errors.languageId.message}</span>
        )}
      </div>

      <div className="form-group">
        <label htmlFor="skillId">Skill</label>
        <select id="skillId" {...register("skillId")}>
          <option value="">Select a skill...</option>
          {skills.map((skill) => (
            <option key={skill.id} value={skill.id}>
              {skill.name}
            </option>
          ))}
        </select>
        {errors.skillId && (
          <span className="field-error">{errors.skillId.message}</span>
        )}
      </div>

      <div className="form-group">
        <label htmlFor="date">Date</label>
        <input type="date" id="date" {...register("date")} />
        {errors.date && (
          <span className="field-error">{errors.date.message}</span>
        )}
      </div>

      <div className="form-group">
        <label htmlFor="durationMinutes">Duration in minutes</label>
        <input
          type="number"
          id="durationMinutes"
          {...register("durationMinutes")}
        />
        {errors.durationMinutes && (
          <span className="field-error">{errors.durationMinutes.message}</span>
        )}
      </div>

      <div className="form-group">
        <label htmlFor="notes">Notes</label>
        <textarea id="notes" {...register("notes")} rows={3} />
        {errors.notes && (
          <span className="field-error">{errors.notes.message}</span>
        )}
      </div>

      <div className="form-actions">
        <button type="button" onClick={onCancel} className="btn-secondary">
          Cancel
        </button>
        <button type="submit" disabled={isPending}>
          {isPending ? "Saving..." : initialData ? "Update" : "Create"}
        </button>
      </div>
    </form>
  );
}

export default SessionForm;
