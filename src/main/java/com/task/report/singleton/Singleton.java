package com.task.report.singleton;


import com.task.report.service.HbaseService;
import com.task.report.service.KafkaService;
import com.task.report.service.MonitoringIssueService;
import com.task.report.service.SSHCallCommand;
import com.task.report.service.impl.UserServiceImpl;

public class Singleton {
	private static Singleton instance;
	private KafkaService kafkaService;
	private UserServiceImpl userService;
	private HbaseService hbaseService;
	private MonitoringIssueService monitoringIssueService;
	private SSHCallCommand sshCallCommand;

	
	 private Singleton(){
		 kafkaService = new KafkaService();
		 userService = new UserServiceImpl();
		 hbaseService = new HbaseService();
		 monitoringIssueService = new MonitoringIssueService();
		 sshCallCommand = new SSHCallCommand();
		 
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

	public HbaseService getHbaseService() {
		return hbaseService;
	}

	public MonitoringIssueService getMonitoringIssueService() {
		return monitoringIssueService;
	}

	public SSHCallCommand getSshCallCommand() {
		return sshCallCommand;
	}
	

	
}
