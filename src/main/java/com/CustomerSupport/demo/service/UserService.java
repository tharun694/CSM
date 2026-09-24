package com.CustomerSupport.demo.service;

import com.CustomerSupport.demo.entity.User;
import com.CustomerSupport.demo.repository.UserRepo;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    UserRepo repo;
    UserService(UserRepo repo){
        this.repo=repo;
    }

    public void adduser(User user) {
repo.save(user);
    }
}
