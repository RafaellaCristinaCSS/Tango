package algoritmos;

import model.Tabuleiro;

/**
 * Armazena estatísticas e resultado de uma execução de algoritmo de busca.
 * <p>
 * Responsabilidade: encapsular métricas de desempenho e a solução encontrada,
 * permitindo comparação entre Força Bruta e Backtracking.
 * </p>
 */
public class ResultadoAlgoritmo {

    /** Tempo de execução em milissegundos. */
    private long tempoMs;

    /** Quantidade de estados/atribuições explorados. */
    private long estadosVisitados;

    /** Quantidade de chamadas recursivas (Backtracking). */
    private long chamadasRecursivas;

    /** Quantidade de soluções válidas encontradas. */
    private long solucoesEncontradas;

    /** Quantidade de podas realizadas (Backtracking). */
    private long podas;

    /** Tabuleiro solução (null se nenhuma solução foi encontrada). */
    private Tabuleiro solucao;

    public long getTempoMs() {
        return tempoMs;
    }

    public void setTempoMs(long tempoMs) {
        this.tempoMs = tempoMs;
    }

    public long getEstadosVisitados() {
        return estadosVisitados;
    }

    public void setEstadosVisitados(long estadosVisitados) {
        this.estadosVisitados = estadosVisitados;
    }

    public long getChamadasRecursivas() {
        return chamadasRecursivas;
    }

    public void setChamadasRecursivas(long chamadasRecursivas) {
        this.chamadasRecursivas = chamadasRecursivas;
    }

    public long getSolucoesEncontradas() {
        return solucoesEncontradas;
    }

    public void setSolucoesEncontradas(long solucoesEncontradas) {
        this.solucoesEncontradas = solucoesEncontradas;
    }

    public long getPodas() {
        return podas;
    }

    public void setPodas(long podas) {
        this.podas = podas;
    }

    public Tabuleiro getSolucao() {
        return solucao;
    }

    public void setSolucao(Tabuleiro solucao) {
        this.solucao = solucao;
    }

    /**
     * Incrementa contadores de forma thread-safe para uso em busca.
     */
    public void incrementarEstados() {
        estadosVisitados++;
    }

    public void incrementarChamadasRecursivas() {
        chamadasRecursivas++;
    }

    public void incrementarPodas() {
        podas++;
    }

    public void incrementarSolucoes() {
        solucoesEncontradas++;
    }
}
