package br.ufjf.dcc.Ativos;

public class Fiis extends Ativos implements RendaVariavel, Nacional{
    private String segmento;
    private float ultimoDividendo, taxaAdmissao;
    public Fiis(String nome, String codigo, float preco, boolean qualificado, String segmento, float ultimoDividendo, float taxaAdimissao){
        super(nome, codigo, preco, qualificado);
        this.segmento = segmento;
        this.ultimoDividendo = ultimoDividendo;
        this.taxaAdmissao = taxaAdimissao;
    }
    @Override
    public void exibirAtivo(){
        super.exibirAtivo();
        System.out.println(validaNacionalidade(EH_NACIONAL));
        System.out.println(capitalize(RENDA_VARIAVEL));
        System.out.println("Segmento: " + this.segmento);
        System.out.println("Ultimo Dividendo: " + this.ultimoDividendo);
        System.out.println("Taxa de Admissao: " + this.taxaAdmissao);
    }

    @Override
    public boolean getNacionalidade() {
        return EH_NACIONAL;
    }

    @Override
    public String getTipoRenda() {
        return RENDA_VARIAVEL;
    }
}
