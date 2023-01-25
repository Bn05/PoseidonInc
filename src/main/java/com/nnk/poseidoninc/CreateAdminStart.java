package com.nnk.poseidoninc;

import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Repository.UserRepository;
import com.nnk.poseidoninc.Service.Implementation.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class CreateAdminStart implements CommandLineRunner {

    private UserServiceImpl userService;
    private UserRepository userRepository;

    public CreateAdminStart(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        String password = "Password1234!";
        String passwordBCrypt = userService.passwordEncoder().encode(password);
        UserDto userDto = new UserDto("admin", passwordBCrypt, "fullName", "ADMIN");
        User user = userService.convertUserDtoToUser(userDto);

        if (userRepository.findUserByUserName(user.getUserName()).isEmpty()) {
            userRepository.save(user);
        }


    }
}
