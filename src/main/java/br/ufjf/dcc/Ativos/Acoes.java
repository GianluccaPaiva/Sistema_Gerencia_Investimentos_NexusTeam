package br.ufjf.dcc.Ativos;

import br.ufjf.dcc.Ativos.Interfaces.Nacional;
import br.ufjf.dcc.Ativos.Interfaces.RendaVariavel;
import br.ufjf.dcc.Tools.Tools;

public class Acoes extends Ativos implements RendaVariavel, Nacional {
    private String tipo;
    public Acoes(String nome, String ticker, float preco, boolean qualificado) {
        super(nome, ticker, preco, qualificado);
        this.tipo = definiTipo();
    }

    public Acoes(String nome, String ticker, float preco){
        this(nome, ticker, preco, false);
        this.tipo = definiTipo();
    }

    private String definiTipo(){
        if(super.ticker == null || super.ticker.isEmpty()){
            return "invalido";
        }
        if(super.ticker.endsWith("11")){
            return "unit";
        }
        int ultimo = super.ticker.charAt(super.ticker.length() - 1);
        if(ultimo == '4' || ultimo == '5' || ultimo == '6'){
            return "preferencial";
        }
        if(ultimo == '3' || ultimo == '8'){
            return "ordinaria";
        }
        return "invalido";
    }
    public boolean getNacionalidade(){
        return EH_NACIONAL;
    }

    public String getTipoRenda(){
        return this.tipo;
    }

    @Override
    public void exibirAtivo(){
        super.exibirAtivo();
        System.out.println(Tools.validaNacionalidade(EH_NACIONAL));
        System.out.println(Tools.capitalize(RENDA_VARIAVEL));
        System.out.println("Tipo: " + Tools.capitalize(this.tipo));
    }

    @Override
    public boolean verificarAtributosValidos(){
        if(!super.verificarAtributosValidos()){
            return false;
        }
        if(this.tipo.equals("invalido")){
            return false;
        }
        if (!this.ticker.matches(".*\\d$")) {
            return false;
        }
        return true;
    }
}
