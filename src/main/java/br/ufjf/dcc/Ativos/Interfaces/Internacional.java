package br.ufjf.dcc.Ativos.Interfaces;

public interface Internacional {
    boolean EH_NACIONAL= false;
    float COTACAO_DOLAR= 5.39f;
    public boolean getNacionalidade();
    public void converterParaReal();
}
