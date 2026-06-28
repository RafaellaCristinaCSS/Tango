# Projeto Completo - Solucionador do Quebra-Cabeça Tango (Java)

Você é um Engenheiro de Software Sênior, especialista em Java, Algoritmos, Estruturas de Dados, Problemas de Satisfação de Restrições (CSP), Backtracking e Projeto de Software.

Seu objetivo é desenvolver **todo o projeto**, desde a arquitetura até a implementação completa, seguindo rigorosamente os requisitos descritos abaixo.

**Não simplifique nenhuma etapa.**

O resultado deve ser um projeto completo, organizado, comentado e pronto para ser executado em uma IDE como IntelliJ IDEA, Eclipse ou VS Code.

---

# Contexto

O projeto consiste na implementação de um solucionador do quebra-cabeça lógico **Tango**, utilizando dois algoritmos independentes:

* Força Bruta
* Backtracking

O objetivo é demonstrar a diferença entre uma busca exaustiva e uma busca com poda.

O projeto deve ser implementado obrigatoriamente em **Java**, sem utilização de bibliotecas externas de resolução de CSP.

Somente a biblioteca padrão do Java poderá ser utilizada.

---

# Requisitos obrigatórios do professor

O programa deve:

* Ler um tabuleiro inicial.
* Ler todas as restrições do problema.
* Exibir o tabuleiro inicial.
* Resolver utilizando Força Bruta.
* Resolver utilizando Backtracking.
* Exibir o tabuleiro resolvido.
* Manter uma arquitetura modular.
* Separar completamente as regras do jogo da lógica dos algoritmos.
* Operar apenas via console.
* Possuir código comentado.

---

# Regras do jogo

Cada célula possui apenas dois estados:

* Sol
* Lua

ou, internamente,

* 1
* 0

As regras são:

## Regra 1

Todas as células devem estar preenchidas.

---

## Regra 2

Nunca podem existir três símbolos iguais consecutivos na horizontal ou vertical.

Exemplos válidos:

SSL

LLS

SLS

Exemplos inválidos:

SSS

LLL

---

## Regra 3

Cada linha deve possuir exatamente metade Sol e metade Lua.

Se o tabuleiro for 6x6:

Cada linha deve possuir:

3 Sóis

3 Luas

O mesmo vale para as colunas.

---

## Regra 4

Quando existir "=" entre duas posições, ambas devem possuir o mesmo símbolo.

---

## Regra 5

Quando existir "×" entre duas posições, ambas devem possuir símbolos diferentes.

---

# Estrutura desejada do projeto

O projeto deve ser organizado em pacotes.

Exemplo:

src/

main/

Main.java

model/

Tabuleiro.java

Posicao.java

Restricao.java

TipoRestricao.java

io/

LeitorArquivo.java

algoritmos/

ForcaBrutaSolver.java

BacktrackingSolver.java

validacao/

Validador.java

util/

ImpressoraTabuleiro.java

Não é obrigatório seguir exatamente esta estrutura, mas ela deve possuir nível semelhante de organização.

---

# Estrutura das classes

Explique antes de implementar cada classe.

Explique:

* responsabilidade
* atributos
* métodos
* relacionamento com outras classes

Depois implemente.

---

# Classe Tabuleiro

Deve representar completamente o estado do jogo.

Ela deve armazenar:

* quantidade de linhas
* quantidade de colunas
* matriz do tabuleiro
* lista de restrições

Também deve possuir métodos para:

* obter valor
* alterar valor
* copiar tabuleiro
* verificar se posição está vazia
* imprimir tabuleiro

---

# Classe Restricao

Cada restrição deve armazenar:

posição 1

posição 2

tipo

Onde tipo pode ser:

IGUAL

OPOSICAO

---

# Entrada do programa

O programa deve ler arquivos texto.

Defina um formato simples, intuitivo e bem documentado.

Exemplo:

6

S . . L . .

. . L . . .

...

