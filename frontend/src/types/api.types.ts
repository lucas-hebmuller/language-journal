export interface ApiError {
    timestamp: string;
    status: number;
    error: string;
    message: string;
    path: string;
}

export interface Page<T> {
    content: T[];
    number: number;
    totalPages: number;
    totalElements: number;
    first: boolean;
    last: boolean;
}