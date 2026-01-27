package br.ufjf.dcc.Investidor;
import br.ufjf.dcc.Erros.DadosInvalidosException;

public class Endereco {
    private String rua, cidade, estado, cep, bairro;
    private int numero;

    public Endereco(String rua, String cidade, String estado, String cep, String bairro, int numero) throws DadosInvalidosException {

        if (rua == null || rua.trim().isEmpty()) throw new DadosInvalidosException("Endereço: Rua é obrigatória.");
        if (cidade == null || cidade.trim().isEmpty()) throw new DadosInvalidosException("Endereço: Cidade é obrigatória.");
        if (estado == null || estado.trim().isEmpty()) throw new DadosInvalidosException("Endereço: Estado é obrigatório.");
        if (cep == null || cep.trim().isEmpty()) throw new DadosInvalidosException("Endereço: CEP é obrigatório.");
        if (bairro == null || bairro.trim().isEmpty()) throw new DadosInvalidosException("Endereço: Bairro é obrigatório.");
        if (numero < 0) throw new DadosInvalidosException("Endereço: Número não pode ser negativo.");

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

    public void setRua(String rua) throws DadosInvalidosException {
        if (rua == null || rua.trim().isEmpty()) throw new DadosInvalidosException("Rua inválida.");
        this.rua = rua;
    }

    public void setCidade(String cidade) throws DadosInvalidosException {
        if (cidade == null || cidade.trim().isEmpty()) throw new DadosInvalidosException("Cidade inválida.");
        this.cidade = cidade;
    }

    public void setEstado(String estado) throws DadosInvalidosException {
        if (estado == null || estado.trim().isEmpty()) throw new DadosInvalidosException("Estado inválido.");
        this.estado = estado;
    }

    public void setCep(String cep) throws DadosInvalidosException {
        if (cep == null || cep.trim().isEmpty()) throw new DadosInvalidosException("CEP inválido.");
        this.cep = cep;
    }

    public void setBairro(String bairro) throws DadosInvalidosException {
        if (bairro == null || bairro.trim().isEmpty()) throw new DadosInvalidosException("Bairro inválido.");
        this.bairro = bairro;
    }

    public void setNumero(int numero) throws DadosInvalidosException {
        if(numero < 0){
            throw new DadosInvalidosException("Número do endereço não pode ser negativo.");
        }
        this.numero = numero;
    }
}