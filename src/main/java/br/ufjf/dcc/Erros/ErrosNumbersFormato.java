package br.ufjf.dcc.Erros;

import br.ufjf.dcc.CoresMensagens.CoresMensagens;

public class ErrosNumbersFormato extends NumberFormatException implements CoresMensagens {
    public ErrosNumbersFormato(String message) {
        super(AMARELO + message + RESET);
    }
}
