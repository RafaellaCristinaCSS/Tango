package algoritmos;

import model.Posicao;
import model.Tabuleiro;
import validacao.Validador;

import java.util.List;

/**
 * Solucionador por Força Bruta (busca exaustiva).
 * <p>
 * Responsabilidade: gerar todas as combinações possíveis para células vazias
 * e validar o tabuleiro completo somente ao final de cada combinação.
 * </p>
 * <p>
 * Não utiliza podas - representa busca completamente exaustiva.
 * Complexidade: O(2^k * validação), onde k = número de células vazias.
 * </p>
 */
public class ForcaBrutaSolver {

    private final Tabuleiro tabuleiroOriginal;
    private final List<Posicao> posicoesVazias;
    private final ResultadoAlgoritmo resultado;
    private boolean pararNaPrimeiraSolucao;

    /**
     * Constrói o solucionador a partir do tabuleiro inicial.
     *
     * @param tabuleiro  tabuleiro com células fixas e vazias
     */
    public ForcaBrutaSolver(Tabuleiro tabuleiro) {
        this.tabuleiroOriginal = tabuleiro;
        this.posicoesVazias = tabuleiro.getPosicoesVazias();
        this.resultado = new ResultadoAlgoritmo();
        this.pararNaPrimeiraSolucao = true;
    }

    /**
     * Define se a busca para na primeira solução válida encontrada.
     */
    public void setPararNaPrimeiraSolucao(boolean parar) {
        this.pararNaPrimeiraSolucao = parar;
    }

    /**
     * Executa a busca exaustiva e retorna estatísticas e solução.
     *
     * @return resultado com métricas e tabuleiro resolvido (se existir)
     */
    public ResultadoAlgoritmo resolver() {
        long inicio = System.currentTimeMillis();

        Tabuleiro tabuleiroBusca = tabuleiroOriginal.copiar();
        gerarCombinacoes(tabuleiroBusca, 0);

        resultado.setTempoMs(System.currentTimeMillis() - inicio);
        return resultado;
    }

    /**
     * Gera recursivamente todas as combinações de Sol/Lua para posições vazias.
     * Só valida quando todas as células estão preenchidas (sem poda intermediária).
     *
     * @param tabuleiro     cópia de trabalho
     * @param indice        índice da próxima posição vazia a preencher
     */
    private void gerarCombinacoes(Tabuleiro tabuleiro, int indice) {
        // Caso base: todas as posições vazias foram preenchidas
        if (indice >= posicoesVazias.size()) {
            resultado.incrementarEstados();
            Validador validador = new Validador(tabuleiro);

            // Validação completa apenas no estado final (característica da força bruta)
            if (validador.tabuleiroValido()) {
                resultado.incrementarSolucoes();
                if (resultado.getSolucao() == null) {
                    resultado.setSolucao(tabuleiro.copiar());
                }
            }
            return;
        }

        Posicao posicao = posicoesVazias.get(indice);

        // Tenta Sol (1)
        tabuleiro.setValor(posicao.getLinha(), posicao.getColuna(), Tabuleiro.SOL);
        gerarCombinacoes(tabuleiro, indice + 1);
        if (pararNaPrimeiraSolucao && resultado.getSolucao() != null) {
            return;
        }

        // Tenta Lua (0)
        tabuleiro.setValor(posicao.getLinha(), posicao.getColuna(), Tabuleiro.LUA);
        gerarCombinacoes(tabuleiro, indice + 1);

        // Desfaz atribuição (backtrack estrutural, mas sem poda lógica)
        tabuleiro.setValor(posicao.getLinha(), posicao.getColuna(), Tabuleiro.VAZIO);
    }
}
