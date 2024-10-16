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
    private UserDao userDao;

    public void saveUser(@NonNull User user) {
        userDao.save(user);
    }

    public Optional<User> getUserById(@NonNull Long id) {
        return userDao.findById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void updateUser(@NonNull User user) {
        user.setUpdatedAt(LocalDateTime.now());
        userDao.update(user);
    }

    public void deleteUser(@NonNull User user) {
        userDao.delete(user);
    }
}
