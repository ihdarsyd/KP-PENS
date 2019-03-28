package com.task.report.singleton;


import com.task.report.service.KafkaService;
import com.task.report.service.impl.UserServiceImpl;

public class Singleton {
	private static Singleton instance;
	private KafkaService kafkaService;
	private UserServiceImpl userService;

	
	 private Singleton(){
		 kafkaService = new KafkaService();
		 userService = new UserServiceImpl();
	 }

	public static Singleton getInstance(){
		 if(instance == null){
	            instance = new Singleton();
	        }
	        return instance;
	 }

	public KafkaService getKafkaService() {
		return kafkaService;
	}

	public UserServiceImpl getUserService() {
		return userService;
	}

	
}
