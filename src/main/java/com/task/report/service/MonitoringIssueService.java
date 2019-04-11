package com.task.report.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

@Service
public class MonitoringIssueService {
	Map<String,String> map = new HashMap<String,String>();
 
	public void insert(String key, String value) {
		map.put(key, value);
	}
 
	public Map<String, String> getAll(){
		try {
			return map;
		}catch(NullPointerException e) {
			return null;
		}
	}
 
	public Entry<String, String> getByKey(String key){
		for (Entry<String,String> entry : map.entrySet()) {
			if(entry.getKey().equals(key)) {
				return entry;
			}
		}
		return null;
	}
	
	public void deleteAll(){
		map.clear();
	}
}
