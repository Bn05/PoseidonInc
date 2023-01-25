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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class PoseidonIncApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoseidonIncApplication.class, args);
    }


}
