package br.edu.utfpr.api_usuario.controllers;

import br.edu.utfpr.api_usuario.dto.LoginRequest;
import br.edu.utfpr.api_usuario.dto.LoginResponse;
import br.edu.utfpr.api_usuario.model.Usuario;
import br.edu.utfpr.api_usuario.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Token", description = "Endpoints para autenticação e geração de token JWT")
public class TokenController {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtEncoder jwtEncoder;

    @PostMapping("/login")
    @Operation(
        summary = "Realiza login e gera token JWT",
        description = "Verifica as credenciais do usuário e, se válidas, retorna um token JWT para acesso seguro à API.",
        requestBody = @RequestBody(
            description = "Dados de login contendo email e senha",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LoginRequest.class)
            )
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "Token JWT gerado com sucesso",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = LoginResponse.class)
        )
    )
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<LoginResponse> login(@org.springframework.web.bind.annotation.RequestBody LoginRequest loginRequest) {
        Usuario usuario = userRepository.findByEmail(loginRequest.email());

        if (usuario == null || !bCryptPasswordEncoder.matches(loginRequest.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("email ou senha invalidos");
        }

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
