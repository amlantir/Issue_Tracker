package com.issue.tracker.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    ERROR_UNKNOWN("error.unknown"),
    ERROR_ARGUMENT("error.argument"),
    ERROR_CREDENTIALS("error.credentials"),
    ERROR_AUTHORIZATION("error.authorization"),
    ERROR_IO("error.io"),
    ERROR_FILE_ALREADY_EXISTS("error.file.already.exists"),
    ERROR_NO_SUCH_ELEMENT("error.no.such.element"),
    ERROR_FILE_TOO_BIG("error.file.too.big");

    private final String propertyName;
}
