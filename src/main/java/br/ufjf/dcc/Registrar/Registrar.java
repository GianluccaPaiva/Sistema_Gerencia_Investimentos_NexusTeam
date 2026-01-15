package br.ufjf.dcc.Registrar;

import br.ufjf.dcc.CoresMensagens.CoresMensagens;
import br.ufjf.dcc.Erros.ErrosLeituraArq;
import br.ufjf.dcc.Tools.Tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Registrar implements CoresMensagens {
    private static final String MOV_DIR_PATH = "movimentacoes";
    private static String idLimpo;
    private static final String MOV_DIR_INVESTIGACAO_PATH = "serInvestigado";

    private static void imprimirCabecalhoTabela(String titulo, String id) {
        System.out.println(CIANO + "\n=======================================================================================");
        System.out.printf("   %s | ID: %s %n", titulo, id);
        System.out.println("=======================================================================================" + RESET);
    }

    private static void imprimirLinhaFormatada(String[] col, String cor) {
        System.out.print(cor);
        String c0 = col.length > 0 ? col[0] : "-";
        String c1 = col.length > 1 ? col[1] : "-";
        String c2 = col.length > 2 ? col[2] : "-";
        String c3 = col.length > 3 ? col[3] : "-";
        String c4 = col.length > 4 ? col[4] : "-";
        String c5 = col.length > 5 ? col[5] : "-";
        String c6 = col.length > 6 ? col[6] : "-";

        System.out.printf("%-10s | %-8s | %-15s | %-8s | %-8s | %-10s | %-10s%n",
                c0, c1, c2, c3, c4, c5, c6);
        System.out.print(RESET);
    }

    private static String verificaGravidade(String id){
        float precoTotal = 0.0f;
        String status;
        String idLimpoLocal = Tools.idLimpo(id);
        File arquivo = new File(MOV_DIR_PATH, idLimpoLocal + ".csv");
        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha;
            leitor.readLine();

            while ((linha = leitor.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] colunas = linha.split(";");
                if (colunas.length > 5) {
                    float precoExec = Float.parseFloat(colunas[5]);
                    int quantidade = Integer.parseInt(colunas[4]);
                    precoTotal += precoExec * quantidade;
                }
            }
        } catch (IOException e) {
            status  = "CasoDesconhecido";
        }
        if (precoTotal >= 1000000.0f) {
            status ="CasoVermelho";
        } else if (precoTotal >= 200000.0f) {
            status = "CasoLaranja";
        } else if (precoTotal >= 50000.0f) {
            status = "CasoVerde";
        }
        else {
            status = "CasoDesconhecido";
        }
        return status;
    }

    public static void registrar(String idInvestidor, String conteudo) throws ErrosLeituraArq {
        idLimpo = Tools.idLimpo(idInvestidor);

        if (idLimpo.isEmpty()) {
            throw new ErrosLeituraArq(VERMELHO + "❌ CPF/CNPJ inválido." + RESET);
        }

        File diretorio = new File(MOV_DIR_PATH);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        File arquivo = new File(diretorio, idLimpo + ".csv");
        try (
                FileWriter fw = new FileWriter(arquivo, StandardCharsets.UTF_8, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)
        ) {
            if (arquivo.length() == 0) {
                out.println("ID_MOV;TIPO;INSTITUICAO;TICKER;QUANTIDADE;PRECO_EXEC;DATA");
            }


            out.println(conteudo);


            out.flush();

            System.out.println(VERDE + "✅ Movimentação anexada em: " + AMARELO + arquivo.getAbsolutePath() + RESET);

        } catch (IOException e) {
            throw new ErrosLeituraArq(VERMELHO + "❌ Falha crítica ao gravar: " + e.getMessage() + RESET);
        }
    }

    public static void exibirRegistro(String idInvestidor) {
        idLimpo = Tools.idLimpo(idInvestidor);
        File arquivo = new File(MOV_DIR_PATH, idLimpo + ".csv");

        if (!arquivo.exists() || arquivo.length() == 0) {
            System.out.println("\n" + AMARELO + "--- ⚠️ Sem histórico para o investidor: " + idInvestidor + " ---" + RESET);
            return;
        }

        imprimirCabecalhoTabela("EXTRATO GERAL", idInvestidor);

        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = leitor.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] colunas = linha.split(";");
                if (primeiraLinha) {
                    imprimirLinhaFormatada(colunas, ROXO);
                    System.out.println(BRANCO + "---------------------------------------------------------------------------------------" + RESET);
                    primeiraLinha = false;
                } else {
                    imprimirLinhaFormatada(colunas, VERDE);
                }
            }
        } catch (IOException e) {
            System.err.println(VERMELHO + "❌ Erro na leitura: " + e.getMessage() + RESET);
        }
        System.out.println(CIANO + "=======================================================================================\n" + RESET);
    }

    public static void exibirRegistroInvestigado( String idInvestidor) {
        String idLimpoLocal = Tools.idLimpo(idInvestidor);
        File diretorio = new File(MOV_DIR_INVESTIGACAO_PATH);
        File[] arquivos = diretorio.listFiles((dir, name) -> name.startsWith("INVEST_" + idLimpoLocal) && name.endsWith(".csv"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("\n" + AMARELO + "--- ⚠️ Histórico investigado não encontrado para o investidor: " + idInvestidor + " ---" + RESET);
            return;
        }

        for (File arquivo : arquivos) {
            imprimirCabecalhoTabela("EXTRATO INVESTIGADO", idInvestidor);

            try (BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
                String linha;
                boolean primeiraLinha = true;

                while ((linha = leitor.readLine()) != null) {
                    if (linha.isBlank()) continue;

                    String[] colunas = linha.split(";");
                    if (primeiraLinha) {
                        imprimirLinhaFormatada(colunas, ROXO);
                        System.out.println(BRANCO + "---------------------------------------------------------------------------------------" + RESET);
                        primeiraLinha = false;
                    } else {
                        imprimirLinhaFormatada(colunas, VERDE);
                    }
                }
            } catch (IOException e) {
                System.err.println(VERMELHO + "❌ Erro na leitura: " + e.getMessage() + RESET);
            }
            System.out.println(CIANO + "=======================================================================================\n" + RESET);
        }
    }

    public static void exibirRegistroPorTag(String idInvestidor, String tag, String conteudoTag) throws ErrosLeituraArq {
        String idLimpoLocal = Tools.idLimpo(idInvestidor);
        File arquivo = new File(MOV_DIR_PATH, idLimpoLocal + ".csv");

        if (!arquivo.exists() || arquivo.length() == 0) {
            System.out.println("\n" + AMARELO + "--- ⚠️ Histórico não encontrado para o investidor: " + idInvestidor + " ---" + RESET);
            return;
        }

        int indiceColuna;
        switch (tag.toUpperCase()) {
            case "TIPO": case "TAG": indiceColuna = 1; break;
            case "INSTITUICAO": indiceColuna = 2; break;
            case "TICKER": indiceColuna = 3; break;
            case "DATA": indiceColuna = 6; break;
            default: indiceColuna = 1; break;
        }

        boolean encontrouRegistro = false;

        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha;
            String header = leitor.readLine();
            StringBuilder resultados = new StringBuilder();

            while ((linha = leitor.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] colunas = linha.split(";");

                if (colunas.length > indiceColuna && colunas[indiceColuna].equalsIgnoreCase(conteudoTag)) {
                    if (!encontrouRegistro) {
                        imprimirCabecalhoTabela("FILTRADO POR " + tag.toUpperCase() + ": " + conteudoTag.toUpperCase(), idInvestidor);
                        if (header != null) {
                            imprimirLinhaFormatada(header.split(";"), ROXO);
                            System.out.println(BRANCO + "---------------------------------------------------------------------------------------" + RESET);
                        }
                    }
                    imprimirLinhaFormatada(colunas, VERDE);
                    encontrouRegistro = true;
                }
            }


            if (!encontrouRegistro) {
                System.out.println("\n" + AMARELO + "--- ℹ️ Não há registros com a tag [" + tag.toUpperCase() + "] contendo o valor: " + conteudoTag + " ---" + RESET);
            } else {
                System.out.println(CIANO + "=======================================================================================\n" + RESET);
            }

        } catch (IOException e) {
            throw new ErrosLeituraArq(VERMELHO + "❌ Erro ao filtrar por tag: " + e.getMessage() + RESET);
        }
    }

    public static void deletarRegistroInvestidor(String idInvestidor) {
        idLimpo = Tools.idLimpo(idInvestidor);
        File arquivo = new File(MOV_DIR_PATH, idLimpo + ".csv");
        if (arquivo.exists() && arquivo.delete()) {
            System.out.println(VERDE + "✅ Ficheiro de " + idInvestidor + " removido." + RESET);
        } else {
            System.out.println(AMARELO + "⚠️ Ficheiro não encontrado ou em uso." + RESET);
        }
    }

    public static void deletarTodasMovimentacoes() {
        File diretorio = new File(MOV_DIR_PATH);
        File[] arquivos = diretorio.listFiles((dir, name) -> name.endsWith(".csv"));
        if (arquivos != null) {
            for (File f : arquivos) f.delete();
            System.out.println(VERDE + "✅ Todas as movimentações foram limpas." + RESET);
        }
    }

    public static void deslocarMovimentoParaSerInvestigado(String idInvestidor) throws ErrosLeituraArq {

        String idLimpoLocal = Tools.idLimpo(idInvestidor);
        File arquivoOrigem = new File(MOV_DIR_PATH, idLimpoLocal + ".csv");

        if (!arquivoOrigem.exists()) {
            throw new ErrosLeituraArq(VERMELHO + "❌ Erro: O arquivo de movimentação não existe para o ID: " + idInvestidor + RESET);
        }

        File diretorioDestino = new File(MOV_DIR_INVESTIGACAO_PATH);
        if (!diretorioDestino.exists()) {
            diretorioDestino.mkdirs();
        }


        String verificado = verificaGravidade(idInvestidor);
        String novoNome = "INVEST_" + idLimpoLocal + "_" + verificado + ".csv";
        File arquivoDestino = new File(diretorioDestino, novoNome);

        try {
            Path origem = arquivoOrigem.toPath();
            Path destino = arquivoDestino.toPath();

            Files.move(origem, destino, StandardCopyOption.REPLACE_EXISTING);

            System.out.println(VERDE + "✅ Movimentação deslocada com sucesso!" + RESET);
            System.out.println(CIANO + "📂 De: " + AMARELO + arquivoOrigem.getName() + RESET);
            System.out.println(CIANO + "📂 Para: " + AMARELO + arquivoDestino.getName() + RESET);
            System.out.println(CIANO + "📍 Local: " + AMARELO + arquivoDestino.getAbsolutePath() + RESET);

        } catch (IOException e) {
            throw new ErrosLeituraArq(VERMELHO + "❌ Falha ao mover arquivo para investigação: " + e.getMessage() + RESET);
        }
    }

}