package com.task.report.controller;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.report.entity.Tweet;
import com.task.report.singleton.Singleton;


@RestController
@CrossOrigin(origins="http://localhost:4200")
public class RestControllerHbase {
	@RequestMapping(value = "/hbase/delete/{tableName}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTable(@PathVariable("tableName") String tableName) throws Exception {
		Singleton.getInstance().getHbaseService().deleteTable(tableName);
		return new ResponseEntity<Tweet>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/hbase/put", method = RequestMethod.GET)
	public ResponseEntity<?> putRow(@RequestParam(value = "rowKey") String rowKey, @RequestParam(value = "column") String column,
	                     @RequestParam(value = "value") String value,  @RequestParam(value = "tableName") String tableName) {
		Singleton.getInstance().getHbaseService().putRowValue(tableName, rowKey, "info", column, value);
		return new ResponseEntity<Tweet>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/hbase/scan", method = RequestMethod.GET)
	public ResponseEntity<?> scanRegexRowKey(@RequestParam("regexKey") String regexKey, @RequestParam("table") String tableName) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Tweet tw = new Tweet();
		List<Cell> result = Singleton.getInstance().getHbaseService().
							scanRegexRowKey(tableName, regexKey);
		System.out.println(regexKey);
		if (null==result) {
			System.out.println("result is null");
		}
		else {
			for (Cell cell : result) {
			String x = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
			tw = objectMapper.readValue(x, Tweet.class);
			}
		}
		 return new ResponseEntity<Tweet>(tw, HttpStatus.OK);
	}
	
	public String scanRowKey(String regexKey, String tableName) throws JsonParseException, JsonMappingException, IOException{
		List<Cell> result = Singleton.getInstance().getHbaseService().
							scanRegexRowKey(tableName, regexKey);
		if (null==result) {
			//System.out.println("result is null");
			return "tidak ada";
		}
		else {
			for (Cell cell : result) {
			Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
			}
			return "ada";
		}
	}

	@RequestMapping(value = "/hbase/delete/allColumn/{rowKey}", method = RequestMethod.GET)
	public String deleteAllColumn(@PathVariable("rowKey") String rowKey, @RequestParam(value = "tableName") String tableName) {

		Singleton.getInstance().getHbaseService().deleteAllColumn(tableName, rowKey);
		return "delete all column  success";
	}

	@RequestMapping(value = "/hbase/delete/column", method = RequestMethod.GET)
	public String deleteColumn(@RequestParam(value = "rowKey") String rowKey, @RequestParam(value = "column") String column, @RequestParam(value = "tableName") String tableName) {
		Singleton.getInstance().getHbaseService().deleteColumn(tableName, rowKey, "info", column);
		return "delete  column success";
}
   
    
    
    
}
