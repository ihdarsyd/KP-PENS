package com.task.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages={"com.task.report"},exclude = {JmxAutoConfiguration.class})
public class ReportStatusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportStatusApplication.class, args);
	}

}

