package br.ufjf.dcc.Erros;

import java.io.IOException;

public class ErrosLeituraArq extends IOException {
    public ErrosLeituraArq(String message)
    {
        super(message);
    }
}
