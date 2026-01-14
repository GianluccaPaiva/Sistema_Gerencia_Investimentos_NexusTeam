
package br.ufjf.dcc.Main;
import br.ufjf.dcc.Erros.ErrosLeituraArq;
import br.ufjf.dcc.Menu.Menu;
import br.ufjf.dcc.Movimentacao.Movimentacao;
import br.ufjf.dcc.Registrar.Registrar;

public class Main {
    public static void main(String[] args) {
        //Menu.run();
        String cpfInvestidor = "123.456.789-00";
        System.out.println("--- Testando o Sistema de Registros ---");


        Movimentacao mov1 = new Movimentacao("COMPRA", "NuInvest", "PETR4", 50.0f, 35.50);
        Movimentacao mov2 = new Movimentacao("COMPRA", "Binance", "BTC", 0.005f, 250000.00);


        System.out.println("\nGravando registros...");
        try {
            Registrar.registrar(cpfInvestidor, mov1.toCSV());
            Registrar.registrar(cpfInvestidor, mov2.toCSV());
        }catch ( ErrosLeituraArq e){
            System.out.println("Erro ao gravar registros: " + e.getMessage());
        }
        System.out.println("\nLendo dados do arquivo e exibindo extrato:");
        Registrar.exibirRegistro(cpfInvestidor);
        try {
            Registrar.exibirRegistroPorTicker("123.456.789-00", "PETR4");
        } catch (ErrosLeituraArq e) {
            throw new RuntimeException(e);
        }


        System.out.println("Teste de investidor inexistente:");
        Registrar.exibirRegistro("000.000.000-00");
        Registrar.deletarRegistroInvestidor(cpfInvestidor);
    }
}