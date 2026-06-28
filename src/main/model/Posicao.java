package model;

/**
 * Representa uma coordenada (linha, coluna) no tabuleiro.
 * <p>
 * Responsabilidade: encapsular uma posição bidimensional de forma imutável,
 * permitindo comparação e uso em restrições e algoritmos de busca.
 * </p>
 */
public class Posicao {

    /** Índice da linha (base zero). */
    private final int linha;

    /** Índice da coluna (base zero). */
    private final int coluna;

    /**
     * Constrói uma posição com linha e coluna informadas.
     *
     * @param linha   índice da linha
     * @param coluna  índice da coluna
     */
    public Posicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    /**
     * Verifica se esta posição é igual a outra (mesma linha e coluna).
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Posicao)) {
            return false;
        }
        Posicao outra = (Posicao) obj;
        return linha == outra.linha && coluna == outra.coluna;
    }

    @Override
    public int hashCode() {
        return 31 * linha + coluna;
    }

    @Override
    public String toString() {
        return "(" + linha + "," + coluna + ")";
    }
}
