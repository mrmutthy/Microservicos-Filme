package br.edu.utfpr.api_recomendacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiRecomendacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRecomendacaoApplication.class, args);
	}

}
