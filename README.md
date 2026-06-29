# Solucionador do Quebra-Cabeça Tango

Implementação em Java de um solucionador do puzzle lógico **Tango**, utilizando dois algoritmos independentes: **Força Bruta** e **Backtracking**. O projeto demonstra a diferença entre busca exaustiva e busca com poda de restrições (CSP).

---

## Descrição do Problema

O Tango é um quebra-cabeça lógico em tabuleiro quadrado (tipicamente 6×6). Cada célula deve ser preenchida com um de dois símbolos:

| Símbolo | Significado | Valor interno |
|---------|-------------|---------------|
| S       | Sol         | 1             |
| L       | Lua         | 0             |
| .       | Vazio       | -1            |

### Regras do Jogo

1. **Completude** — Todas as células devem estar preenchidas.
2. **Adjacência** — Nunca podem existir três símbolos iguais consecutivos na horizontal ou vertical.
3. **Equilíbrio** — Cada linha e coluna deve possuir exatamente metade Sóis e metade Luas.
4. **Igualdade (=)** — Posições ligadas por "=" devem ter o mesmo símbolo.
5. **Oposição (×)** — Posições ligadas por "×" devem ter símbolos diferentes.

---

## Como Executar

### Pré-requisitos

- Java JDK 8 ou superior instalado

### Compilação

No diretório raiz do projeto (`tango/`):

```bash
mkdir out
javac -encoding UTF-8 -d out src/main/Main.java src/main/model/*.java src/main/io/*.java src/main/validacao/*.java src/main/util/*.java src/main/algoritmos/*.java
```

### Execução

```bash
java -cp out Main tabuleiro1.txt
```

Substitua `tabuleiro1.txt` por qualquer arquivo de entrada válido (`tabuleiro2.txt`, `tabuleiro3.txt`, etc.).

### IntelliJ IDEA / Eclipse / VS Code

1. Importe o diretório `src/main` como projeto Java.
2. Defina `Main.java` como classe principal.
3. Configure o argumento de linha de comando com o caminho do arquivo (ex.: `tabuleiro1.txt`).

---

## Estrutura do Projeto

```
tango/
├── src/main/
│   ├── Main.java                    # Ponto de entrada (console)
│   ├── model/
│   │   ├── Tabuleiro.java           # Estado do jogo
│   │   ├── Posicao.java             # Coordenada (linha, coluna)
│   │   ├── Restricao.java           # Restrição entre duas posições
│   │   └── TipoRestricao.java       # Enum: IGUAL, OPOSICAO
│   ├── io/
│   │   └── LeitorArquivo.java       # Leitura de arquivos de entrada
│   ├── validacao/
│   │   └── Validador.java           # Toda a lógica das regras do jogo
│   ├── algoritmos/
│   │   ├── ForcaBrutaSolver.java    # Busca exaustiva
│   │   ├── BacktrackingSolver.java  # Backtracking com poda
│   │   └── ResultadoAlgoritmo.java  # Estatísticas de execução
│   └── util/
│       └── ImpressoraTabuleiro.java # Formatação de saída
├── tabuleiro1.txt                   # Exemplo fácil
├── tabuleiro2.txt                   # Exemplo médio
├── tabuleiro3.txt                   # Exemplo desafiador
├── README.md
└── Relatorio.pdf                     # Relatório técnico completo
```

---

## Descrição dos Algoritmos

### Força Bruta

- Gera **todas** as combinações possíveis (2^k) para as k células vazias.
- Valida as regras **somente** quando o tabuleiro está completamente preenchido.
- **Não utiliza podas** — busca genuinamente exaustiva.
- Complexidade: O(2^k × n²).

### Backtracking

- Preenche uma célula por vez e valida **imediatamente** as regras afetadas.
- Se alguma regra for violada, desfaz a jogada e retrocede (poda).
- Podas aplicadas: adjacência, equilíbrio parcial, restrições "=" e "×".
- Complexidade: exponencial no pior caso, mas drasticamente reduzida na prática.

---

## Formato dos Arquivos de Entrada

```
# Comentários iniciam com #
N                          (tamanho N×N, N deve ser par)
linha0                     (N tokens: S, L ou .)
linha1
...
linha(N-1)
---                        (separador de restrições)
= linha1,col1 linha2,col2  (restrição de igualdade)
x linha1,col1 linha2,col2  (restrição de oposição)
```

### Exemplo

```
6
S L . L . .
L S L . L .
. L S L . .
L . L S L .
S L . L S .
. S L . L S
---
= 0,0 1,1
x 0,1 0,2
```

**Legenda dos tokens:**
- `S` — Sol (célula fixa)
- `L` — Lua (célula fixa)
- `.` — Célula vazia (a ser preenchida pelo solucionador)

**Legenda das restrições:**
- `=` — As duas posições devem ter o mesmo símbolo
- `x` — As duas posições devem ter símbolos diferentes
- Coordenadas no formato `linha,coluna` (base zero)

---

## Exemplos

Três arquivos de exemplo estão incluídos:

| Arquivo         | Descrição                          |
|-----------------|------------------------------------|
| tabuleiro1.txt  | Poucas células vazias (rápido)     |
| tabuleiro2.txt  | Células vazias moderadas           |
| tabuleiro3.txt  | Muitas restrições (desafiador)     |

---

## Explicação da Saída

O programa imprime quatro seções:

```
====================
TABULEIRO INICIAL
====================
(tabuleiro com índices)

====================
FORCA BRUTA
====================
Tempo de execucao: X ms
Estados explorados: N
Chamadas recursivas: N
Solucoes encontradas: N
(solucao ou mensagem de insucesso)

====================
BACKTRACKING
====================
Tempo de execucao: X ms
Estados explorados: N
Chamadas recursivas: N
Solucoes encontradas: N
Quantidade de podas: N
(solucao ou mensagem de insucesso)
```

A comparação entre as seções demonstra como o backtracking explora menos estados e executa mais rapidamente graças às podas.

---

## Documentação Adicional

Consulte `Relatorio.pdf` para o relatório técnico completo exigido pelo professor, incluindo modelagem, estratégias algorítmicas, exemplos de execução e análise de complexidade.
