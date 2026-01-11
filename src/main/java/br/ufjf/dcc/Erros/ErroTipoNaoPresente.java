package br.ufjf.dcc.Erros;

import br.ufjf.dcc.CoresMensagens.CoresMensagens;

public class ErroTipoNaoPresente extends TypeNotPresentException implements CoresMensagens {
    public ErroTipoNaoPresente(String message) {
        super(VERMELHO + message + RESET, null);
    }
}
