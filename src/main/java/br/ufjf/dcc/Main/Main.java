package br.ufjf.dcc.Main;

import br.ufjf.dcc.Ativos.Acoes;
import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.Ativos.Fiis;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Ativos ativo = new Acoes("Petrobras", "PETR4", 28.50f, true);
        ativo.exibirAtivo();
        System.out.println();
        Ativos ativo2 = new Fiis("XP Log", "XPLG11", 120.75f, true, "Logistica", 0.85f, 150.0f);
        ativo2.exibirAtivo();
    }
}
