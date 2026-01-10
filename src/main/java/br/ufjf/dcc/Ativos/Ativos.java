package br.ufjf.dcc.Ativos;

import br.ufjf.dcc.Tools.Tools;


public abstract class Ativos {
    protected String nome, codigo;
    protected float preco;
    protected boolean qualificado;
    public Ativos(String nome, String codigo, float preco, boolean qualificado){
        this.nome = nome.toLowerCase();
        this.codigo = codigo.toUpperCase();
        this.preco = preco;
        this.qualificado = qualificado;
    }
    public void exibirAtivo(){
        System.out.println("Nome: " + Tools.capitalize(this.nome));
        System.out.println("Codigo: " + this.codigo);
        System.out.println("Preco: " + this.preco);
        System.out.println("Qualificado: " + this.qualificado);
    }
}
