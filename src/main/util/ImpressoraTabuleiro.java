package util;

import model.Tabuleiro;

/**
 * Responsável pela formatação e impressão de tabuleiros no console.
 * <p>
 * Separa a apresentação visual da lógica do jogo e dos algoritmos.
 * </p>
 */
public class ImpressoraTabuleiro {

    private ImpressoraTabuleiro() {
        // Classe utilitária - não instanciável
    }

    /**
     * Imprime o tabuleiro formatado com índices de linha e coluna.
     *
     * @param tabuleiro  tabuleiro a exibir
     */
    public static void imprimir(Tabuleiro tabuleiro) {
        System.out.println(formatar(tabuleiro));
    }

    /**
     * Converte o tabuleiro em String formatada para exibição ou relatório.
     */
    public static String formatar(Tabuleiro tabuleiro) {
        StringBuilder sb = new StringBuilder();

        sb.append("    ");
        for (int j = 0; j < tabuleiro.getColunas(); j++) {
            sb.append(j).append(" ");
        }
        sb.append(System.lineSeparator());

        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            sb.append(i).append(" | ");
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                sb.append(Tabuleiro.valorParaChar(tabuleiro.getValor(i, j))).append(" ");
            }
            sb.append(System.lineSeparator());
        }

        if (!tabuleiro.getRestricoes().isEmpty()) {
            sb.append("Restricoes:").append(System.lineSeparator());
            for (var restricao : tabuleiro.getRestricoes()) {
                sb.append("  ").append(restricao).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    /**
     * Imprime um separador visual com título.
     */
    public static void imprimirSecao(String titulo) {
        System.out.println("====================");
        System.out.println(titulo);
        System.out.println("====================");
    }
}
