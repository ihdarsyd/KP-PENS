package com.task.report.service;

import java.util.Properties;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import java.time.Duration;



public class KafkaService{
	String topicName;
	Properties props 					= new Properties();
	private KafkaConsumer<String, String> consumer;
	
	public KafkaService(){
		
	}
	
	public void setTopic(String topicName){
		this.topicName = topicName;
		
		props.put("bootstrap.servers", "master001.cluster02.bt:6667,namenode005.cluster02.bt:6667");
		props.put("group.id", "cobai2");
		props.put("enable.auto.commit", "true");
	    props.put("auto.commit.interval.ms", "1000");
	    props.put("session.timeout.ms", "30000");
	    props.put("key.deserializer", 
	    		  "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("value.deserializer", 
	    		  "org.apache.kafka.common.serialization.StringDeserializer");
	}
	
	public String kafkaConsumer(String topicName){
		setTopic(topicName);
		consumer = new KafkaConsumer<String, String>(props);
	    consumer.subscribe(Arrays.asList(topicName));
	    int i= 0;
	      while(true) {
	    	  ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(60));
	        if(!records.isEmpty()) {
	        	for (Iterator<ConsumerRecord<String, String>> iterator = records.iterator(); 
	        		iterator.hasNext();) {
		        		if(i>=100)
		        			break;
		        		i++;
				}
		        return "ada";
	        }
	        else {
	  	      	return "tidak ada";
	        }
	      }
	}
}
