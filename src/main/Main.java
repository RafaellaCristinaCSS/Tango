import algoritmos.BacktrackingSolver;
import algoritmos.ForcaBrutaSolver;
import algoritmos.ResultadoAlgoritmo;
import io.LeitorArquivo;
import model.Tabuleiro;
import util.ImpressoraTabuleiro;

/**
 * Ponto de entrada do Solucionador do Quebra-Cabeça Tango.
 * <p>
 * Responsabilidade: ler arquivo de entrada, exibir tabuleiro inicial,
 * executar Força Bruta e Backtracking, e apresentar resultados comparativos.
 * </p>
 * <p>
 * Uso: java Main &lt;caminho-do-arquivo&gt;
 * Exemplo: java Main tabuleiro1.txt
 * </p>
 */
public class Main {

    /**
     * Método principal - opera exclusivamente via console.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: java Main <arquivo-tabuleiro.txt>");
            System.err.println("Exemplo: java Main tabuleiro1.txt");
            System.exit(1);
        }

        String caminhoArquivo = args[0];

        try {
            Tabuleiro tabuleiroInicial = LeitorArquivo.ler(caminhoArquivo);

            ImpressoraTabuleiro.imprimirSecao("TABULEIRO INICIAL");
            ImpressoraTabuleiro.imprimir(tabuleiroInicial);
            System.out.println();

            executarForcaBruta(tabuleiroInicial);
            System.out.println();

            executarBacktracking(tabuleiroInicial);

        } catch (Exception e) {
            System.err.println("Erro ao processar arquivo: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Executa e exibe resultados da Força Bruta.
     */
    private static void executarForcaBruta(Tabuleiro tabuleiroInicial) {
        ImpressoraTabuleiro.imprimirSecao("FORCA BRUTA");

        ForcaBrutaSolver solver = new ForcaBrutaSolver(tabuleiroInicial);
        ResultadoAlgoritmo resultado = solver.resolver();

        imprimirEstatisticas(resultado, false);
        imprimirSolucao(resultado);
    }

    /**
     * Executa e exibe resultados do Backtracking.
     */
    private static void executarBacktracking(Tabuleiro tabuleiroInicial) {
        ImpressoraTabuleiro.imprimirSecao("BACKTRACKING");

        BacktrackingSolver solver = new BacktrackingSolver(tabuleiroInicial);
        ResultadoAlgoritmo resultado = solver.resolver();

        imprimirEstatisticas(resultado, true);
        imprimirSolucao(resultado);
    }

    /**
     * Imprime métricas de desempenho do algoritmo executado.
     */
    private static void imprimirEstatisticas(ResultadoAlgoritmo resultado, boolean incluirPodas) {
        System.out.println("Tempo de execucao: " + resultado.getTempoMs() + " ms");
        System.out.println("Estados explorados: " + resultado.getEstadosVisitados());
        System.out.println("Chamadas recursivas: " + resultado.getChamadasRecursivas());
        System.out.println("Solucoes encontradas: " + resultado.getSolucoesEncontradas());
        if (incluirPodas) {
            System.out.println("Quantidade de podas: " + resultado.getPodas());
        }
        System.out.println();
    }

    /**
     * Imprime o tabuleiro resolvido ou mensagem de insucesso.
     */
    private static void imprimirSolucao(ResultadoAlgoritmo resultado) {
        if (resultado.getSolucao() != null) {
            System.out.println("Solucao:");
            ImpressoraTabuleiro.imprimir(resultado.getSolucao());
        } else {
            System.out.println("Nenhuma solucao encontrada.");
        }
    }
}
