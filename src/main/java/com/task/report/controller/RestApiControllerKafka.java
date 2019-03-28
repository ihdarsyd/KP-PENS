package com.task.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.task.report.entity.Data;
import com.task.report.entity.Status;
import com.task.report.repository.SQLRepository;
import com.task.report.singleton.Singleton;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RestApiControllerKafka {
	Status status 					= new Status();
	public String url;
	@Autowired
	private SQLRepository sqlService;
    
 // -------------------Send Report--------------------------------------------- 
	@RequestMapping(value = "/send/", method = RequestMethod.GET)
	@Scheduled(cron = "0 01 * * * *")
	@Scheduled(cron = "0 31 * * * *")
    public ResponseEntity<?> checkData() {
    	 List<Data> data = Singleton.getInstance().getUserService().findAllData();
    	 status.setDate();
    	 status.setDataKafka(Singleton.getInstance().getKafkaService().kafkaConsumer("tw-post-v3"));
    	 try {
    		 if(data.isEmpty()) {
    		 }
    		 status.setStatus("not null");
    		 status.setMedsos(data.get(0).getMedsos());
    		 
    	 }catch(NullPointerException e) {
    		Map<String, String> requestParams = new HashMap<>();
    		status.setStatus("null");
    		status.setMedsos("TW");
    		
 	    	requestParams.put("msg", status.toString());
 	    	requestParams.put("to", "Monitoring-Issue-EWS");
 	    	RestTemplate restTemplate = new RestTemplate();
 	    	url = restTemplate.getForObject("http://192.168.20.110:6969/send?msg={msg}&to={to}", 
 	    											String.class, requestParams);
    	 }
    	 sqlService.save(status);
    	 return new ResponseEntity<Status>(status, HttpStatus.OK);
    }
    
    
    
}
