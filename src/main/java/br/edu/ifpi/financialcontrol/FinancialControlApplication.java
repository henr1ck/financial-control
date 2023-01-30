package br.edu.ifpi.financialcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
public class FinancialControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialControlApplication.class, args);
	}

	@PostConstruct
	public void initialConfig(){
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
	}

}
