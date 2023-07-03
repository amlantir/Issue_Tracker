package com.issue.tracker.authentication;

import java.util.List;

public record LoginResponse(String token, List<String> roles) {}