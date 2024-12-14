package br.edu.utfpr.api_avaliacao.service;

import br.edu.utfpr.api_avaliacao.dtos.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="api-usuario", url="localhost:8083")
public interface UsuarioFeignClient {

	@GetMapping("/usuarios/{id}")
	UsuarioDTO getUsuarioById(@PathVariable Long id);

}
