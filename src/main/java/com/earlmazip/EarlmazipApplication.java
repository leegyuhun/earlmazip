package com.earlmazip;

import com.earlmazip.utils.HttpSessionCheckingListner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
public class EarlmazipApplication {

	public static void main(String[] args) {
		SpringApplication.run(EarlmazipApplication.class, args);
	}

	@Bean
	public HttpSessionListener httpSessionListener() {
		return new HttpSessionCheckingListner();
	}
}
