package com.despereaux.jpascheduler.controller;

import com.despereaux.jpascheduler.dto.UserDto;
import com.despereaux.jpascheduler.entity.User;
import com.despereaux.jpascheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        userService.saveUser(user);
        return ResponseEntity.ok("사용자가 성공적으로 생성되었습니다");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            UserDto userDto = new UserDto();
            userDto.setId(user.get().getId());
            userDto.setName(user.get().getName());
            userDto.setEmail(user.get().getEmail());
            userDto.setCreatedAt(user.get().getCreatedAt());
            userDto.setUpdatedAt(user.get().getUpdatedAt());
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setCreatedAt(user.getCreatedAt());
            userDto.setUpdatedAt(user.getUpdatedAt());
            userDtos.add(userDto);
        }
        return ResponseEntity.ok(userDtos);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        Optional<User> optionalUser = userService.getUserById(userDto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setUpdatedAt(LocalDateTime.now());
            userService.updateUser(user);
            return ResponseEntity.ok("사용자가 성공적으로 업데이트되었습니다");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(user.get());
            return ResponseEntity.ok("사용자가 성공적으로 삭제되었습니다");
        }
        return ResponseEntity.notFound().build();
    }
}