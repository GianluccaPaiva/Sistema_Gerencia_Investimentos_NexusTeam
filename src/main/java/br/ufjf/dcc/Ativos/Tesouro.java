package br.ufjf.dcc.Ativos;

import java.util.Arrays;


public class Tesouro extends Ativos implements RendaFixa, Nacional{
    private String tipoRendimento, dataVencimento;
    public Tesouro(String nome, String codigo, float preco, boolean qualificado, String tipoRendimento, String dataVencimento) {
        super(nome, codigo, preco, qualificado);
        this.tipoRendimento = defineTipoRendimento(tipoRendimento.toLowerCase());
        this.dataVencimento = dataVencimento;
    }

    private String defineTipoRendimento(String tipoRendimento){
        String [] tipos= {"selic", "prefixado", "ipca+"};
        if(tipoRendimento == null){
            return "invalido";
        }
        if (Arrays.asList(tipos).contains(tipoRendimento)){
            return tipoRendimento;
        }
        return "invalido";
    }

    @Override
    public boolean getNacionalidade() {
        return EH_NACIONAL;
    }

    @Override
    public String getTipoRenda() {
        return RENDA_FIXA;
    }

    @Override
    public void exibirAtivo() {
        super.exibirAtivo();
        System.out.println(validaNacionalidade(EH_NACIONAL));
        System.out.println(capitalize(RENDA_FIXA));
        System.out.println("Tipo de Rendimento: " + capitalize(this.tipoRendimento));
        System.out.println("Data de Vencimento: " + this.dataVencimento);
    }
}
