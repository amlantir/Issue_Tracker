package com.issue.tracker.common;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    Authentication getAuthentication();
}
