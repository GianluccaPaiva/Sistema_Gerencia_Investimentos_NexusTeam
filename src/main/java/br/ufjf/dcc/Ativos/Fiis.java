package br.ufjf.dcc.Ativos;

import br.ufjf.dcc.Ativos.Interfaces.Nacional;
import br.ufjf.dcc.Ativos.Interfaces.RendaVariavel;
import br.ufjf.dcc.Ativos.Interfaces.TaxaPorcentagem;
import br.ufjf.dcc.Tools.Tools;

public class Fiis extends Ativos implements RendaVariavel, Nacional, TaxaPorcentagem {
    private String segmento;
    private float ultimoDividendo, taxaAdmissao;
    public Fiis(String nome, String ticker, float preco, boolean qualificado, String segmento, float ultimoDividendo, float taxaAdimissao){
        super(nome, ticker, preco, qualificado);
        this.segmento = segmento;
        this.ultimoDividendo = ultimoDividendo;
        this.taxaAdmissao = taxaAdimissao;
    }
    public Fiis(String nome, String ticker, float preco, String segmento, float ultimoDividendo, float taxaAdimissao){
        super(nome, ticker, preco);
        this.segmento = segmento;
        this.ultimoDividendo = ultimoDividendo;
        this.taxaAdmissao = taxaAdimissao;
    }

    public String getSegmento() {
        return segmento;
    }
    public float getUltimoDividendo() {
        return ultimoDividendo;
    }
    public float getTaxaAdmissao() {
        return taxaAdmissao;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }
    public void setUltimoDividendo(float ultimoDividendo) {
        this.ultimoDividendo = ultimoDividendo;
    }
    public void setTaxaAdmissao(float taxaAdmissao) {
        this.taxaAdmissao = taxaAdmissao;
    }

    @Override
    public void exibirAtivo(){
        super.exibirAtivo();
        System.out.println("Nacionalidade: " + Tools.validaNacionalidade(EH_NACIONAL));
        System.out.println("Renda: " + Tools.capitalize(RENDA_VARIAVEL));
        System.out.println("Segmento: " + this.segmento);
        System.out.println("Ultimo Dividendo: " + this.ultimoDividendo);
        exibirTaxaAdministracao();
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
    public void exibirTaxaAdministracao() {
        System.out.println("Taxa de Admissao: " + this.taxaAdmissao + "%");
    }

    @Override
    public boolean verificarAtributosValidos(){
        if(!super.verificarAtributosValidos()){
            return false;
        }
        if(this.segmento == null || this.segmento.isEmpty()){
            return false;
        }
        if(this.ultimoDividendo < 0){
            return false;
        }
        if(this.taxaAdmissao < 0){
            return false;
        }
        return true;
    }

    @Override
    public void editarAtributos(String comando){
        comando.toLowerCase();
        super.editarAtributos(comando);
        String[] partes = comando.split("=", 2);
        if(partes.length != 2){
            return;
        }
        String atributo = partes[0].trim().toLowerCase();
        String valor = partes[1].trim();
        switch(atributo){
            case "segmento":
                setSegmento(valor);
                System.out.println("Segmento atualizado");
                break;
            case "ultimo dividendo":
                try{
                    float ultimoDividendo = Float.parseFloat(valor);
                    setUltimoDividendo(ultimoDividendo);
                    System.out.println("Ultimo Dividendo atualizado");
                } catch (NumberFormatException e){
                    System.out.println("Valor invalido para ultimoDividendo: " + valor);
                }
                break;
            case "taxa de admissao":
                try{
                    float taxaAdmissao = Float.parseFloat(valor);
                    setTaxaAdmissao(taxaAdmissao);
                    System.out.println("Taxa de Admissao atualizada");
                } catch (NumberFormatException e){
                    System.out.println("Valor invalido para taxaAdmissao: " + valor);
                }
                break;
            default:
                break;
        }
    }
}
