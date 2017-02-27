package com.beisbol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping(value="sup")
public class BeisbolApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeisbolApplication.class, args);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")   //TODO: MAKE THIS CONFIGURABLE OR NOT NECESSARY
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Message sup() {
		return new Message("not much here");
	}

}
