
package br.ufjf.dcc.Main;
import br.ufjf.dcc.Menu.Menu;
import br.ufjf.dcc.Registrar.Registrar;

public class Main {
    public static void main(String[] args) {
        Menu.run();
        Registrar.deletarTodosRegistros();
    }
}