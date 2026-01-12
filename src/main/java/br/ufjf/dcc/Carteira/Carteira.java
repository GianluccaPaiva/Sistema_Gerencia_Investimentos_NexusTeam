package br.ufjf.dcc.Carteira;
import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.Ativos.Interfaces.Internacional;
import br.ufjf.dcc.Ativos.Interfaces.RendaVariavel;
import br.ufjf.dcc.Erros.DadosInvalidosException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carteira {
    private List<ItemCarteira> ativos;

    public Carteira() {
        this.ativos = new ArrayList<>();
    }

    public void addAtivo(Ativos ativo, float qtd, double precoPagoNaCompra) throws DadosInvalidosException {
        for (ItemCarteira item : ativos) {
            if (item.getAtivo().getTicker().equals(ativo.getTicker())) {
                item.adicionarCompra(qtd, precoPagoNaCompra);
                return;
            }
        }
        this.ativos.add(new ItemCarteira(ativo, qtd, precoPagoNaCompra));
    }

    public void removerAtivo(String ticker, float qtdVendida) throws DadosInvalidosException {
        ItemCarteira itemParaRemover = null;
        boolean encontrado = false;

        for (ItemCarteira item : ativos) {
            if (item.getAtivo().getTicker().equals(ticker)) {
                item.removerVenda(qtdVendida);
                encontrado = true;
                if (item.getQtd() == 0) {
                    itemParaRemover = item;
                }
                break;
            }
        }
        if (!encontrado) {
            throw new DadosInvalidosException("Erro: Ativo com ticker " + ticker + " não encontrado na carteira.");
        }
        if (itemParaRemover != null) {
            ativos.remove(itemParaRemover);
        }
    }

    public double valorTotalCarteira() {
        double total = 0;
        for (ItemCarteira item : ativos) {
            total += item.getValorAtualTotal();
        }
        return total;
    }

    public double[] porcentagemRendaFixaVariavel() {
        double totalRendaFixa = 0;
        double totalRendaVariavel = 0;

        for (ItemCarteira item : ativos) {
            if (item.getAtivo() instanceof RendaVariavel) {
                totalRendaVariavel += item.getValorAtualTotal();
            } else {
                totalRendaFixa += item.getValorAtualTotal();
            }
        }

        double totalGeral = totalRendaFixa + totalRendaVariavel;
        double percRendaFixa = (totalGeral == 0) ? 0 : (totalRendaFixa / totalGeral) * 100;
        double percRendaVariavel = (totalGeral == 0) ? 0 : (totalRendaVariavel / totalGeral) * 100;

        return new double[]{percRendaFixa, percRendaVariavel};
    }

    public double[] porcentagemNacionalInternacional() {
        double totalNacional = 0;
        double totalInternacional = 0;

        for (ItemCarteira item : ativos) {
            if (item.getAtivo() instanceof Internacional) {
                totalInternacional += item.getValorAtualTotal();
            } else {
                totalNacional += item.getValorAtualTotal();
            }
        }

        double totalGeral = totalNacional + totalInternacional;
        double percNacional = (totalGeral == 0) ? 0 : (totalNacional / totalGeral) * 100;
        double percInternacional = (totalGeral == 0) ? 0 : (totalInternacional / totalGeral) * 100;

        return new double[]{percNacional, percInternacional};
    }

    public void exibirCarteira(){
        System.out.println("----- Carteira de Investimentos -----");
        for(ItemCarteira item : this.ativos){
            System.out.println("Ativo: " + item.getAtivo().getTicker());
            System.out.println("Quantidade: " + item.getQtd());
            System.out.printf("Valor Atual Total: R$ %.2f\n", item.getValorAtualTotal());
            System.out.printf("Valor Pago Total: R$ %.2f\n", item.getValorPagoTotal());
            System.out.printf("Lucro/Prejuízo: R$ %.2f\n", item.getLucroPrejuizo());
            System.out.println("-------------------------------------");
        }
        double[] rendaFixaVariavel = porcentagemRendaFixaVariavel();
        double[] nacionalInternacional = porcentagemNacionalInternacional();
        System.out.printf("Valor Total da Carteira: R$ %.2f\n", valorTotalCarteira());
        System.out.printf("Renda Fixa: %.1f%% | Renda Variável: %.1f%%\n", rendaFixaVariavel[0], rendaFixaVariavel[1]);
        System.out.printf("Nacional:   %.1f%% | Internacional:  %.1f%%\n", nacionalInternacional[0], nacionalInternacional[1]);
    }

    public List<ItemCarteira> getAtivos(){
        return Collections.unmodifiableList(this.ativos);
    }

}
