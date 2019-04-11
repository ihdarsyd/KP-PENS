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
import com.task.report.entity.Hbase;
import com.task.report.entity.SshConf;
import com.task.report.service.MonitoringIssueService;
import com.task.report.singleton.Singleton;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class RestControllerMonitoringIssue {
	@Autowired
	RestControllerHbase hbase = new RestControllerHbase();
	
	
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
		Singleton.getInstance().getMonitoringIssueService().deleteAll();
		while (i++!=48) {
			cal.add(Calendar.MINUTE, 30);
			String time = key + df.format(cal.getTime());
			Singleton.getInstance().getMonitoringIssueService().insert(time, "0");
		}
		treeMap = new TreeMap<String, String>(Singleton.getInstance().getMonitoringIssueService().getAll());

		return new ResponseEntity<List<Hbase>>(checkMap(treeMap, tableName), HttpStatus.OK);
	}
	
	
	private List<Hbase> checkMap(Map<String,String> treeMap, String tableName) throws JsonParseException, JsonMappingException, IOException{
		String newValue;
		List<Hbase> status = new ArrayList<>();
		status.removeAll(status);
		for (Entry<String, String> entry : treeMap.entrySet()) {
			Hbase statusHbase = new Hbase();
			newValue = hbase.scanRowKey(entry.getKey(), tableName);
			entry.setValue(newValue);
			statusHbase.setDate(entry.getKey());
			statusHbase.setStatus(newValue);
			status.add(statusHbase);
		}

		return status;
	}
	
}