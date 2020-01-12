package com.example.demo.shared;

import org.springframework.stereotype.Component;

@Component
public class Utils
{
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public String generateUserId(int length)
	{
		return generateRandomString(length);
	}

	private String generateRandomString(int length)
	{
		StringBuilder builder = new StringBuilder();
		while (length-- != 0)
		{
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
}
