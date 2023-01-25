package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.AlreadyExistException;
import com.nnk.poseidoninc.Exception.BadParamException;
import com.nnk.poseidoninc.Exception.NotFoundException;

import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Repository.UserRepository;
import com.nnk.poseidoninc.Security.ValidPassword.PasswordConstraintValidator;
import com.nnk.poseidoninc.Service.Interface.IUserService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return List<UserDto>
     *     return all User in db
     */
    @Override
    public List<UserDto> findAll() {
        Iterable<User> userIterable = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userIterable) {
            userDtoList.add(convertUserToUserDto(user));
        }

        return userDtoList;
    }

    /**
     * @param userDto we want to add to db
     * @return User added
     * Create new User in db
     */
    @Override
    public UserDto create(UserDto userDto) {
        Optional<User> userOptional = userRepository.findUserByUserName(userDto.getUserName());

        if (userOptional.isPresent()) {
            throw new AlreadyExistException();
        }

        User user = convertUserDtoToUser(userDto);

        //Crypt password
        String password = user.getPassword();
        String passwordBcrypt = passwordEncoder().encode(password);

        user.setPassword(passwordBcrypt);
        user.setRole("USER");


        user = userRepository.save(user);

        return convertUserToUserDto(user);
    }

    /**
     * @param userId of User we are looking for
     * @return User
     * Find User By Id
     */
    @Override
    public UserDto findById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            logger.warn("NotFoundUserWithThisId");
            throw new NotFoundException();
        }

        return convertUserToUserDto(userOptional.get());
    }

    /**
     * @param userDto with new param
     * @param userId of User we want update
     * @return User with modif
     */
    @Override
    public UserDto update(UserDto userDto, int userId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userSecurity = getCurrentUser(authentication);
        String role = userSecurity.getRole();

        //only ADMIN can update another user.
        if (role.equals("USER")) {
            if (userSecurity.getUserId() != userId) {
                throw new BadParamException();
            }
        }

        //verify User exist
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            logger.warn("NotFoundUserWithThisId");
            throw new NotFoundException();
        }
        User user = userOptional.get();


        user.setUserName(userDto.getUserName());
        user.setFullName(userDto.getFullName());

        //If new password -> encrypt
        //If NO new password, keep the old password
        String password = userDto.getPassword();
        if (!Objects.equals(password, null) && !password.equals("")) {

            String passwordBcrypt = passwordEncoder().encode(password);
            user.setPassword(passwordBcrypt);
        }

        //Only ADMIN can change role
        String roleUpdate = userDto.getRole();
        if (role.equals("ADMIN") && !Objects.equals(roleUpdate, null)) {
            user.setRole(roleUpdate);
        }


        user = userRepository.save(user);

        return convertUserToUserDto(user);
    }

    /**
     * @param userId of User we want delete
     */
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
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setFullName(user.getFullName());
        userDto.setRole(user.getRole());

        return userDto;
    }

    @Override
    public User convertUserDtoToUser(UserDto userDto) {
        User user = new User();

        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());

        return user;
    }

    @Override
    public UserDto getCurrentUser(Authentication authentication) {
        String userName = authentication.getName();
        Optional<User> userOptional = userRepository.findUserByUserName(userName);

        if (userOptional.isEmpty()) {
            throw new NotFoundException();
        }

        User user = userOptional.get();

        return convertUserToUserDto(user);
    }


}

