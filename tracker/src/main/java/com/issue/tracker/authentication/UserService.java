package com.issue.tracker.authentication;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getCurrentUser();
}
