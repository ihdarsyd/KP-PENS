package com.task.report.controller;

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

import com.task.report.entity.Account;
import com.task.report.repository.AccountRepository;

@RestController
@RequestMapping(path = "/account")
@CrossOrigin(origins = "http://localhost:4200")
public class RestControllerAccount {

    @Autowired
	private AccountRepository sqlService;
    
    @GetMapping
    public Iterable<Account> findAll() {
        return sqlService.findAll();
    }

    @GetMapping(path = "/{date}")
    public Optional<Account> find(@PathVariable("date") String date) {
        return sqlService.findById(date);
    }

    @PostMapping
    public Account create(@RequestBody Account account) {
        return sqlService.save(account);
    }

    @DeleteMapping(path = "/{date}")
    public void delete(@PathVariable("date") String date) {
    	sqlService.deleteById(date);
    }


}
