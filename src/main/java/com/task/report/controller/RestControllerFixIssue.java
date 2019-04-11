package com.task.report.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jcraft.jsch.JSchException;
import com.task.report.entity.SshConf;
import com.task.report.singleton.Singleton;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class RestControllerFixIssue  {
	@Autowired
	RestControllerHbase hbase = new RestControllerHbase();
	String tableName = "cyber-data-v4";
	private String result;
	
	@RequestMapping(value = "/hbase/monitoring/fix-issue", method = RequestMethod.GET)
	public ResponseEntity<?> monitoringIssue(@RequestParam(value = "date") String key) 
										throws ParseException, JsonParseException, JsonMappingException, IOException, JSchException{
		 Date date=new SimpleDateFormat("yyyyMMddHHmm").parse(key);
		 Date today = new Date();
		 long difference = timeDifference(date,today);
		 result = execute(difference, key);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	private String execute(long difference, String key) throws JsonParseException, JsonMappingException, IOException {
		SshConf conf = new SshConf("192.168.20.110","root","rahasia2016");
		String statusHbase = hbase.scanRowKey(key, tableName);
		if(statusHbase.equals("tidak ada")) {
			try {
				Singleton.getInstance().getSshCallCommand().executeCommand(conf, command(difference));
				System.out.println( command(difference));
				return "success fix issue";
			}catch (Exception e) {
				return "failed fix issue";
			}
		}
		return "Data Hbase ada";
	}

	private long timeDifference(Date date, Date today) {
		 long mili = Math.abs(today.getTime() - date.getTime());
		 long minute = mili / (60 * 1000) % 60;
	     long hour = mili / (60 * 60 * 1000) % 24;
	     long day = mili / (24 * 60 * 60 * 1000);
	     long minutes = (long) Math.floor(minute/30);
	     long total = (((((day*24)+hour)*60)/30))+minutes;
	     System.out.println("====================================================="+total);
	     return total;
	}

	private String command(long time) {
		long param = time*(-30);
		return "spark-submit --class com.ebdesk.polri.v5.main.BatchIssueV5 --master local[3] "
				+ "--name BATCH-CYBER-PATROL-V5 --driver-memory 12G "
				+ "/home/apps/cyber-deploy-bintaro/cyber-patrol-v5.jar "
				+ param
				+ " tw 1";
	}
}