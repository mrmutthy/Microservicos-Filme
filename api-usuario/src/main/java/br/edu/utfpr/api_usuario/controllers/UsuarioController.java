package br.edu.utfpr.api_usuario.controllers;

import br.edu.utfpr.api_usuario.dto.UsuarioDTO;
import br.edu.utfpr.api_usuario.model.Usuario;
import br.edu.utfpr.api_usuario.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {

    private UsuarioRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioController(UsuarioRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    @Operation(
        summary = "Listar todos os usuários",
        description = "Retorna todos os usuários cadastrados."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuários listados com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))
    )
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar usuário por ID",
        description = "Retorna o usuário com base no ID, caso o email do token corresponda ao email do usuário."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuário encontrado",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))
    )
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<Usuario> getById(
            @Parameter(description = "ID do usuário", required = true) @PathVariable(name = "id") Long id,
            @Parameter(hidden = true) JwtAuthenticationToken jwt) {
        String loggedEmail = jwt.getToken().getSubject();

        Usuario usuario = this.repository.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else if (!usuario.getEmail().equals(loggedEmail)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            return ResponseEntity.ok(usuario);
        }
    }

    @PostMapping
    @Operation(
        summary = "Criar usuário",
        description = "Cria um novo usuário com os dados fornecidos. A senha é criptografada antes de ser salva."
    )
    @ApiResponse(
        responseCode = "201",
        description = "Usuário criado com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    public ResponseEntity<String> addOne(
            @Parameter(description = "Dados do usuário a ser criado", required = true) @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(
                usuarioDTO.id(),
                usuarioDTO.nome(),
                usuarioDTO.email(),
                this.bCryptPasswordEncoder.encode(usuarioDTO.senha())
        );

        this.repository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario adicionado com sucesso!");
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar usuário",
        description = "Atualiza os dados do usuário com base no ID, desde que o email do token corresponda ao email do usuário."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuário alterado com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<String> updateOne(
            @Parameter(description = "ID do usuário a ser atualizado", required = true) @PathVariable(name = "id") Long id,
            @Parameter(description = "Dados do usuário para atualização", required = true) @RequestBody UsuarioDTO usuarioDTO,
            @Parameter(hidden = true) JwtAuthenticationToken jwt) {
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

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletar usuário",
        description = "Exclui o usuário com base no ID, caso o email do token corresponda ao email do usuário."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuário deletado com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<String> deleteOne(
            @Parameter(description = "ID do usuário a ser deletado", required = true) @PathVariable(name = "id") Long id,
            @Parameter(hidden = true) JwtAuthenticationToken jwt) {
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
