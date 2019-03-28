package com.task.report.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
//import com.task.kp.repository.SQLRepository;
import com.task.report.entity.Hbase;
import com.task.report.service.MonitoringIssueService;
import com.task.report.singleton.Singleton;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class RestControllerMonitoringIssue {
	@Autowired
//	private SQLRepository sqlService;
	RestControllerHbase hbase = new RestControllerHbase();
	List<Hbase> status = new ArrayList<>();
	
	@RequestMapping(value = "/hbase/monitoring/issue1", method = RequestMethod.GET)
	public ResponseEntity<?> monitoringIssue(@RequestParam(value = "date") String key,
										@RequestParam(value = "tableName") String tableName) 
										throws ParseException, JsonParseException, JsonMappingException, IOException, JSchException{
		Map<String, String> treeMap;
		
		DateFormat df = new SimpleDateFormat("HHmm");
		Calendar cal = Calendar.getInstance();
		int i=0;
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		while (i++!=48) {
			cal.add(Calendar.MINUTE, 30);
			String time = key + df.format(cal.getTime());
			Singleton.getInstance().getMonitoringIssueService();
			MonitoringIssueService.insert(time, "0");
		}
		treeMap = new TreeMap<String, String>(Singleton.getInstance().getMonitoringIssueService().getAll());
		treeMap = checkMap(treeMap, tableName);
		return new ResponseEntity<List<Hbase>>(status, HttpStatus.OK);
	}
	
	
	
	private Map<String,String> checkMap(Map<String,String> treeMap, String tableName) throws JsonParseException, JsonMappingException, IOException{
		String newValue;
		Hbase statusHbase = new Hbase();
		
		for (Entry<String, String> entry : treeMap.entrySet()) {
			newValue = hbase.scanRowKey(entry.getKey(), tableName);
			entry.setValue(newValue);
			statusHbase.setDate(entry.getKey());
			statusHbase.setStatus(newValue);
			status.add(statusHbase);
//			sqlService.save(statusHbase);
		}
//		for(Hbase sh: status) {
//			System.out.println(sh.getDate()+sh.getStatus());
//		}
		return treeMap;
		
	}
}