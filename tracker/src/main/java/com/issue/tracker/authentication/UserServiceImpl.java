package com.issue.tracker.authentication;

import com.issue.tracker.common.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getCurrentUser() {
        String currentUsername = authenticationService.getAuthentication().getName();
        return userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }
}
