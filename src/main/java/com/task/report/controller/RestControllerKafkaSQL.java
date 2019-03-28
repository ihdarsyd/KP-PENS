package com.task.report.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.report.entity.Status;
import com.task.report.repository.SQLRepository;

@RestController
@RequestMapping(path = "/sql")
@CrossOrigin(origins = "http://localhost:4200")
public class RestControllerKafkaSQL {

    @Autowired
	private SQLRepository sqlService;
    
    @GetMapping
    public Iterable<Status> findAll() {
        return sqlService.findAll();
    }

    @GetMapping(path = "/{date}")
    public Optional<Status> find(@PathVariable("date") String date) {
        return sqlService.findById(date);
    }

    @PostMapping
    public Status create(@RequestBody Status status) {
        return sqlService.save(status);
    }

    @DeleteMapping(path = "/{date}")
    public void delete(@PathVariable("date") String date) {
    	sqlService.deleteById(date);
    }
    
    
    @GetMapping(path = "/date/{date}")
    public  Iterable<Status>  findByDate(@PathVariable("date") String date) {
         try {
        	 List<Status> s = new ArrayList<Status>();
             for (Status status : sqlService.findAll()) {
            	 if(status.getDate().contains(date)) {
            		 s.add(status);
            	 }else {
            	sqlService.deleteById(status.getDate());
            	}
    		}
        	 return s;
         }catch (NullPointerException e) {
        	 return null;
		}
		
    }

}
