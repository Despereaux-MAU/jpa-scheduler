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
    private UserService uS;
    // 사용자 저장
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        uS.saveUser(user);
        return ResponseEntity.ok("사용자가 성공적으로 생성되었습니다");
    }
    // 사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<User> userOptional = uS.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDTO = new UserDto();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setCreatedAt(user.getCreatedAt());
            userDTO.setUpdatedAt(user.getUpdatedAt());
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }
    // 전체 조회
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = uS.getAllUsers();
        List<UserDto> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDto userDTO = new UserDto();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setCreatedAt(user.getCreatedAt());
            userDTO.setUpdatedAt(user.getUpdatedAt());
            userDTOs.add(userDTO);
        }
        return ResponseEntity.ok(userDTOs);
    }
    // 사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDto userDTO) {
        Optional<User> userOptional = uS.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setUpdatedAt(LocalDateTime.now());
            uS.updateUser(user);
            return ResponseEntity.ok("사용자 정보가 성공적으로 업데이트되었습니다");
        }
        return ResponseEntity.notFound().build();
    }
    // 사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = uS.getUserById(id);
        if (userOptional.isPresent()) {
            uS.deleteUser(userOptional.get());
            return ResponseEntity.ok("사용자가 성공적으로 삭제되었습니다");
        }
        return ResponseEntity.notFound().build();
    }
}