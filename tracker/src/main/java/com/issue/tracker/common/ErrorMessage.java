package com.issue.tracker.common;

import java.time.OffsetDateTime;

public record ErrorMessage(String details,
                           String url,
                           String stackTrace,
                           OffsetDateTime timestamp) {
}