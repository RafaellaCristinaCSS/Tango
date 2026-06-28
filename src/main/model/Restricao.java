package model;

/**
 * Representa uma restrição lógica entre duas posições do tabuleiro.
 * <p>
 * Responsabilidade: armazenar o par de posições e o tipo de relação
 * (igualdade ou oposição), conforme as Regras 4 e 5 do jogo Tango.
 * </p>
 * <p>
 * Relacionamentos: utilizada por {@link Tabuleiro} e validada por {@code Validador}.
 * </p>
 */
public class Restricao {

    /** Primeira posição envolvida na restrição. */
    private final Posicao posicao1;

    /** Segunda posição envolvida na restrição. */
    private final Posicao posicao2;

    /** Tipo da restrição: IGUAL (=) ou OPOSICAO (×). */
    private final TipoRestricao tipo;

    /**
     * Constrói uma restrição entre duas posições.
     *
     * @param posicao1  primeira posição
     * @param posicao2  segunda posição
     * @param tipo      tipo da restrição
     */
    public Restricao(Posicao posicao1, Posicao posicao2, TipoRestricao tipo) {
        this.posicao1 = posicao1;
        this.posicao2 = posicao2;
        this.tipo = tipo;
    }

    public Posicao getPosicao1() {
        return posicao1;
    }

    public Posicao getPosicao2() {
        return posicao2;
    }

    public TipoRestricao getTipo() {
        return tipo;
    }

    /**
     * Verifica se a restrição envolve a posição informada.
     *
     * @param linha   linha a verificar
     * @param coluna  coluna a verificar
     * @return true se a posição participa desta restrição
     */
    public boolean envolve(int linha, int coluna) {
        return posicao1.getLinha() == linha && posicao1.getColuna() == coluna
                || posicao2.getLinha() == linha && posicao2.getColuna() == coluna;
    }

    @Override
    public String toString() {
        String operador = tipo == TipoRestricao.IGUAL ? "=" : "x";
        return operador + " " + posicao1 + " " + posicao2;
    }
}
