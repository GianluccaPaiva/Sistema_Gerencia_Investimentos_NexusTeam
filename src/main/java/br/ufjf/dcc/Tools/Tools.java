package br.ufjf.dcc.Tools;

import br.ufjf.dcc.CoresMensagens.CoresMensagens;
import br.ufjf.dcc.Erros.ErrosNumbersFormato;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools implements CoresMensagens {
    private static String normalizarChave(String s) {
        if (s == null) return "";
        String t = s.replace("\uFEFF", "").trim().toLowerCase();
        t = Normalizer.normalize(t, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        // remove caracteres não alfanuméricos para comparações mais robustas
        t = t.replaceAll("[^a-z0-9]", "");
        return t;
    }

    private static String normalizarValor(String v) {
        if (v == null) return "";
        String t = v.replace("\uFEFF", "").trim();
        if (t.startsWith("\"") && t.endsWith("\"") && t.length() > 1) {
            t = t.substring(1, t.length() - 1);
        }
        t = Normalizer.normalize(t, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return t.toLowerCase();
    }

    private static <T extends Number> T lerNumeroGeneric(String entrada, Function<String, T> parser, String mensagemErro) {
        Scanner sc = new Scanner(System.in);
        String s = entrada;
        while (true) {
            try {
                return parser.apply(s.trim());
            } catch (Exception e) {
                System.out.print(AMARELO + mensagemErro + RESET);
                if (sc.hasNextLine()) {
                    s = sc.nextLine();
                } else {
                    throw new IllegalStateException("Nenhuma entrada disponível");
                }
            }
        }
    }

    private static <T extends Number> T parseNumberNullableTemplate(String valor, java.util.function.Function<String, T> parser, java.util.function.Function<String, String> normalizer) {
        if (valor == null || valor.trim().isEmpty()) return null;
        String s = normalizer.apply(valor.trim());
        try {
            return parser.apply(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static String capitalize(String str){
        if(str == null || str.isEmpty()){
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String validaNacionalidade(boolean ehNacional){
        return ehNacional ? "Nacional" : "Internacional";
    }

    public static boolean dataValida(String data) {
        if (data == null || !data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        String[] partes = data.split("/");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int ano = Integer.parseInt(partes[2]);

        if (mes < 1 || mes > 12 || dia < 1 || ano < 0) {
            return false;
        }

        int[] diasPorMes = {31, (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0)) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return dia <= diasPorMes[mes - 1];
    }

    public static void espera(float segundos){
        try {
            Thread.sleep((long) (segundos * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("A espera foi interrompida: " + e.getMessage());
        }
    }

    public static Map<String, Integer> construirMapaCabecalho(String headerLine, String delimiter) {
        String[] headers = headerLine.split(delimiter);
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            map.put(normalizarChave(headers[i]), i);
        }
        return map;
    }

    public static String obterCampo(String[] dados, Map<String, Integer> headerMap, String... aliases) {
        for (String a : aliases) {
            Integer idx = headerMap.get(normalizarChave(a));
            if (idx != null && idx < dados.length) {
                String v = dados[idx].trim();
                if (!v.isEmpty() && !v.equals("-")) return normalizarValor(v);
            }
        }
        return "";
    }

    public static Float parseFloatNullable(String valor) {
        return parseNumberNullableTemplate(valor,
                Float::parseFloat,
                s -> {
                    if (s.contains(",") && s.contains(".")) {
                        return s.replace(".", "").replace(",", ".");
                    } else if (s.contains(",")) {
                        return s.replace(",", ".");
                    } else {
                        return s;
                    }
                });
    }

    public static Long parseLongNullable(String valor) {
        return parseNumberNullableTemplate(valor,
                Long::parseLong,
                s -> s.replace(".", "").replace(",", ""));
    }

    public static int extractIndex(String nome) {

        Pattern padrao= Pattern.compile("(_)(\\d+)(\\.)");
        Matcher compativel = padrao.matcher(nome);
        if (compativel.find()) {
            try {
                return Integer.parseInt(compativel.group(2));
            } catch (NumberFormatException e) {
                System.out.println("❌ Erro ao converter número do índice: " + nome);
            }
        }
        return 1;
    }

    public static int lerNumeroInteiro(String entrada) {
        Integer val = lerNumeroGeneric(entrada, str -> Integer.parseInt(str), "Entrada inválida. Digite um número inteiro: ");
        return val;
    }

    public static double lerNumeroDecimal(String entrada) {
        Double val = lerNumeroGeneric(entrada, str -> Double.parseDouble(str.replace(",", ".")), "Entrada inválida. Digite um valor numérico: ");
        return val;
    }

    public static String idLimpo(String id){
        return id.replaceAll("[^0-9]", "").trim();
    }
}
