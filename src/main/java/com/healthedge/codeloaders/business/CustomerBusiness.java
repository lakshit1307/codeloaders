package com.healthedge.codeloaders.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.healthedge.codeloaders.dao.CustomerDao;
import com.healthedge.codeloaders.dto.BaseResponse;

@Component
public class CustomerBusiness {

	@Autowired
	private CustomerDao customerDao;

	public BaseResponse save(String name, String city) {
		return customerDao.save(name, city);
	}

}
