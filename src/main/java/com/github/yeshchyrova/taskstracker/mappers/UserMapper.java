//package com.github.yeshchyrova.taskstracker.mappers;
//import com.github.yeshchyrova.taskstracker.dtos.SignUpDto;
//import com.github.yeshchyrova.taskstracker.dtos.UserDto;
//import com.github.yeshchyrova.taskstracker.model.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring")
//public interface UserMapper {
//
//  UserDto toUserDto(User user);
//
//  @Mapping(target = "password", ignore = true)
//  @Mapping(target = "firstName", ignore = true)
//  @Mapping(target = "lastName", ignore = true)
//  User signUpToUser(SignUpDto signUpDto);
//
//}