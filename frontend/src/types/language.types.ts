export interface LanguageRequest {
    name: string;
    colorHex: string;
}

export interface LanguageResponse {
    id: number;
    name: string;
    colorHex: string;
    createdAt: string;
}