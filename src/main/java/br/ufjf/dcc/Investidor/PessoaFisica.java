package br.ufjf.dcc.Investidor;

import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.Ativos.Criptomoedas;
import br.ufjf.dcc.Ativos.Interfaces.Internacional;
import br.ufjf.dcc.Ativos.Stocks;
import br.ufjf.dcc.Erros.DadosInvalidosException;

public class PessoaFisica extends Investidor {
    private String perfil;

    public PessoaFisica(String nome, String cpf, String telefone, String dataNascimento, Endereco endereco, double patrimonio, String perfil) throws DadosInvalidosException {
        super(nome, cpf, telefone, dataNascimento, endereco, patrimonio);

        if(!perfil.equalsIgnoreCase("Conservador") && !perfil.equalsIgnoreCase("Moderado") && !perfil.equalsIgnoreCase("Arrojado")){
            throw new DadosInvalidosException("Erro: Perfil de investidor inválido. Deve ser 'Conservador', 'Moderado' ou 'Arrojado'.");
        }
        this.perfil = perfil;
    }

    @Override
    public boolean podeComprar(Ativos ativo) {

        if(ativo.qualificado() && this.getPatrimonio() < 1000000){
            return false;
        }

        if(ativo instanceof Criptomoedas && !this.perfil.equalsIgnoreCase("Arrojado")){
            return false;
        }

        if((ativo instanceof Stocks || ativo instanceof Internacional) && this.perfil.equalsIgnoreCase("Conservador")){
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("%s | Perfil: %-12s | Tipo: Pessoa Física", super.toString(), this.perfil);
    }

    public String getPerfil() {return perfil;}
}
