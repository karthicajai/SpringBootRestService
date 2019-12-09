package com.example.demo.ui.controller;

import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ui.model.request.UpdateUserRequestModel;
import com.example.demo.ui.model.request.UserRequestModel;
import com.example.demo.ui.model.response.UserResponseModel;
import com.example.demo.userservice.IUserService;

@RestController
@RequestMapping("users") //http://localhost:8080/users
public class UserController {
	
	private Map<String, UserResponseModel> users ;
	
	//Autowired: this annotation will automatically create the instance of UserServiceImpl class and inject it into this call
	@Autowired
	IUserService userService;
	

	/*
	 * to get all users
	 * this method will be called from PostMan as GetRequest to url : http://localhost:8080/users
	 * 

	@GetMapping() //http://localhost:8080/users
	public String getAllUser()
	{
		return "Return all users from DB";
	}
	*/
	
	/*
	 * Example method for passing "Query string request params"
	 * In this example "page" and "limit" are request parameters passed in request
	 * 
	 * 	defaultValue: Makes that request param optional and assign a default value to it if not sent in Url
	 *  required: Makes that request param optional without any default value assigned to that param.
	 */
	@GetMapping() //http://localhost:8080/users?page=1&limit=30
	public String getUsers( @RequestParam(value="page", defaultValue="1") int page , 
							@RequestParam(value="limit") int limit,
							@RequestParam(value="sort", required=false) String sort)
	{
		return "get users method was called with page = "+ page + " and limit = "+ limit + "and sort = "+sort;
	}
	
	/*
	 * http://localhost:8080/users/1
	 * 
	 * Returns: JSON/XML
	 * 		By default return the value as JSON if no produces mentioned in mapping annotation
	 * 		But if you want to return value as specific format like JSON / XML: use Media
	 * 		if you want to use XML type , u have add dependency for jackson xml jars in pom.xml
	 */
	@GetMapping(
				path="/{userId}", 
				produces = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
						}
				)
	public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId)
	{
		//sample code to produce own custom exception "UserServiceException"
//		if(true)
//			throw new UserServiceException("A user service exception is thrown");
		
		if(users.containsKey(userId)) {
			return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	/*
	 *  http://localhost:8080/users/1
	 * 
	 * Params: UserRequestModel 
	 * 		Request params accepts only json type value and create UserRequestModel from that json
	 * 
	 * Returns: JSON/XML
	 * 		By default return the value as JSON if no produces mentioned in mapping annotation
	 * 		But if you want to return value as specific format like JSON / XML: use Media
	 * 		if you want to use XML type , u have add dependency for jackson xml jars in pom.xml
	 * 
	 * @RequestBody:
	 * 		Accept the json request which has parameters in UserRequestModel
	 * @Valid:
	 * 		Will kick in the validation inside our UserRequestModel object
	 * 
	 */
	@PostMapping(
				 consumes = {
						 	MediaType.APPLICATION_JSON_VALUE,
						 	MediaType.APPLICATION_XML_VALUE
						 	}, 
				 produces = {
						 	 MediaType.APPLICATION_JSON_VALUE,
						 	 MediaType.APPLICATION_XML_VALUE
						 	 }
				 )
	public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel userDetails)
	{
		UserResponseModel returnValue = userService.createUser(userDetails);
		
		return new ResponseEntity<UserResponseModel>(returnValue, HttpStatus.OK);
	}
	
	/*
	 * http://localhost:8080/users/3585c9cb-ab60-4320-91e8-99a723889133
	 * http://localhost:8080/users/{id}
	 * 
	 * Update user details:
	 * 
	 */
	@PutMapping(
			 path="/{userId}", 
			 consumes = {
					 	MediaType.APPLICATION_JSON_VALUE,
					 	MediaType.APPLICATION_XML_VALUE
					 	}, 
			 produces = {
					 	 MediaType.APPLICATION_JSON_VALUE,
					 	 MediaType.APPLICATION_XML_VALUE
					 	 }
			 )
	public UserResponseModel updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequestModel userDetails)
	{
		UserResponseModel storedUserDetails = users.get(userId);
		storedUserDetails.setFirstName(userDetails.getFirstName());
		storedUserDetails.setLastName(userDetails.getLastName());
		
		users.put(userId, storedUserDetails);
		
		return storedUserDetails;
	}
	
	/*
	 * http://localhost:8080/users/{id}
	 * ex: http://localhost:8080/users/3585c9cb-ab60-4320-91e8-99a723889133
	 * 
	 * delete user details:
	 * 
	 */
	@DeleteMapping(path="/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String userId)
	{
		users.remove(userId);
		
		return ResponseEntity.noContent().build();
	}
}
