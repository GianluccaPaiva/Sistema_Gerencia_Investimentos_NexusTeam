package br.ufjf.dcc.Ativos;

import br.ufjf.dcc.Ativos.Interfaces.Nacional;
import br.ufjf.dcc.Ativos.Interfaces.RendaFixa;
import br.ufjf.dcc.Tools.Tools;

import java.util.Arrays;


public class Tesouro extends Ativos implements RendaFixa, Nacional {
    private String tipoRendimento, dataVencimento;
    public Tesouro(String nome, String ticker, float preco, boolean qualificado, String tipoRendimento) {
        super(nome, ticker, preco, qualificado);
        this.tipoRendimento = defineTipoRendimento(tipoRendimento.toLowerCase());
        this.dataVencimento = dataVencimento;
    }

    public Tesouro(String nome, String ticker, float preco, String tipoRendimento, String dataVencimento) {
        super(nome, ticker, preco);
        this.tipoRendimento = defineTipoRendimento(tipoRendimento.toLowerCase());
        this.dataVencimento = dataVencimento;
    }

    public String getTipoRendimento() {
        return tipoRendimento;
    }
    public String getDataVencimento() {
        return dataVencimento;
    }
    public void setTipoRendimento(String tipoRendimento) {
        this.tipoRendimento = defineTipoRendimento(tipoRendimento.toLowerCase());
    }
    public void setDataVencimento(String dataVencimento) {
        if(Tools.dataValida(dataVencimento)){
            System.out.println("Data de vencimento inválida. Deve estar no formato DD/MM/AAAA.");
            return;
        }
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
        System.out.println("Nacionalidade" + Tools.validaNacionalidade(EH_NACIONAL));
        System.out.println("Renda: "+Tools.capitalize(RENDA_FIXA));
        System.out.println("Tipo de Rendimento: " + Tools.capitalize(this.tipoRendimento));
        System.out.println("Data de Vencimento: " + this.dataVencimento);
    }

    @Override
    public boolean verificarAtributosValidos() {
        if (!super.verificarAtributosValidos()) {
            return false;
        }
        String [] tipos= {"selic", "prefixado", "ipca+"};
        if (!Arrays.asList(tipos).contains(this.tipoRendimento)) {
            return false;
        }
        if (this.dataVencimento == null || this.dataVencimento.isEmpty() || !Tools.dataValida(this.dataVencimento)) {
            return false;
        }
        return true;
    }

    @Override
    public void editarAtributos(String comando){
        String[] partes = comando.split("=", 2);
        if (partes.length != 2) {
            System.out.println("Comando inválido. Use o formato atributo=valor.");
            return;
        }
        String atributo = partes[0].trim().toLowerCase();
        String valor = partes[1].trim();

        super.editarAtributos(comando);
        switch (atributo) {
            case "rendimento":
                setTipoRendimento(valor);
                System.out.println("Tipo de rendimento atualizado");
                break;
            case "data de vencimento":
                setDataVencimento(valor);
                System.out.println("Data de vencimento atualizada");
                break;
            default:
                break;
        }
    }
}
