package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.shared.dto.UserDto;

public interface IUserService extends UserDetailsService
{
	UserDto createUser(UserDto user);

	UserDto getUser(String userName);

	UserDto getUserByUserId(String userId);

	UserDto updateUser(String userId, UserDto userDto);

	void deleteUserBy(String userId);

	List<UserDto> getUsers(int page, int limit);
}
