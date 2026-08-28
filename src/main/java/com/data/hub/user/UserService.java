package com.data.hub.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(UserResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return UserResponse.from(findUser(id));
    }

    public UserResponse create(UserRequest request) {
        ensureEmailIsAvailable(request.email(), null);
        return UserResponse.from(userRepository.save(new User(request.name().trim(), request.email().trim())));
    }

    public UserResponse update(Long id, UserRequest request) {
        User user = findUser(id);
        ensureEmailIsAvailable(request.email(), id);
        user.update(request.name().trim(), request.email().trim());
        return UserResponse.from(userRepository.save(user));
    }

    public void delete(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private void ensureEmailIsAvailable(String email, Long currentUserId) {
        userRepository.findByEmailIgnoreCase(email.trim())
                .filter(user -> currentUserId == null || !user.getId().equals(currentUserId))
                .ifPresent(user -> { throw new DuplicateEmailException(email); });
    }
}
