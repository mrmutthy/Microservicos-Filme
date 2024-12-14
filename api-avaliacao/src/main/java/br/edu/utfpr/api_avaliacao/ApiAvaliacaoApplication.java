package br.edu.utfpr.api_avaliacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiAvaliacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAvaliacaoApplication.class, args);
	}

}
