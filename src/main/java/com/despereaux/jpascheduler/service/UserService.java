package com.despereaux.jpascheduler.service;

import com.despereaux.jpascheduler.entity.User;
import com.despereaux.jpascheduler.repository.UserDao;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao uDao;
    // 사용자 저장
    public void saveUser(@NonNull User user) {
        uDao.save(user);
    }
    // 사용자 조회(id)
    public Optional<User> getUserById(@NonNull Long id) {
        return uDao.findById(id);
    }
    // 전체 조회
    public List<User> getAllUsers() {
        return uDao.findAll();
    }
    // 사용자 수정
    public void updateUser(@NonNull User user) {
        user.setUpdatedAt(LocalDateTime.now());
        uDao.update(user);
    }
    // 사용자 삭제
    public void deleteUser(@NonNull User user) {
        uDao.delete(user);
    }
}