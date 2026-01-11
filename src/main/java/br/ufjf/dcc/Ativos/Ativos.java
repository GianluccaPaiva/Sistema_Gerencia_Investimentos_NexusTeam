package br.ufjf.dcc.Ativos;

import br.ufjf.dcc.Tools.Tools;


public abstract class Ativos {
    protected String nome, ticker;
    protected float preco;
    protected boolean qualificado = false;

    public Ativos(String nome, String ticker, float preco, boolean qualificado){
        this.nome = nome.toLowerCase();
        this.ticker = ticker.toUpperCase();
        this.preco = preco;
        this.qualificado = qualificado;
    }

    public Ativos(String nome, String ticker, float preco) {
        this(nome, ticker, preco, false);
    }

    public void exibirAtivo(){
        System.out.println("Nome: " + Tools.capitalize(this.nome));
        System.out.println("ticker: " + this.ticker);
        System.out.println("Preco: " + this.preco);
        System.out.println("Qualificado: " + this.qualificado);
    }

    public String getTicker(){
        return this.ticker;
    }
    public String getNome(){
        return this.nome;
    }

    protected boolean verificarAtributosValidos(){
        if(this.nome == null || this.nome.isEmpty()){
            return false;
        }
        if(this.ticker == null || this.ticker.isEmpty() ){
            return false;
        }
        if (!this.ticker.equals(this.ticker.toUpperCase())) {
            return false;
        }
        if(this.preco < 0){
            return false;
        }
        return true;
    }
}
