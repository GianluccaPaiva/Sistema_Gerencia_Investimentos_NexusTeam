package br.ufjf.dcc.Investidor;
import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.Carteira.Carteira;
import br.ufjf.dcc.Erros.DadosInvalidosException;

public abstract class Investidor {
    private String nome, id, telefone, dataNascimento;
    private Endereco endereco;
    private double patrimonio;
    private Carteira carteira;

    public Investidor(String nome, String id, String telefone, String dataNascimento, Endereco endereco, double patrimonio) throws DadosInvalidosException {

        if (nome == null || nome.trim().isEmpty()) {
            throw new DadosInvalidosException("Erro: O nome do investidor não pode ser vazio.");
        }

        if (id == null || id.trim().isEmpty()) {
            throw new DadosInvalidosException("Erro: O documento (CPF/CNPJ) é obrigatório.");
        }

        if (patrimonio < 0) {
            throw new DadosInvalidosException("Erro: O patrimônio não pode ser negativo.");
        }

        this.nome = nome;
        this.id = id;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.patrimonio = patrimonio;
        this.carteira = new Carteira();
    }
    public void comprar(Ativos ativo, float qtd, double preco) throws DadosInvalidosException {
        if(podeComprar(ativo)){
            carteira.addAtivo(ativo, qtd, preco);
            System.out.println("Compra realizada com sucesso!");
        }
        else{
            throw new DadosInvalidosException("Erro: Tipo de ativo não permitido para este investidor.");
        }
    }
    public void vender(Ativos ativo, float qtd) throws DadosInvalidosException {
        carteira.removerAtivo(ativo.getTicker(), qtd);
        System.out.println("Venda realizada com sucesso!");
    }

    public void exibirCarteira(){
        carteira.exibirCarteira();
    }

    public abstract boolean podeComprar(Ativos ativo);

    @Override
    public String toString() {
        return String.format("Nome: %-20s | ID: %-14s | Patrimônio: R$ %.2f", this.nome, this.id, this.patrimonio);
    }

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public String getDataNascimento() {return dataNascimento;}
    public void setDataNascimento(String dataNascimento) {this.dataNascimento = dataNascimento;}

    public Endereco getEndereco() {return endereco;}

    public double getPatrimonio() {return patrimonio;}
    public void setPatrimonio(double patrimonio) {
        if(patrimonio >= 0){
            this.patrimonio = patrimonio;
        }
    }

    public Carteira getCarteira() {return this.carteira;}
}