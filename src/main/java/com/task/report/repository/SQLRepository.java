package com.task.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.task.report.entity.Status;


@RepositoryRestResource(path = "/coba")
@CrossOrigin(origins = "http://localhost:4200")
public interface SQLRepository extends JpaRepository<Status,String> {
	
}