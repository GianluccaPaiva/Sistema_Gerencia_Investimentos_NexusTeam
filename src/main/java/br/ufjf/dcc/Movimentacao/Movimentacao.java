package br.ufjf.dcc.Movimentacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Movimentacao {
    private String id;
    private String tipoMovimentacao;
    private String instituicao;
    private String tickerAtivo;
    private float quantidade;
    private double precoExecucao;
    private LocalDateTime dataNegociacao;

    public Movimentacao(String tipoMovimentacao, String instituicao, String tickerAtivo, float quantidade, double precoExecucao) {
        this.id = UUID.randomUUID().toString();
        this.tipoMovimentacao = tipoMovimentacao;
        this.instituicao = instituicao;
        this.tickerAtivo = tickerAtivo;
        this.quantidade = quantidade;
        this.precoExecucao = precoExecucao;
        this.dataNegociacao = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getTipoMovimentacao() { return tipoMovimentacao; }
    public String getInstituicao() { return instituicao; }
    public String getTickerAtivo() { return tickerAtivo; }
    public float getQuantidade() { return quantidade; }
    public double getPrecoExecucao() { return precoExecucao; }

    public String getDataFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataNegociacao.format(formatter);
    }

    @Override
    public String toString() {
        return String.format("ID: %s | %s | %s | Qtd: %.2f | Preço: R$ %.2f | %s",
                id.substring(0, 8),
                tipoMovimentacao,
                tickerAtivo,
                quantidade,
                precoExecucao,
                getDataFormatada());
    }

    public String toCSV() {
        return String.format("%s;%s;%s;%s;%.2f;%.2f;%s",
                this.id != null ? this.id.substring(0, Math.min(this.id.length(), 8)) : "N/A",
                this.tipoMovimentacao,
                this.instituicao,
                this.tickerAtivo,
                this.quantidade,
                this.precoExecucao,
                this.getDataFormatada()
        );
    }
}