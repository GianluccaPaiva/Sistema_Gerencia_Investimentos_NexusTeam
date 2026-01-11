package br.ufjf.dcc.Carteira;
import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.Ativos.Interfaces.Internacional;
import br.ufjf.dcc.Ativos.Stocks;

public class ItemCarteira{
    private Ativos ativo;
    private int qtd;
    private double valorPagoTotal;


    public ItemCarteira(Ativos ativo, int qtd, double precoPagoNaCompra){
        this.ativo = ativo;
        this.qtd = qtd;
        this.valorPagoTotal = precoPagoNaCompra * qtd;
    }
}
