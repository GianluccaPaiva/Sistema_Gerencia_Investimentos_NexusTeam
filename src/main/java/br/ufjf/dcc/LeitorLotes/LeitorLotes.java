package br.ufjf.dcc.LeitorLotes;

import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.CoresMensagens.CoresMensagens;
import br.ufjf.dcc.Erros.*;
import br.ufjf.dcc.Investidor.*;
import br.ufjf.dcc.Mercado.Mercado;
import br.ufjf.dcc.Movimentacao.Movimentacao;
import br.ufjf.dcc.Registrar.Registrar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorLotes {
    private static final String INVESTIDORES_DIR_PATH = "lotes/investidoresLotes/";
    private static final String MOVIMENTACOES_DIR_PATH = "lotes/movimentacoesLotes/";

    public static List<Investidor> carregarInvestidores(String nomeArquivo) throws ErrosLeituraArq {
        List<Investidor> novosInvestidores = new ArrayList<>();
        File arquivo = new File(INVESTIDORES_DIR_PATH, nomeArquivo);
        if (!arquivo.exists()) {

            throw new ErrosLeituraArq("Arquivo não encontrado no diretório: " + arquivo.getAbsolutePath());
        }

        int linhaAtual = 0;
        int sucessos = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            br.readLine();

            while ((linha = br.readLine()) != null) {
                linhaAtual++;
                if (linha.trim().isEmpty()) continue;

                try {
                    String[] dados = linha.split(";");

                    if (dados.length < 13) {
                        throw new DadosInvalidosException("Linha incompleta (faltam colunas).");
                    }

                    String tipo = dados[0].trim();
                    String nome = dados[1].trim();
                    String id = dados[2].trim();
                    String tel = dados[3].trim();
                    String nasc = dados[4].trim();
                    String rua = dados[5].trim();

                    int numero;
                    try {
                        numero = Integer.parseInt(dados[6].trim());
                    } catch (NumberFormatException e) {
                        throw new ErrosNumbersFormato("Número do endereço inválido na linha " + linhaAtual);
                    }

                    String bairro = dados[7].trim();
                    String cidade = dados[8].trim();
                    String estado = dados[9].trim();
                    String cep = dados[10].trim();

                    Endereco endereco = new Endereco(rua, cidade, estado, cep, bairro, numero);

                    double patrimonio;
                    try {
                        patrimonio = Double.parseDouble(dados[11].replace(",", ".").trim());
                    } catch (NumberFormatException e) {
                        throw new ErrosNumbersFormato("Valor de patrimônio inválido na linha " + linhaAtual);
                    }

                    String extra = dados[12].trim();

                    Investidor inv;
                    if (tipo.equalsIgnoreCase("PF")) {
                        inv = new PessoaFisica(nome, id, tel, nasc, endereco, patrimonio, extra);
                    } else if (tipo.equalsIgnoreCase("PJ")) {
                        inv = new PessoaJuridica(nome, id, tel, nasc, endereco, patrimonio, extra);
                    } else {
                        throw new ErroTipoNaoPresente("Tipo '" + tipo + "' desconhecido. Use PF ou PJ.");
                    }

                    novosInvestidores.add(inv);
                    sucessos++;
                    System.out.println("✅ Carregado: " + nome);

                } catch (DadosInvalidosException | ErrosNumbersFormato | ErroTipoNaoPresente e) {
                    System.out.println(CoresMensagens.AMARELO + "⚠️ Falha na linha " + linhaAtual + ": " + e.getMessage() + CoresMensagens.RESET);
                } catch (Exception e) {
                    System.out.println(CoresMensagens.VERMELHO + "Erro inesperado linha " + linhaAtual + ": " + e.getMessage() + CoresMensagens.RESET);
                }
            }
            System.out.println("\nResumo: " + sucessos + " investidores carregados com sucesso.");

        } catch (IOException e) {
            throw new ErrosLeituraArq("Erro crítico ao ler arquivo: " + e.getMessage());
        }

        return novosInvestidores;
    }

    public static void processarLoteMovimentacoes(String nomeArquivo, Investidor investidor, Mercado mercado) throws ErrosLeituraArq {
        File arquivo = new File(MOVIMENTACOES_DIR_PATH, nomeArquivo);

        if (!arquivo.exists()) {
            throw new ErrosLeituraArq("Arquivo não encontrado no diretório: " + arquivo.getAbsolutePath());
        }

        int linhaAtual = 0;
        int sucessos = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            br.readLine();

            while ((linha = br.readLine()) != null) {
                linhaAtual++;
                if (linha.trim().isEmpty()) continue;

                try {
                    String[] dados = linha.split(";");
                    if (dados.length < 3) throw new DadosInvalidosException("Colunas insuficientes.");

                    String tipoOperacao = dados[0].trim().toUpperCase();
                    String ticker = dados[1].trim().toUpperCase();

                    float qtd;
                    try {
                        qtd = Float.parseFloat(dados[2].replace(",", ".").trim());
                    } catch (NumberFormatException e) {
                        throw new ErrosNumbersFormato("Quantidade inválida na linha " + linhaAtual);
                    }

                    Ativos ativo = mercado.buscaAtivo(ticker);
                    if (ativo == null) throw new DadosInvalidosException("Ativo '" + ticker + "' não cadastrado no mercado.");

                    if (tipoOperacao.equals("COMPRA")) {
                        investidor.comprar(ativo, qtd, ativo.getPreco());

                        Movimentacao mov = new Movimentacao("COMPRA", "LoteArq", ticker, qtd, ativo.getPreco());
                        Registrar.registrar(investidor.getId(), mov.toCSV());

                    } else if (tipoOperacao.equals("VENDA")) {
                        investidor.vender(ativo, qtd);

                        Movimentacao mov = new Movimentacao("VENDA", "LoteArq", ticker, qtd, ativo.getPreco());
                        Registrar.registrar(investidor.getId(), mov.toCSV());

                    } else {
                        throw new ErroTipoNaoPresente("Operação '" + tipoOperacao + "' inválida. Use COMPRA ou VENDA.");
                    }

                    sucessos++;
                    System.out.println("✅ Processado linha " + linhaAtual + ": " + tipoOperacao + " " + ticker);

                } catch (DadosInvalidosException | ErrosNumbersFormato | ErroTipoNaoPresente e) {
                    System.out.println(CoresMensagens.AMARELO + "⚠️ Falha linha " + linhaAtual + ": " + e.getMessage() + CoresMensagens.RESET);
                } catch (Exception e) {
                    System.out.println(CoresMensagens.VERMELHO + "Erro inesperado linha " + linhaAtual + ": " + e.getMessage() + CoresMensagens.RESET);
                }
            }
            System.out.println("\nProcessamento concluído. " + sucessos + " movimentações registradas.");

        } catch (IOException e) {
            throw new ErrosLeituraArq("Erro crítico na leitura: " + e.getMessage());
        }
    }
}
