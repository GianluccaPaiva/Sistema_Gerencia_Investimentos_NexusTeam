package br.ufjf.dcc.Ativos;

import br.ufjf.dcc.Tools.Tools;

public class Stocks extends Ativos implements RendaVariavel, Internacional{
    private String bolsaNegociacao, setor;
    public Stocks(String nome, String ticker, float preco, boolean qualificado, String bolsaNegociacao, String setor) {
        super(nome, ticker, preco, qualificado);
        this.bolsaNegociacao = bolsaNegociacao.toUpperCase();
        this.setor = setor;
    }

    public Stocks(String nome, String ticker, float preco, String bolsaNegociacao, String setor) {
        super(nome, ticker, preco);
        this.bolsaNegociacao = bolsaNegociacao.toUpperCase();
        this.setor = setor;
    }

    @Override
    public boolean getNacionalidade() {
        return EH_NACIONAL;
    }

    @Override
    public void converterParaReal() {
        this.preco = COTACAO_DOLAR * this.preco;
    }

    @Override
    public String getTipoRenda() {
        return RENDA_VARIAVEL;
    }

    @Override
    public void exibirAtivo(){
        super.exibirAtivo();
        System.out.println("Tipo Renda: " + Tools.capitalize(RENDA_VARIAVEL));
        System.out.println("Nacionalidade: " + Tools.validaNacionalidade(EH_NACIONAL));
        System.out.println("Bolsa de Negociacao: " + this.bolsaNegociacao);
        System.out.println("Setor: " + Tools.capitalize(this.setor));
    }
}
