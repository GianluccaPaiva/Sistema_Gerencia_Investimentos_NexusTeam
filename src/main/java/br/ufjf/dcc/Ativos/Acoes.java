package br.ufjf.dcc.Ativos;

public class Acoes extends Ativos{
    private String tipo;
    public Acoes(String nome, String codigo, float preco, boolean qualificado) {
        super(nome, codigo, preco, qualificado);
        this.tipo = definiTipo();
        super.ehNacional = true;
        super.tipoRenda="variável";
    }
    private String definiTipo(){
        if(super.codigo == null || super.codigo.isEmpty()){
            return "Invalido";
        }
        if(super.codigo.endsWith("11")){
            return "Unit";
        }
        int ultimo = super.codigo.charAt(super.codigo.length() - 1);
        if(ultimo == '4' || ultimo == '5' || ultimo == '6'){
            return "Preferencial";
        }
        if(ultimo == '3' || ultimo == '8'){
            return "Ordinaria";
        }
        return "Invalido";
    }
    @Override
    public void exibirAtivo(){
        super.exibirAtivo();
        System.out.println("Tipo: " + this.tipo);
    }
}
