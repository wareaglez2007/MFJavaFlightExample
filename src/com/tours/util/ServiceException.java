package com.tours.util;

import java.io.Serializable;

public class ServiceException extends RuntimeException
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public ServiceException()
	{
		super("Unknown Service Error");
	}

	public ServiceException(String message)
	{
		super(message);
	}

	public ServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ServiceException(Throwable cause)
	{
		super(cause);
	}
}
