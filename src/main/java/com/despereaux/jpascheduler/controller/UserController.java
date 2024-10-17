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
    public ResponseEntity<String> createUser(@RequestBody UserDto uDto) {
        User user = new User();
        user.setName(uDto.getName());
        user.setEmail(uDto.getEmail());
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
            UserDto uDto = new UserDto();
            uDto.setId(user.getId());
            uDto.setName(user.getName());
            uDto.setEmail(user.getEmail());
            uDto.setCreatedAt(user.getCreatedAt());
            uDto.setUpdatedAt(user.getUpdatedAt());
            return ResponseEntity.ok(uDto);
        }
        return ResponseEntity.notFound().build();
    }
    // 전체 조회
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = uS.getAllUsers();
        List<UserDto> uDtos = new ArrayList<>();
        for (User user : users) {
            UserDto uDto = new UserDto();
            uDto.setId(user.getId());
            uDto.setName(user.getName());
            uDto.setEmail(user.getEmail());
            uDto.setCreatedAt(user.getCreatedAt());
            uDto.setUpdatedAt(user.getUpdatedAt());
            uDtos.add(uDto);
        }
        return ResponseEntity.ok(uDtos);
    }
    // 사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDto uDto) {
        Optional<User> userOptional = uS.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(uDto.getName());
            user.setEmail(uDto.getEmail());
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