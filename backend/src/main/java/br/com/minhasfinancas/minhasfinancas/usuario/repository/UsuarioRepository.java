package br.com.minhasfinancas.minhasfinancas.usuario.repository;

import br.com.minhasfinancas.minhasfinancas.usuario._model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Aqui eu busco o usuário pelo email, findBy(Email) -> Pode ser qualquer atributo da minha class Usuario
    // Aqui me retorna um objeto de User, se tiver ele vem composto | caso não tenha vem nulo.
    //Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}
