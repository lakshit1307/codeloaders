package com.healthedge.codeloaders.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseResponse.class);

	private String status;
	private String message;

	public BaseResponse () {
		LOGGER.info("BaseResponse class initialized");
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

}
