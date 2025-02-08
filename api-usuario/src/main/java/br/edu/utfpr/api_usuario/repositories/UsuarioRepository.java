package br.edu.utfpr.api_usuario.repositories;

import br.edu.utfpr.api_usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
}
