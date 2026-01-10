package br.ufjf.dcc.Ativos;

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
    protected String capitalize(String str){
        if(str == null || str.isEmpty()){
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    protected String validaNacionalidade(boolean ehNacional){
        return ehNacional ? "Nacional" : "Internacional";
    }
    public void exibirAtivo(){
        System.out.println("Nome: " + capitalize(this.nome));
        System.out.println("Codigo: " + this.codigo);
        System.out.println("Preco: " + this.preco);
        System.out.println("Qualificado: " + this.qualificado);
    }
}
