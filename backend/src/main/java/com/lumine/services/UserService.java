package com.lumine.services;

import com.lumine.dtos.UserRequestDTO;
import com.lumine.dtos.UserResponseDTO;
import com.lumine.helpers.UserExceptionMessages;
import com.lumine.helpers.UserRole;
import com.lumine.models.User;
import com.lumine.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    //POST
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO request) {
        validateRole(request.role());
        checkEmailUniqueness(request.email());

        User user = new User(request.email(), request.role());
        user = userRepo.save(user);
        return toResponseDTO(user);
    }


    //POST crear usuario invitado
    @Transactional
    public UserResponseDTO createGuestUser(String email)
    {
        checkEmailUniqueness(email);
        User user = new User(email, UserRole.GUEST);
        user = userRepo.save(user);
        return toResponseDTO(user);
    }


    // GET
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        UserExceptionMessages.USER_NOT_FOUND.message() + userId
                ));
        return toResponseDTO(user);
    }


    //UPDATE
    @Transactional
    public UserResponseDTO updateEmail(Long userId, String newEmail) {
        User user = getUserEntity(userId);
        checkEmailUniqueness(newEmail);

        user.setEmail(newEmail);
        user = userRepo.save(user);
        return toResponseDTO(user);
    }


    // POST LOGIN
    @Transactional
    public void recordLogin(Long userId)
    {
        User user = getUserEntity(userId);
        user.setLastLogin(LocalDateTime.now());
        userRepo.save(user);
    }

    // DELETE USER
    @Transactional
    public void deleteUser(Long userId)
    {
        if (!userRepo.existsById(userId))
        {
            throw new IllegalArgumentException(
                    UserExceptionMessages.USER_NOT_FOUND.message() + userId
            );
        }
        userRepo.deleteById(userId);
    }

    // ------------------- PRIVATE HELPERS -------------------
    @Transactional(readOnly = true)
    public User getUserEntity(Long userId)
    {
        return userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        UserExceptionMessages.USER_NOT_FOUND.message() + userId
                ));
    }

    private void validateRole(UserRole role)
    {
        if (role == null)
        {
            throw new IllegalArgumentException(
                    UserExceptionMessages.USER_INVALID_ROLE_ASSIGNMENT.message()
            );
        }
    }

    private void checkEmailUniqueness(String email)
    {
        if (userRepo.existsByEmail(email))
        {
            throw new IllegalArgumentException(
                    UserExceptionMessages.USER_EMAIL_DUPLICATE.message()
            );
        }
    }

    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getLastLogin()
        );
    }
}