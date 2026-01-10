package br.ufjf.dcc.Ativos;

import br.ufjf.dcc.Tools.Tools;

public class Criptomoedas extends Ativos implements Internacional, RendaVariavel{
    private String consenso;
    private int qtdMax;
    public Criptomoedas( String nome, String ticker, float preco, boolean qualificado, String consenso, int qtdMax) {
        super(nome, ticker, preco, qualificado);
        this.consenso = consenso;
        this.qtdMax = qtdMax;
    }

    @Override
    public boolean getNacionalidade() {
        return EH_NACIONAL;
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
        System.out.println("Consenso: " + Tools.capitalize(this.consenso));
        System.out.println("Quantidade Maxima: " + this.qtdMax);
    }
}
