package br.ufjf.dcc.Ativos;

import br.ufjf.dcc.Tools.Tools;


public abstract class Ativos {
    protected String nome, ticker;
    protected float preco;
    protected boolean qualificado = false;

    public Ativos(String nome, String ticker, float preco, boolean qualificado){
        this.nome = nome.toLowerCase();
        this.ticker = ticker.toUpperCase();
        this.preco = preco;
        this.qualificado = qualificado;
    }

    public Ativos(String nome, String ticker, float preco) {
        this(nome, ticker, preco, false);
    }

    public void exibirAtivo(){
        System.out.println("Nome: " + Tools.capitalize(this.nome));
        System.out.println("ticker: " + this.ticker);
        System.out.println("Preco: " + this.preco);
        System.out.println("Qualificado: " + (this.qualificado? "Sim" : "Nao"));
    }

    public String getTicker(){
        return this.ticker;
    }
    public String getNome(){
        return this.nome;
    }

    public float getPreco(){
        return this.preco;
    }

    public boolean qualificado(){
        return this.qualificado;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setTicker(String ticker){
        this.ticker = ticker;
    }
    public void setPreco(Float preco){
        this.preco = preco;
    }
    public void setQualificado(boolean qualificado){
        this.qualificado = qualificado;
    }


    protected boolean verificarAtributosValidos(){
        if(this.nome == null || this.nome.isEmpty()){
            return false;
        }
        if(this.ticker == null || this.ticker.isEmpty() ){
            return false;
        }
        if (!this.ticker.equals(this.ticker.toUpperCase())) {
            return false;
        }
        if(this.preco < 0){
            return false;
        }
        return true;
    }


    public void editarAtributos(String comando) {
        if (comando == null || comando.trim().isEmpty()) {
            System.out.println("Comando de edição vazio para Acoes.");
            return;
        }
        String[] parts = comando.split("=", 2);
        if (parts.length < 2) {
            System.out.println("Formato inválido. Use campo=valor");
            return;
        }
        String campo = parts[0].trim().toLowerCase();
        String valor = parts[1].trim();

        try {
            switch (campo) {
                case "nome":
                    setNome(valor);
                    System.out.println("Nome atualizado.");
                    break;
                case "ticker":
                    setTicker(valor);
                    System.out.println("Ticker atualizado e tipo recalculado.");
                    break;
                case "preco":
                    setPreco(Float.parseFloat(valor.replace(",", ".")));
                    System.out.println("Preço atualizado.");
                    break;
                case "qualificado":
                    setQualificado(valor.equalsIgnoreCase("sim") || valor.equalsIgnoreCase("true") || valor.equals("1"));
                    System.out.println("Qualificado atualizado.");
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor numérico inválido para campo " + campo + ": " + valor);
        } catch (Exception e) {
            System.out.println("Erro ao editar Acoes: " + e.getMessage());
        }
    }
}
