package br.ufjf.dcc.Mercado;

import br.ufjf.dcc.Ativos.*;
import br.ufjf.dcc.CoresMensagens.CoresMensagens;
import br.ufjf.dcc.Erros.ErroInterrupcao;
import br.ufjf.dcc.Erros.ErroTipoNaoPresente;
import br.ufjf.dcc.Erros.ErrosLeituraArq;
import br.ufjf.dcc.Erros.ErrosNumbersFormato;
import br.ufjf.dcc.Tools.Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Mercado implements CoresMensagens {
    private List<Ativos> listaAtivosAcoes = new LinkedList<Ativos>();
    private List<Ativos> listaAtivosFiis = new LinkedList<Ativos>();
    private List<Ativos> listaAtivosTesouros = new LinkedList<Ativos>();
    private List<Ativos> listaAtivosCriptos = new LinkedList<Ativos>();
    private List<Ativos> listaAtivosStocks = new LinkedList<Ativos>();

    public Mercado() {
        carregarBaseDeDados();
    }

    public List<Ativos> getListaAcoes() { return listaAtivosAcoes; }
    public List<Ativos> getListaFiis() { return listaAtivosFiis; }
    public List<Ativos> getListaTesouros() { return listaAtivosTesouros; }
    public List<Ativos> getListaCriptos() { return listaAtivosCriptos; }
    public List<Ativos> getListaStocks() { return listaAtivosStocks; }

    public void estruturarAtivo(String tipoAtivo, String dados){
        String[] info = dados.split(",");
        boolean qualificado;
        switch (tipoAtivo.toLowerCase()) {
            case "acao":
            case "ações":
                if (info.length >= 3 && info.length <=4) {
                    try {
                        String ticker = info[0].trim().toUpperCase();
                        String nome = Tools.capitalize(info[1].trim());
                        float preco = Float.parseFloat(info[2].trim());
                        if (info.length == 3) {
                            qualificado = false;
                        }else {
                            qualificado = info[3].trim().equals("sim") ? true : false;
                        }
                        Acoes acao = new Acoes(nome, ticker, preco, qualificado);
                        if (acao.verificarAtributosValidos()) {
                            adicaoAtivo(acao, 1);
                        } else {
                            System.out.println("Atributos inválidos");
                        }
                    }catch (TypeNotPresentException e){
                        throw new ErroTipoNaoPresente("Erro ao estruturar Ação deve ser ordenado igual o pedido do input: " + e.getMessage());
                    }
                }
                else {
                    System.out.println(VERMELHO+"Há mais atributos do que o esperado para Ação ou estão faltando atributos. Formato esperado: Ticker, Nome, Preço, Qualificado" + RESET);
                }
                break;
            // java
            case "fii":
            case "fiis":
                if (info.length >= 6 && info.length <=7) {
                    String setor;
                    float ultimoDividendo, taxaAdm;
                    try {
                        String ticker = info[0].trim().toUpperCase();
                        String nome = Tools.capitalize(info[1].trim());

                        float preco;
                        try {
                            preco = Float.parseFloat(info[2].trim().replace(",", "."));
                        } catch (NumberFormatException e) {
                            System.out.println("Erro de formato numérico no campo 'Preço': " + info[2].trim());
                            break;
                        }

                        if (info.length == 6) {
                            qualificado = false;
                            setor = info[3].trim();
                            try {
                                ultimoDividendo = Float.parseFloat(info[4].trim().replace(",", "."));
                                taxaAdm = Float.parseFloat(info[5].trim().replace(",", "."));
                            } catch (NumberFormatException e) {
                                System.out.println("Erro de formato numérico em 'Ultimo Dividendo' ou 'Taxa de Admissão': " + e.getMessage());
                                break;
                            }
                        } else if (info.length >= 7) {
                            qualificado = info[3].trim().equalsIgnoreCase("sim");
                            setor = info[4].trim();
                            try {
                                ultimoDividendo = Float.parseFloat(info[5].trim().replace(",", "."));
                                taxaAdm = Float.parseFloat(info[6].trim().replace(",", "."));
                            } catch (NumberFormatException e) {
                                System.out.println("Erro de formato numérico em 'Ultimo Dividendo' ou 'Taxa de Admissão': " + e.getMessage());
                                break;
                            }
                        } else {
                            System.out.println("Entrada incompleta para FII. Formato esperado: Ticket, Nome, Preço, [Qualificado], Segmento, UltimoDividendo, TaxaAdm");
                            break;
                        }

                        Fiis fii = new Fiis(nome, ticker, preco, qualificado, setor, ultimoDividendo, taxaAdm);
                        if (fii.verificarAtributosValidos()) {
                            adicaoAtivo(fii, 2);
                        } else {
                            System.out.println("Atributos inválidos");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao estruturar FII: " + e.getMessage());
                    }
                } else {
                    System.out.println("Insira separando por vírgula: Ticket, Nome, Preço, Qualificado (sim/não) (opcional), Segmento, Ultimo Dividendo, Taxa de Admissão");
                }
                break;

                case "stock":
                case "stocks":
                if (info.length >= 5 && info.length <=6) {
                    try {
                        String ticker = info[0].trim().toUpperCase();
                        String nome = Tools.capitalize(info[1].trim());
                        float preco = Float.parseFloat(info[2].trim());
                        String bolsaNegociacao = info[3].trim().toUpperCase();
                        String setor = info[4].trim();
                        if (info.length == 5) {
                            qualificado = false;
                        }else {
                            qualificado = info[6].trim().equals("sim") ? true : false;
                        }
                        Stocks stock = new Stocks(nome, ticker, preco, qualificado,bolsaNegociacao, setor);
                        if (stock.verificarAtributosValidos()) {
                            adicaoAtivo(stock, 5);
                        } else {
                            System.out.println("Atributos inválidos");
                        }
                    }catch (TypeNotPresentException e){
                        throw new ErroTipoNaoPresente("Erro ao estruturar Stock deve ser ordenado igual o pedido do input: " + e.getMessage());
                    }
                }
                else {
                    System.out.println(VERMELHO+"Há mais atributos do que o esperado para Stock ou estão faltando atributos. Formato esperado: Ticker, Nome, Preço, Bolsa de Negociação, Setor, Qualificado" + RESET);
                }
                break;

                case "cripto":
                    case "criptomoeda":
            case "criptomoedas":
                if (info.length >= 4 && info.length <=5) {
                    try {
                        String ticker = info[0].trim().toUpperCase();
                        String nome = Tools.capitalize(info[1].trim());
                        float preco = Float.parseFloat(info[2].trim());
                        String consenso = info[3].trim();
                        long qtdMax = 0L;
                        if (info.length >= 5) {
                            try {
                                qtdMax = Long.parseLong(info[4].trim());
                            } catch (NumberFormatException e) {
                                throw new ErrosNumbersFormato("Erro de formato numérico no campo 'Quantidade Máxima': " + info[4].trim());
                            }
                        }
                        Criptomoedas cripto = new Criptomoedas(nome, ticker, preco, consenso, qtdMax);
                        if (cripto.verificarAtributosValidos()) {
                            adicaoAtivo(cripto, 4);
                        } else {
                            System.out.println("Atributos inválidos");
                        }
                    }catch (TypeNotPresentException e){
                        throw new ErroTipoNaoPresente("Erro ao estruturar Criptomoeda deve ser ordenado igual o pedido do input: " + e.getMessage());
                    }
                }
                else {
                    System.out.println(VERMELHO+"Há mais atributos do que o esperado para Criptomoeda ou estão faltando atributos. Formato esperado: Ticker, Nome, Preço, Algoritmo Consenso, Quantidade Máxima" + RESET);
                }
                break;
            case "tesouro":
            case "tesouros":
                if (info.length >= 4 && info.length <=5) {
                    try {
                        String ticker = info[0].trim().toUpperCase();
                        String nome = Tools.capitalize(info[1].trim());
                        float preco = Float.parseFloat(info[2].trim());
                        String tipoRendimento = info[3].trim();
                        String vencimento = info[4].trim();
                        Tesouro tesouro = new Tesouro(nome, ticker, preco, tipoRendimento, vencimento);
                        if (tesouro.verificarAtributosValidos()) {
                            adicaoAtivo(tesouro, 3);
                        } else {
                            System.out.println("Atributos inválidos");
                        }
                    }catch (TypeNotPresentException e){
                        throw new ErroTipoNaoPresente("Erro ao estruturar Tesouro deve ser ordenado igual o pedido do input: " + e.getMessage());
                    }
                }else{
                    System.out.println(VERMELHO+"Há mais atributos do que o esperado para Tesouro ou estão faltando atributos. Formato esperado: Ticker, Nome, Preço, TipoRendimento, Vencimento" + RESET);
                }
                break;
            default:
                System.out.println("Tipo de ativo desconhecido para estruturação: " + tipoAtivo);
        }
    }
   private Ativos adicaoAtivo(Ativos ativo, int tipoAtivo) {
        switch (tipoAtivo) {
            case 1:
                listaAtivosAcoes.add(ativo);
                System.out.println("Ativo adicionado com sucesso!");
                break;
            case 2:
                listaAtivosFiis.add(ativo);
                System.out.println("Ativo adicionado com sucesso!");
                break;
            case 3:
                listaAtivosTesouros.add(ativo);
                System.out.println("Ativo adicionado com sucesso!");
                break;
            case 4:
                listaAtivosCriptos.add(ativo);
                System.out.println("Ativo adicionado com sucesso!");
                break;
            case 5:
                listaAtivosStocks.add(ativo);
                System.out.println("Ativo adicionado com sucesso!");
                break;
            default:
                System.out.println("Tipo de ativo desconhecido: " + tipoAtivo);
        }
        return ativo;
    }
    public void carregarBaseDeDados() {
        try {
            carregarAcoes("src/main/dados/acao.csv");
        } catch (ErrosLeituraArq e) {
            System.err.println("Erro ao carregar Ações: " + e.getMessage());
        } catch (ErrosNumbersFormato e) {
            System.err.println("Erro de formato numérico ao carregar Ações: " + e.getMessage());
        }

        try {
            carregarFiis("src/main/dados/fii.csv");
        } catch (ErrosLeituraArq e) {
            System.err.println("Erro ao carregar FIIs: " + e.getMessage());
        } catch (ErrosNumbersFormato e) {
            System.err.println("Erro de formato numérico ao carregar FIIs: " + e.getMessage());
        }

        try {
            carregarTesouros("src/main/dados/tesouro.csv");
        } catch (ErrosLeituraArq e) {
            System.err.println("Erro ao carregar Tesouros: " + e.getMessage());
        } catch (ErrosNumbersFormato e) {
            System.err.println("Erro de formato numérico ao carregar Tesouros: " + e.getMessage());
        }

        try {
            carregarCriptos("src/main/dados/criptoativo.csv");
        } catch (ErrosLeituraArq e) {
            System.err.println("Erro ao carregar Criptoativos: " + e.getMessage());
        } catch (ErrosNumbersFormato e) {
            System.err.println("Erro de formato numérico ao carregar Criptoativos: " + e.getMessage());
        }

        try {
            carregarStocks("src/main/dados/stock.csv");
        } catch (ErrosLeituraArq e) {
            System.err.println("Erro ao carregar Stocks: " + e.getMessage());
        } catch (ErrosNumbersFormato e) {
            System.err.println("Erro de formato numérico ao carregar Stocks: " + e.getMessage());
        }
    }

    public void adicionarAtivosLote(String arquivo, String tipoAtivo) {
        switch (tipoAtivo.toLowerCase()) {
            case "acao":
            case "ações":
                try {
                    carregarAcoes(arquivo);
                } catch (ErrosLeituraArq | ErrosNumbersFormato e) {
                    System.err.println("Erro ao adicionar Ações em lote: " + e.getMessage());
                }
                break;
            case "fii":
            case "fiis":
                try {
                    carregarFiis(arquivo);
                } catch (ErrosLeituraArq | ErrosNumbersFormato e) {
                    System.err.println("Erro ao adicionar FIIs em lote: " + e.getMessage());
                }
                break;
            case "tesouro":
            case "tesouros":
                try {
                    carregarTesouros(arquivo);
                } catch (ErrosLeituraArq | ErrosNumbersFormato e) {
                    System.err.println("Erro ao adicionar Tesouros em lote: " + e.getMessage());
                }
                break;
            case "cripto":
            case "criptomoeda":
            case "criptomoedas":
                try {
                    carregarCriptos(arquivo);
                } catch (ErrosLeituraArq | ErrosNumbersFormato e) {
                    System.err.println("Erro ao adicionar Criptoativos em lote: " + e.getMessage());
                }
                break;
            case "stock":
            case "stocks":
                try {
                    carregarStocks(arquivo);
                } catch (ErrosLeituraArq | ErrosNumbersFormato e) {
                    System.err.println("Erro ao adicionar Stocks em lote: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Tipo de ativo desconhecido: " + tipoAtivo);
        }
    }

    private void carregarAcoes(String caminho) throws ErrosLeituraArq, ErrosNumbersFormato {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(caminho));
            String linha;
            br.readLine(); // Pular cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 4) {
                    String ticker = dados[0].trim();
                    String nome = dados[1].trim();

                    float preco;
                    try {
                        preco = Float.parseFloat(dados[2].trim().replace(".", "").replace(",", "."));
                    } catch (NumberFormatException e) {
                        throw new ErrosNumbersFormato("Número inválido em Ações (ticker=" + ticker + "): " + dados[2]);
                    }

                    boolean qualificado = "1".equals(dados[3].trim());

                    if (!ticker.isEmpty() && preco > 0f) {
                        listaAtivosAcoes.add(new Acoes(nome, ticker, preco, qualificado));
                    }
                }
            }
        } catch (IOException e) {
            throw new ErrosLeituraArq("Erro ao ler arquivo de Ações: " + e.getMessage());
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException closeEx) { System.err.println("Falha ao fechar leitor de arquivo: " + closeEx.getMessage()); }
            }
        }
    }

    private void carregarFiis(String caminho) throws ErrosLeituraArq, ErrosNumbersFormato {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(caminho));
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 6 && !dados[3].equals("-")) {
                    String ticker = dados[0].trim();
                    String nome = dados[1].trim();
                    String setor = dados[2].trim();

                    float preco;
                    float ultimoDividendo;
                    float taxaAdm;
                    try {
                        preco = Float.parseFloat(dados[3].replace(".", "").replace(",", "."));
                        ultimoDividendo = Float.parseFloat(dados[4].replace(".", "").replace(",", "."));
                        taxaAdm = Float.parseFloat(dados[5].replace(".", "").replace(",", "."));
                    } catch (NumberFormatException e) {
                        throw new ErrosNumbersFormato("Número inválido em FIIs (linha com ticker=" + ticker + "): " + e.getMessage());
                    }

                    listaAtivosFiis.add(new Fiis(nome, ticker, preco, false, setor, ultimoDividendo, taxaAdm));
                }
            }
        } catch (IOException e) {
            throw new ErrosLeituraArq("Erro ao ler arquivo de FIIs: " + e.getMessage());
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException closeEx) { System.err.println("Falha ao fechar leitor de arquivo: " + closeEx.getMessage()); }
            }
        }
    }

    private void carregarTesouros(String caminho) throws ErrosLeituraArq, ErrosNumbersFormato {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(caminho));
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 5) {
                    String ticker = dados[0].trim();
                    String nome = dados[1].trim();

                    float preco;
                    try {
                        preco = Float.parseFloat(dados[2].trim().replace(",", "."));
                    } catch (NumberFormatException e) {
                        throw new ErrosNumbersFormato("Número inválido em Tesouros (ticker=" + ticker + "): " + dados[2]);
                    }

                    String tipo = dados[3].trim();
                    String vencimento = dados[4].trim();

                    listaAtivosTesouros.add(new Tesouro(nome, ticker, preco, tipo, vencimento));
                }
            }
        } catch (IOException e) {
            throw new ErrosLeituraArq("Erro ao ler arquivo de Tesouros: " + e.getMessage());
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException closeEx) { System.err.println("Falha ao fechar leitor de arquivo: " + closeEx.getMessage()); }
            }
        }
    }

    private void carregarCriptos(String caminho) throws ErrosLeituraArq, ErrosNumbersFormato {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(caminho));
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 4) {
                    String ticker = dados[0].trim();
                    String nome = dados[1].trim();

                    float preco;
                    try {
                        preco = Float.parseFloat(dados[2].trim().replace(",", "."));
                    } catch (NumberFormatException e) {
                        throw new ErrosNumbersFormato("Número inválido em Criptoativos (ticker=" + ticker + "): " + dados[2]);
                    }

                    String consenso = dados[3].trim();

                    long qtdMax;
                    try {
                        qtdMax = (dados.length > 4 && !dados[4].trim().isEmpty())
                                ? Long.parseLong(dados[4].trim())
                                : 0L;
                    } catch (NumberFormatException e) {
                        throw new ErrosNumbersFormato("Quantidade máxima inválida em Criptoativos (ticker=" + ticker + "): " + dados[4]);
                    }

                    listaAtivosCriptos.add(new Criptomoedas(nome, ticker, preco, consenso, qtdMax));
                }
            }
        } catch (IOException e) {
            throw new ErrosLeituraArq("Erro ao ler arquivo de Criptoativos: " + e.getMessage());
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException closeEx) { System.err.println("Falha ao fechar leitor de arquivo: " + closeEx.getMessage()); }
            }
        }
    }

    private void carregarStocks(String caminho) throws ErrosLeituraArq, ErrosNumbersFormato {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(caminho));
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 5) {
                    String ticker = dados[0].trim();
                    String nome = dados[1].trim();

                    float preco;
                    try {
                        preco = Float.parseFloat(dados[2].trim().replace(",", "."));
                    } catch (NumberFormatException e) {
                        throw new ErrosNumbersFormato("Número inválido em Stocks (ticker=" + ticker + "): " + dados[2]);
                    }

                    String bolsa = dados[3].trim();
                    String setor = dados[4].trim();

                    listaAtivosStocks.add(new Stocks(nome, ticker, preco, bolsa, setor));
                }
            }
        } catch (IOException e) {
            throw new ErrosLeituraArq("Erro ao ler arquivo de Stocks: " + e.getMessage());
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException closeEx) { System.err.println("Falha ao fechar leitor de arquivo: " + closeEx.getMessage()); }
            }
        }
    }

    public void listarTodosAtivos() {
        System.out.println(ROSA + "===== RELATÓRIO DE TODOS OS ATIVOS =====" + RESET);

        System.out.println(AZUL + "----- AÇÕES -----" + RESET);
        for (Ativos a : listaAtivosAcoes) {
            System.out.print(AZUL);
            a.exibirAtivo();
            System.out.println("--------------------" + RESET);
        }

        System.out.println(VERDE_CLARO + "----- FIIs -----" + RESET);
        for (Ativos f : listaAtivosFiis) {
            System.out.print(VERDE_CLARO);
            f.exibirAtivo();
            System.out.println("--------------------" + RESET);
        }

        System.out.println(VERDE + "----- TESOUROS -----" + RESET);
        for (Ativos t : listaAtivosTesouros) {
            System.out.print(VERDE);
            t.exibirAtivo();
            System.out.println("--------------------" + RESET);
        }

        System.out.println(ROXO + "----- CRIPTOMOEDAS -----" + RESET);
        for (Ativos c : listaAtivosCriptos) {
            System.out.print(ROXO);
            c.exibirAtivo();
            System.out.println("--------------------" + RESET);
        }

        System.out.println(CIANO + "----- STOCKS -----" + RESET);
        for (Ativos s : listaAtivosStocks) {
            System.out.print(CIANO);
            s.exibirAtivo();
            System.out.println("--------------------" + RESET);
        }
    }
}