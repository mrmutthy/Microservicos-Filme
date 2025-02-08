package br.edu.utfpr.api_usuario.controllers;

import br.edu.utfpr.api_usuario.dto.LoginRequest;
import br.edu.utfpr.api_usuario.dto.LoginResponse;
import br.edu.utfpr.api_usuario.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class TokenController {

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JwtEncoder jwtEncoder;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		var claims = JwtClaimsSet.builder()
			.issuer("br.edu.utfpr")
			.subject(loginRequest.email())
			.issuedAt(Instant.now())
			.expiresAt(Instant.now().plusSeconds(300L))
			.build();

		var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

		return ResponseEntity.ok(new LoginResponse(jwtValue));
	}

}
