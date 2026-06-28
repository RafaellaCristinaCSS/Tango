package io;

import model.Posicao;
import model.Restricao;
import model.Tabuleiro;
import model.TipoRestricao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsável por ler arquivos texto contendo tabuleiro inicial e restrições.
 * <p>
 * Formato do arquivo de entrada:
 * </p>
 * <pre>
 * # Comentários iniciam com #
 * N                          (tamanho NxN, N par)
 * linha0                     (N tokens: S, L ou .)
 * linha1
 * ...
 * linha(N-1)
 * ---                        (separador de restrições)
 * = linha1,col1 linha2,col2  (igualdade)
 * x linha1,col1 linha2,col2  (oposição)
 * </pre>
 * <p>
 * Exemplo de token de linha: "S . . L . ."
 * </p>
 */
public class LeitorArquivo {

    private LeitorArquivo() {
        // Classe utilitária - não instanciável
    }

    /**
     * Lê um arquivo e constrói o tabuleiro correspondente.
     *
     * @param caminho  caminho do arquivo texto
     * @return tabuleiro inicial carregado
     * @throws IOException se houver erro de leitura
     */
    public static Tabuleiro ler(String caminho) throws IOException {
        List<String> linhas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha.trim());
            }
        }

        return parsear(linhas);
    }

    /**
     * Interpreta as linhas lidas e monta o tabuleiro.
     */
    private static Tabuleiro parsear(List<String> linhas) {
        List<String> conteudo = new ArrayList<>();
        for (String linha : linhas) {
            if (linha.isEmpty() || linha.startsWith("#")) {
                continue;
            }
            conteudo.add(linha);
        }

        if (conteudo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio ou sem conteúdo válido.");
        }

        int tamanho = Integer.parseInt(conteudo.get(0));
        if (tamanho % 2 != 0) {
            throw new IllegalArgumentException("O tamanho do tabuleiro deve ser par.");
        }

        if (conteudo.size() < tamanho + 1) {
            throw new IllegalArgumentException("Número insuficiente de linhas do tabuleiro.");
        }

        Tabuleiro tabuleiro = new Tabuleiro(tamanho);

        for (int i = 0; i < tamanho; i++) {
            String[] tokens = conteudo.get(i + 1).split("\\s+");
            if (tokens.length != tamanho) {
                throw new IllegalArgumentException(
                        "Linha " + i + " deve conter exatamente " + tamanho + " símbolos.");
            }
            for (int j = 0; j < tamanho; j++) {
                tabuleiro.setValor(i, j, Tabuleiro.charParaValor(tokens[j].charAt(0)));
            }
        }

        int indiceRestricoes = tamanho + 1;
        while (indiceRestricoes < conteudo.size() && !conteudo.get(indiceRestricoes).equals("---")) {
            indiceRestricoes++;
        }

        if (indiceRestricoes < conteudo.size()) {
            indiceRestricoes++;
        }

        for (int i = indiceRestricoes; i < conteudo.size(); i++) {
            Restricao restricao = parsearRestricao(conteudo.get(i));
            tabuleiro.adicionarRestricao(restricao);
        }

        return tabuleiro;
    }

    /**
     * Interpreta uma linha de restrição no formato "= 0,0 0,1" ou "x 1,2 2,2".
     */
    private static Restricao parsearRestricao(String linha) {
        String[] partes = linha.split("\\s+");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato de restrição inválido: " + linha);
        }

        TipoRestricao tipo;
        char operador = partes[0].charAt(0);
        if (operador == '=') {
            tipo = TipoRestricao.IGUAL;
        } else if (operador == 'x' || operador == 'X' || operador == '×') {
            tipo = TipoRestricao.OPOSICAO;
        } else {
            throw new IllegalArgumentException("Operador de restrição inválido: " + partes[0]);
        }

        Posicao p1 = parsearPosicao(partes[1]);
        Posicao p2 = parsearPosicao(partes[2]);
        return new Restricao(p1, p2, tipo);
    }

    /**
     * Converte "linha,coluna" em {@link Posicao}.
     */
    private static Posicao parsearPosicao(String texto) {
        String[] coords = texto.split(",");
        if (coords.length != 2) {
            throw new IllegalArgumentException("Posição inválida: " + texto);
        }
        return new Posicao(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
    }
}
