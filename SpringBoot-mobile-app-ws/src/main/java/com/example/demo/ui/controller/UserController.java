package com.example.demo.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.UserServiceException;
import com.example.demo.service.IUserService;
import com.example.demo.shared.dto.UserDto;
import com.example.demo.ui.model.request.UserDetailsRequestModel;
import com.example.demo.ui.model.response.ErrorMessages;
import com.example.demo.ui.model.response.OperationStatusModel;
import com.example.demo.ui.model.response.UserResponseModel;

@RestController
@RequestMapping("users")// http://localhost:8080/users
public class UserController {

	@Autowired
	IUserService userService;
	
	@GetMapping(
			path="/{id}",
			produces = { MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE}
			)
	public UserResponseModel getUser(@PathVariable String id)
	{
		UserResponseModel returnValue = new UserResponseModel();
		
		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);
		
		return returnValue;
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE}
			)
	public UserResponseModel createUser(@RequestBody UserDetailsRequestModel userDetails) throws UserServiceException
	{
		//example to show custom error message
		if(userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
			
		UserResponseModel returnValue = new UserResponseModel();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
	}
	
	@PutMapping(
			path="/{id}",
			consumes = { MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE}
			)
	public UserResponseModel updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails)
	{
		//example to show custom error message
		if(userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
			
		UserResponseModel returnValue = new UserResponseModel();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		
		return returnValue;
	}
	
	@DeleteMapping(path="/{id}",
			produces = { MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE})
	public OperationStatusModel deleteUser(@PathVariable String userId)
	{
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName("DELETE");//TODO: load DELETE value from constants
		
		userService.deleteUserBy(userId);

		returnValue.setOperationResult("SUCCESS");//TODO: load SUCCESS value from constants
		
		return returnValue;
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE})
	public List<UserResponseModel> getUsers(@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="limit", defaultValue="10") int limit)
	{
		List<UserResponseModel> returnValue = new ArrayList<UserResponseModel>();
		
		List<UserDto> users = userService.getUsers(page, limit);
		
		for(UserDto user : users)
		{
			UserResponseModel userResponseModel = new UserResponseModel();
			BeanUtils.copyProperties(user, userResponseModel);
			returnValue.add(userResponseModel);
		}
		
		
		
		return returnValue;
	}
}
