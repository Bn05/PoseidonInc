package com.nnk.poseidoninc.UT.Service;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Repository.UserRepository;
import com.nnk.poseidoninc.Service.Implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepositoryMock;

    User user1 = new User();
    User user2 = new User();
    User userUpdate = new User();
    UserDto userDto1 = new UserDto();
    UserDto userDto2 = new UserDto();
    UserDto userDtoUpdate = new UserDto();

    Optional<User> userOptional0 = Optional.empty();
    Optional<User> userOptional1;
    Optional<User> userOptional2;
    List<User> userIterable = new ArrayList<>();
    List<UserDto> userDtoList = new ArrayList<>();

    @BeforeAll
    void buildTest() {

        user1.setUserId(1);
        user1.setEmail("email@test1.com");
        user1.setPassword("passwordTest1");
        user1.setFullName("fullnameTest1");
        user1.setRole("roleTest1");
        userOptional1 = Optional.of(user1);

        user2.setUserId(2);
        user2.setEmail("email@test2.com");
        user2.setPassword("passwordTest2");
        user2.setFullName("fullnameTest2");
        user2.setRole("roleTest2");
        userOptional2 = Optional.of(user2);

        userIterable.add(user1);
        userIterable.add(user2);


        userDto1.setUserId(1);
        userDto1.setEmail("email@test1.com");
        userDto1.setPassword("passwordTest1");
        userDto1.setFullName("fullnameTest1");
        userDto1.setRole("roleTest1");


        userDto2.setUserId(2);
        userDto2.setEmail("email@test2.com");
        userDto2.setPassword("passwordTest2");
        userDto2.setFullName("fullnameTest2");
        userDto2.setRole("roleTest2");

        userDtoList.add(userDto1);
        userDtoList.add(userDto2);


        userUpdate.setUserId(1);
        userUpdate.setEmail("email@testUpdate.com");
        userUpdate.setPassword("passwordTestUpdate");
        userUpdate.setFullName("fullnameTestUpdate");
        userUpdate.setRole("roleTestUpdate");

        userDtoUpdate.setUserId(1);
        userDtoUpdate.setEmail("email@testUpdate.com");
        userDtoUpdate.setPassword("passwordTestUpdate");
        userDtoUpdate.setFullName("fullnameTestUpdate");
        userDtoUpdate.setRole("roleTestUpdate");
    }

    @Test
    void findAll() {
        when(userRepositoryMock.findAll()).thenReturn(userIterable);

        List<UserDto> userDtoListResult = userService.findAll();

        verify(userRepositoryMock, times(1)).findAll();
        assertEquals(userDtoList, userDtoListResult);
    }

    @Test
    void create() {
        when(userRepositoryMock.save(any())).thenReturn(user1);

        UserDto userDtoResult = userService.create(userDto1);

        verify(userRepositoryMock, times(1)).save(any());
        assertEquals(userDto1, userDtoResult);
    }

    @Test
    void findByIdExist() {
        when(userRepositoryMock.findById(1)).thenReturn(userOptional1);

        UserDto userDtoResult = userService.findById(1);

        verify(userRepositoryMock, times(1)).findById(1);
        assertEquals(userDto1, userDtoResult);
    }

    @Test
    void findByIdNoExist() {
        when(userRepositoryMock.findById(1)).thenReturn(userOptional0);
        boolean errorTest = true;

        try {
            UserDto userDtoResult = userService.findById(1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(userRepositoryMock, times(1)).findById(1);
        assertFalse(errorTest);
    }

    @Test
    void updateExist() {
        when(userRepositoryMock.findById(1)).thenReturn(userOptional1);
        when(userRepositoryMock.save(any())).thenReturn(user1);

        UserDto userDtoResult = userService.update(userDto1, 1);

        verify(userRepositoryMock, times(1)).findById(1);
        verify(userRepositoryMock, times(1)).save(any());
        assertEquals(userDto1, userDtoResult);
    }

    @Test
    void updateNoExist() {
        when(userRepositoryMock.findById(1)).thenReturn(userOptional0);
        boolean errorTest = true;

        try {
            UserDto userDtoResult = userService.update(userDto1, 1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(userRepositoryMock, times(1)).findById(1);
        verify(userRepositoryMock, times(0)).save(any());
        assertFalse(errorTest);
    }

    @Test
    void deleteExist() {
        when(userRepositoryMock.findById(1)).thenReturn(userOptional1);
        doNothing().when(userRepositoryMock).deleteById(1);

        userService.delete(1);

        verify(userRepositoryMock, times(1)).findById(1);
        verify(userRepositoryMock, times(1)).deleteById(1);
    }

    @Test
    void deleteNoExist() {
        when(userRepositoryMock.findById(1)).thenReturn(userOptional0);
        boolean errorTest = true;

        try {
            userService.delete(1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(userRepositoryMock, times(1)).findById(1);
        verify(userRepositoryMock, times(0)).deleteById(1);
        assertFalse(errorTest);
    }
}