package br.ufjf.dcc.Tools;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class Tools {
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
    public static String classeObjeto(Object obj){
        if(obj == null){
            return "null";
        }
        return obj.getClass().getSimpleName();
    }

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
        if (valor == null || valor.trim().isEmpty()) return null;
        String s = valor.trim();
        if (s.contains(",") && s.contains(".")) {
            s = s.replace(".", "").replace(",", ".");
        } else if (s.contains(",")) {
            s = s.replace(",", ".");
        }
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long parseLongNullable(String valor) {
        if (valor == null || valor.trim().isEmpty()) return null;
        try {
            return Long.parseLong(valor.trim());
        } catch (NumberFormatException e) {
            // tentar com pontos/virgulas removidos
            String s = valor.trim().replace(".", "").replace(",", "");
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException ex) {
                return null;
            }
        }
    }
}
