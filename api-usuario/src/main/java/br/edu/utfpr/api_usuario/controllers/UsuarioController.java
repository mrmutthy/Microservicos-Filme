package br.edu.utfpr.api_usuario.controllers;

import br.edu.utfpr.api_usuario.dto.UsuarioDTO;
import br.edu.utfpr.api_usuario.model.Usuario;
import br.edu.utfpr.api_usuario.repositories.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private UsuarioRepository repository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UsuarioController(UsuarioRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.repository = repository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> getAll() {
		return ResponseEntity.ok(this.repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable(name = "id") Long id, JwtAuthenticationToken jwt) {
		String loggedEmail = jwt.getToken().getSubject();

		Usuario usuario = this.repository.findById(id).orElse(null);

		if (usuario == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else if (!usuario.getEmail().equals(loggedEmail)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		} else {
			return ResponseEntity.ok().body(usuario);
		}
	}

	@PostMapping
	public ResponseEntity<String> addOne(@RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario(usuarioDTO.id(), usuarioDTO.nome(), usuarioDTO.email(),
			this.bCryptPasswordEncoder.encode(usuarioDTO.senha())
		);

		this.repository.save(usuario);

		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario adicionado com sucesso!");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateOne(@PathVariable(name = "id") Long id, @RequestBody UsuarioDTO usuarioDTO, JwtAuthenticationToken jwt) {
		Usuario usuario = this.repository.findById(id).orElse(null);

		String loggedEmail = jwt.getToken().getSubject();

		if (usuario != null && usuario.getEmail().equals(loggedEmail)) {
			usuario.setNome(usuarioDTO.nome());
			usuario.setEmail(usuarioDTO.email());

			this.repository.save(usuario);

			return ResponseEntity.status(HttpStatus.OK).body("Usuario alterado com sucesso!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<String> deleteOne(@PathVariable(name = "id") Long id, JwtAuthenticationToken jwt) {
		Usuario usuario = this.repository.findById(id).orElse(null);

		String loggedEmail = jwt.getToken().getSubject();

		if (usuario != null && loggedEmail.equals(usuario.getEmail())) {
			this.repository.delete(usuario);
			return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
		}
	}

}
