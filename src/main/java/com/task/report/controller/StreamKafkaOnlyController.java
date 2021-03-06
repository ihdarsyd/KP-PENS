package com.task.report.controller;

import java.util.HashMap;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.task.report.entity.Status;
import com.task.report.singleton.Singleton;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StreamKafkaOnlyController {
	Status status = new Status();
	String url;
	//----------------------------send-kafka-status-----------------------------------------------
	 @RequestMapping(value = "/send-report/", method = RequestMethod.GET)
	 @Scheduled(cron = "0 01 * * * *")
	 @Scheduled(cron = "0 31 * * * *")
	 public ResponseEntity<?> SendStatusKafka() {
		Map<String, String> requestParams = new HashMap<>();
		status.setDate();
		status.setDataKafka(Singleton.getInstance().getKafkaService().kafkaConsumer("tw-post-v3"));
	    requestParams.put("msg", status.toStringKafka());
	    requestParams.put("to", "Monitoring-Issue-EWS");
	    RestTemplate restTemplate = new RestTemplate();
	    url = restTemplate.getForObject("http://192.168.20.110:6969/send?msg={msg}&to={to}", 
	    								String.class, requestParams);
	    
	    return new ResponseEntity<Status>(status, HttpStatus.OK);
	 }
}
