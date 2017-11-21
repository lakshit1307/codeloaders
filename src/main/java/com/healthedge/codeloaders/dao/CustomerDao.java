package com.healthedge.codeloaders.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthedge.codeloaders.dto.BaseResponse;
import com.healthedge.codeloaders.entity.Customer;
import com.healthedge.codeloaders.repository.CustomerRepository;

@Service
public class CustomerDao {

	@Autowired
	private CustomerRepository customerRepository;
	
	public BaseResponse save(String name, String city) {
		BaseResponse response=new BaseResponse();
		try {
			Customer customer=new Customer();
//			customer.setId(1212121);
			customer.setCustomerName(name);
			customer.setCity(city);
			customerRepository.save(customer);
			response.setStatus("SUCCESS");
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus("FAILURE");
		}
		return response;
	}
	
	
}
