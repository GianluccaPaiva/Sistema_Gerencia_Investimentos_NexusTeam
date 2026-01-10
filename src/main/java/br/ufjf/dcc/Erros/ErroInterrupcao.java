package br.ufjf.dcc.Erros;

import br.ufjf.dcc.CoresMensagens.CoresMensagens;

public class ErroInterrupcao extends InterruptedException implements CoresMensagens {
    public ErroInterrupcao(String message) {
        super(message);
    }
}
