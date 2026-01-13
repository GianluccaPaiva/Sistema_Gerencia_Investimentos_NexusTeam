// java
            package br.ufjf.dcc.Registrar;

            import br.ufjf.dcc.Erros.ErrosLeituraArq;
            import br.ufjf.dcc.Tools.Tools;

            import java.io.BufferedReader;
            import java.io.File;
            import java.io.FileReader;
            import java.io.FileWriter;
            import java.io.IOException;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;

public class Registrar {
                private static final String CAMINHO_PONTEIRO = "src/main/ponteiro/arqRegistro.json";
                private static final String MOV_DIR = "movimentacoes";

                private static class RegistrarConfig {
                    private String ponteiroArqAtual;
                    private int index;

                    public RegistrarConfig() {
                        this.ponteiroArqAtual = MOV_DIR + "/registroMovimentacao_1.txt";
                        this.index = 1;
                        try {
                            carregarConfig();
                        } catch (IOException e) {
                            System.out.println("❌ Erro ao carregar configuração de registro: " + e.getMessage());
                        }
                    }

                    private String ensureMovimentacoesPath(String caminho) {
                        if (caminho == null) return MOV_DIR + "/registroMovimentacao_1.txt";
                        String caminhoSanitizado = caminho
                                .replace("\uFEFF", "")
                                .trim()
                                .replaceAll("^\"+|\"+$", "")
                                .replace("\\", "/");
                        if (caminhoSanitizado.startsWith(MOV_DIR + "/")) {
                            return caminhoSanitizado;
                        }

                        if (caminhoSanitizado.contains("/")) {
                            return caminhoSanitizado;
                        }

                        return MOV_DIR + "/" + caminhoSanitizado;
                    }

                    private void clearregistroFile(String caminho) {
                        String caminhoSanitizado = ensureMovimentacoesPath(caminho);

                        File arquivo = new File(caminhoSanitizado);

                        try {
                            if (!arquivo.exists()) {
                                File dir = arquivo.getParentFile();
                                if (dir != null && !dir.exists()) {
                                    dir.mkdirs();
                                }
                                if (arquivo.createNewFile()) {
                                    System.out.println("✅ Arquivo " + caminhoSanitizado + " criado.");
                                }
                            }

                            try (FileWriter escrever = new FileWriter(arquivo, false)) {
                                escrever.write("");
                                System.out.println("✅ Conteúdo do arquivo " + caminhoSanitizado + " apagado.");
                            }

                        } catch (IOException e) {
                            System.out.println("❌ Erro ao tentar limpar/criar arquivo de registro: " + e.getMessage());
                        }
                    }

                    private void carregarConfig() throws IOException {
                        try (BufferedReader leituraBuffer = new BufferedReader(new FileReader(CAMINHO_PONTEIRO))) {
                            StringBuilder sb = new StringBuilder();
                            String linha;
                            while ((linha = leituraBuffer.readLine()) != null) {
                                sb.append(linha);
                            }
                            String json = sb.toString().replace("\\", "/").trim();
                            if (json.isEmpty()) {
                                throw new ErrosLeituraArq("Arquivo de ponteiro vazio");
                            }

                            Pattern p = Pattern.compile("\"ponteiroAtual\"\\s*:\\s*\"([^\"]+)\"");
                            Matcher m = p.matcher(json);
                            String arqDoJson = null;

                            if (m.find()) {
                                arqDoJson = m.group(1);
                            } else {
                                p = Pattern.compile("\"[^\"]+\"\\s*:\\s*\"([^\"]+)\"");
                                m = p.matcher(json);
                                if (m.find()) {
                                    arqDoJson = m.group(1);
                                }
                            }

                            if (arqDoJson == null) {
                                throw new ErrosLeituraArq("Formato de JSON inválido: " + json);
                            }

                            this.ponteiroArqAtual = ensureMovimentacoesPath(arqDoJson);
                            Tools tools = new Tools();
                            this.index = tools.extractIndex(arqDoJson);
                            System.out.println("✅ Caminho base carregado: " + this.ponteiroArqAtual);
                        } catch (ErrosLeituraArq e) {
                            System.out.println("❌ Erro ao ler config: " + e.getMessage() + ". Usando padrão: " + this.ponteiroArqAtual);
                        }
                    }

                    private void atualizarConfig(String novoNomeArquivo) {
                        try (FileWriter arqEscrita = new FileWriter(CAMINHO_PONTEIRO)) {
                            // grava apenas o nome/valor no JSON; mantém caminho completo se fornecido
                            String json = "{\"nomeArqregistro\": \"" + novoNomeArquivo + "\"}";
                            arqEscrita.write(json);
                            arqEscrita.flush();
                            System.out.println("💾 JSON atualizado: " + CAMINHO_PONTEIRO);
                        } catch (IOException e) {
                            System.out.println("❌ Erro ao atualizar JSON: " + e.getMessage());
                        }
                    }

