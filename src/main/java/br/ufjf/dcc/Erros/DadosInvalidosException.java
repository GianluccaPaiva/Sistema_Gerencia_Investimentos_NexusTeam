package br.ufjf.dcc.Erros;

import br.ufjf.dcc.CoresMensagens.CoresMensagens;

public class DadosInvalidosException extends Exception {
    public DadosInvalidosException(String message) {
        super(message);
    }
}
