package com.amigoscode.cohort2d.onlinebookstore.exceptions;

import java.time.LocalDateTime;

public record ApiError(
        String path,
        String[] messages,
        int statusCode,
        LocalDateTime localDateTime
) {
}
