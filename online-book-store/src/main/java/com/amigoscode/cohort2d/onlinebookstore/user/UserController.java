package com.amigoscode.cohort2d.onlinebookstore.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtosResponse = userService.getUsers();
        return ResponseEntity.ok()
                .body(userDtosResponse);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        UserDto userDtoResponse = userService.getUserById(id);
        return ResponseEntity.ok()
                .body(userDtoResponse);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto userDtoResponse = userService.createUser(userDto);
        return ResponseEntity.ok()
                .body(userDtoResponse);
    }

    @PutMapping(path = "{id}/addAddress")
    public ResponseEntity<UserDto> addAddress(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        UserDto userDtoResponse = userService.addAddress(id, userDto);
        return ResponseEntity.ok()
                .body(userDtoResponse);
    }
}
