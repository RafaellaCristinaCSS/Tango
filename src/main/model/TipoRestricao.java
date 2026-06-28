package model;

/**
 * Enumeração dos tipos de restrição entre duas posições do tabuleiro Tango.
 * <p>
 * IGUAL    - operador "=" : ambas as posições devem possuir o mesmo símbolo.
 * OPOSICAO - operador "×" : ambas as posições devem possuir símbolos diferentes.
 * </p>
 */
public enum TipoRestricao {
    /** Restrição de igualdade (operador "="). */
    IGUAL,

    /** Restrição de oposição (operador "×"). */
    OPOSICAO
}
