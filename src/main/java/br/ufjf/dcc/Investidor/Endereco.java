package br.ufjf.dcc.Investidor;

public class Endereco {
    private String rua, cidade, estado, cep, bairro;
    private int numero;

    public Endereco(String rua, String cidade, String estado, String cep, String bairro, int numero) {
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.bairro = bairro;
        this.numero = numero;
    }

    public String getRua() {return rua;}
    public String getCidade() {return cidade;}
    public String getEstado() {return estado;}
    public String getCep() {return cep;}
    public String getBairro() {return bairro;}
    public int getNumero() {return numero;}

    public void setRua(String rua) {this.rua = rua;}
    public void setCidade(String cidade) {this.cidade = cidade;}
    public void setEstado(String estado) {this.estado = estado;}
    public void setCep(String cep) {this.cep = cep;}
    public void setBairro(String bairro) {this.bairro = bairro;}
    public void setNumero(int numero) {
        if(numero < 0){
            throw new IllegalArgumentException("Número do endereço não pode ser negativo.");
        }
        this.numero = numero;
    }
}