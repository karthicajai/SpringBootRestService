package com.example.demo.userservice;

import com.example.demo.ui.model.request.UserRequestModel;
import com.example.demo.ui.model.response.UserResponseModel;

public interface IUserService {
	UserResponseModel createUser(UserRequestModel userDetails);
}
