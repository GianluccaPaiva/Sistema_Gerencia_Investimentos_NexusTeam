package br.ufjf.dcc.Carteira;
import br.ufjf.dcc.Ativos.Ativos;
import br.ufjf.dcc.Ativos.Interfaces.Internacional;
import br.ufjf.dcc.Ativos.Interfaces.RendaVariavel;

import java.util.ArrayList;
import java.util.List;

public class Carteira {
    private List<ItemCarteira> ativos;

    public Carteira() {
        this.ativos = new ArrayList<>();
    }

    public void addAtivo(Ativos ativo, float qtd, double precoPagoNaCompra) {
        for (ItemCarteira item : ativos) {
            if (item.getAtivo().getTicker().equals(ativo.getTicker())) {
                item.adicionarCompra(qtd, precoPagoNaCompra);
                return;
            }
        }
        this.ativos.add(new ItemCarteira(ativo, qtd, precoPagoNaCompra));
    }

    public void removerAtivo(String ticker, float qtdVendida) {
        ItemCarteira itemParaRemover = null;

        for (ItemCarteira item : ativos) {
            if (item.getAtivo().getTicker().equals(ticker)) {
                item.removerVenda(qtdVendida);
                if (item.getQtd() == 0) {
                    itemParaRemover = item;
                }
                break;
            }
        }
        if (itemParaRemover != null) {
            ativos.remove(itemParaRemover);
        }
    }

    private float valorTotalCarteira() {
        float total = 0;
        for (ItemCarteira item : ativos) {
            total += item.getValorAtualTotal();
        }
        return total;
    }

    private float[] porcentagemRendaFixaVariavel() {
        float totalRendaFixa = 0;
        float totalRendaVariavel = 0;

        for (ItemCarteira item : ativos) {
            if (item.getAtivo() instanceof RendaVariavel) {
                totalRendaVariavel += item.getValorAtualTotal();
            } else {
                totalRendaFixa += item.getValorAtualTotal();
            }
        }

        float totalGeral = totalRendaFixa + totalRendaVariavel;
        float percRendaFixa = (totalGeral == 0) ? 0 : (totalRendaFixa / totalGeral) * 100;
        float percRendaVariavel = (totalGeral == 0) ? 0 : (totalRendaVariavel / totalGeral) * 100;

        return new float[]{percRendaFixa, percRendaVariavel};
    }

    private float[] porcentagemNacionalInternacional() {
        float totalNacional = 0;
        float totalInternacional = 0;

        for (ItemCarteira item : ativos) {
            if (item.getAtivo() instanceof Internacional) {
                totalInternacional += item.getValorAtualTotal();
            } else {
                totalNacional += item.getValorAtualTotal();
            }
        }

        float totalGeral = totalNacional + totalInternacional;
        float percNacional = (totalGeral == 0) ? 0 : (totalNacional / totalGeral) * 100;
        float percInternacional = (totalGeral == 0) ? 0 : (totalInternacional / totalGeral) * 100;

        return new float[]{percNacional, percInternacional};
    }

    public void exibirCarteira(){
        System.out.println("----- Carteira de Investimentos -----");
        for(ItemCarteira item : this.ativos){
            System.out.println("Ativo: " + item.getAtivo().getNome() + " | Ticker: " + item.getAtivo().getTicker());
            System.out.println("Quantidade: " + item.getQtd());
            System.out.printf("Valor Atual Total: R$ %.2f\n", item.getValorAtualTotal());
            System.out.printf("Valor Pago Total: R$ %.2f\n", item.getValorPagoTotal());
            System.out.printf("Lucro/Prejuízo: R$ %.2f\n", item.getLucroPrejuizo());
            System.out.println("-------------------------------------");
        }
        float[] rendaFixaVariavel = porcentagemRendaFixaVariavel();
        float[] nacionalInternacional = porcentagemNacionalInternacional();
        System.out.println("Valor Total da Carteira: R$ " + valorTotalCarteira());
        System.out.printf("Porcentagem Renda Fixa: %.2f%%\n", rendaFixaVariavel[0]);
        System.out.printf("Porcentagem Renda Variável: %.2f%%\n", rendaFixaVariavel[1]);
        System.out.printf("Porcentagem Nacional: %.2f%%\n", nacionalInternacional[0]);
        System.out.printf("Porcentagem Internacional: %.2f%%\n", nacionalInternacional[1]);
    }

}
