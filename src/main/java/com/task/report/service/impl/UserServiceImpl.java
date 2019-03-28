package com.task.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.task.report.entity.Data;
import com.task.report.entity.Parent;
import com.task.report.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService{
	
	private static Parent parent;
	private static List<Data> data = new ArrayList<Data>();
	
	static{
    parent = new Parent();
    parent = populateDummyUsers();
    data = parent.getData();
	}
	

	private static Parent populateDummyUsers() {
		try {
			Client client = Client.create();

			WebResource webResource = client
					.resource("http://103.75.25.60:4321/new-cyber-patrol-api/get-list-issue");
			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String output = response.getEntity(String.class);
			ObjectMapper mapper = new ObjectMapper();
			parent = mapper.readValue(output, Parent.class);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return parent;
	}


	@Override
	public List<Data> findAllData() {
		try {
			return data;
		} catch(NullPointerException e) {
			return null;
		}
	}

	public Parent findAllParent() {
		return parent;
	}
}
