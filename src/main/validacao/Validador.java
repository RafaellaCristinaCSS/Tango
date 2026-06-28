package validacao;

import model.Restricao;
import model.Tabuleiro;
import model.TipoRestricao;

/**
 * Centraliza TODA a lógica de validação das regras do jogo Tango.
 * <p>
 * Responsabilidade: verificar adjacência, equilíbrio de símbolos e restrições
 * de igualdade/oposição, tanto para tabuleiros completos quanto parciais
 * (suporte à poda do backtracking).
 * </p>
 * <p>
 * Relacionamento: recebe {@link Tabuleiro} e não depende dos algoritmos de busca.
 * </p>
 */
public class Validador {

    private final Tabuleiro tabuleiro;

    /**
     * Constrói um validador associado a um tabuleiro.
     *
     * @param tabuleiro  tabuleiro a ser validado
     */
    public Validador(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    /**
     * Verifica se o tabuleiro completo satisfaz todas as regras do jogo.
     * Utilizado pela Força Bruta após preenchimento total.
     *
     * @return true se todas as regras forem atendidas
     */
    public boolean tabuleiroValido() {
        return verificarTabuleiroCompleto()
                && verificarAdjacenciaHorizontal()
                && verificarAdjacenciaVertical()
                && verificarEquilibrioLinha()
                && verificarEquilibrioColuna()
                && verificarRestricoesIgualdade()
                && verificarRestricoesOposicao();
    }

    /**
     * Regra 1: todas as células devem estar preenchidas.
     */
    public boolean verificarTabuleiroCompleto() {
        return tabuleiro.isCompleto();
    }

    /**
     * Regra 2 (horizontal): nenhuma linha pode conter três símbolos iguais consecutivos.
     * Percorre cada linha verificando triplas adjacentes.
     * Complexidade: O(n * m), onde n=linhas e m=colunas.
     */
    public boolean verificarAdjacenciaHorizontal() {
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas() - 2; j++) {
                int a = tabuleiro.getValor(i, j);
                int b = tabuleiro.getValor(i, j + 1);
                int c = tabuleiro.getValor(i, j + 2);
                if (a != Tabuleiro.VAZIO && a == b && b == c) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Regra 2 (vertical): nenhuma coluna pode conter três símbolos iguais consecutivos.
     * Complexidade: O(n * m).
     */
    public boolean verificarAdjacenciaVertical() {
        for (int j = 0; j < tabuleiro.getColunas(); j++) {
            for (int i = 0; i < tabuleiro.getLinhas() - 2; i++) {
                int a = tabuleiro.getValor(i, j);
                int b = tabuleiro.getValor(i + 1, j);
                int c = tabuleiro.getValor(i + 2, j);
                if (a != Tabuleiro.VAZIO && a == b && b == c) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Regra 3 (linhas): cada linha completa deve ter exatamente metade Sóis e metade Luas.
     * Linhas parcialmente preenchidas são ignoradas nesta verificação completa.
     */
    public boolean verificarEquilibrioLinha() {
        int metade = tabuleiro.getMetade();
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            int sol = 0;
            int lua = 0;
            int vazios = 0;
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                int valor = tabuleiro.getValor(i, j);
                if (valor == Tabuleiro.VAZIO) {
                    vazios++;
                } else if (valor == Tabuleiro.SOL) {
                    sol++;
                } else {
                    lua++;
                }
            }
            if (vazios == 0 && (sol != metade || lua != metade)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Regra 3 (colunas): cada coluna completa deve ter exatamente metade Sóis e metade Luas.
     */
    public boolean verificarEquilibrioColuna() {
        int metade = tabuleiro.getMetade();
        for (int j = 0; j < tabuleiro.getColunas(); j++) {
            int sol = 0;
            int lua = 0;
            int vazios = 0;
            for (int i = 0; i < tabuleiro.getLinhas(); i++) {
                int valor = tabuleiro.getValor(i, j);
                if (valor == Tabuleiro.VAZIO) {
                    vazios++;
                } else if (valor == Tabuleiro.SOL) {
                    sol++;
                } else {
                    lua++;
                }
            }
            if (vazios == 0 && (sol != metade || lua != metade)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Regra 4: restrições de igualdade ("=") devem ter o mesmo símbolo em ambas as posições.
     * Posições ainda vazias são ignoradas.
     */
    public boolean verificarRestricoesIgualdade() {
        for (Restricao restricao : tabuleiro.getRestricoes()) {
            if (restricao.getTipo() != TipoRestricao.IGUAL) {
                continue;
            }
            if (!restricaoSatisfeita(restricao)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Regra 5: restrições de oposição ("×") devem ter símbolos diferentes.
     * Posições ainda vazias são ignoradas.
     */
    public boolean verificarRestricoesOposicao() {
        for (Restricao restricao : tabuleiro.getRestricoes()) {
            if (restricao.getTipo() != TipoRestricao.OPOSICAO) {
                continue;
            }
            if (!restricaoSatisfeita(restricao)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validação parcial após atribuir valor em (linha, coluna) durante backtracking.
     * Aplica podas imediatas em adjacência, equilíbrio e restrições locais.
     *
     * @param linha   linha da última atribuição
     * @param coluna  coluna da última atribuição
     * @return true se o estado parcial ainda é viável
     */
    public boolean validarParcial(int linha, int coluna) {
        return verificarAdjacenciaHorizontalNaLinha(linha, coluna)
                && verificarAdjacenciaVerticalNaColuna(linha, coluna)
                && verificarEquilibrioParcialLinha(linha)
                && verificarEquilibrioParcialColuna(coluna)
                && verificarRestricoesEnvolvendo(linha, coluna);
    }

    /**
     * Poda por adjacência horizontal: verifica triplas que incluem a coluna informada.
     */
    public boolean verificarAdjacenciaHorizontalNaLinha(int linha, int coluna) {
        int inicio = Math.max(0, coluna - 2);
        int fim = Math.min(tabuleiro.getColunas() - 3, coluna);
        for (int j = inicio; j <= fim; j++) {
            int a = tabuleiro.getValor(linha, j);
            int b = tabuleiro.getValor(linha, j + 1);
            int c = tabuleiro.getValor(linha, j + 2);
            if (a != Tabuleiro.VAZIO && a == b && b == c) {
                return false;
            }
        }
        return true;
    }

    /**
     * Poda por adjacência vertical: verifica triplas que incluem a linha informada.
     */
    public boolean verificarAdjacenciaVerticalNaColuna(int linha, int coluna) {
        int inicio = Math.max(0, linha - 2);
        int fim = Math.min(tabuleiro.getLinhas() - 3, linha);
        for (int i = inicio; i <= fim; i++) {
            int a = tabuleiro.getValor(i, coluna);
            int b = tabuleiro.getValor(i + 1, coluna);
            int c = tabuleiro.getValor(i + 2, coluna);
            if (a != Tabuleiro.VAZIO && a == b && b == c) {
                return false;
            }
        }
        return true;
    }

    /**
     * Poda por equilíbrio parcial da linha: não pode exceder metade de Sóis ou Luas.
     */
    public boolean verificarEquilibrioParcialLinha(int linha) {
        int metade = tabuleiro.getMetade();
        int sol = 0;
        int lua = 0;
        for (int j = 0; j < tabuleiro.getColunas(); j++) {
            int valor = tabuleiro.getValor(linha, j);
            if (valor == Tabuleiro.SOL) {
                sol++;
            } else if (valor == Tabuleiro.LUA) {
                lua++;
            }
        }
        return sol <= metade && lua <= metade;
    }

    /**
     * Poda por equilíbrio parcial da coluna: não pode exceder metade de Sóis ou Luas.
     */
    public boolean verificarEquilibrioParcialColuna(int coluna) {
        int metade = tabuleiro.getMetade();
        int sol = 0;
        int lua = 0;
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            int valor = tabuleiro.getValor(i, coluna);
            if (valor == Tabuleiro.SOL) {
                sol++;
            } else if (valor == Tabuleiro.LUA) {
                lua++;
            }
        }
        return sol <= metade && lua <= metade;
    }

    /**
     * Poda por restrições "=" e "×" que envolvem a posição recém-preenchida.
     */
    public boolean verificarRestricoesEnvolvendo(int linha, int coluna) {
        for (Restricao restricao : tabuleiro.getRestricoes()) {
            if (restricao.envolve(linha, coluna) && !restricaoSatisfeita(restricao)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se uma restrição individual está satisfeita ou ainda inconclusiva.
     * Retorna false apenas quando ambas as células estão preenchidas e violam a regra.
     */
    private boolean restricaoSatisfeita(Restricao restricao) {
        int v1 = tabuleiro.getValor(
                restricao.getPosicao1().getLinha(),
                restricao.getPosicao1().getColuna());
        int v2 = tabuleiro.getValor(
                restricao.getPosicao2().getLinha(),
                restricao.getPosicao2().getColuna());

        if (v1 == Tabuleiro.VAZIO || v2 == Tabuleiro.VAZIO) {
            return true;
        }

        if (restricao.getTipo() == TipoRestricao.IGUAL) {
            return v1 == v2;
        }
        return v1 != v2;
    }
}
