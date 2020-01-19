package br.com.minhasfinancas.minhasfinancas.usuario;

import br.com.minhasfinancas.minhasfinancas.usuario._model.Usuario;

import java.math.BigDecimal;
import java.util.Optional;

public interface UsuarioService {

    /**
     *
     * @param email
     * @param senha
     * @return
     *
     * Método responsável por autenticar um usuário, recebe seu email e senha para realizar a operação.
     * Caso o usuário exista, o mesmo é retornado.
     */
    Usuario autenticar(String email, String senha);

    /**
     *
     * @param usuario
     * @return
     *
     * Método responsável por salvar um novo usuário no sistema.
     */
    Usuario salvarUsuario(Usuario usuario);

    /**
     *
     * @param email
     *
     * Método responsável por validar um email.
     */
    void validarEmail(String email);

    /**
     *
     * @param id
     * @return
     *
     * Método responsável por buscar um usuário pelo id informado por paramêtro.
     */
    Optional<Usuario> obterPorId(Long id);

    /**
     *
     * @param id
     * @return
     *
     * Método responsável por buscar o saldo do usuário informado.
     */
    BigDecimal obterSaldoPorUsuario(Long id);
}