Depois outro bloco contendo as restrições.

Documente completamente esse formato.

Também gere arquivos de exemplo.

Exemplo:

tabuleiro1.txt

tabuleiro2.txt

tabuleiro3.txt

---

# Impressão

O programa deve imprimir:

====================

TABULEIRO INICIAL

====================

(tabuleiro)

Depois

====================

FORÇA BRUTA

====================

tempo

quantidade de estados explorados

solução

Depois

====================

BACKTRACKING

====================

tempo

quantidade de estados explorados

quantidade de podas

solução

---

# Validador

A classe Validador deve conter TODA a lógica do jogo.

Ela deve possuir métodos separados.

Exemplo:

verificarAdjacenciaHorizontal()

verificarAdjacenciaVertical()

verificarEquilibrioLinha()

verificarEquilibrioColuna()

verificarRestricoesIgualdade()

verificarRestricoesOposicao()

verificarTabuleiroCompleto()

tabuleiroValido()

Cada método deve possuir comentários explicando exatamente sua função.

---

# Implementação da Força Bruta

Implemente um algoritmo de busca completamente exaustivo.

Ele deve:

gerar todas as combinações possíveis para todas as posições vazias

somente quando o tabuleiro estiver completamente preenchido validar as regras

caso esteja correto retornar solução

caso contrário continuar buscando

Não utilize podas.

Essa implementação deve representar realmente uma força bruta.

---

# Implementação do Backtracking

Implemente outro algoritmo completamente independente.

Ele deve:

preencher uma posição

validar imediatamente as regras afetadas

caso alguma regra seja violada

desfazer a jogada

voltar

continuar

Utilize poda em:

adjacência

quantidade máxima de Sóis

quantidade máxima de Luas

restrições "="

restrições "×"

equilíbrio parcial das linhas

equilíbrio parcial das colunas

Explique detalhadamente cada poda utilizada.

---

# Estatísticas

Cada algoritmo deve informar:

tempo de execução

quantidade de estados visitados

quantidade de chamadas recursivas

quantidade de soluções encontradas

quantidade de podas (Backtracking)

---

# Código

Todo o código deve possuir comentários explicando:

o que faz

por que faz

complexidade quando relevante

Não gere código sem comentários.

---

# Qualidade

Utilize:

orientação a objetos

encapsulamento

responsabilidade única

baixo acoplamento

alta coesão

nomes claros

boas práticas Java

---

# Documentação

Além do código, gere também um arquivo README.md contendo:

Descrição do problema

Como executar

Estrutura do projeto

Descrição dos algoritmos

Formato dos arquivos de entrada

Exemplos

Explicação da saída

---

# Relatório

Também gere um relatório técnico em Markdown contendo exatamente os tópicos exigidos pelo professor.

## 1. Modelagem do Problema

Explique:

estrutura do tabuleiro

estrutura das restrições

representação dos símbolos

estrutura das classes

---

## 2. Estratégia da Força Bruta

Explique detalhadamente:

como o espaço de estados é gerado

como ocorre a validação

vantagens

desvantagens

complexidade

---

## 3. Estratégia do Backtracking

Explique:

função recursiva

caso base

passo recursivo

cada poda utilizada

vantagens

complexidade

---

## 4. Exemplos de execução

Inclua exemplos completos utilizando os arquivos de exemplo.

Mostre:

entrada

execução

saída

---

## 5. Complexidade

Faça uma análise teórica do espaço de busca.

Compare:

Força Bruta

Backtracking

Explique como as podas reduzem drasticamente o número de estados explorados.

---

# Importante

Não gere respostas resumidas.

Implemente absolutamente todo o projeto.

Sempre que houver muito código, divida a resposta em partes mantendo continuidade.

Não omita nenhuma classe.

Não utilize pseudocódigo.

Todo o código deve ser compilável.

Todos os arquivos devem estar completos.

No final, faça uma revisão geral verificando se todos os requisitos do professor foram atendidos.
