package com.qa.demo;

import java.time.LocalTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CelinaTdlApplication {

	public static void main(String[] args) {
		SpringApplication.run(CelinaTdlApplication.class, args);
		System.out.println("The time is: "+LocalTime.now());
	}

}
