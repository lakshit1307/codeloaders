package com.healthedge.codeloaders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.healthedge.codeloaders.dto.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@Controller
public class MyController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/helloworld", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BaseResponse helloWorld() {
		BaseResponse response=new BaseResponse();
		response.setStatus("SUCCESS");
		return response;
	}
}
