package br.ufjf.dcc.Mercado;

import br.ufjf.dcc.Ativos.*;
import br.ufjf.dcc.CoresMensagens.CoresMensagens;
import br.ufjf.dcc.Erros.ErroTipoNaoPresente;
import br.ufjf.dcc.Erros.ErrosLeituraArq;
import br.ufjf.dcc.Erros.ErrosNumbersFormato;
import br.ufjf.dcc.Tools.Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

    private List<Ativos> localizaListAtivo(Ativos ativo){
            if (listaAtivosAcoes.contains(ativo)) {
                return listaAtivosAcoes;
            } else if (listaAtivosFiis.contains(ativo)) {
                return listaAtivosFiis;
            } else if (listaAtivosTesouros.contains(ativo)) {
                return listaAtivosTesouros;
            } else if (listaAtivosCriptos.contains(ativo)) {
                return listaAtivosCriptos;
            } else if (listaAtivosStocks.contains(ativo)) {
                return listaAtivosStocks;
            }
            return null;
    }

    private Ativos auxBuscaAtivo(String texto, int opcao) {
        if (opcao == 1) {
            for (Ativos a : listaAtivosAcoes) {
                if (a.getTicker().equalsIgnoreCase(texto) || a.getNome().equalsIgnoreCase(texto)) {
                    return a;
                }
            }
        } else if (opcao == 2) {
            for (Ativos f : listaAtivosFiis) {
                if (f.getTicker().equalsIgnoreCase(texto) || f.getNome().equalsIgnoreCase(texto)) {
                    return f;
                }
            }
        } else if (opcao == 3) {
            for (Ativos t : listaAtivosTesouros) {
                if (t.getTicker().equalsIgnoreCase(texto) || t.getNome().equalsIgnoreCase(texto)) {
                    return t;
                }
            }
        } else if (opcao == 4) {
            for (Ativos c : listaAtivosCriptos) {
                if (c.getTicker().equalsIgnoreCase(texto) || c.getNome().equalsIgnoreCase(texto)) {
                    return c;
                }
            }
        } else if (opcao == 5) {
            for (Ativos s : listaAtivosStocks) {
                if (s.getTicker().equalsIgnoreCase(texto) || s.getNome().equalsIgnoreCase(texto)) {
                    return s;
                }
            }
        }
        return null;
    }
    private boolean existeTicker(String ticker) {
        if (ticker == null || ticker.trim().isEmpty()) return false;
        String t = ticker.trim().toUpperCase(Locale.ROOT);
        for (Ativos a : listaAtivosAcoes)   if (a != null && a.getTicker() != null && t.equalsIgnoreCase(a.getTicker())) return true;
        for (Ativos f : listaAtivosFiis)    if (f != null && f.getTicker() != null && t.equalsIgnoreCase(f.getTicker())) return true;
        for (Ativos te : listaAtivosTesouros) if (te != null && te.getTicker() != null && t.equalsIgnoreCase(te.getTicker())) return true;
        for (Ativos c : listaAtivosCriptos) if (c != null && c.getTicker() != null && t.equalsIgnoreCase(c.getTicker())) return true;
        for (Ativos s : listaAtivosStocks)  if (s != null && s.getTicker() != null && t.equalsIgnoreCase(s.getTicker())) return true;
        return false;
    }

    private Ativos auxAdicaoAtivo(Ativos ativo, int tipoAtivo) {
        if (ativo == null) return null;
        String ticker = ativo.getTicker();
        if (existeTicker(ticker)) {
            System.out.println(AMARELO + "Ignorado: ativo com ticker " + ticker + " já existe." + RESET);
            return ativo;
        }

        switch (tipoAtivo) {
            case 1:
                listaAtivosAcoes.add(ativo);
                break;
            case 2:
                listaAtivosFiis.add(ativo);
                break;
            case 3:
                listaAtivosTesouros.add(ativo);
                break;
            case 4:
                listaAtivosCriptos.add(ativo);
                break;
            case 5:
                listaAtivosStocks.add(ativo);
                break;
            default:
                System.out.println("Tipo de ativo desconhecido: " + tipoAtivo);
        }
        return ativo;
    }

    private void carregarBaseDeDados() {
        try {
            carregarAtivosLote("dadosBase/acao.csv", 1);
        } catch (ErrosNumbersFormato e) {
            System.err.println("Erro de formato numérico ao carregar Ações: " + e.getMessage());
        }

        try {
            carregarAtivosLote( "dadosBase/fii.csv", 2);
        } catch (ErrosNumbersFormato e) {
            System.err.println("Erro de formato numérico ao carregar FIIs: " + e.getMessage());
        }

        try {
            carregarAtivosLote( "dadosBase/tesouro.csv", 5);
        } catch (ErrosNumbersFormato e) {
            System.err.println("Erro de formato numérico ao carregar Tesouros: " + e.getMessage());
        }

        try {
            carregarAtivosLote( "dadosBase/criptoativo.csv", 4);
        } catch (ErrosNumbersFormato e) {
            System.err.println("Erro de formato numérico ao carregar Criptoativos: " + e.getMessage());
        }

        try {
            carregarAtivosLote( "dadosBase/stock.csv", 3);
        } catch (ErrosNumbersFormato e) {
            System.err.println("Erro de formato numérico ao carregar Stocks: " + e.getMessage());
        }
    }


    private void listarListasAtivosEspecifico(List<Ativos> ativos){
        for (Ativos ativo : ativos) {
            ativo.exibirAtivo();
            System.out.println("-".repeat(20));
        }
    }

    public void listarAtivosPorTipo(int opcao){
        switch (opcao){
            case 1:
                listarTodosAtivos();
                break;
            case 2:
                System.out.println(AZUL + "===== RELATÓRIO DE AÇÕES =====");
                listarListasAtivosEspecifico(listaAtivosAcoes);
                System.out.println(RESET);
                break;
            case 3:
                System.out.println(VERDE_CLARO + "===== RELATÓRIO DE FIIs =====");
                listarListasAtivosEspecifico(listaAtivosFiis);
                System.out.println(RESET);
                break;
            case 4:
                System.out.println(VERDE + "===== RELATÓRIO DE TESOUROS =====");
                listarListasAtivosEspecifico(listaAtivosTesouros);
                System.out.println(RESET);
                break;
            case 5:
                System.out.println(ROXO + "===== RELATÓRIO DE CRIPTOMOEDAS =====");
                listarListasAtivosEspecifico(listaAtivosCriptos);
                System.out.println(RESET);
                break;
            case 6:
                System.out.println(CIANO + "===== RELATÓRIO DE STOCKS =====");
                listarListasAtivosEspecifico(listaAtivosStocks);
                System.out.println(RESET);
                break;
            default:
                System.out.println(VERMELHO+"Opção inválida para listagem de ativos por tipo." + RESET);
        }
    }

    public Ativos buscaAtivo(String entrada){
        System.out.println(AZUL);
        for(int i = 1; i <=5; i++){
            Ativos ativoEncontrado = auxBuscaAtivo(entrada, i);
            if(ativoEncontrado != null){
                if(i == 1) System.out.println("+".repeat(5) + " AÇÃO " + "+".repeat(5));
                else if(i == 2) System.out.println("+".repeat(5) + " FII " + "+".repeat(5));
                else if(i == 3) System.out.println("+".repeat(5) + " TESOURO " + "+".repeat(5));
                else if(i == 4) System.out.println("+".repeat(5) + " CRIPTOMOEDA " + "+".repeat(5));
                else if(i == 5) System.out.println("+".repeat(5) + " STOCK " + "+".repeat(5));
                System.out.println("Ativo encontrado:");
                ativoEncontrado.exibirAtivo();
                System.out.println(RESET);
                Tools.espera(3);
                return ativoEncontrado;
            }
        }
        System.out.println(RESET);
        System.out.println(AMARELO + "Ativo não encontrado para: " + entrada + RESET);
        return null;
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

    public void removerAtivo(String ticket) {
        Ativos ativo = buscaAtivo(ticket);
        List<Ativos> lista = localizaListAtivo(ativo);
        if (lista != null) {
            lista.remove(ativo);
            System.out.println(VERDE + "Ativo removido com sucesso!" + RESET);
        } else {
            System.out.println(VERMELHO+"Ativo não encontrado na lista." + RESET);
        }
    }

    public void adicaoAtivo(String tipoAtivo, String dados){
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
                            auxAdicaoAtivo(acao, 1);
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
                            auxAdicaoAtivo(fii, 2);
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
                            auxAdicaoAtivo(stock, 5);
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
                            auxAdicaoAtivo(cripto, 4);
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
                            auxAdicaoAtivo(tesouro, 3);
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

    public void editarAtivo(Ativos ativo, String atributo) {
        String texto = "";
        if(!atributo.isEmpty()) {
             texto = atributo.toLowerCase();
         }else {
             throw new ErroTipoNaoPresente("Não há atributos para edição.");
         }
        if(ativo != null && !(texto.equals(""))){
            for (String atributoEditar : texto.split(",")) {
                ativo.editarAtributos(atributoEditar);
            }
        }else{
            System.out.println(VERMELHO+"Ativo nulo ou atributo vazio para edição." + RESET);
        }
    }

    public void carregarAtivosLote(String caminho, int opcao) {
        System.out.println(AMARELO + "Iniciando carregamento em lote: " + caminho + RESET);
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String header = br.readLine();
            if (header == null) {
                System.out.println(AMARELO + "Arquivo vazio: " + caminho + RESET);
                return;
            }
            String delimiter = header.contains(",") ? "," : header.contains(";") ? ";" : ",";
            Map<String, Integer> hdr = Tools.construirMapaCabecalho(header, delimiter);

            String linha;
            int linhaNum = 1;
            while ((linha = br.readLine()) != null) {
                linhaNum++;
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(delimiter, -1);

                try {
                    switch (opcao) {
                        case 1: {
                            String ticker = Tools.obterCampo(dados, hdr, "ticker", "codigo", "symbol", "Ticker");
                            String nome = Tools.obterCampo(dados, hdr, "nome", "name", "descricao", "Nome");
                            String precoStr = Tools.obterCampo(dados, hdr, "preco", "valor", "price", "preço", "Preço", "preço (r$)", "Preço (R$)");
                            String qualStr = Tools.obterCampo(dados, hdr, "qualificado", "qualif", "qtd", "is_qualified", "qual", "Qualificado", "qualificado?");

                            if (ticker.isEmpty()) {
                                ticker = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": ticker ausente, usando (Ausente)." + RESET);
                            }
                            if (nome.isEmpty()) {
                                nome = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": nome ausente, usando (Ausente)." + RESET);
                            }

                            Float preco = Tools.parseFloatNullable(precoStr);
                            if (preco == null) {
                                preco = 0f;
                                System.out.println(AMARELO + "Linha " + linhaNum + ": preço ausente/ inválido, usando 0." + RESET);
                            }

                            boolean qualificado = "1".equals(qualStr) || qualStr.equalsIgnoreCase("sim") || qualStr.equalsIgnoreCase("true");

                            auxAdicaoAtivo(new Acoes(nome, ticker, preco, qualificado), 1);
                            break;
                        }
                        case 2: { // FIIs
                            String ticker = Tools.obterCampo(dados, hdr, "ticker", "codigo", "symbol", "Ticker");
                            String nome = Tools.obterCampo(dados, hdr, "nome", "name", "descricao" , "Nome");
                            String precoStr = Tools.obterCampo(dados, hdr, "preco", "valor", "price", "preço", "Preço", "preço (r$)", "Preço (R$)");
                            String qualStr = Tools.obterCampo(dados, hdr, "qualificado", "qualif", "qtd", "is_qualified", "qual", "Qualificado", "qualificado?");
                            String setor = Tools.obterCampo(dados, hdr, "setor", "segmento", "sector", "Setor");
                            String ultimoDivStr = Tools.obterCampo(dados, hdr, "ultimodividendo", "ultimo_dividendo", "ultimo", "dividendo", "UltimoDividendo", "último dividendo", "Último Dividendo");
                            String taxaAdmStr = Tools.obterCampo(dados, hdr, "taxaadm", "taxa_adm", "taxa", "taxa_admissao", "TaxaAdm", "taxa de administração", "Taxa de Administração");

                            if (ticker.isEmpty()) {
                                ticker = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": ticker ausente para FII, usando (Ausente)." + RESET);
                            }
                            if (nome.isEmpty()) {
                                nome = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": nome ausente para FII, usando (Ausente)." + RESET);
                            }
                            if (setor.isEmpty()) {
                                setor = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": setor ausente para FII, usando (Ausente)." + RESET);
                            }

                            Float preco = Tools.parseFloatNullable(precoStr);
                            if (preco == null) {
                                preco = 0f;
                                System.out.println(AMARELO + "Linha " + linhaNum + ": preço ausente/ inválido para FII, usando 0." + RESET);
                            }

                            Float ultimoDiv = Tools.parseFloatNullable(ultimoDivStr);
                            if (ultimoDiv == null) {
                                ultimoDiv = 0f;
                                System.out.println(AMARELO + "Linha " + linhaNum + ": ultimoDividendo ausente/ inválido, usando 0." + RESET);
                            }

                            Float taxaAdm = Tools.parseFloatNullable(taxaAdmStr);
                            if (taxaAdm == null) {
                                taxaAdm = 0f;
                                System.out.println(AMARELO + "Linha " + linhaNum + ": taxaAdm ausente/ inválida, usando 0." + RESET);
                            }

                            boolean qualificado = "1".equals(qualStr) || qualStr.equalsIgnoreCase("sim") || qualStr.equalsIgnoreCase("true");
                            auxAdicaoAtivo(new Fiis(nome, ticker, preco, qualificado, setor, ultimoDiv, taxaAdm), 2);
                            break;
                        }
                        case 3: { // Stocks
                            String ticker = Tools.obterCampo(dados, hdr, "ticker", "codigo", "symbol");
                            String nome = Tools.obterCampo(dados, hdr, "nome", "name", "descricao");
                            String precoStr = Tools.obterCampo(dados, hdr,
                                "preco", "valor", "price", "preço", "Preço",
                                "preço (r$)", "Preço (R$)",
                                "preço (usd)", "Preço (USD)");
                            String bolsa = Tools.obterCampo(dados, hdr,
                                "bolsa", "exchange", "bolsa_negociacao", "Bolsa", "Bolsa de negociação");
                            String setor = Tools.obterCampo(dados, hdr, "setor", "segmento", "sector");

                            if (ticker.isEmpty()) {
                                ticker = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": ticker ausente para Stock, usando (Ausente)." + RESET);
                            }
                            if (nome.isEmpty()) {
                                nome = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": nome ausente para Stock, usando (Ausente)." + RESET);
                            }
                            if (bolsa.isEmpty()) {
                                bolsa = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": bolsa ausente para Stock, usando (Ausente)." + RESET);
                            }
                            if (setor.isEmpty()) {
                                setor = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": setor ausente para Stock, usando (Ausente)." + RESET);
                            }

                            Float preco = Tools.parseFloatNullable(precoStr);
                            if (preco == null) {
                                preco = 0f;
                                System.out.println(AMARELO + "Linha " + linhaNum + ": preço ausente/ inválido para Stock, usando 0." + RESET);
                            }
                            auxAdicaoAtivo(new Stocks(nome, ticker, preco, bolsa, setor), 3);
                            break;
                        }
                        case 4: { // Criptomoedas
                            String ticker = Tools.obterCampo(dados, hdr, "ticker", "codigo", "symbol");
                            String nome = Tools.obterCampo(dados, hdr, "nome", "name", "descricao");
                            String precoStr = Tools.obterCampo(dados, hdr, "preco", "valor", "price", "preço", "Preço", "preço (usd)", "Preço (USD)");
                            String consenso = Tools.obterCampo(dados, hdr, "consenso", "algoritmo", "consensus", "Algoritmo Consenso", "algoritmo consenso");
                            String qtdMaxStr = Tools.obterCampo(dados, hdr, "qtdmax", "quantidademaxima", "max", "max_supply", "quantidade máxima", "Quantidade Máxima");

                            if (ticker.isEmpty()) {
                                ticker = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": ticker ausente para Cripto, usando (Ausente)." + RESET);
                            }
                            if (nome.isEmpty()) {
                                nome = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": nome ausente para Cripto, usando (Ausente)." + RESET);
                            }
                            if (consenso.isEmpty()) {
                                consenso = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": consenso ausente para Cripto, usando (Ausente)." + RESET);
                            }

                            Float preco = Tools.parseFloatNullable(precoStr);
                            if (preco == null) {
                                preco = 0f;
                                System.out.println(AMARELO + "Linha " + linhaNum + ": preço ausente/ inválido para Cripto, usando 0." + RESET);
                            }

                            Long qtdMax = Tools.parseLongNullable(qtdMaxStr);
                            if (qtdMax == null) {
                                qtdMax = 0L;
                                if (qtdMaxStr != null && !qtdMaxStr.trim().isEmpty()) {
                                    System.out.println(AMARELO + "Linha " + linhaNum + ": qtdMax inválida, usando 0." + RESET);
                                } else {
                                    System.out.println(AMARELO + "Linha " + linhaNum + ": qtdMax ausente, usando 0." + RESET);
                                }
                            }

                            auxAdicaoAtivo(new Criptomoedas(nome, ticker, preco, consenso, qtdMax), 4);
                            break;
                        }
                        case 5: { // Tesouros
                            String ticker = Tools.obterCampo(dados, hdr, "ticker", "codigo", "symbol");
                            String nome = Tools.obterCampo(dados, hdr, "nome", "name", "descricao");
                            String precoStr = Tools.obterCampo(dados, hdr, "preco", "valor", "price", "preço", "Preço", "preço (r$)", "Preço (R$)");
                            String tipoRend = Tools.obterCampo(dados, hdr, "tipo", "tiporendimento", "tipo_rendimento", "Tipo de Rendimento", "tipo de rendimento");
                            String venc = Tools.obterCampo(dados, hdr, "vencimento", "venc", "maturity", "Vencimento");

                            if (ticker.isEmpty()) {
                                ticker = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": ticker ausente para Tesouro, usando (Ausente)." + RESET);
                            }
                            if (nome.isEmpty()) {
                                nome = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": nome ausente para Tesouro, usando (Ausente)." + RESET);
                            }
                            if (tipoRend.isEmpty()) {
                                tipoRend = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": tipo rendimento ausente para Tesouro, usando (Ausente)." + RESET);
                            }
                            if (venc.isEmpty()) {
                                venc = "(Ausente)";
                                System.out.println(AMARELO + "Linha " + linhaNum + ": vencimento ausente para Tesouro, usando (Ausente)." + RESET);
                            }

                            Float preco = Tools.parseFloatNullable(precoStr);
                            if (preco == null) {
                                preco = 0f;
                                System.out.println(AMARELO + "Linha " + linhaNum + ": preço ausente/ inválido para Tesouro, usando 0." + RESET);
                            }
                            auxAdicaoAtivo(new Tesouro(nome, ticker, preco, tipoRend, venc), 5);
                            break;
                        }
                        default:
                            System.out.println(VERMELHO + "Opção de tipo inválida: " + opcao + RESET);
                            return;
                    }
                } catch (Exception e) {
                    System.out.println(VERMELHO + "Erro ao processar linha " + linhaNum + ": " + e.getMessage() + RESET);
                }
            }

            System.out.println(VERDE + "Carregamento em lote concluído." + RESET);
        } catch (IOException e) {
            System.out.println(VERMELHO + "Erro ao ler arquivo: " + e.getMessage() + RESET);
        }
    }
}