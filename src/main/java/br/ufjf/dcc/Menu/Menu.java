package br.ufjf.dcc.Menu;
import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.CoresMensagens.CoresMensagens;
import br.ufjf.dcc.Erros.*;
import br.ufjf.dcc.Investidor.Endereco;
import br.ufjf.dcc.Investidor.Investidor;
import br.ufjf.dcc.Investidor.PessoaFisica;
import br.ufjf.dcc.Investidor.PessoaJuridica;
import br.ufjf.dcc.LeitorLotes.LeitorLotes;
import br.ufjf.dcc.Mercado.Mercado;
import br.ufjf.dcc.Movimentacao.Movimentacao;
import br.ufjf.dcc.Registrar.Registrar;
import br.ufjf.dcc.Tools.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.ufjf.dcc.Registrar.Registrar.deslocarMovimentoParaSerInvestigado;
import static br.ufjf.dcc.Tools.Tools.lerNumeroDecimal;
import static br.ufjf.dcc.Tools.Tools.lerNumeroInteiro;

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
        }catch (NumberFormatException e){
                System.out.println(AMARELO + "Erro: insira um número." + RESET);
                addAtivoIndividuo();
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
            System.out.println("Escreva o caminho do arquivo de entrada:");
            String caminhoArquivo = scanner.nextLine();
            mercado.carregarAtivosLote(caminhoArquivo, opcao);
        } catch (NumberFormatException e) {
            System.out.println(AMARELO+"Erro: insira um número."+RESET);
        }
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
            mercado.listarAtivosPorTipo(opcao);
        } catch (NumberFormatException e) {
            System.out.println(AMARELO+"Erro: insira um número." + RESET);
            menuTipoExibicaoAtivos();
        }
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
            } catch (NumberFormatException e) {
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
            String entrada = scanner.nextLine();
            opcao = lerNumeroInteiro(entrada);

            switch (opcao) {
                case 1:
                    cadastrarInvestidor();
                    break;
                case 2:
                    cadastrarInvestidorLote();
                    break;
                case 3:
                    listarInvestidores();
                    break;
                case 4:
                    excluirInvestidorEmLote();
                    break;
                case 5:
                    buscarInvestidorEOperar();
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
            String numeroStr = scanner.nextLine();
            int numero = lerNumeroInteiro(numeroStr);
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
            String patrimonioStr = scanner.nextLine();
            double patrimonio = lerNumeroDecimal(patrimonioStr);

            System.out.println("Tipo: [1] Pessoa Física | [2] Pessoa Jurídica");
            String tipoStr = scanner.nextLine();
            int tipo = lerNumeroInteiro(tipoStr);

            if (tipo == 1) {
                System.out.print("CPF: ");
                String cpf = scanner.nextLine();
                System.out.print("Perfil (Conservador, Moderado, Arrojado): ");
                String perfil = scanner.nextLine();

                Investidor pf = new PessoaFisica(nome, cpf, telefone, nascimento, endereco, patrimonio, perfil);
                investidores.add(pf);
                System.out.println(VERDE + "Pessoa Física cadastrada com sucesso!" + RESET);

            } else if (tipo == 2) {
                System.out.print("CNPJ: ");
                String cnpj = scanner.nextLine();
                System.out.print("Razão Social: ");
                String razao = scanner.nextLine();

                Investidor pj = new PessoaJuridica(nome, cnpj, telefone, nascimento, endereco, patrimonio, razao);
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

    private static void buscarInvestidorEOperar() {
        System.out.print("Digite o CPF ou CNPJ do investidor: ");
        String id = scanner.nextLine();

        Investidor investidorEncontrado = null;

        for (Investidor inv : investidores) {
            if (inv.getId().equals(id)) {
                investidorEncontrado = inv;
                break;
            }
        }

        if (investidorEncontrado == null) {
            System.out.println(AMARELO + "Investidor não encontrado." + RESET);
        } else {
            menuOperacoesInvestidor(investidorEncontrado);
        }
    }

    private static void menuOperacoesInvestidor(Investidor inv) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println(AZUL + "\n--- Painel de " + inv.getNome() + " ---" + RESET);
            System.out.println("1. Editar informações do investidor");
            System.out.println("2. Excluir este investidor");
            System.out.println("3. Exibir Carteira de Ativos do Investidor");
            System.out.println("4. Exibir Valor Total Gasto");
            System.out.println("5. Exibir Valor Total Atual");
            System.out.println("6. Exibir Porcentagens (Renda Fixa/Var e Nac/Int)");
            System.out.println("7. Salvar Relatório da Carteira em JSON");
            System.out.println("8. Adicionar Movimentação de Compra");
            System.out.println("9. Adicionar Movimentação de Venda");
            System.out.println("10. Adicionar Lote de Movimentações");
            System.out.println("0. Voltar ao menu anterior");
            System.out.print("Escolha uma opção: ");

            opcao = lerNumeroInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    editarInvestidor(inv);
                    break;
                case 2:
                    opcao = excluirInvestidorAtual(inv);
                    break;
                case 3:
                    inv.exibirCarteira();
                    break;
                case 4:
                    System.out.printf("Valor Total Gasto: R$ %.2f\n", inv.getCarteira().valorTotalGasto());
                    break;
                case 5:
                    System.out.printf("Valor Total Atual: R$ %.2f\n", inv.getCarteira().valorTotalCarteira());
                    break;
                case 6:
                    double[] rfRv = inv.getCarteira().porcentagemRendaFixaVariavel();
                    double[] nacInt = inv.getCarteira().porcentagemNacionalInternacional();
                    System.out.printf("Renda Fixa: %.1f%% | Renda Variável: %.1f%%\n", rfRv[0], rfRv[1]);
                    System.out.printf("Nacional:   %.1f%% | Internacional:  %.1f%%\n", nacInt[0], nacInt[1]);
                    break;
                case 7:
                    salvarRelatorioJson(inv);
                    break;
                case 8:
                    realizarCompra(inv);
                    break;
                case 9:
                    realizarVenda(inv);
                    break;
                case 10:
                    addMovimentacaoLote(inv);
                    break;
                case 0:
                    break;
                default:
                    System.out.println(AMARELO + "Opção inválida!" + RESET);
            }
        }
    }

    private static void realizarCompra(Investidor inv) {
        System.out.println("\n--- Nova Compra ---");
        System.out.print("Digite o Ticker do ativo: ");
        String ticker = scanner.nextLine();

        Ativos ativo = mercado.buscaAtivo(ticker);

        if (ativo == null) {
            System.out.println(AMARELO + "Ativo não encontrado no mercado. Cadastre-o primeiro no Menu de Ativos." + RESET);
            return;
        }

        System.out.println("Ativo selecionado: " + ativo.getNome() + " | Preço: R$ " + ativo.getPreco());
        System.out.print("Quantidade a comprar: ");
        float qtd = (float) lerNumeroDecimal(scanner.nextLine());

        try {
            inv.comprar(ativo, qtd, ativo.getPreco());

            Movimentacao mov = new Movimentacao("COMPRA", "NexusBank", ativo.getTicker(), qtd, ativo.getPreco());
            Registrar.registrar(inv.getId(), mov.toCSV());

            System.out.println(VERDE + "Compra realizada com sucesso!" + RESET);
            System.out.println(ROXO + "Recibo da Operação:" + RESET);
            System.out.println(mov);

        } catch (DadosInvalidosException e) {
            System.out.println(VERMELHO + "Falha na compra: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(VERMELHO + "Erro inesperado: " + e.getMessage() + RESET);
        }
    }

    private static void realizarVenda(Investidor inv) {
        System.out.println("\n--- Venda de Ativos ---");
        System.out.print("Digite o Ticker do ativo para vender: ");
        String ticker = scanner.nextLine();

        Ativos ativo = mercado.buscaAtivo(ticker);

        if (ativo == null) {
            System.out.println(AMARELO + "Ativo não identificado no mercado." + RESET);
            return;
        }

        System.out.print("Quantidade a vender: ");
        float qtd = (float) lerNumeroDecimal(scanner.nextLine());

        try {
            inv.vender(ativo, qtd);

            Movimentacao mov = new Movimentacao("VENDA", "NexusBank", ativo.getTicker(), qtd, ativo.getPreco());
            Registrar.registrar(inv.getId(), mov.toCSV());

            System.out.println(VERDE + "Venda realizada com sucesso!" + RESET);
            System.out.println(ROXO + "Recibo da Operação:" + RESET);
            System.out.println(mov);

        } catch (DadosInvalidosException e) {
            System.out.println(VERMELHO + "Erro na venda: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(VERMELHO + "Erro inesperado: " + e.getMessage() + RESET);
        }
    }

    private static void excluirInvestidorEmLote() {
        System.out.println(ROXO + "Exclusão em Lote (Manual)" + RESET);
        System.out.println("Digite os CPFs ou CNPJs separados por vírgula (ex: 111,222,333):");
        String entradaLista = scanner.nextLine();

        String[] idsParaRemover = entradaLista.split(",");
        int contagemRemovidos = 0;

        for (String idBruto : idsParaRemover) {
            String idLimpo = idBruto.trim();

            boolean removeu = investidores.removeIf(inv -> inv.getId().equals(idLimpo));

            if (removeu) {
                System.out.println(VERDE + "Removido: " + idLimpo + RESET);
                try {
                    deslocarMovimentoParaSerInvestigado(idLimpo);
                } catch (ErrosLeituraArq e) {
                    System.out.println(VERMELHO + "Erro ao deslocar movimentações para investigação: " + e.getMessage() + RESET);
                }
                contagemRemovidos++;
            } else {
                System.out.println(AMARELO + "Não encontrado: " + idLimpo + RESET);
            }
        }
        System.out.println("Total de investidores removidos: " + contagemRemovidos);
    }

    private static void editarInvestidor(Investidor inv) {
        int opcaoEdit = -1;
        while (opcaoEdit != 0) {
            System.out.println(CIANO + "\n--- Editar Dados de " + inv.getNome() + " ---" + RESET);
            System.out.println("1. Alterar Nome");
            System.out.println("2. Alterar Data de Nascimento");
            System.out.println("3. Alterar Telefone");
            System.out.println("4. Alterar Endereço Completo");
            System.out.println("5. Alterar Patrimônio (Ajuste manual de saldo)");

            if (inv instanceof PessoaFisica) {
                System.out.println("6. Alterar Perfil (Conservador/Moderado/Arrojado)");
            } else if (inv instanceof PessoaJuridica) {
                System.out.println("6. Alterar Razão Social");
            }

            System.out.println("0. Voltar");
            System.out.print("Escolha o dado para alterar: ");

            opcaoEdit = lerNumeroInteiro(scanner.nextLine());

            try {
                switch (opcaoEdit) {
                    case 1:
                        System.out.print("Novo Nome: ");
                        inv.setNome(scanner.nextLine());
                        System.out.println(VERDE + "Nome atualizado!" + RESET);
                        break;
                    case 2:
                        System.out.print("Nova Data de Nascimento: ");
                        inv.setDataNascimento(scanner.nextLine());
                        System.out.println(VERDE + "Data de Nascimento atualizada!" + RESET);
                        break;
                    case 3:
                        System.out.print("Novo Telefone: ");
                        inv.setTelefone(scanner.nextLine());
                        System.out.println(VERDE + "Telefone atualizado!" + RESET);
                        break;
                    case 4:
                        System.out.println("--- Novo Endereço ---");
                        System.out.print("Rua: "); String rua = scanner.nextLine();
                        System.out.print("Número: "); int num = lerNumeroInteiro(scanner.nextLine());
                        System.out.print("Bairro: "); String bairro = scanner.nextLine();
                        System.out.print("Cidade: "); String cid = scanner.nextLine();
                        System.out.print("Estado: "); String est = scanner.nextLine();
                        System.out.print("CEP: "); String cep = scanner.nextLine();

                        Endereco novoEnd = new Endereco(rua, cid, est, cep, bairro, num);
                        inv.setEndereco(novoEnd);
                        System.out.println(VERDE + "Endereço atualizado!" + RESET);
                        break;
                    case 5:
                        System.out.print("Novo valor de Patrimônio: ");
                        double novoPatrimonio = lerNumeroDecimal(scanner.nextLine());
                        inv.setPatrimonio(novoPatrimonio);
                        System.out.println(VERDE + "Patrimônio atualizado!" + RESET);
                        break;
                    case 6:
                        if (inv instanceof PessoaFisica) {
                            System.out.print("Novo Perfil (Conservador/Moderado/Arrojado): ");
                            String perfil = scanner.nextLine();
                            ((PessoaFisica) inv).setPerfil(perfil);
                            System.out.println(VERDE + "Perfil atualizado!" + RESET);
                        } else if (inv instanceof PessoaJuridica) {
                            System.out.print("Nova Razão Social: ");
                            String razao = scanner.nextLine();
                            ((PessoaJuridica) inv).setRazaoSocial(razao);
                            System.out.println(VERDE + "Razão Social atualizada!" + RESET);
                        }
                        break;

                    case 0:
                        break;

                    default:
                        System.out.println(AMARELO + "Opção inválida." + RESET);
                }
            } catch (DadosInvalidosException e) {
                System.out.println(VERMELHO + "Erro ao atualizar: " + e.getMessage() + RESET);
            } catch (Exception e) {
                System.out.println(VERMELHO + "Erro inesperado: " + e.getMessage() + RESET);
            }
        }
    }

    private static int excluirInvestidorAtual(Investidor inv){
        System.out.print(VERMELHO + "Tem certeza que deseja excluir este investidor? (S/N): " + RESET);
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            investidores.remove(inv);
            try{
                deslocarMovimentoParaSerInvestigado(inv.getId());
            } catch (ErrosLeituraArq e) {
                System.out.println(VERMELHO + "Erro ao deslocar movimentações para investigação: " + e.getMessage() + RESET);
            }
            System.out.println(VERDE + "Investidor removido com sucesso." + RESET);
            return 0;
        } else {
            System.out.println("Operação cancelada.");
        }
        return -1;
    }

    private static void salvarRelatorioJson(Investidor inv) {
        String nomePasta = "relatorios";
        String nomeArquivo = "Relatorio_" + inv.getNome().replace(" ", "_") + "_" + inv.getId() + ".json";


        java.io.File diretorio = new java.io.File(nomePasta);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        java.io.File arquivoDestino = new java.io.File(diretorio, nomeArquivo);

        try (java.io.FileWriter writer = new java.io.FileWriter(arquivoDestino)) {
            StringBuilder json = new StringBuilder();
            json.append("{\n");

            json.append("  \"investidor\": \"").append(inv.getNome()).append("\",\n");
            json.append("  \"id\": \"").append(inv.getId()).append("\",\n");

            double totalGasto = 0;
            double totalAtual = inv.getCarteira().valorTotalCarteira();
            for(var item : inv.getCarteira().getAtivos()){
                totalGasto += item.getValorPagoTotal();
            }

            json.append("  \"patrimonio_total_gasto\": ").append(String.format(java.util.Locale.US, "%.2f", totalGasto)).append(",\n");
            json.append("  \"patrimonio_total_atual\": ").append(String.format(java.util.Locale.US, "%.2f", totalAtual)).append(",\n");

            double[] rfRv = inv.getCarteira().porcentagemRendaFixaVariavel();
            double[] nacInt = inv.getCarteira().porcentagemNacionalInternacional();

            json.append("  \"renda_fixa_percent\": ").append(String.format(java.util.Locale.US, "%.1f", rfRv[0])).append(",\n");
            json.append("  \"renda_variavel_percent\": ").append(String.format(java.util.Locale.US, "%.1f", rfRv[1])).append(",\n");
            json.append("  \"nacional_percent\": ").append(String.format(java.util.Locale.US, "%.1f", nacInt[0])).append(",\n");
            json.append("  \"internacional_percent\": ").append(String.format(java.util.Locale.US, "%.1f", nacInt[1])).append(",\n");

            json.append("  \"ativos_carteira\": [\n");

            var listaAtivos = inv.getCarteira().getAtivos();
            for (int i = 0; i < listaAtivos.size(); i++) {
                var item = listaAtivos.get(i);
                json.append("    {\n");
                json.append("      \"ticker\": \"").append(item.getAtivo().getTicker()).append("\",\n");
                json.append("      \"quantidade\": ").append(item.getQtd()).append(",\n");
                json.append("      \"valor_medio_compra\": ").append(String.format(java.util.Locale.US, "%.2f", item.getValorPagoTotal() / item.getQtd())).append(",\n");
                json.append("      \"valor_total_atual\": ").append(String.format(java.util.Locale.US, "%.2f", item.getValorAtualTotal())).append("\n");

                json.append("    }");
                if (i < listaAtivos.size() - 1) json.append(",");
                json.append("\n");
            }

            json.append("  ]\n");
            json.append("}");

            writer.write(json.toString());

            System.out.println(VERDE + "✅ Relatório salvo em: " + arquivoDestino.getAbsolutePath() + RESET);

        } catch (java.io.IOException e) {
            System.out.println(VERMELHO + "❌ Erro ao salvar relatório: " + e.getMessage() + RESET);
        }
    }

    private static void cadastrarInvestidorLote() {
        System.out.println(CIANO + "\n--- Cadastro de Investidores em Lote ---" + RESET);
        System.out.println("Diretório de leitura: lotes/investidoresLotes/");
        System.out.println("Formato CSV: TIPO;NOME;ID;TEL;NASC;RUA;NUM;BAIRRO;CIDADE;EST;CEP;PATRIMONIO;EXTRA");
        System.out.print("Digite o NOME do arquivo (ex: novos.csv): ");
        String nomeArquivoInv = scanner.nextLine();

        try {
            List<Investidor> novos = LeitorLotes.carregarInvestidores(nomeArquivoInv);

            if (!novos.isEmpty()) {
                investidores.addAll(novos);
                System.out.println(VERDE + "✅ Lote finalizado! Total de investidores no sistema: " + investidores.size() + RESET);
            }
        } catch (ErrosLeituraArq e) {
            System.out.println(VERMELHO + e.getMessage() + RESET);
        }
    }

    private static void addMovimentacaoLote(Investidor inv){
        System.out.println(CIANO + "--- Movimentações em Lote ---" + RESET);
        System.out.println("Diretório de leitura: lotes/movimentacoesLotes/");
        System.out.println("Formato CSV: TIPO;TICKER;QUANTIDADE");
        System.out.print("Digite o NOME do arquivo (ex: compras_janeiro.csv): ");
        String nomeArquivoMov = scanner.nextLine();

        try {
            LeitorLotes.processarLoteMovimentacoes(nomeArquivoMov, inv, mercado);
        } catch (ErrosLeituraArq e) {
            System.out.println(VERMELHO + e.getMessage() + RESET);
        }
    }

}