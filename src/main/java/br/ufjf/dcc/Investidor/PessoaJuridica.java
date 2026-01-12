package br.ufjf.dcc.Investidor;

import br.ufjf.dcc.Erros.DadosInvalidosException;

public class PessoaJuridica extends Investidor{
    private String razaoSocial;

    public PessoaJuridica(String nome, String cnpj, String telefone, String dataNascimento, Endereco endereco, double patrimonio, String razaoSocial) throws br.ufjf.dcc.Erros.DadosInvalidosException {
        super(nome, cnpj, telefone, dataNascimento, endereco, patrimonio);
        if(razaoSocial == null || razaoSocial.trim().isEmpty()){
            throw new DadosInvalidosException("Erro: Razão Social é obrigatória para Pessoa Jurídica.");
        }
        this.razaoSocial = razaoSocial;
    }

    @Override
    public boolean podeComprar(br.ufjf.dcc.Ativos.Ativos ativo) {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s | Razão Social: %-20s | Tipo: Pessoa Jurídica", super.toString(), this.razaoSocial);
    }

    public String getRazaoSocial() {return razaoSocial;}

}
