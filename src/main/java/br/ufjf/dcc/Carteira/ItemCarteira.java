package br.ufjf.dcc.Carteira;
import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.Ativos.Interfaces.Internacional;

public class ItemCarteira{
    private Ativos ativo;
    private float qtd;
    private double valorPagoTotal;

    public ItemCarteira(Ativos ativo, float qtd, double precoPagoNaCompra){
        this.ativo = ativo;
        this.qtd = qtd;
        this.valorPagoTotal = precoPagoNaCompra * qtd;
    }

    public double getValorAtualTotal(){
        double precoEmReais;
        if(this.ativo instanceof Internacional){
            precoEmReais = this.ativo.getPreco() * Internacional.COTACAO_DOLAR;
        } else {
            precoEmReais = this.ativo.getPreco();
        }
        return precoEmReais * this.qtd;
    }

    public void adicionarCompra(float novaQtd, double precoPagoNaCompra){
        if (novaQtd > 0) {
            this.qtd += novaQtd;
            this.valorPagoTotal += (precoPagoNaCompra * novaQtd);
        }
    }

    public void removerVenda(float qtdVendida){
        if (qtdVendida > 0 && qtdVendida <= this.qtd) {
            double proporcao = qtdVendida/this.qtd;
            double valorReduzido = this.valorPagoTotal * proporcao;
            this.qtd -= qtdVendida;
            this.valorPagoTotal -= valorReduzido;
        }
    }

    public Ativos getAtivo(){return this.ativo;}
    public float getQtd(){return this.qtd;}
    public double getValorPagoTotal(){return this.valorPagoTotal;}
    public double getLucroPrejuizo(){return getValorAtualTotal() - this.valorPagoTotal;}

}