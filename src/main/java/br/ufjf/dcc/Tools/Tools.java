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
}
