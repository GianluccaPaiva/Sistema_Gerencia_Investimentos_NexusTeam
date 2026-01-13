package br.ufjf.dcc.Menu;

import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.CoresMensagens.CoresMensagens;
import br.ufjf.dcc.Erros.DadosInvalidosException;
import br.ufjf.dcc.Erros.ErroInterrupcao;
import br.ufjf.dcc.Erros.ErrosNumbersFormato;
import br.ufjf.dcc.Investidor.Endereco;
import br.ufjf.dcc.Investidor.Investidor;
import br.ufjf.dcc.Investidor.PessoaFisica;
import br.ufjf.dcc.Investidor.PessoaJuridica;
import br.ufjf.dcc.Mercado.Mercado;
import br.ufjf.dcc.Tools.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu implements CoresMensagens {
    private static final Mercado mercado = new Mercado();
    private static final List<Investidor> investidores = new ArrayList<>();
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
                    menuInvestidores();
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
                mercado.adicaoAtivo("acao", dadosAcao);
                break;
            case 2:
                System.out.println("Insira separando por vírgula: Ticket, Nome, Preço, Qualificado (sim/não), Segmento, Ultimo Dividendo, Taxa de Admissão deve ser essa ordem");
                System.out.println("Exemplo: XPML11,XP Malls,150.75,(Não é só necessário a qualificação, mas será não de default),Shopping,0.85,1.5");
                String dadosFiis = scanner.nextLine();
                mercado.adicaoAtivo("fiis", dadosFiis);
                break;
            case 3:
                System.out.println("Insira separando por vírgula: Ticket, Nome, Preço, Bolsa de Negociação, Setor, Qualificado deve ser essa ordem");
                System.out.println("Exemplo:  AAPL,Apple Inc,145.30,NASDAQ,Tecnologia,(Não é só necessário a qualificação, mas será não de default)");
                String dadosStocks = scanner.nextLine();
                mercado.adicaoAtivo("stocks", dadosStocks);
                break;
            case 4:
                System.out.println("Insira separando por vírgula:Ticker,Nome,Preço (USD),Algoritmo Consenso,Quantidade Máxima");
                System.out.println("Exemplo: BTC,Bitcoin,30000.00,Proof of Work,21000000");
                String dadosCripto = scanner.nextLine();
                mercado.adicaoAtivo("criptomoeda", dadosCripto);
                break;
            case 5:
                System.out.println("Insira separando por vírgula:Ticker,Nome,Preço (R$),Tipo de Rendimento,Vencimento");
                System.out.println("Exemplo: LFT123,Tesouro Selic,1000.00,Selic,01/01/2030");
                String dadosTesouro = scanner.nextLine();
                mercado.adicaoAtivo("tesouro", dadosTesouro);
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

    private static void editarAtivo() {
        System.out.println(ROXO + "Editar Ativo ");
        System.out.println("Insira o ticker do ativo que deseja editar:" + RESET);
        String ticker = scanner.nextLine();
        Ativos ativo = mercado.buscaAtivo(ticker);
        if(ativo == null){
            System.out.println(AMARELO + "Ativo não encontrado."+ RESET);
            return;
        }
        System.out.println(ROXO+ "O que deseja editar?(digite o nome ou nomes dos atributos = novo nome separados por vírgula esceto nacionalidade, renda e tipo de ação)");
        System.out.println("Exemplo: nome=Novo Nome,preco=150.75" + RESET);
        String atributos = scanner.nextLine();
        mercado.editarAtivo(ativo, atributos);
    }

    private static void menuCarregamentoLote(){
        System.out.println("Carregamento de Ativos em Lote (A implementar)");
        System.out.println("Esse pacote aceita csv deve ter a primeira linha como cabeçalho com as propriedades do ativo em questão.");
        System.out.println("Informe o tipo de ativos a serem carregados:");
        System.out.println("1. Ações");
        System.out.println("2. Fundos Imobiliários");
        System.out.println("3. Stocks");
        System.out.println("4. Criptomoedas");
        System.out.println("5. Tesouros");
        System.out.print("Escolha uma opção (caso queira sair indique 0): ");
        String entrada = scanner.nextLine();
        int opcao;
        try {
            opcao = Integer.parseInt(entrada.trim());
            if (opcao == 0) {
                System.out.println("Voltando ao menu anterior...");
                return;
            }
        } catch (ErrosNumbersFormato e) {
            throw new ErrosNumbersFormato("Erro: insira um número.");
        }
        System.out.println("Escreva o caminho do arquivo de entrada:");
        String caminhoArquivo = scanner.nextLine();
        mercado.carregarAtivosLote(caminhoArquivo, opcao);
    }

    private static void menuTipoExibicaoAtivos(){
        System.out.println("Defina o tipo de exibição dos ativos:");
        System.out.println("1. Exibir todos os ativos");
        System.out.println("2. Exibir apenas ações");
        System.out.println("3. Exibir apenas fundos imobiliários");
        System.out.println("4. Exibir apenas stocks");
        System.out.println("5. Exibir apenas criptomoedas");
        System.out.println("6. Exibir apenas tesouros");
        System.out.println("0. Voltar ao Menu Anterior");
        System.out.print("Escolha uma opção: ");
        String entrada = scanner.nextLine();
        int opcao;
        try {
            opcao = Integer.parseInt(entrada.trim());
            if (opcao == 0) {
                System.out.println("Voltando ao menu anterior...");
            }
        } catch (ErrosNumbersFormato e) {
            throw new ErrosNumbersFormato("Erro: insira um número.");
        }
        mercado.listarAtivosPorTipo(opcao);
    }
    private static void menuAtivos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n---------- MENU DE ATIVOS ----------");
            System.out.println("1. Exibir Relatório: Todos os Ativos");
            System.out.println("2. Cadastrar Ativo Individualmente");
            System.out.println("3. Cadastrar Ativos via Arquivo");
            System.out.println("4. Buscar Ativo por Ticker ou Nome");
            System.out.println("5. Editar Ativo");
            System.out.println("6. Remover Ativo ");
            System.out.println("0. Voltar ao Menu Anterior");
            System.out.print("Escolha uma opção: ");

            String entrada = scanner.nextLine();
            try {
                opcao = Integer.parseInt(entrada.trim());
            } catch (ErrosNumbersFormato e) {
                System.out.println(AMARELO+"Erro: insira um número."+ RESET);
                menuAtivos();
            }

            switch (opcao) {
                case 1:
                    menuTipoExibicaoAtivos();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    System.out.println();
                    break;
                case 2:
                    addAtivoIndividuo();
                    break;

                    case 3:
                        menuCarregamentoLote();
                        break;
                case 4:
                    sistemaBusca();
                    break;

                case 5:
                    editarAtivo();
                    break;

                case 6:
                    menuRemoverAtivo();
                    break;

                default:
                    System.out.println(AMARELO + "Opção inválida!" + RESET);
            }
        }
    }

    private static int lerNumeroInteiro() {
        while (true) {
            try {
                String entrada = scanner.nextLine();
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.print(AMARELO + "Entrada inválida. Digite um número inteiro: " + RESET);
            }
        }
    }

    private static double lerNumeroDecimal() {
        while (true) {
            try {
                String entrada = scanner.nextLine();
                return Double.parseDouble(entrada.replace(",", ".").trim());
            } catch (NumberFormatException e) {
                System.out.print(AMARELO + "Entrada inválida. Digite um valor numérico: " + RESET);
            }
        }
    }

    // Talvez colocar no tools esses dois cidadãos acima?

    private static void menuInvestidores() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println(ROXO + "\n========== MENU INVESTIDORES ==========" + RESET);
            System.out.println("1. Cadastrar Investidor");
            System.out.println("2. Cadastrar Investidor em Lote (Arquivo)");
            System.out.println("3. Exibir Todos Investidores");
            System.out.println("4. Excluir Investidores (Lista de CPFs)");
            System.out.println("5. Selecionar Investidor por CPF ou CNPJ");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = lerNumeroInteiro();

            switch (opcao) {
                case 1:
                    cadastrarInvestidor();
                    break;
                case 2:
                    System.out.println(AMARELO + "Funcionalidade 'Cadastrar em Lote' a ser implementada." + RESET);
                    break;
                case 3:
                    listarInvestidores();
                    break;
                case 4:
                    System.out.println(AMARELO + "Funcionalidade 'Excluir em Lote' a ser implementada." + RESET);
                    break;
                case 5:
                    System.out.println(AMARELO + "Funcionalidade 'Selecionar Investidor' será a proxima vitima hahahahahaha." + RESET);
                    // buscarInvestidorEOperar();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println(AMARELO + "Opção inválida!" + RESET);
            }
        }
    }

    private static void cadastrarInvestidor() {
        System.out.println(VERDE + "\n--- Cadastro de Investidor ---" + RESET);
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Data de Nascimento: ");
            String nascimento = scanner.nextLine();

            System.out.println("--- Endereço ---");
            System.out.print("Rua: ");
            String rua = scanner.nextLine();
            System.out.print("Número: ");
            int numero = lerNumeroInteiro();
            System.out.print("Bairro: ");
            String bairro = scanner.nextLine();
            System.out.print("Cidade: ");
            String cidade = scanner.nextLine();
            System.out.print("Estado: ");
            String estado = scanner.nextLine();
            System.out.print("CEP: ");
            String cep = scanner.nextLine();

            Endereco endereco = new Endereco(rua, cidade, estado, cep, bairro, numero);

            System.out.print("Patrimônio Inicial: ");
            double patrimonio = lerNumeroDecimal();

            System.out.println("Tipo: [1] Pessoa Física | [2] Pessoa Jurídica");
            int tipo = lerNumeroInteiro();

            if (tipo == 1) {
                System.out.print("CPF: ");
                String cpf = scanner.nextLine();
                System.out.print("Perfil (Conservador, Moderado, Arrojado): ");
                String perfil = scanner.nextLine();

                PessoaFisica pf = new PessoaFisica(nome, cpf, telefone, nascimento, endereco, patrimonio, perfil);
                investidores.add(pf);
                System.out.println(VERDE + "Pessoa Física cadastrada com sucesso!" + RESET);

            } else if (tipo == 2) {
                System.out.print("CNPJ: ");
                String cnpj = scanner.nextLine();
                System.out.print("Razão Social: ");
                String razao = scanner.nextLine();

                PessoaJuridica pj = new PessoaJuridica(nome, cnpj, telefone, nascimento, endereco, patrimonio, razao);
                investidores.add(pj);
                System.out.println(VERDE + "Investidor Institucional cadastrado com sucesso!" + RESET);
            } else {
                System.out.println(AMARELO + "Tipo inválido." + RESET);
            }

        } catch (DadosInvalidosException e) {
            System.out.println(VERMELHO + "Erro na validação: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(VERMELHO + "Erro inesperado: " + e.getMessage() + RESET);
        }
    }

    private static void listarInvestidores() {
        System.out.println(ROXO + "\n--- Lista de Investidores ---" + RESET);
        if (investidores.isEmpty()) {
            System.out.println(AMARELO + "Nenhum investidor cadastrado." + RESET);
        } else {
            for (Investidor inv : investidores) {
                System.out.println(inv.toString());
            }
        }
    }

}