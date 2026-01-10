package br.ufjf.dcc.Menu;

import br.ufjf.dcc.CoresMensagens.CoresMensagens;
import br.ufjf.dcc.Erros.ErroInterrupcao;
import br.ufjf.dcc.Erros.ErrosNumbersFormato;
import br.ufjf.dcc.Mercado.Mercado;

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
                Thread.sleep(40);
            }
            System.out.println(RESET);

            // Spinner "Acessando cofre"
            String[] spinner = { "|", "/", "-", "\\" };
            for (int i = 0; i < 10; i++) {
                System.out.print(CIANO + "Acessando cofre seguro " + spinner[i % spinner.length] + RESET + "\r");
                System.out.flush();
                Thread.sleep(200);
            }
            System.out.println();

            // Barra de progresso "Abrindo cofres / Carregando ativos"
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
                Thread.sleep(60);
            }
            System.out.println();

            // pequena pausa final
            Thread.sleep(200);

        } catch (ErroInterrupcao e) {
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            throw new ErroInterrupcao("Animação interrompida.");
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("========== MENU PRINCIPAL ==========");
        System.out.println("1. Menu de Ativos");
        System.out.println("2. Menu de Investidores");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void menuAtivos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n---------- MENU DE ATIVOS ----------");
            System.out.println("1. Exibir Relatório: Todos os Ativos");
            System.out.println("2. Cadastrar Ativo Individualmente (A implementar)");
            System.out.println("3. Editar Ativo (A implementar)");
            System.out.println("4. Remover Ativo (A implementar)");
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
                default:
                    System.out.println(AMARELO + "Opção inválida!" + RESET);
            }
        }
    }
}