                    public String getCurrentFilePath() {
                        return ponteiroArqAtual;
                    }

                    public int getCurrentIndex() {
                        return index;
                    }

                    public void nextregistro() {
                        this.index++;
                        int sub = ponteiroArqAtual.lastIndexOf("_");
                        int ponto = ponteiroArqAtual.lastIndexOf(".");
                        String novoNomeArquivo;

                        if (sub != -1 && ponto != -1) {
                            String prefixo = ponteiroArqAtual.substring(0, sub + 1);
                            String sufixo = ponteiroArqAtual.substring(ponto);
                            novoNomeArquivo = prefixo + this.index + sufixo;
                        } else {
                            novoNomeArquivo = ponteiroArqAtual.substring(0, ponto) + "_" + this.index + ponteiroArqAtual.substring(ponto);
                        }
                        novoNomeArquivo = novoNomeArquivo.replaceAll("^\"+|\"+$", "");
                        this.ponteiroArqAtual = ensureMovimentacoesPath(novoNomeArquivo);

                        clearregistroFile(this.ponteiroArqAtual);
                        System.out.println("🔁 Novo registro configurado: " + this.ponteiroArqAtual);
                        atualizarConfig(this.ponteiroArqAtual);
                    }

                    public void resetConfigToInitial() {
                        clearregistroFile(MOV_DIR + "/registroMovimentacao_1.txt");
                        this.ponteiroArqAtual = MOV_DIR + "/registroMovimentacao_1.txt";
                        this.index = 1;
                        System.out.println("⚙️ Configuração de registro resetada para: " + this.ponteiroArqAtual);
                        atualizarConfig(this.ponteiroArqAtual);
                    }
                }

                private static RegistrarConfig config = new RegistrarConfig();

                public static void reproduzirRegistro(String caminhoregistro) {
                    mostrarRegistro(caminhoregistro);
                }

                public static void registrar(String conteudo) {
                    String caminho = config.getCurrentFilePath();

                    caminho = caminho
                            .replace("\uFEFF", "")
                            .trim()
                            .replaceAll("^\"+|\"+$", "")
                            .replace("\\", "/");

                    // Garantir que o arquivo esteja dentro de movimentacoes
                    if (!caminho.startsWith(MOV_DIR + "/") && !caminho.contains("/")) {
                        caminho = MOV_DIR + "/" + caminho;
                    }

                    if (caminho.isBlank()) {
                        System.out.println("❌ Caminho do arquivo de registro está vazio.");
                        return;
                    }

                    File arquivo = new File(caminho);
                    File dir = arquivo.getParentFile();

                    if (dir != null && !dir.exists()) {
                        boolean ok = dir.mkdirs();
                        if (!ok) {
                            System.out.println("⚠️ Não foi possível criar diretório: " + dir.getAbsolutePath());
                        }
                    }

                    String nomeArquivo = arquivo.getName();
                    if (nomeArquivo.contains("\"") || nomeArquivo.contains(":\"")) {
                        System.out.println("❌ Nome do arquivo contém caracteres inválidos: " + nomeArquivo);
                        return;
                    }

                    try (FileWriter escreve = new FileWriter(arquivo, true)) {
                        escreve.write(conteudo + System.lineSeparator());
                    } catch (IOException e) {
                        System.out.println("❌ Erro ao registrar registro: " + e.getMessage());
                    }
                }

                public static void novoRegistro() {
                    config.nextregistro();
                }

                public static void deletarTodosRegistros() {
                    config.resetConfigToInitial();
                }

                public static void mostrarRegistro(String caminhoregistro) {
                    String caminho = caminhoregistro
                            .replace("\uFEFF", "")
                            .trim()
                            .replaceAll("^\"+|\"+$", "")
                            .replace("\\", "/");

                    if (!caminho.startsWith(MOV_DIR + "/") && !caminho.contains("/")) {
                        caminho = MOV_DIR + "/" + caminho;
                    }

                    System.out.println("📁 Caminho final para leitura: " + caminho);

                    try (BufferedReader leituraBuffer = new BufferedReader(new FileReader(caminho))) {
                        String linha;
                        int contador = 1;
                        System.out.println("----- Início do registro -----");

                        while ((linha = leituraBuffer.readLine()) != null) {
                            System.out.println(contador++ + "️⃣  " + linha);
                        }

                        System.out.println("------ 🏁 Fim do registro ------");
                        System.out.println("✅ registro lido com sucesso: " + caminho);
                    } catch (IOException e) {
                        System.out.println("❌ Erro ao ler registro: " + e.getMessage());
                        System.out.println("⚠️ Dica: verifique se o arquivo existe em relação ao diretório do projeto.");
                    }
                }

                public static int getIndice() {
                    return config.getCurrentIndex();
                }
            }