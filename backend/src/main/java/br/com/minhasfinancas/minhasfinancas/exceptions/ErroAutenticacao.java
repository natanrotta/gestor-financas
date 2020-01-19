package br.com.minhasfinancas.minhasfinancas.exceptions;

public class ErroAutenticacao extends RuntimeException {
    public ErroAutenticacao(String msg) {
        super(msg);
    }
}
