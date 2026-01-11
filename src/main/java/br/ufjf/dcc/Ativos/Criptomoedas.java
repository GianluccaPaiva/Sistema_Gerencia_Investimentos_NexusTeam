package br.ufjf.dcc.Ativos;

import br.ufjf.dcc.Ativos.Interfaces.Internacional;
import br.ufjf.dcc.Ativos.Interfaces.RendaVariavel;
import br.ufjf.dcc.Tools.Tools;

public class Criptomoedas extends Ativos implements Internacional, RendaVariavel {
    private String consenso;
    private long qtdMax;
    public Criptomoedas( String nome, String ticker, float preco, boolean qualificado, String consenso, long qtdMax) {
        super(nome, ticker, preco, qualificado);
        this.consenso = consenso;
        this.qtdMax = qtdMax;
    }

    public Criptomoedas( String nome, String ticker, float preco,String consenso, long qtdMax) {
        super(nome, ticker, preco);
        this.consenso = consenso;
        this.qtdMax = qtdMax;
    }

    public String getConsenso() {
        return consenso;
    }

    public long getQtdMax() {
        return qtdMax;
    }

    public void setConsenso( String consenso) {
        this.consenso = consenso;
    }

    public void setQtdMax(long qtdMax) {
        this.qtdMax = qtdMax;
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
        System.out.println("Consenso: " + Tools.capitalize(this.consenso));
        System.out.println("Quantidade Maxima: " + this.qtdMax);
    }

    @Override
    public boolean verificarAtributosValidos(){
        if(!super.verificarAtributosValidos()){
            return false;
        }
        if(this.consenso == null || this.consenso.isEmpty()){
            return false;
        }
        if(this.qtdMax <= 0){
            return false;
        }
        return true;
    }

    @Override
    public void editarAtributos(String comando){
        super.editarAtributos(comando);
        String[] partes = comando.split("=");
        if(partes.length != 2){
            return;
        }
        String atributo = partes[0].trim().toLowerCase();
        String valor = partes[1].trim();
        switch(atributo){
            case "consenso":
                this.setConsenso(valor);
                System.out.println("Consenso atualizado");
                break;
            case "quantidade maxima":
                try{
                    long qtdMax = Long.parseLong(valor);
                    this.setQtdMax(qtdMax);
                    System.out.println("Quantidade maxima atualizada");
                }catch(NumberFormatException e){
                    System.out.println("Valor invalido para quantidade maxima: " + valor);
                }
                break;
            default:
                break;
        }
    }
}
