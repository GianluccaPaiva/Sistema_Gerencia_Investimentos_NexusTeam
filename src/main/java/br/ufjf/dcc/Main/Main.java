package br.ufjf.dcc.Main;

import br.ufjf.dcc.Ativos.Acoes;
import br.ufjf.dcc.Ativos.Ativos;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Ativos ativo = new Acoes("Petrobras", "PETR4", 28.50f, true);
        ativo.exibirAtivo();
    }
}
