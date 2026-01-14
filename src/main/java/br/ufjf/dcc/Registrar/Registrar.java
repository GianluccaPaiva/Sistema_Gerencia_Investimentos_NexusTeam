package br.ufjf.dcc.Registrar;

import br.ufjf.dcc.CoresMensagens.CoresMensagens;
import br.ufjf.dcc.Erros.ErrosLeituraArq;
import java.io.*;

public class Registrar implements CoresMensagens {
    private static final String MOV_DIR_PATH = "src/main/dados/movimentacoes";

    // Método auxiliar para buscar caminho (opcional, mas mantido conforme seu código)
    private static String buscarRegistroPorCPFOuCNPJ(String idInvestidor) {
        String idLimpo = idInvestidor.replaceAll("[^0-9]", "");
        String nomeArquivo = idLimpo + ".csv";
        File arquivo = new File(MOV_DIR_PATH, nomeArquivo);
        return arquivo.exists() ? arquivo.getPath() : null;
    }

    public static void exibirRegistro(String idInvestidor) {
        String idLimpo = idInvestidor.replaceAll("[^0-9]", "");
        File arquivo = new File(MOV_DIR_PATH, idLimpo + ".csv");

        if (!arquivo.exists()) {
            System.out.println("\n" + AMARELO + "--- ⚠️ Histórico não encontrado para o investidor: " + idInvestidor + " ---" + RESET);
            return;
        }

        imprimirCabecalhoTabela("EXTRATO GERAL", idInvestidor);

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = leitor.readLine()) != null) {
                String[] colunas = linha.split(";");
                if (primeiraLinha) {
                    imprimirLinhaFormatada(colunas, ROXO);
                    System.out.println(BRANCO + "---------------------------------------------------------------------------------------" + RESET);
                    primeiraLinha = false;
                } else {
                    imprimirLinhaFormatada(colunas, BRANCO);
                }
            }
        } catch (IOException e) {
            System.err.println(VERMELHO + "❌ Erro ao ler o histórico: " + e.getMessage() + RESET);
        }
        System.out.println(CIANO + "=======================================================================================\n" + RESET);
    }

    public static void exibirRegistroPorTicker(String idInvestidor, String ticker) throws ErrosLeituraArq {
        String idLimpo = idInvestidor.replaceAll("[^0-9]", "");
        File arquivo = new File(MOV_DIR_PATH, idLimpo + ".csv");

        if (!arquivo.exists()) {
            System.out.println("\n" + AMARELO + "--- ⚠️ Histórico não encontrado para o investidor: " + idInvestidor + " ---" + RESET);
            return;
        }

        imprimirCabecalhoTabela("FILTRADO POR TICKER: " + ticker.toUpperCase(), idInvestidor);

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            String headerOriginal = leitor.readLine(); // Lê o cabeçalho do CSV
            if (headerOriginal != null) {
                imprimirLinhaFormatada(headerOriginal.split(";"), ROXO);
                System.out.println(BRANCO + "---------------------------------------------------------------------------------------" + RESET);
            }

            while ((linha = leitor.readLine()) != null) {
                String[] colunas = linha.split(";");
                if (colunas[3].equalsIgnoreCase(ticker)) {
                    imprimirLinhaFormatada(colunas,VERDE);
                }
            }
        } catch (IOException e) {
            throw new ErrosLeituraArq(VERMELHO + "❌ Erro ao ler o histórico: " + e.getMessage() + RESET);
        }
        System.out.println(CIANO + "=======================================================================================\n" + RESET);
    }

    public static void registrar(String idInvestidor, String conteudo) throws ErrosLeituraArq {
        String idLimpo = idInvestidor.replaceAll("[^0-9]", "");
        File diretorio = new File(MOV_DIR_PATH);

        if (!diretorio.exists()) {
            if (diretorio.mkdirs()) {
                System.out.println(CIANO+ "📁 Diretório 'movimentacoes' criado em: " + AMARELO + MOV_DIR_PATH + RESET);
            }
        }

        File arquivo = new File(diretorio, idLimpo + ".csv");
        boolean arquivoNovo = !arquivo.exists();

        try (FileWriter escritor = new FileWriter(arquivo, true)) {
            if (arquivoNovo) {
                escritor.write("ID_MOV;TIPO;INSTITUICAO;TICKER;QUANTIDADE;PRECO_EXEC;DATA\n");
            }
            escritor.write(conteudo + System.lineSeparator());
            System.out.println(VERDE + "✅ Registro salvo com sucesso em: " + AMARELO + arquivo.getName() + RESET);
        } catch (IOException e) {
            throw new ErrosLeituraArq("❌ Erro ao salvar registro: " + e.getMessage());
        }
    }

    public static void deletarRegistroInvestidor(String idInvestidor) {
        String idLimpo = idInvestidor.replaceAll("[^0-9]", "");
        File arquivo = new File(MOV_DIR_PATH, idLimpo + ".csv");

        if (arquivo.exists()) {
            if (arquivo.delete()) {
                System.out.println(VERDE + "✅ Histórico deletado para o investidor: " + BRANCO + idInvestidor + RESET);
            } else {
                System.err.println(VERMELHO + "❌ Erro ao deletar o histórico de " + idInvestidor + RESET);
            }
        } else {
            System.out.println(AMARELO + "⚠️ Nenhum histórico encontrado para: " + idInvestidor + RESET);
        }
    }

    public static void deletarTodasMovimentacoes() {
        File diretorio = new File(MOV_DIR_PATH);
        if (diretorio.exists() && diretorio.isDirectory()) {
            File[] arquivos = diretorio.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.delete()) {
                        System.out.println(VERMELHO + "🗑️  Deletado: " + BRANCO + arquivo.getName() + RESET);
                    }
                }
                System.out.println(VERDE + "✅ Limpeza concluída." + RESET);
            }
        } else {
            System.out.println(AMARELO + "⚠️ Diretório não encontrado." + RESET);
        }
    }

    private static void imprimirCabecalhoTabela(String titulo, String id) {
        System.out.println(CIANO + "\n=======================================================================================");
        System.out.printf("   %s | INVESTIDOR: %s %n", titulo, id);
        System.out.println("=======================================================================================" + RESET);
    }

    private static void imprimirLinhaFormatada(String[] col, String cor) {
        System.out.print(cor);
        System.out.printf("%-10s | %-8s | %-15s | %-8s | %-8s | %-10s | %-10s%n",
                col[0], col[1], col[2], col[3], col[4], col[5], col[6]);
        System.out.print(RESET);
    }
}