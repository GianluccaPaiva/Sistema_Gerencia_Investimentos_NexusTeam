package br.ufjf.dcc.Menu;

import br.ufjf.dcc.CoresMensagens.CoresMensagens;
import br.ufjf.dcc.Erros.ErroInterrupcao;
import br.ufjf.dcc.Erros.ErrosNumbersFormato;
import br.ufjf.dcc.Mercado.Mercado;
import br.ufjf.dcc.Tools.Tools;

import java.util.Scanner;

public class Menu implements CoresMensagens {
    private static final Mercado mercado = new Mercado();
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean ativar = true;
    public static void run() {
        int opcao;
        while (true) {
            inciarAnimacao(ativar);
            ativar = false;
            exibirMenuPrincipal();
            String entrada = scanner.nextLine();
            try {
                opcao = Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println(AMARELO + "Opção inválida, insira um número." + RESET);
                continue;
            }

            if (opcao == 0) {
                System.out.println("A encerrar o sistema...");
                scanner.close();
                return;
            }

            switch (opcao) {
                case 1:
                    menuAtivos();
                    break;
                case 2:
                    System.out.println("Menu de Investidores - (A implementar)");
                    break;
                default:
                    System.out.println(AMARELO + "Opção inválida!"+ RESET);
            }
        }
    }

    private static void inciarAnimacao(boolean ativar) {
        if (ativar) {
            try {
                animacaoEntrada();
            } catch (ErroInterrupcao e) {
                System.out.println(VERMELHO + "Erro na animação de entrada: " + e.getMessage() + RESET);
            }
        }
    }

