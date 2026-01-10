package br.ufjf.dcc.Main;
import br.ufjf.dcc.Erros.ErrosNumbersFormato;
import br.ufjf.dcc.Mercado.Mercado;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Mercado mercado = new Mercado();

        // 1. Inicialização: Carregar ativos em lote a partir dos ficheiros CSV
        System.out.println("SISTEMA DE GESTÃO DE INVESTIMENTOS NEXUSTEAM");
        System.out.println("A carregar base de dados de ativos...");
        mercado.carregarBaseDeDados();
        System.out.println("Carga concluída com sucesso!\n");

        int opcao = -1;

        while (true) {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        // Menu de Ativos (Conforme Requisito)
                        menuAtivos(mercado, scanner);
                        break;
                    case 2:
                        System.out.println("Menu de Investidores - (A implementar conforme lógica de Investidor)");
                        break;
                    case 0:
                        System.out.println("A encerrar o sistema...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                throw new ErrosNumbersFormato("Erro: Por favor, insira um número válido.");
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("========== MENU PRINCIPAL ==========");
        System.out.println("1. Menu de Ativos");
        System.out.println("2. Menu de Investidores");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void menuAtivos(Mercado mercado, Scanner scanner) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n---------- MENU DE ATIVOS ----------");
            System.out.println("1. Exibir Relatório: Todos os Ativos");
            System.out.println("2. Cadastrar Ativo Individualmente (A implementar)");
            System.out.println("3. Editar Ativo (A implementar)");
            System.out.println("4. Remover Ativo (A implementar)");
            System.out.println("0. Voltar ao Menu Anterior");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        mercado.listarTodosAtivos();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                throw new ErrosNumbersFormato("Erro: Insira um número.");
            }
        }
    }
}