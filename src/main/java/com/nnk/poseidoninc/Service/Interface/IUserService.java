package com.nnk.poseidoninc.Service.Interface;

import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;

import java.util.List;

public interface IUserService {

    List<UserDto> findAll();
    UserDto create(UserDto userDto);
    UserDto findById(int userId);
    UserDto update(UserDto userDto, int userId);
    void delete(int userId);
    UserDto convertUserToUserDto(User user);
    User convertUserDtoToUser(UserDto userDto);

}
