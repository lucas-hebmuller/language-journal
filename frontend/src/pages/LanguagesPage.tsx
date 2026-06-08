import { useState } from "react";
import { useLanguages } from "@/hooks/useLanguages";
import LanguageForm from "@/components/LanguageForm";
import type { LanguageFormData } from "@/types/language.schemas";
import type { LanguageResponse } from "@/types/language.types";

function LanguagesPage() {
  const {
    languages,
    isLoading,
    error,
    createMutation,
    updateMutation,
    deleteMutation,
  } = useLanguages();

  const [showForm, setShowForm] = useState(false);
  const [editingLanguage, setEditingLanguage] =
    useState<LanguageResponse | null>(null);

  const handleCreate = (data: LanguageFormData) => {
    createMutation.mutate(data, {
      onSuccess: () => {
        setShowForm(false);
      },
    });
  };

  const handleUpdate = (data: LanguageFormData) => {
    if (editingLanguage) {
      updateMutation.mutate(
        { id: editingLanguage.id, data },
        {
          onSuccess: () => {
            setEditingLanguage(null);
          },
        },
      );
    }
  };

  const handleDelete = (id: number) => {
    if (confirm("Delete this language?")) {
      deleteMutation.mutate(id);
    }
  };

  if (isLoading) return <p>Loading languages...</p>;
  if (error) return <p>Error loading languages.</p>;

  return (
    <div>
      <h1>My Languages</h1>
      <button onClick={() => setShowForm(true)} className="btn-primary">
        + Add Language
      </button>

      {showForm && (
        <LanguageForm
          onSubmit={handleCreate}
          onCancel={() => setShowForm(false)}
          isPending={createMutation.isPending}
        />
      )}

      {languages && languages.length > 0 ? (
        <div className="language-list">
          {languages.map((language) => (
            <div key={language.id} className="language-card">
              <div className="language-info">
                <span
                  className="color-swatch"
                  style={{
                    backgroundColor: language.colorHex,
                    display: "inline-block",
                    width: 20,
                    height: 20,
                    borderRadius: "50%",
                  }}
                />
                <span>{language.name}</span>
              </div>
              <div className="language-actions">
                <button
                  onClick={() => setEditingLanguage(language)}
                  className="btn-small"
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDelete(language.id)}
                  className="btn-small btn-danger"
                >
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p>No languages yet. Add your first one!</p>
      )}

      {editingLanguage && (
        <LanguageForm
          onSubmit={handleUpdate}
          onCancel={() => setEditingLanguage(null)}
          initialData={editingLanguage}
          isPending={updateMutation.isPending}
        />
      )}
    </div>
  );
}

export default LanguagesPage;
