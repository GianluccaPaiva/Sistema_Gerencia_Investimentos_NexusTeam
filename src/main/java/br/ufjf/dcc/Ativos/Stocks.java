package br.ufjf.dcc.Ativos;

import br.ufjf.dcc.Ativos.Interfaces.Internacional;
import br.ufjf.dcc.Ativos.Interfaces.RendaVariavel;
import br.ufjf.dcc.Tools.Tools;

public class Stocks extends Ativos implements RendaVariavel, Internacional {
    private String bolsaNegociacao, setor;
    public Stocks(String nome, String ticker, float preco, boolean qualificado, String bolsaNegociacao, String setor) {
        super(nome, ticker, preco, qualificado);
        this.bolsaNegociacao = bolsaNegociacao.toUpperCase();
        this.setor = setor;
    }

    public Stocks(String nome, String ticker, float preco, String bolsaNegociacao, String setor) {
        super(nome, ticker, preco);
        this.bolsaNegociacao = bolsaNegociacao.toUpperCase();
        this.setor = setor;
    }

    public String getBolsaNegociacao() {
        return bolsaNegociacao;
    }
    public String getSetor() {
        return setor;
    }

    public void setBolsaNegociacao(String bolsaNegociacao) {
        this.bolsaNegociacao = bolsaNegociacao.toUpperCase();
    }
    public void setSetor(String setor) {
        this.setor = setor;
    }

    @Override
    public boolean getNacionalidade() {
        return EH_NACIONAL;
    }

    @Override
    public void converterParaReal() {
        this.preco = COTACAO_DOLAR * this.preco;
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
        System.out.println("Bolsa de Negociacao: " + this.bolsaNegociacao);
        System.out.println("Setor: " + Tools.capitalize(this.setor));
    }

    @Override
    public boolean verificarAtributosValidos(){
        if(!super.verificarAtributosValidos()){
            return false;
        }
        if(this.bolsaNegociacao == null || this.bolsaNegociacao.isEmpty()){
            return false;
        }
        if(this.setor == null || this.setor.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public void editarAtributos(String comando){
        super.editarAtributos(comando);
        String[] partes = comando.split("=", 2);
        if(partes.length != 2){
            return;
        }
        String atributo = partes[0].trim().toLowerCase();
        String valor = partes[1].trim();
        switch(atributo){
            case "bolsa de negociacao":
                setBolsaNegociacao(valor);
                System.out.println("Bolsa de Negociacao atualizada");
                break;
            case "setor":
                setSetor(valor);
                System.out.println("Setor atualizado");
                break;
            default:
                break;
        }
    }
}
