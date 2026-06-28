package algoritmos;

import model.Posicao;
import model.Tabuleiro;
import validacao.Validador;

import java.util.List;

/**
 * Solucionador por Backtracking com poda inteligente.
 * <p>
 * Responsabilidade: preencher células vazias incrementalmente, validando
 * imediatamente as regras afetadas e retrocedendo quando uma violação ocorre.
 * </p>
 * <p>
 * Podas aplicadas após cada atribuição:
 * <ul>
 *   <li>Adjacência horizontal (Regra 2)</li>
 *   <li>Adjacência vertical (Regra 2)</li>
 *   <li>Quantidade máxima de Sóis por linha/coluna (Regra 3 parcial)</li>
 *   <li>Quantidade máxima de Luas por linha/coluna (Regra 3 parcial)</li>
 *   <li>Restrições de igualdade "=" (Regra 4)</li>
 *   <li>Restrições de oposição "×" (Regra 5)</li>
 * </ul>
 * </p>
 */
public class BacktrackingSolver {

    private final Tabuleiro tabuleiroOriginal;
    private final List<Posicao> posicoesVazias;
    private final ResultadoAlgoritmo resultado;
    private boolean pararNaPrimeiraSolucao;

    /**
     * Constrói o solucionador a partir do tabuleiro inicial.
     */
    public BacktrackingSolver(Tabuleiro tabuleiro) {
        this.tabuleiroOriginal = tabuleiro;
        this.posicoesVazias = tabuleiro.getPosicoesVazias();
        this.resultado = new ResultadoAlgoritmo();
        this.pararNaPrimeiraSolucao = true;
    }

    public void setPararNaPrimeiraSolucao(boolean parar) {
        this.pararNaPrimeiraSolucao = parar;
    }

    /**
     * Executa backtracking com poda e retorna estatísticas e solução.
     */
    public ResultadoAlgoritmo resolver() {
        long inicio = System.currentTimeMillis();

        Tabuleiro tabuleiroBusca = tabuleiroOriginal.copiar();
        Validador validadorBusca = new Validador(tabuleiroBusca);
        backtrack(tabuleiroBusca, validadorBusca, 0);

        resultado.setTempoMs(System.currentTimeMillis() - inicio);
        return resultado;
    }

    /**
     * Função recursiva de backtracking.
     *
     * @param tabuleiro   estado atual
     * @param validador   validador associado ao tabuleiro atual
     * @param indice      índice da próxima posição vazia
     * @return true se uma solução foi encontrada (quando pararNaPrimeiraSolucao)
     */
    private boolean backtrack(Tabuleiro tabuleiro, Validador validador, int indice) {
        resultado.incrementarChamadasRecursivas();

        // Caso base: não há mais células vazias - tabuleiro completo e válido
        if (indice >= posicoesVazias.size()) {
            resultado.incrementarEstados();
            if (validador.tabuleiroValido()) {
                resultado.incrementarSolucoes();
                if (resultado.getSolucao() == null) {
                    resultado.setSolucao(tabuleiro.copiar());
                }
                return pararNaPrimeiraSolucao;
            }
            return false;
        }

        Posicao posicao = posicoesVazias.get(indice);
        int linha = posicao.getLinha();
        int coluna = posicao.getColuna();

        // Domínio: apenas Sol e Lua
        int[] valores = {Tabuleiro.SOL, Tabuleiro.LUA};

        for (int valor : valores) {
            tabuleiro.setValor(linha, coluna, valor);
            resultado.incrementarEstados();

            // Poda imediata: valida regras afetadas pela atribuição
            if (validador.validarParcial(linha, coluna)) {
                if (backtrack(tabuleiro, validador, indice + 1)) {
                    return true;
                }
            } else {
                // Contabiliza poda quando atribuição viola regra parcial
                resultado.incrementarPodas();
            }

            // Desfaz jogada (backtrack)
            tabuleiro.setValor(linha, coluna, Tabuleiro.VAZIO);
        }

        return false;
    }
}
