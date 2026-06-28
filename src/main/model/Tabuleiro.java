package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa completamente o estado do quebra-cabeça Tango.
 * <p>
 * Responsabilidade: armazenar dimensões, matriz de valores e restrições;
 * fornecer operações de leitura, escrita, cópia e consulta de células vazias.
 * </p>
 * <p>
 * Representação interna dos símbolos:
 * <ul>
 *   <li>{@link #SOL} (1)  - Sol (S)</li>
 *   <li>{@link #LUA} (0)  - Lua (L)</li>
 *   <li>{@link #VAZIO} (-1) - célula não preenchida (.)</li>
 * </ul>
 * </p>
 */
public class Tabuleiro {

    /** Constante para o símbolo Sol (valor interno 1). */
    public static final int SOL = 1;

    /** Constante para o símbolo Lua (valor interno 0). */
    public static final int LUA = 0;

    /** Constante para célula vazia (valor interno -1). */
    public static final int VAZIO = -1;

    /** Quantidade de linhas do tabuleiro. */
    private final int linhas;

    /** Quantidade de colunas do tabuleiro. */
    private final int colunas;

    /** Matriz que armazena o estado de cada célula. */
    private final int[][] matriz;

    /** Lista de restrições "=" e "×" entre pares de posições. */
    private final List<Restricao> restricoes;

    /**
     * Constrói um tabuleiro quadrado com dimensão informada.
     *
     * @param tamanho  número de linhas e colunas (deve ser par)
     */
    public Tabuleiro(int tamanho) {
        this(tamanho, tamanho);
    }

    /**
     * Constrói um tabuleiro com dimensões informadas.
     *
     * @param linhas    quantidade de linhas
     * @param colunas   quantidade de colunas
     */
    public Tabuleiro(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.matriz = new int[linhas][colunas];
        this.restricoes = new ArrayList<>();

        // Inicializa todas as células como vazias
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = VAZIO;
            }
        }
    }

    /**
     * Construtor privado para cópia profunda.
     */
    private Tabuleiro(int linhas, int colunas, int[][] matriz, List<Restricao> restricoes) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.matriz = matriz;
        this.restricoes = restricoes;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public List<Restricao> getRestricoes() {
        return restricoes;
    }

    /**
     * Retorna o valor armazenado em uma posição.
     *
     * @param linha   índice da linha
     * @param coluna  índice da coluna
     * @return SOL, LUA ou VAZIO
     */
    public int getValor(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    /**
     * Altera o valor de uma célula.
     *
     * @param linha   índice da linha
     * @param coluna  índice da coluna
     * @param valor   SOL, LUA ou VAZIO
     */
    public void setValor(int linha, int coluna, int valor) {
        matriz[linha][coluna] = valor;
    }

    /**
     * Verifica se a célula está vazia.
     */
    public boolean isVazio(int linha, int coluna) {
        return matriz[linha][coluna] == VAZIO;
    }

    /**
     * Retorna a metade do tamanho de uma linha/coluna (quantidade exata de Sóis e Luas).
     */
    public int getMetade() {
        return colunas / 2;
    }

    /**
     * Adiciona uma restrição ao tabuleiro.
     */
    public void adicionarRestricao(Restricao restricao) {
        restricoes.add(restricao);
    }

    /**
     * Cria uma cópia profunda independente deste tabuleiro.
     * Necessária para que algoritmos não alterem o estado original.
     */
    public Tabuleiro copiar() {
        int[][] copiaMatriz = new int[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            System.arraycopy(matriz[i], 0, copiaMatriz[i], 0, colunas);
        }
        return new Tabuleiro(linhas, colunas, copiaMatriz, new ArrayList<>(restricoes));
    }

    /**
     * Retorna lista de posições vazias, em ordem linha-major.
     */
    public List<Posicao> getPosicoesVazias() {
        List<Posicao> vazias = new ArrayList<>();
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (isVazio(i, j)) {
                    vazias.add(new Posicao(i, j));
                }
            }
        }
        return vazias;
    }

    /**
     * Verifica se todas as células estão preenchidas.
     */
    public boolean isCompleto() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (isVazio(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Converte valor interno para caractere de exibição.
     */
    public static char valorParaChar(int valor) {
        if (valor == SOL) {
            return 'S';
        }
        if (valor == LUA) {
            return 'L';
        }
        return '.';
    }

    /**
     * Converte caractere de entrada para valor interno.
     */
    public static int charParaValor(char c) {
        if (c == 'S' || c == 's' || c == '1') {
            return SOL;
        }
        if (c == 'L' || c == 'l' || c == '0') {
            return LUA;
        }
        if (c == '.' || c == '-' || c == '_') {
            return VAZIO;
        }
        throw new IllegalArgumentException("Símbolo inválido: " + c);
    }
}
