package br.ufjf.dcc.Carteira;
import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.Ativos.Interfaces.Internacional;
import br.ufjf.dcc.Erros.DadosInvalidosException;

public class ItemCarteira{
    private Ativos ativo;
    private float qtd;
    private double valorPagoTotal;

    public ItemCarteira(Ativos ativo, float qtd, double precoPagoNaCompra) throws DadosInvalidosException {
        if (ativo == null) {
            throw new DadosInvalidosException("Erro: Tentativa de adicionar item com ativo nulo.");
        }
        if (qtd <= 0) {
            throw new DadosInvalidosException("Erro: Quantidade inicial deve ser maior que zero.");
        }

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

    public void adicionarCompra(float novaQtd, double precoPagoNaCompra) throws DadosInvalidosException {
        if(novaQtd <= 0){
            throw new DadosInvalidosException("Erro: Quantidade de compra inválida.");
        }
        this.valorPagoTotal += precoPagoNaCompra * novaQtd;
        this.qtd += novaQtd;
    }

    public void removerVenda(float qtdVendida) throws DadosInvalidosException {
        if (qtdVendida <= 0) {
            throw new DadosInvalidosException("Erro: Quantidade de venda inválida.");
        }
        if (qtdVendida > this.qtd) {
            throw new DadosInvalidosException("Erro: Saldo insuficiente para venda. Você tem " + this.qtd + " mas tentou vender " + qtdVendida);
        }

        double proporcao = qtdVendida / this.qtd;
        double valorReduzido = this.valorPagoTotal * proporcao;
        this.qtd -= qtdVendida;
        this.valorPagoTotal -= valorReduzido;
    }

    public Ativos getAtivo(){return this.ativo;}
    public float getQtd(){return this.qtd;}
    public double getValorPagoTotal(){return this.valorPagoTotal;}
    public double getLucroPrejuizo(){return getValorAtualTotal() - this.valorPagoTotal;}

}