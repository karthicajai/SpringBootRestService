package com.example.demo.ui.model.response;

import java.util.Date;

public class ErrorMessage {
	private Date timeStamp;
	private String message;

	public ErrorMessage(Date date, String message)
	{
		this.timeStamp = date;
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
