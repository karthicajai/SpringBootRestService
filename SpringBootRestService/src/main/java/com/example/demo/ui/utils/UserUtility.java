package com.example.demo.ui.utils;

import java.util.UUID;

import org.springframework.stereotype.Service;

/*
 * This class will have common utility methods
 */

@Service
public class UserUtility {
	
	public String generateUserId()
	{
		return UUID.randomUUID().toString();
	}

}
