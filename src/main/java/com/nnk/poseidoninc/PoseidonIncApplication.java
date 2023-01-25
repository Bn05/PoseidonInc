package com.nnk.poseidoninc;

import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Repository.UserRepository;
import com.nnk.poseidoninc.Security.Configuration.RsaKeyProperties;
import com.nnk.poseidoninc.Service.Implementation.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class PoseidonIncApplication implements CommandLineRunner {

    private UserServiceImpl userService;
    private UserRepository userRepository;

    public PoseidonIncApplication(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(PoseidonIncApplication.class, args);
    }


    @Override
    public void run(String... args) {

        String password = "Password1234!";
        String passwordBCrypt = userService.passwordEncoder().encode(password);
        UserDto userDto = new UserDto("admin", passwordBCrypt, "fullName", "ADMIN");
        User user = userService.convertUserDtoToUser(userDto);

        if(userRepository.findUserByUserName(user.getUserName()).isEmpty()){
            userRepository.save(user);
        }

    }


}
