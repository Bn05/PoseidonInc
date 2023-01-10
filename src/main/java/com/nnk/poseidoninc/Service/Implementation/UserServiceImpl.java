package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Repository.UserRepository;
import com.nnk.poseidoninc.Service.Interface.IUserService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@DynamicUpdate
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAll() {
        Iterable<User> userIterable = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userIterable) {
            userDtoList.add(convertUserToUserDto(user));
        }

        return userDtoList;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = convertUserDtoToUser(userDto);

        user = userRepository.save(user);

        return convertUserToUserDto(user);
    }

    @Override
    public UserDto findById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            logger.warn("NotFoundUserWithThisId");
            throw new NotFoundException();
        }

        return convertUserToUserDto(userOptional.get());
    }

    @Override
    public UserDto update(UserDto userDto, int userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            logger.warn("NotFoundUserWithThisId");
            throw new NotFoundException();
        }

        User user = userOptional.get();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());

        user = userRepository.save(user);

        return convertUserToUserDto(user);
    }

    @Override
    public void delete(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            logger.warn("NotFoundUserWithThisId");
            throw new NotFoundException();
        }

        userRepository.deleteById(userId);
    }

    @Override
    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setFullName(user.getFullName());
        userDto.setRole(user.getRole());

        return userDto;
    }

    @Override
    public User convertUserDtoToUser(UserDto userDto) {
        User user = new User();

        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());

        return user;
    }
}
