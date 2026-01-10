package br.ufjf.dcc.Ativos;

public class Acoes extends Ativos implements RendaVariavel, Nacional{
    private String tipo;
    public Acoes(String nome, String codigo, float preco, boolean qualificado) {
        super(nome, codigo, preco, qualificado);
        this.tipo = definiTipo();
    }
    private String definiTipo(){
        if(super.codigo == null || super.codigo.isEmpty()){
            return "invalido";
        }
        if(super.codigo.endsWith("11")){
            return "unit";
        }
        int ultimo = super.codigo.charAt(super.codigo.length() - 1);
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
        System.out.println(validaNacionalidade(EH_NACIONAL));
        System.out.println(capitalize(RENDA_VARIAVEL));
        System.out.println("Tipo: " + capitalize(this.tipo));
    }
}
