package br.com.minhasfinancas.minhasfinancas.lancamento;

import br.com.minhasfinancas.minhasfinancas.enums.StatusLancamento;
import br.com.minhasfinancas.minhasfinancas.lancamento._model.Lancamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LancamentoService {

    /**
     * @param lancamento
     * @return Método responsável por salvar um lançamento.
     */
    Lancamento salvar(Lancamento lancamento);

    /**
     * @param lancamento
     * @return Método responsável por atualizar um lançamento.
     */
    Lancamento atualizar(Lancamento lancamento);

    /**
     * @param lancamento Método responsável por deletar um lançamento.
     */
    void deletar(Lancamento lancamento);

    /**
     * @param lancamentoFiltro
     * @return Método responsável por buscar um lançamento.
     */
    List<Lancamento> buscar(Lancamento lancamentoFiltro);

    /**
     * @param lancamento
     * @param statusLancamento Método responsável por atualziar o status de um lançamento.
     */
    void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento);

    /**
     * @param lancamento Método responsável por validar um lançamento.
     */
    void validar(Lancamento lancamento);

    /**
     * @param id
     * @return Método responsável por obter um lançamento pelo id.
     */
    Optional<Lancamento> obterPorId(Long id);

    /**
     * @param id
     * @return Método responsável por buscar o saldo do usuário informado.
     */
    BigDecimal obterSaldoPorUsuario(Long id);
}
