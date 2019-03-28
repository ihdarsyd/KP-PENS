package com.task.report.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Status {
	
	@Id
	private String date;
	private String status;
	private String medsos;
	private String data_kafka;
	
	public String getDate() {
		return date;
	}
	public void setDate() {
		this.date = new SimpleDateFormat("yyyyMMddHHmm").format(Calendar.
				getInstance().getTime());
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMedsos() {
		return medsos;
	}
	public void setMedsos(String medsos) {
		this.medsos = medsos;
	}
	
	public String getDataKafka() {
		return data_kafka;
	}
	public void setDataKafka(String dataKafka) {
		this.data_kafka= dataKafka;
	}
	public String toString() {
		return("Report Status Engine EWS-Patrol : "+"\\\\\\\\n"+"\\\\\\\\n"+"Date : "+ date 
				+"\\\\\\\\n"+"Status : " + status+"\\\\\\\\n"+ "Medsos : "+ medsos +"\\\\\\\\n"
				+ "Data Kafka : "+ data_kafka);
	}
	public String toStringKafka() {
		return("Report Status Engine EWS-Patrol Kafka : "+"\\\\\\\\n"+"\\\\\\\\n"+"Date : "+ date 
				+"\\\\\\\\n" + "Data Kafka : "+ data_kafka);
	}
	
}
