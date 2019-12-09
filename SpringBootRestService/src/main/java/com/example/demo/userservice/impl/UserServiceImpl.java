package com.example.demo.userservice.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.ui.model.request.UserRequestModel;
import com.example.demo.ui.model.response.UserResponseModel;
import com.example.demo.ui.utils.UserUtility;
import com.example.demo.userservice.IUserService;

/*
 * This class will have business logic
 */
@Service
public class UserServiceImpl implements IUserService{
	private Map<String, UserResponseModel> users;
	UserUtility userUtility;
	
	//here Autowired annotation will automatically instantiate the UserUtility.class and inject it to this constructor
	@Autowired
	public UserServiceImpl(UserUtility userUtility)
	{
		this.userUtility = userUtility;
	}
	
	@Override
	public UserResponseModel createUser(UserRequestModel userDetails)
	{
		UserResponseModel returnValue = new UserResponseModel();
		returnValue.setFirstName( userDetails.getFirstName() );
		returnValue.setLastName( userDetails.getLastName() );
		returnValue.setEmail( userDetails.getEmail() );
		returnValue.setPassword( userDetails.getPassword() );
		
		//Generate random id
		String userId = userUtility.generateUserId();
		returnValue.setUserId(userId);
		
		if(users == null) 
			users = new HashMap<String, UserResponseModel>();
		
		users.put(userId, returnValue);
		
		return returnValue;
	}

}
