package br.ufjf.dcc.Erros;

import br.ufjf.dcc.CoresMensagens.CoresMensagens;

import java.io.IOException;

public class ErrosLeituraArq extends IOException implements CoresMensagens {
    public ErrosLeituraArq(String message)
    {
        super(VERMELHO + message+ RESET);
    }
}
