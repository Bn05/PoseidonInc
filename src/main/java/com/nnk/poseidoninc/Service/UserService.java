package com.nnk.poseidoninc.Service;

import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> findAll() {



        return userRepository.findAll();
    }
}
