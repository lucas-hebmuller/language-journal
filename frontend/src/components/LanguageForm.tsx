import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  languageSchema,
  type LanguageFormData,
} from "@/types/language.schemas";
import type { LanguageResponse } from "@/types/language.types";

interface LanguageFormProps {
  onSubmit: (data: LanguageFormData) => void;
  onCancel: () => void;
  initialData?: LanguageResponse;
  isPending: boolean;
}

function LanguageForm({
  onSubmit,
  onCancel,
  initialData,
  isPending,
}: LanguageFormProps) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LanguageFormData>({
    resolver: zodResolver(languageSchema),
    defaultValues: {
      name: initialData?.name || "",
      colorHex: initialData?.colorHex || "#000000",
    },
  });

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <div className="form-group">
        <label htmlFor="name">Language Name</label>
        <input
          type="text"
          id="name"
          {...register("name")}
          placeholder="e.g. French"
        />
        {errors.name && (
          <span className="field-error">{errors.name.message}</span>
        )}
      </div>

      <div className="form-group">
        <label htmlFor="colorHex">Color</label>
        <input
          type="color"
          id="colorHex"
          {...register("colorHex")}
          placeholder="#FFFFFF"
        />
        {errors.colorHex && (
          <span className="field-error">{errors.colorHex.message}</span>
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

export default LanguageForm;
