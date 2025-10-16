package com.lumine.controllers;

import com.lumine.dtos.UserRequestDTO;
import com.lumine.dtos.UserResponseDTO;
import com.lumine.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. Create User
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody UserRequestDTO request) {
        return userService.createUser(request);
    }

    // 2. Create Guest User
    @PostMapping("/guest")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createGuestUser(@RequestParam String email) {
        return userService.createGuestUser(email);
    }

    // 3. Get User
    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    // 4. Update Email
    @PutMapping("/{id}/email")
    public UserResponseDTO updateEmail(@PathVariable Long id,
                                       @RequestBody EmailUpdateRequest request) {
        return userService.updateEmail(id, request.newEmail());
    }

    // 5. Delete User
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // ✅ Record-based Email update DTO (inline or extract as needed)
    public record EmailUpdateRequest(String newEmail) {}
}
