package br.ufjf.dcc.Tools;

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
}