    private static void animacaoEntrada() throws ErroInterrupcao {
        try {
            // Título animado
            String titulo = "NEXUSBANK - SISTEMA DE GESTÃO";
            System.out.print(AZUL);
            for (int i = 0; i < titulo.length(); i++) {
                System.out.print(titulo.charAt(i));
                System.out.flush();
                Tools.espera(0.04f);
            }
            System.out.println(RESET);

            String[] spinner = { "|", "/", "-", "\\" };
            for (int i = 0; i < 10; i++) {
                System.out.print(CIANO + "Acessando cofre seguro " + spinner[i % spinner.length] + RESET + "\r");
                System.out.flush();
                Tools.espera(0.2f);
            }
            System.out.println();

            int total = 30;
            System.out.print(AMARELO + "Abrindo cofres: [" + RESET);
            for (int i = 0; i <= total; i++) {
                int filled = i;
                int empty = total - filled;
                StringBuilder bar = new StringBuilder();
                bar.append(AMARELO);
                for (int j = 0; j < filled; j++) bar.append("█");
                for (int j = 0; j < empty; j++) bar.append(" ");
                bar.append(RESET);
                int percent = (i * 100) / total;
                System.out.print("\r" + AMARELO + "Abrindo cofres: [" + RESET + bar.toString() + AMARELO + "] " + percent + "% " + RESET);
                System.out.flush();
                Tools.espera(0.06f);
            }
            System.out.println();

            // pequena pausa final
            Tools.espera(0.2f);

        }
        catch (Exception e) {
            throw new ErroInterrupcao("Animação interrompida: " + e.getMessage());
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("========== MENU PRINCIPAL ==========");
        System.out.println("1. Menu de Ativos");
        System.out.println("2. Menu de Investidores");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void addAtivoIndividuo() {
        System.out.println(VERDE+"Iniciando cadastro de ativo individualmente...");
        System.out.println("Defina o tipo de ativo a ser cadastrado:");
        System.out.println("1. Ação");
        System.out.println("2. Fundo Imobiliário");
        System.out.println("3. Stocks ");
        System.out.println("4. Criptomoeda");
        System.out.println("5. Tesouro");
        System.out.println("0. Voltar ao Menu Anterior");
        System.out.print("Escolha uma opção: ");
        String entrada = scanner.nextLine();
        int opcao;
        try {
            opcao = Integer.parseInt(entrada.trim());
        } catch (ErrosNumbersFormato e) {
            throw new ErrosNumbersFormato("Erro: insira um número.");
        }
        System.out.println(VERDE+"Opção selecionada: " + opcao + RESET);
        switch (opcao){
            case 1:
                System.out.println("Insira separando por vírgula: Ticket, Nome, Preço, Qualificado (sim/não) deve ser essa ordem");
                System.out.println("Exemplo: PETR4,Petrobras,28.50,(Não é só necessário a qualificação, mas será não de default)");
                String dadosAcao = scanner.nextLine();
                mercado.estruturarAtivo("acao", dadosAcao);
                break;
            case 2:
                System.out.println("Insira separando por vírgula: Ticket, Nome, Preço, Qualificado (sim/não), Segmento, Ultimo Dividendo, Taxa de Admissão deve ser essa ordem");
                System.out.println("Exemplo: XPML11,XP Malls,150.75,(Não é só necessário a qualificação, mas será não de default),Shopping,0.85,1.5");
                String dadosFiis = scanner.nextLine();
                mercado.estruturarAtivo("fiis", dadosFiis);
                break;
            case 3:
                System.out.println("Insira separando por vírgula: Ticket, Nome, Preço, Bolsa de Negociação, Setor, Qualificado deve ser essa ordem");
                System.out.println("Exemplo:  AAPL,Apple Inc,145.30,NASDAQ,Tecnologia,(Não é só necessário a qualificação, mas será não de default)");
                String dadosStocks = scanner.nextLine();
                mercado.estruturarAtivo("stocks", dadosStocks);
                break;
            case 4:
                System.out.println("Insira separando por vírgula:Ticker,Nome,Preço (USD),Algoritmo Consenso,Quantidade Máxima");
                System.out.println("Exemplo: BTC,Bitcoin,30000.00,Proof of Work,21000000");
                String dadosCripto = scanner.nextLine();
                mercado.estruturarAtivo("criptomoeda", dadosCripto);
                break;
            case 5:
                System.out.println("Insira separando por vírgula:Ticker,Nome,Preço (R$),Tipo de Rendimento,Vencimento");
                System.out.println("Exemplo: LFT123,Tesouro Selic,1000.00,Selic,01/01/2030");
                String dadosTesouro = scanner.nextLine();
                mercado.estruturarAtivo("tesouro", dadosTesouro);
                break;
            case 0:
                System.out.println("Voltando ao menu anterior...");
                break;
            default:
                System.out.println(AMARELO+"Opção inválida!"+ RESET);
        }
    }
    private static void sistemaBusca(){
        System.out.println(ROXO);
        System.out.println("Sistema de busca de ativos");
        System.out.println("Insira o ticker ou nome do ativo que deseja buscar:");
        Scanner entradaScanner = new Scanner(System.in);
        String entrada = entradaScanner.nextLine();
        System.out.println(RESET);
        mercado.buscaAtivo(entrada);

    }
    private static void menuRemoverAtivo() {
        System.out.println(CIANO + "Remover Ativo" + RESET);
        System.out.println("Insira o ticker do ativo que deseja remover:");
        String ticker = scanner.nextLine();
        mercado.removerAtivo(ticker);
    }

    private static void menuAtivos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n---------- MENU DE ATIVOS ----------");
            System.out.println("1. Exibir Relatório: Todos os Ativos");
            System.out.println("2. Cadastrar Ativo Individualmente");
            System.out.println("3. Cadastrar Ativos via Arquivo (A implementar)");
            System.out.println("4. Buscar Ativo por Ticker ou Nome");
            System.out.println("5. Editar Ativo (A implementar)");
            System.out.println("6. Remover Ativo (A implementar)");
            System.out.println("0. Voltar ao Menu Anterior");
            System.out.print("Escolha uma opção: ");

            String entrada = scanner.nextLine();
            try {
                opcao = Integer.parseInt(entrada.trim());
            } catch (ErrosNumbersFormato e) {
                System.out.println(AMARELO+"Erro: insira um número."+ RESET);
                continue;
            }

            switch (opcao) {
                case 1:
                    mercado.listarTodosAtivos();
                    break;
                case 0:
                    break;
                case 2:
                    addAtivoIndividuo();
                    break;
                case 6:
                    menuRemoverAtivo();
                    break;

                case 4:
                    sistemaBusca();
                    break;
                default:
                    System.out.println(AMARELO + "Opção inválida!" + RESET);
            }
        }
    }
}